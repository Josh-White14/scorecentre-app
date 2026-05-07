package com.scorecentre.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "teams")
public class Team {

    @Id
    private String id; // MongoDB, our ID

    private String teamName;
    private String country;
    private String managerName;
    
    @Indexed(unique=true)
    private int footballDataId; // ID within FBDATA API
    private String shortName;
    private String tla; // Three letter abr e.g. MCI, LIV
    private String crest;
    private String lastResult;

    private List<String> competitions;

    private List<String> playerIds = new ArrayList<>(); //(ID, Name)  // Stores Our MONGO id

    // Empty Constructor, add attributes via setters.
    public Team() {
        
    }

    public List<String> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<String> competitions) {
        this.competitions = competitions;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getFootballDataId() {
        return footballDataId;
    }

    public void setFootballDataId(int footballDataId) {
        this.footballDataId = footballDataId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getId() {
        return id;
    }

    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }
    
    

    
}
