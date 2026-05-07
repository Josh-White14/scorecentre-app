package com.scorecentre.footballData.DTOs;

import java.io.Serializable;

import com.scorecentre.models.Team;

public class MatchDTO implements Serializable {

    private TeamDTO homeTeam;
    private TeamDTO awayTeam;
    private String status;
    private String result;
    private String competition;
    private String utcDate;

    public MatchDTO(TeamDTO homeTeam, TeamDTO awayTeam, String status,
                    String result, String competition, String utcDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = status;
        this.result = result;
        this.competition = competition;
        this.utcDate = utcDate;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamDTO awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getUtcDate() {
        return utcDate;
    }

    public void setUtcDate(String utcDate) {
        this.utcDate = utcDate;
    }

    // getters...
}