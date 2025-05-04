package com.furiafan.chat.service;

import com.furiafan.chat.model.OpenAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<String> getChatResponse(String message) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", new Object[]{
                Map.of("role", "system", "content", "Você é um assistente amigável da FURIA e conhece tudo sobre Counter-Strike e esports."),
                Map.of("role", "user", "content", message)
        });

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .map(response -> {
                    if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                        return response.getChoices().get(0).getMessage().getContent();
                    }
                    return "🤖 Desculpe, não consegui entender sua mensagem.";
                })
                .doOnError(error -> System.err.println("Erro na OpenAI API: " + error.getMessage()))
                .onErrorResume(error -> Mono.just("⚠️ A API da FURIA está ocupada no momento. Tente novamente mais tarde."));
    }
}
