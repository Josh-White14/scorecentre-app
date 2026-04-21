package com.scorecentre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scorecentre.footballData.FootballDataService;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ScoreCentreApplication {
    
    public static void main( String[] args )
    {
        SpringApplication.run(ScoreCentreApplication.class, args);

        
    }
}
