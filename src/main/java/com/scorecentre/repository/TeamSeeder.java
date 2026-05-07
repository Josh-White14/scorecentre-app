package com.scorecentre.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.scorecentre.models.Team;

@Component
public class TeamSeeder implements ApplicationRunner {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(ApplicationArguments args) {
        /** 
        if (teamRepository.count() == 0) {
            Team burnley = new Team();
            burnley.setFootballDataId(328);
            burnley.setTeamName("Burnley FC");
            burnley.setShortName("Burnley");
            burnley.setTla("BUR");
            burnley.setCrest("https://crests.football-data.org/328.png");
            burnley.setCountry("England");

            Team manCity = new Team();
            manCity.setFootballDataId(65);
            manCity.setTeamName("Manchester City FC");
            manCity.setShortName("Man City");
            manCity.setTla("MCI");
            manCity.setCrest("https://crests.football-data.org/65.png");
            manCity.setCountry("England");

            teamRepository.saveAll(List.of(burnley, manCity));
            System.out.println("Teams seeded successfully");
        }
            */
    }
}

    

