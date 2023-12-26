package com.NexusSportHub.NexusSportHub.dto;

import java.io.Serial;
import java.io.Serializable;

public class MatchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    Long matchId;
    String teamA;
    String teamB;


    public MatchDTO(Long matchId, String teamA, String teamB) {
        this.matchId= matchId;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamA) {
        this.teamB = teamB;
    }

}
