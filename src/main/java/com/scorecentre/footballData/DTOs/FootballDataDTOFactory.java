package com.scorecentre.footballData.DTOs;

import java.util.Map;

import com.scorecentre.models.Team;
import com.scorecentre.models.Player;
import com.scorecentre.repository.TeamFactory;


//TODO: This must be refactored such that this class only produces DTOs From Objects
// I.e. Match Obj -> MatchDTO
// Match factory should worry about matchData -> match obj
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
        String fullName = (String) playerData.get("name");
        
        //Split fullname to name parts.
        String[] nameParts = fullName != null ? fullName.split(" ", 2) : new String[]{"", ""}; // ternary to check null
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : ""; // ternary to check length

        String position = (String) playerData.get("position");
        String dateOfBirth = (String) playerData.get("dateOfBirth");
        String nationality = (String) playerData.get("nationality");
        
        String startOfContractWithCurrentTeam = null;
        String endOfContractWithCurrentTeam = null;
        
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

    public static PlayerDTO createPlayerDTOfromPlayer(Player player) {
        return new PlayerDTO(
            player.getFirstName(),
            player.getLastName(),
            player.getPosition(),
            player.getDateOfBirth(),
            player.getNationality(),
            player.getStartOfContractWithCurrentTeam(),
            player.getEndOfContractWithCurrentTeam()
        );
    }

    
    public static TeamDTO createTeamDTO (Team team) {
        TeamDTO teamDTO = new TeamDTO(team);
        return teamDTO;
    }
}