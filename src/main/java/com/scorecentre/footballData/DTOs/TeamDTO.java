package com.scorecentre.footballData.DTOs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.scorecentre.models.Player;
import com.scorecentre.models.Team;

import java.io.Serializable;

public class TeamDTO implements Serializable {
    
    private String teamName;
    private String shortName;
    private String tla;
    private String crest;
    private String country;
    private String lastResult;
    private String managerName;
    private List<String> competitions;

    private Map<String, String> playerIds;

    // No players Contstructor
    public TeamDTO(Team team) {
        this.teamName = team.getTeamName();
        this.shortName = team.getShortName();
        this.tla = team.getTla();
        this.crest = team.getCrest();
        this.country = team.getCountry();
        this.lastResult = team.getLastResult();
        this.managerName = team.getManagerName();
        this.competitions = team.getCompetitions();
    }

    public TeamDTO(Team team, Map<String, String> players) {
        this.teamName = team.getTeamName();
        this.shortName = team.getShortName();
        this.tla = team.getTla();
        this.crest = team.getCrest();
        this.country = team.getCountry();
        this.lastResult = team.getLastResult();
        this.managerName = team.getManagerName();
        this.competitions = team.getCompetitions();
        this.playerIds = players;
    }

    public String getTeamName() { return teamName; }
    public String getShortName() { return shortName; }
    public String getTla() { return tla; }
    public String getCrest() { return crest; }
    public String getCountry() { return country; }
    public String getLastResult() { return lastResult; }
    public String getManagerName() { return managerName; }
    
    public List<String> getCompetitions() {
        return competitions;
    }

    public Map<String, String> getPlayerIds() {
        return playerIds;
    }
}

