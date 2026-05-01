package com.scorecentre.footballData;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.scorecentre.footballData.DTOs.FootballDataDTOFactory;
import com.scorecentre.footballData.DTOs.MatchDTO;
import com.scorecentre.footballData.DTOs.TeamDTO;
import com.scorecentre.models.Team;
import com.scorecentre.repository.TeamRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController()
@RequestMapping("/api/v1/data")
public class FootballDataController {

    @Autowired
    FootballDataService footballDataService;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/matches/{teamName}")
    public MatchDTO getTeamLatestMatchByName(@PathVariable String teamName) {
        MatchDTO response = footballDataService.queryTeamMatchesByName(teamName); //"Burnley FC" 
        return response;
    }

    // Repeat above for teams endpoint, but return list of teams instead of matches. 

    @GetMapping("/teams/all")
    public List<TeamDTO> getAllTeams() {
        List<TeamDTO> teams = teamRepository.findAll().stream().map(team -> FootballDataDTOFactory.createTeamDTO(team)).toList();
        return teams;
    }
   
    
    

}
