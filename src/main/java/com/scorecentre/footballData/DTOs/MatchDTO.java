package com.scorecentre.footballData.DTOs;

import java.io.Serializable;

import com.scorecentre.models.Team;

public class MatchDTO implements Serializable {
    

    private Team homeTeam;
    private Team awayTeam;

    private String status;
    private String result;

    // ..
    
}
