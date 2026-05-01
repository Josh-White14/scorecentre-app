package com.scorecentre.footballData;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.scorecentre.exceptions.ResourceNotFoundException;
import com.scorecentre.footballData.DTOs.FootballDataDTOFactory;
import com.scorecentre.footballData.DTOs.MatchDTO;
import com.scorecentre.footballData.DTOs.PlayerDTO;
import com.scorecentre.models.Player;
import com.scorecentre.models.Team;
import com.scorecentre.repository.TeamRepository;
import com.scorecentre.repository.PlayerRepository;


@Service
public class FootballDataService {
    
    private final RestClient restClient;
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;
    
    public FootballDataService(RestClient.Builder builder, @Value("${FOOTBALL_DATA_API_KEY}") String apiKey) {

        this.restClient = builder
                            .defaultHeader("X-Auth-Token", apiKey)
                            .build();
        }
    
    

    
    public MatchDTO queryTeamMatchesByName(String teamName) {
        Team team = teamRepository.findByteamName(teamName);

        if (team == null) {
            team = new Team();
            team.setTeamName(teamName);
        }

        int teamId = team.getFootballDataId();

        if (teamId == 0) {
            teamId = fetchTeamIdFromApi(teamName);
            team.setFootballDataId(teamId);
            teamRepository.save(team);
        }

        return fetchMatchesFromApi(teamId);
    }

    public PlayerDTO queryPlayerByFullName(String fullPlayerName) {
        
        throw new UnsupportedOperationException("Player search not implemented yet");


    }

    private MatchDTO fetchMatchesFromApi(int teamId) {
        try {
            String uriString = "https://api.football-data.org/v4/teams/" + teamId + "/matches?status=FINISHED&limit=3";
            System.out.println("Requesting URL: " + uriString);

            ResponseEntity<Object> response = this.restClient.get()
                    .uri(URI.create(uriString))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Object.class);

            Object responseBody = response.getBody();

            if (responseBody instanceof Map) {
                Map<String, Object> mapResponse = (Map<String, Object>) responseBody;
                List<Map<String, Object>> matches = (List<Map<String, Object>>) mapResponse.get("matches");

                if (matches != null && !matches.isEmpty()) {
                    return FootballDataDTOFactory.createMatchDTOfromMatchData((Map<String, Object>) matches.get(0));
                }
            }

        } catch (RestClientException e) {
            System.err.println("Error making request to API: " + e.getMessage());
            e.printStackTrace();
        }
            throw new ResourceNotFoundException("Team not found");
    }

    
    private int fetchTeamIdFromApi(String teamName) {
        try {
            //TODO: THIS ONLY WORKS FOR PRMIER LEAGUE TEAMS
            
            String uri = "https://api.football-data.org/v4/competitions/PL/teams";
            
            System.out.println("Fetching PL teams to find: " + teamName);
            
            ResponseEntity<Map> response = restClient.get()
                .uri(URI.create(uri))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Map.class);

            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("teams")) {
                List<Map<String, Object>> teams = (List<Map<String, Object>>) body.get("teams");

                for (Map<String, Object> t : teams) {
                    String name = (String) t.get("name");
                    String shortName = (String) t.get("shortName");

                    if ((name != null && name.equalsIgnoreCase(teamName)) ||
                        (shortName != null && shortName.equalsIgnoreCase(teamName))) {
                        return ((Number) t.get("id")).intValue();
                    }
                }

                throw new RuntimeException(
                    "No match for team: " + teamName +
                    ". PL teams available: " + teams.stream()
                        .map(x -> x.get("shortName"))
                        .toList()
                );
            }

            throw new RuntimeException("No teams found in PL response");

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch team ID for: " + teamName, e);
        }
    }

    private int fetchPlayerIdFromApi(String fullPlayerName) {
       
        throw new UnsupportedOperationException("Player search not implemented yet");

    }
}
