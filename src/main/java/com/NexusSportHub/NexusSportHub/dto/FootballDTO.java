package com.NexusSportHub.NexusSportHub.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class FootballDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    Long competicionId;
    String competicion;
    List<MatchDTO> match;

    public FootballDTO(Long competicionId, String competicion, List<MatchDTO> match) {
        this.competicionId = competicionId;
        this.competicion = competicion;
        this.match = match;
    }

    public Long getCompeticionId() {
        return competicionId;
    }

    public void setCompeticionId(Long competicionId) {
        this.competicionId = competicionId;
    }

    public String getCompeticion() {
        return competicion;
    }

    public void setCompeticion(String competicion) {
        this.competicion = competicion;
    }

    public List<MatchDTO> getMatch() {
        return match;
    }

    public void setMatch(List<MatchDTO> match) {
        this.match = match;
    }
}
