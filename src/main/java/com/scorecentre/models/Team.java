package com.scorecentre.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("Collection = teams")
public class Team {

    @Id
    private Long id;

    private String teamName;
    private String country;
    private String managerName;

    // ... 
}
