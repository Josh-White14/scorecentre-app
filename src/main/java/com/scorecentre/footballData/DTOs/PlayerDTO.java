package com.scorecentre.footballData.DTOs;

import java.io.Serializable;
import com.scorecentre.models.Team;
import java.io.Serializable;

public class PlayerDTO implements Serializable {
    
    private String firstName;
    private String lastName;
    private String position;
    private String dateOfBirth;
    private String nationality;
    private String startOfContractWithCurrentTeam;
    private String endOfContractWithCurrentTeam;        

    public PlayerDTO(String firstName, String lastName, String position, String dateOfBirth, String nationality, 
        String startOfContractWithCurrentTeam, String endOfContractWithCurrentTeam) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.startOfContractWithCurrentTeam = startOfContractWithCurrentTeam;
        this.endOfContractWithCurrentTeam = endOfContractWithCurrentTeam;       
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPosition() { return position; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }
    public String getStartOfContractWithCurrentTeam() { return startOfContractWithCurrentTeam; }
    public String getEndOfContractWithCurrentTeam() { return endOfContractWithCurrentTeam; }    

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPosition(String position) { this.position = position; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setStartOfContractWithCurrentTeam(String startOfContractWithCurrentTeam) { this.startOfContractWithCurrentTeam = startOfContractWithCurrentTeam; }
    public void setEndOfContractWithCurrentTeam(String endOfContractWithCurrentTeam) { this.endOfContractWithCurrentTeam = endOfContractWithCurrentTeam; }

}
