package com.scorecentre.repository;

import java.util.Map;

import com.scorecentre.models.Team;

public class TeamFactory {

    public static Team createFromMatchAPIData(Map<String, Object> teamData) {
        Team team = new Team();
        team.setFootballDataId(((int) teamData.get("id")));
        team.setTeamName((String) teamData.get("name"));
        team.setShortName((String) teamData.get("shortName"));
        team.setTla((String) teamData.get("tla"));
        team.setCrest((String) teamData.get("crest"));
    return team;
}
    
}
