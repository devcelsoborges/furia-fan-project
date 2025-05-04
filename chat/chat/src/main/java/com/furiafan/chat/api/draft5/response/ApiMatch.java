
package com.furiafan.chat.api.draft5.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMatch {
    private int matchId;
    private long matchDate;
    private ApiTeam teamA;
    private ApiTeam teamB;
    private ApiTournament tournament;
    private int isFinished;
    private int seriesScoreA;
    private int seriesScoreB;


    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public long getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(long matchDate) {
        this.matchDate = matchDate;
    }

    public ApiTeam getTeamA() {
        return teamA;
    }

    public void setTeamA(ApiTeam teamA) {
        this.teamA = teamA;
    }

    public ApiTeam getTeamB() {
        return teamB;
    }

    public void setTeamB(ApiTeam teamB) {
        this.teamB = teamB;
    }

    public ApiTournament getTournament() {
        return tournament;
    }

    public void setTournament(ApiTournament tournament) {
        this.tournament = tournament;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public int getSeriesScoreA() {
        return seriesScoreA;
    }

    public void setSeriesScoreA(int seriesScoreA) {
        this.seriesScoreA = seriesScoreA;
    }

    public int getSeriesScoreB() {
        return seriesScoreB;
    }

    public void setSeriesScoreB(int seriesScoreB) {
        this.seriesScoreB = seriesScoreB;
    }
}