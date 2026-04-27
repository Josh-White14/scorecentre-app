package com.scorecentre.footballData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.scorecentre.footballData.DTOs.MatchDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController()
@RequestMapping("/api/v1")
public class FootballDataController {

    @Autowired
    FootballDataService footballDataService;

    @GetMapping("/data/{teamName}")
    public MatchDTO getTestData(@PathVariable String teamName) {
        //
        MatchDTO response = footballDataService.queryTeamMatchesByName(teamName); //"Burnley FC" 
        return response;
    }
    

}
