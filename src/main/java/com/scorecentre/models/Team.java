package com.scorecentre.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "teams")
public class Team {

    @Id
    private String id; // MongoDB, our ID

    private String teamName;
    private String country;
    private String managerName;
    private int footballDataId; // ID within FBDATA API
    private String shortName;
    private String tla; // Three letter abr e.g. MCI, LIV
    private String crest; // URI FOR KIT
    
    // Empty Constructor, add attributes via setters.
    public Team() {
        
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

    public String getId() {
        return id;
    }
    
    

    
}
