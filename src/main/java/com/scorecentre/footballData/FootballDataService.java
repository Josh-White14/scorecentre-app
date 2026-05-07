package com.scorecentre.footballData;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.scorecentre.footballData.DTOs.TeamDTO;
import com.scorecentre.models.Player;
import com.scorecentre.models.Team;
import com.scorecentre.repository.TeamRepository;
import com.scorecentre.repository.PlayerFactory;
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
        Team team = resolveTeam(teamName);
        return fetchMatchesFromApi(team.getFootballDataId());
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

    public List<PlayerDTO> fetchSquadByTeamName(String teamName) {
        Team team = resolveTeam(teamName);
        System.out.println("Player IDs size: " + team.getPlayerIds().size());
        System.out.println("Player IDs: " + team.getPlayerIds());

        if (team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            throw new ResourceNotFoundException("No squad found for team: " + teamName);
        }
        List<Player> players = playerRepository.findByIdIn(team.getPlayerIds());
        System.out.println("Players found by findAllById: " + players.size());
        
        return players.stream()
                .map(FootballDataDTOFactory::createPlayerDTOfromPlayer)
                .toList();
    }

    private Team resolveTeam(String teamName) {
        Team team = teamRepository.findByteamName(teamName);

        if (team == null) {
            team = new Team();
            team.setTeamName(teamName);
        }

        int teamId = team.getFootballDataId();
        if (teamId == 0) {
            teamId = fetchTeamIdFromApi(teamName);
            team.setFootballDataId(teamId);
        }

        if (team.getShortName() == null || team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            team = fetchAndSaveTeamDetails(team, teamId);
            teamRepository.save(team);
        }

        return team;
    }

    public TeamDTO queryTeamByName(String teamName) {
        Team team = resolveTeam(teamName);

        List<Player> playersList = playerRepository.findAllById(team.getPlayerIds());

         Map<String, String> players = playersList.stream()
            .collect(Collectors.toMap(
                    Player::getId,
                    p -> p.getFirstName() + " " + p.getLastName()
            ));

        return new TeamDTO(team, players);
    }


    private Team fetchAndSaveTeamDetails(Team team, int teamId) {
        try {
            String uriString = "https://api.football-data.org/v4/teams/" + teamId;

            ResponseEntity<Object> response = this.restClient.get()
                    .uri(URI.create(uriString))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Object.class);

            Object responseBody = response.getBody();

            if (responseBody instanceof Map) {
                Map<String, Object> mapResponse = (Map<String, Object>) responseBody;

                // area
                Map<String, Object> area = (Map<String, Object>) mapResponse.get("area");
                if (area != null) {
                    team.setCountry((String) area.get("name"));
                }

                // coach
                Map<String, Object> coach = (Map<String, Object>) mapResponse.get("coach");
                if (coach != null) {
                    team.setManagerName((String) coach.get("name"));
                }

                // competitions
                List<Map<String, Object>> competitions = (List<Map<String, Object>>) mapResponse.get("runningCompetitions");
                if (competitions != null) {
                    List<String> competitionNames = competitions.stream()
                            .map(c -> (String) c.get("name"))
                            .toList();
                    team.setCompetitions(competitionNames);
                }
                
                // players
                List<Map<String, Object>> squad = (List<Map<String, Object>>) mapResponse.get("squad");
                if (squad != null && !squad.isEmpty()) {
                    List<String> playerIds = new ArrayList<>();
                    for (Map<String, Object> playerData : squad) {
                        System.out.println("Saving " + playerIds.size() + " players to team");
                        int apiId = ((Number) playerData.get("id")).intValue();
                        Player player = playerRepository.findByPlayerFootballDataId(apiId);
                        if (player == null) {
                            player = PlayerFactory.createFromAPIData(playerData);
                        }
                        Player saved = playerRepository.save(player);
                        playerIds.add(saved.getId());
                    }
                    System.out.println("Saving " + playerIds.size() + " players to team");
                    team.setPlayerIds(playerIds);
                }
            }

        } catch (RestClientException e) {
            System.err.println("Error fetching team details: " + e.getMessage());
            e.printStackTrace();
        }

        return team;
    }

}

