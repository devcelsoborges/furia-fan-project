package com.furiafan.chat.service;

import com.furiafan.chat.model.FanProfile;
import com.furiafan.chat.model.Message;
import com.furiafan.chat.util.CpfValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private static final String TWITCH_USER_URL = "https://api.twitch.tv/helix/users?login=";
    private static final String TWITTER_USER_URL = "https://api.twitter.com/2/users/by/username/";

    private final List<Message> messages = new ArrayList<>();
    private final FanProfile fanProfile = new FanProfile();
    private final OpenAIService openAIService;
    private final WebClient webClient;

    private final String twitchClientId;
    private final String twitchAccessToken;
    private final String twitterBearerToken;

    private int questionStep = 0;
    private boolean perfilInterpretado = false;

    public ChatService(OpenAIService openAIService,
                       @Value("${twitch.client-id}") String twitchClientId,
                       @Value("${twitch.access-token}") String twitchAccessToken,
                       @Value("${twitter.bearer-token}") String twitterBearerToken) {

        this.openAIService = openAIService;
        this.twitchClientId = twitchClientId;
        this.twitchAccessToken = twitchAccessToken;
        this.twitterBearerToken = twitterBearerToken;
        this.webClient = WebClient.create();
    }

    public List<Message> getAllMessages() {
        if (messages.isEmpty()) {
            startProfileCollection();
        }
        return messages;
    }

    public void processMessage(Message userMessage) {
        messages.add(userMessage);

        if (questionStep > 0 && questionStep <= 6) {
            handleProfileCollection(userMessage.getContent());
        } else {
            openAIService.getChatResponse(userMessage.getContent())
                    .doOnError(error -> {
                        System.err.println("Erro OpenAI: " + error.getMessage());
                        messages.add(new Message("FuriaBot", "⚠️ A API da FURIA está ocupada no momento. Tente novamente mais tarde!"));
                    })
                    .onErrorResume(error -> Mono.empty())
                    .subscribe(response -> {
                        messages.add(new Message("FuriaBot", response.trim()));
                    });
        }
    }

    private void startProfileCollection() {
        messages.add(new Message("FuriaBot", "🔥 Olá, fã da FURIA E-Sports!"));
        messages.add(new Message("FuriaBot", "😎 Queremos conhecer você melhor para oferecer experiências incríveis!"));
        messages.add(new Message("FuriaBot", "📋 Primeiro, qual o seu nome completo?"));
        questionStep = 1;
    }

    private void handleProfileCollection(String userResponse) {
        switch (questionStep) {
            case 1 -> {
                fanProfile.setName(userResponse);
                messages.add(new Message("FuriaBot", "🔢 Agora, qual o seu CPF? (apenas números)"));
                questionStep++;
            }
            case 2 -> {
                if (!CpfValidator.isValidCPF(userResponse)) {
                    messages.add(new Message("FuriaBot", "❌ CPF inválido. Por favor, insira um CPF válido com 11 dígitos."));
                    return;
                }
                fanProfile.setCpf(userResponse);
                messages.add(new Message("FuriaBot", "🏠 Qual o seu endereço completo?"));
                questionStep++;
            }
            case 3 -> {
                fanProfile.setAddress(userResponse);
                messages.add(new Message("FuriaBot", "🎯 Quais são seus interesses sobre esports e FURIA? (Jogos, campeonatos, vídeos...)"));
                questionStep++;
            }
            case 4 -> {
                fanProfile.setInterests(userResponse);
                messages.add(new Message("FuriaBot", "🛒 Participou de eventos ou comprou produtos da FURIA no último ano?"));
                questionStep++;
            }
            case 5 -> {
                fanProfile.setEventsAndPurchases(userResponse);
                messages.add(new Message("FuriaBot", "🔗 Pode compartilhar links de suas redes sociais relacionadas a esports?"));
                questionStep++;
            }
            case 6 -> {
                if (validateSocialLink(userResponse)) {
                    fanProfile.setSocialLinks(userResponse);
                    enrichProfileWithSocialData(userResponse);
                    messages.add(new Message("FuriaBot", "🎉 Perfil completo! Agora podemos personalizar sua experiência na FURIA FanChat!"));
                    interpretFanProfile();
                    questionStep = 0;
                } else {
                    messages.add(new Message("FuriaBot", "⚠️ O link fornecido não parece ser de uma rede social válida. Por favor, envie um link correto."));
                }
            }
        }
    }

    private boolean validateSocialLink(String link) {
        if (link == null || link.isEmpty()) return false;

        String[] allowedDomains = {
                "twitter.com", "instagram.com", "facebook.com", "twitch.tv", "youtube.com", "tiktok.com"
        };

        for (String domain : allowedDomains) {
            if (link.contains(domain)) return true;
        }
        return false;
    }

    private void enrichProfileWithSocialData(String link) {
        if (link.contains("twitter.com")) {
            fetchTwitterProfile(extractUsernameFromLink(link));
        } else if (link.contains("twitch.tv")) {
            fetchTwitchProfile(extractUsernameFromLink(link));
        }
    }

    public FanProfile getFanProfile() {
        return fanProfile;
    }

    private String extractUsernameFromLink(String link) {
        try {
            String[] parts = link.split("/");
            return parts[parts.length - 1].split("\\?")[0];
        } catch (Exception e) {
            return "";
        }
    }

    private void fetchTwitterProfile(String username) {
        if (username.isEmpty()) return;

        String url = TWITTER_USER_URL + username;

        webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + twitterBearerToken)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("Erro ao buscar Twitter: " + error.getMessage()))
                .subscribe(response -> {
                    System.out.println("Twitter profile response: " + response);
                });
    }

    private void fetchTwitchProfile(String username) {
        if (username.isEmpty()) return;

        String url = TWITCH_USER_URL + username;

        webClient.get()
                .uri(url)
                .header("Client-ID", twitchClientId)
                .header("Authorization", "Bearer " + twitchAccessToken)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("Erro ao buscar Twitch: " + error.getMessage()))
                .subscribe(response -> {
                    System.out.println("Twitch profile response: " + response);
                });
    }

    private void interpretFanProfile() {
        if (perfilInterpretado) return;
        perfilInterpretado = true;

        String prompt = "Com base neste perfil de fã de esports, gere uma breve descrição de como personalizar sua experiência: \n" +
                "Nome: " + fanProfile.getName() + "\n" +
                "Interesses: " + fanProfile.getInterests() + "\n" +
                "Eventos/Compras: " + fanProfile.getEventsAndPurchases() + "\n" +
                "Redes Sociais: " + fanProfile.getSocialLinks() + "\n" +
                "Crie algo breve e engajante.";

        openAIService.getChatResponse(prompt)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .doOnError(error -> System.err.println("Erro ao interpretar perfil: " + error.getMessage()))
                .onErrorResume(error -> Mono.empty())
                .subscribe(response -> {
                    messages.add(new Message("FuriaBot", "🎯 Dica personalizada: " + response.trim()));
                });
    }
}
