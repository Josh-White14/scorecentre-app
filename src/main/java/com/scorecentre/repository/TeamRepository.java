package com.scorecentre.repository;

import com.scorecentre.models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
    Team findByteamName(String teamName);
    
}