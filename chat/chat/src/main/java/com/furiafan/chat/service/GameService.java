package com.furiafan.chat.service;

import com.furiafan.chat.model.Match;
import com.furiafan.chat.api.draft5.response.ApiMatch;
import com.furiafan.chat.api.draft5.response.ApiMatchResponse;
import com.furiafan.chat.api.draft5.response.ApiTeam;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class GameService {

    private static final int FURIA_TEAM_ID = 330;
    private static final String API_BASE_URL = "https://api.draft5.gg/matches";
    private static final String STATIC_ASSETS_BASE_URL = "https://static.draft5.gg/";


    private final RestTemplate restTemplate;

    @Autowired
    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Match> getRecentMatches() {
        List<Match> allMatches = new ArrayList<>();


        String upcomingMatchesUrl = API_BASE_URL + "?page=1&amount=20&finished=0&featured=0&team=" + FURIA_TEAM_ID + "&showHidden=0";
        try {
            ApiMatchResponse upcomingResponse = restTemplate.getForObject(upcomingMatchesUrl, ApiMatchResponse.class);
            if (upcomingResponse != null && upcomingResponse.getData() != null && upcomingResponse.getData().getList() != null) {
                allMatches.addAll(mapApiMatchesToMatches(upcomingResponse.getData().getList()));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar próximas partidas da API: " + e.getMessage());

        }


        String resultsMatchesUrl = API_BASE_URL + "?page=1&amount=20&finished=1&featured=0&team=" + FURIA_TEAM_ID + "&showHidden=0";
        try {
            ApiMatchResponse resultsResponse = restTemplate.getForObject(resultsMatchesUrl, ApiMatchResponse.class);
            if (resultsResponse != null && resultsResponse.getData() != null && resultsResponse.getData().getList() != null) {
                allMatches.addAll(mapApiMatchesToMatches(resultsResponse.getData().getList()));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar resultados das partidas da API: " + e.getMessage());

        }


        allMatches.sort((m1, m2) -> {
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date1 = sdf.parse(m1.getDate());
                Date date2 = sdf.parse(m2.getDate());
                return date2.compareTo(date1);
            } catch (Exception e) {
                System.err.println("Erro ao ordenar partidas: " + e.getMessage());
                return 0;
            }
        });


        return allMatches;
    }


    private List<Match> mapApiMatchesToMatches(List<ApiMatch> apiMatches) {
        List<Match> matches = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (ApiMatch apiMatch : apiMatches) {
            Match match = new Match();


            ApiTeam teamA = apiMatch.getTeamA();
            ApiTeam teamB = apiMatch.getTeamB();

            if (teamA.getTeamId() == FURIA_TEAM_ID) {
                match.setOpponent(teamB.getTeamName());

                if (teamB.getTeamLogo() != null && !teamB.getTeamLogo().isEmpty()) {
                    match.setLogoUrl(STATIC_ASSETS_BASE_URL + teamB.getTeamLogo());
                } else {
                    match.setLogoUrl("");
                }
            } else if (teamB.getTeamId() == FURIA_TEAM_ID) {
                match.setOpponent(teamA.getTeamName());

                if (teamA.getTeamLogo() != null && !teamA.getTeamLogo().isEmpty()) {
                    match.setLogoUrl(STATIC_ASSETS_BASE_URL + teamA.getTeamLogo());
                } else {
                    match.setLogoUrl("");
                }
            } else {

                continue;
            }


            Date matchDate = new Date(apiMatch.getMatchDate() * 1000L);
            match.setDate(dateFormat.format(matchDate));



            if (apiMatch.getIsFinished() == 1) {
                match.setResult(apiMatch.getSeriesScoreA() + " - " + apiMatch.getSeriesScoreB());
            } else {
                match.setResult("");
            }


            if (apiMatch.getTournament() != null) {
                match.setTournament(apiMatch.getTournament().getTournamentName());
            } else {
                match.setTournament("");
            }


            matches.add(match);
        }

        return matches;
    }
}