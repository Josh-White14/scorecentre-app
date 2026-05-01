package com.scorecentre.footballData.DTOs;

import java.io.Serializable;

import com.scorecentre.models.Team;

import java.io.Serializable;

public class TeamDTO implements Serializable {
    
    private String teamName;
    private String shortName;
    private String tla;
    private String crest;
    private String country;

    public TeamDTO(Team team) {
        this.teamName = team.getTeamName();
        this.shortName = team.getShortName();
        this.tla = team.getTla();
        this.crest = team.getCrest();
        this.country = team.getCountry();
    }

    public String getTeamName() { return teamName; }
    public String getShortName() { return shortName; }
    public String getTla() { return tla; }
    public String getCrest() { return crest; }
    public String getCountry() { return country; }
}

