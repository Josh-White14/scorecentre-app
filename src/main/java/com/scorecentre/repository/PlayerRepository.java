package com.scorecentre.repository;

import com.scorecentre.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByFullName(String fullName);
    
    //TODO: These should probably be optional
    Player findByPlayerFootballDataId(int apiId);

    // takes a list of player(mongo)IDs
    @Query("{ '_id': { $in: ?0 } }")
    List<Player> findByIdIn(List<String> ids);
    
}
