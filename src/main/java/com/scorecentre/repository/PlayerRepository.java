package com.scorecentre.repository;

import com.scorecentre.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByFulllName(String fullName);
    
}
