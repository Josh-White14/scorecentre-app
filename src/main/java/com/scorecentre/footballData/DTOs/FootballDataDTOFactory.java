package com.scorecentre.footballData.DTOs;

import java.util.Map;

import com.scorecentre.models.Team;
import com.scorecentre.repository.TeamFactory;

public class FootballDataDTOFactory {

    public static MatchDTO createMatchDTOfromMatchData(Map<String, Object> matchData) {

        Map<String, Object> homeTeamData = (Map<String, Object>) matchData.get("homeTeam");
        Map<String, Object> awayTeamData = (Map<String, Object>) matchData.get("awayTeam");
        Map<String, Object> scoreData    = (Map<String, Object>) matchData.get("score");
        Map<String, Object> fullTime     = (Map<String, Object>) scoreData.get("fullTime");
        Map<String, Object> competition  = (Map<String, Object>) matchData.get("competition");

        Team homeTeam = TeamFactory.createFromMatchAPIData(homeTeamData);
        Team awayTeam = TeamFactory.createFromMatchAPIData(awayTeamData);
        String result = fullTime.get("home") + "-" + fullTime.get("away");
        

        return new MatchDTO(homeTeam,awayTeam,
            (String) matchData.get("status"),
            result,
            (String) competition.get("name"),
            (String) matchData.get("utcDate")
        );
    }

    public static TeamDTO createTeamDTO (Team team) {
        TeamDTO teamDTO = new TeamDTO(team);
        return teamDTO;
    }
}