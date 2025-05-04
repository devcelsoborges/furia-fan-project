
package com.furiafan.chat.api.draft5.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTournament {
    private int tournamentId;
    private String tournamentName;
    private String tournamentImage;


    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentImage() {
        return tournamentImage;
    }

    public void setTournamentImage(String tournamentImage) {
        this.tournamentImage = tournamentImage;
    }
}