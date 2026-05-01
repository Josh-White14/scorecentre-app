package com.scorecentre.footballData.DTOs;

import java.util.Map;

import com.scorecentre.models.Team;
import com.scorecentre.models.Player;
import com.scorecentre.repository.TeamFactory;

public class FootballDataDTOFactory {

    public static MatchDTO createMatchDTOfromMatchData(Map<String, Object> matchData) {

        Map<String, Object> homeTeamData = (Map<String, Object>) matchData.get("homeTeam");
        Map<String, Object> awayTeamData = (Map<String, Object>) matchData.get("awayTeam");
        Map<String, Object> scoreData    = (Map<String, Object>) matchData.get("score");
        Map<String, Object> fullTime     = (Map<String, Object>) scoreData.get("fullTime");
        Map<String, Object> competition  = (Map<String, Object>) matchData.get("competition");

        String status = (String) matchData.get("status");

        Team homeTeam = TeamFactory.createFromMatchAPIData(homeTeamData);
        Team awayTeam = TeamFactory.createFromMatchAPIData(awayTeamData);
        
        String result = fullTime.get("home") + "-" + fullTime.get("away");
        String competitionName = (String) competition.get("name");
        String utcDate = (String) matchData.get("utcDate");
        

        return new MatchDTO(
            homeTeam,
            awayTeam,
            status,
            result,
            competitionName, 
            utcDate
        );
    }

    public static PlayerDTO createPlayerDTOfromPlayerData(Map<String, Object> playerData) {
        String firstName = (String) playerData.get("firstName");
        String lastName = (String) playerData.get("lastName");
        String position = (String) playerData.get("position");
        String dateOfBirth = (String) playerData.get("dateOfBirth");
        String nationality = (String) playerData.get("nationality");
        String startOfContractWithCurrentTeam = (String) playerData.get("startOfContract");
        String endOfContractWithCurrentTeam = (String) playerData.get("endOfContract");
        
        return new PlayerDTO(
            firstName, 
            lastName, 
            position, 
            dateOfBirth, 
            nationality, 
            startOfContractWithCurrentTeam, 
            endOfContractWithCurrentTeam
        );
    }

    public static TeamDTO createTeamDTO (Team team) {
        TeamDTO teamDTO = new TeamDTO(team);
        return teamDTO;
    }
}