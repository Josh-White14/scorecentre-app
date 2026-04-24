package com.scorecentre.footballData.DTOs;

import java.util.Map;

import com.scorecentre.models.Team;

public class FootballDataDTOFactory {

    public static MatchDTO createMatchDTO(Map<String, Object> matchData) {
        Map<String, Object> homeTeamData = (Map<String, Object>) matchData.get("homeTeam");
        Map<String, Object> awayTeamData = (Map<String, Object>) matchData.get("awayTeam");
        Map<String, Object> scoreData    = (Map<String, Object>) matchData.get("score");
        Map<String, Object> fullTime     = (Map<String, Object>) scoreData.get("fullTime");
        Map<String, Object> competition  = (Map<String, Object>) matchData.get("competition");

        Team homeTeam = new Team();
        homeTeam.setTeamName((String) homeTeamData.get("name"));

        Team awayTeam = new Team();
        awayTeam.setTeamName((String) awayTeamData.get("name"));

        String result = fullTime.get("home") + "-" + fullTime.get("away");

        return new MatchDTO(
            homeTeam,
            awayTeam,
            (String) matchData.get("status"),
            result,
            (String) competition.get("name"),
            (String) matchData.get("utcDate")
        );
    }
}