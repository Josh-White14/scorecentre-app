package com.scorecentre.footballData;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.scorecentre.footballData.DTOs.FootballDataDTOFactory;
import com.scorecentre.footballData.DTOs.MatchDTO;
import com.scorecentre.footballData.DTOs.PlayerDTO;
import com.scorecentre.footballData.DTOs.TeamDTO;
import com.scorecentre.models.Player;
import com.scorecentre.models.Team;
import com.scorecentre.repository.TeamRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//TODO: We need to seperate controllers

@RestController()
@RequestMapping("/api/v1/data")
public class FootballDataController {

    @Autowired
    FootballDataService footballDataService;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/matches/{teamName}")
    public ResponseEntity<MatchDTO> getTeamLatestMatchByName(@PathVariable String teamName) {
        return footballDataService.queryTeamMatchesByName(teamName.replace("-", " ").trim()); //remove spaces
    }


    @GetMapping("/teams/all")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teams = teamRepository.findAll().stream().map(team -> FootballDataDTOFactory.createTeamDTO(team)).toList();
        return ResponseEntity.ok(teams);
    }
    

    @GetMapping("/teams/{teamName}/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayerFromTeam(@PathVariable String teamName) {
        return footballDataService.fetchSquadByTeamName(teamName.replace("-", " ").trim());
    }


    @GetMapping("/teams/{teamName}")
    public ResponseEntity<TeamDTO> getTeamByName(@PathVariable String teamName) {
        TeamDTO response = footballDataService.queryTeamByName(teamName.replace("-", " ").trim());
        return ResponseEntity.ok(response);
    }
    
    
    

}
