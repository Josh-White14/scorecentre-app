package com.scorecentre.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
public class Player {

    @Id
    private String id;

    private int playerFootballDataId; // ID within FBDATA API   
    private String firstName;
    private String lastName;
    private String fullName;
    private String position;
    private String dateOfBirth;
    private String nationality;
    private String startOfContractWithCurrentTeam;
    private String endOfContractWithCurrentTeam;

    public Player(){

    }

    public int getPlayerFootballDataId() {
        return playerFootballDataId;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;     
    }

    public String getStartOfContractWithCurrentTeam() {
        return startOfContractWithCurrentTeam;
    }

    public String getEndOfContractWithCurrentTeam() {
        return endOfContractWithCurrentTeam;
    }

    public void setPlayerFootballDataId(int playerFootballDataId) {
        this.playerFootballDataId = playerFootballDataId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality =  nationality;        
    }

    public void setStartOfContractWithCurrentTeam(String startOfContractWithCurrentTeam) {
        this.startOfContractWithCurrentTeam = startOfContractWithCurrentTeam;
    }

    public void setEndOfContractWithCurrentTeam(String endOfContractWithCurrentTeam) {
        this.endOfContractWithCurrentTeam = endOfContractWithCurrentTeam;
    }
    
}
