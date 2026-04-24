package com.scorecentre.footballData;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.scorecentre.footballData.DTOs.MatchDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController()
@RequestMapping("/api/v1")
public class FootballDataController {

    @Autowired
    FootballDataService footballDataService;

    @GetMapping("/data/test")
    public MatchDTO getTestData() {
        //
        MatchDTO response = footballDataService.queryFBDATA(URI.create("https://api.football-data.org/v4/teams/65/matches?status=FINISHED&limit=1"));
        return response;
    }
    

}
