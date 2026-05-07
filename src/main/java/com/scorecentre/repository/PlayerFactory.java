package com.scorecentre.repository;

import java.util.Map;

import com.scorecentre.models.Player;

public class PlayerFactory {

    public static Player createFromAPIData(Map<String, Object> playerData) {
        Player player = new Player();

        // FBDATA API ID
        player.setPlayerFootballDataId(
            ((Number) playerData.get("id")).intValue()
        );

        
        String fullName = (String) playerData.get("name");
        player.setFullName(fullName);

        
        if (fullName != null) {
            String[] nameParts = fullName.split(" ", 2);
            player.setFirstName(nameParts[0]);
            player.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        } else {
            player.setFirstName("");
            player.setLastName("");
        }

        player.setPosition((String) playerData.get("position"));
        player.setDateOfBirth((String) playerData.get("dateOfBirth"));
        player.setNationality((String) playerData.get("nationality"));

        // optional fields (not in API yet)
        player.setStartOfContractWithCurrentTeam(null);
        player.setEndOfContractWithCurrentTeam(null);

        return player;
    }
}