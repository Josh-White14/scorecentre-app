package com.scorecentre.footballData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.scorecentre.footballData.DTOs.MatchDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController()
@RequestMapping("/api/v1")
public class FootballDataController {

    @Autowired
    FootballDataService footballDataService;

    @GetMapping("/data/test")
    public MatchDTO getTestData() {
        //
        MatchDTO response = footballDataService.queryTeamMatchesByName("Arsenal"); //"Burnley FC" 
        return response;
    }
    

}
