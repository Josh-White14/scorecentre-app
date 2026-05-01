package com.scorecentre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scorecentre.repository.TeamFactory;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ScoreCentreApplication {
    
    public static void main( String[] args )
    {
        SpringApplication.run(ScoreCentreApplication.class, args);
        //TeamFactory.createTeam("Burnley", "England", "Scott Parker");

    }
}
