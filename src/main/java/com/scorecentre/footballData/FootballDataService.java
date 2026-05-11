package com.scorecentre.footballData;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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


/** TODO: This needs to be refactored */
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
    
    
    public ResponseEntity<MatchDTO> queryTeamMatchesByName(String teamName) {
        Team team = resolveTeam(teamName);
        return ResponseEntity.of(fetchMatchesFromApi(team.getFootballDataId()));
    }


    private Optional<MatchDTO> fetchMatchesFromApi(int teamId) {
        try {
            String uriString = "https://api.football-data.org/v4/teams/" + teamId + "/matches?status=FINISHED";

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
                    Map<String, Object> matchData = (Map<String, Object>) matches.get(0);

                    Map<String, Object> homeTeamData = (Map<String, Object>) matchData.get("homeTeam");
                    Map<String, Object> awayTeamData = (Map<String, Object>) matchData.get("awayTeam");

                    TeamDTO homeTeam = resolveTeamDTO(homeTeamData);
                    TeamDTO awayTeam = resolveTeamDTO(awayTeamData);

                    return Optional.of(FootballDataDTOFactory.createMatchDTOfromMatchData(matchData, homeTeam, awayTeam));
                }
                return Optional.empty();
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
            
            
            ResponseEntity<Map> response = restClient.get()
                .uri(URI.create(uri))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Map.class);

            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("teams")) {
                List<Map<String, Object>> teams = (List<Map<String, Object>>) body.get("teams");

                for (Map<String, Object> t : teams) {

                    if ((teamNameMatches((String) t.get("name"), (String) t.get("shortName"), teamName))) {
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

    public ResponseEntity<List<PlayerDTO>> fetchSquadByTeamName(String teamName) {
        Team team = resolveTeam(teamName);

        if (team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            throw new ResourceNotFoundException("No squad found for team: " + teamName);
        }
        List<Player> players = playerRepository.findByIdIn(team.getPlayerIds());
        
        return ResponseEntity.ok(players.stream()
                .map(FootballDataDTOFactory::createPlayerDTOfromPlayer)
                .toList());
    }

    private Team resolveTeam(String teamName) {
        String normalised = teamName.trim().toLowerCase();
        Team team = teamRepository.findByteamName(normalised);

        if (team == null) {
            team = new Team();
            team.setTeamName(normalised);
        }

        int teamId = team.getFootballDataId();
        if (teamId == 0) {
            teamId = fetchTeamIdFromApi(normalised);
            team.setFootballDataId(teamId);
        }

        if (team.getShortName() == null || team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            team = fetchAndSaveTeamDetails(team, teamId);
            teamRepository.save(team);
        }

        return team;
    }

    public TeamDTO queryTeamByName(String teamName) {
        String normalised = teamName.trim().toLowerCase();
        Team team = resolveTeam(normalised);
        return new TeamDTO(team, getPlayerMapForTeam(team));
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

                team.setShortName((String) mapResponse.get("shortName"));
                team.setTla((String) mapResponse.get("tla"));
                team.setCrest((String) mapResponse.get("crest"));

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
                        int apiId = ((Number) playerData.get("id")).intValue();
                        Player player = playerRepository.findByPlayerFootballDataId(apiId);
                        if (player == null) {
                            player = PlayerFactory.createFromAPIData(playerData);
                        }
                        Player saved = playerRepository.save(player);
                        playerIds.add(saved.getId());
                    }
                    team.setPlayerIds(playerIds);
                }
            }

        } catch (RestClientException e) {
            System.err.println("Error fetching team details: " + e.getMessage());
            e.printStackTrace();
        }

        return team;
    }

    private TeamDTO resolveTeamDTO(Map<String, Object> teamData) {
        String teamName = (String) teamData.get("name");
        Team team = resolveTeam(teamName);
        return new TeamDTO(team, getPlayerMapForTeam(team));
    }

    private Map<String, String> getPlayerMapForTeam(Team team) {

        if (team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            return new HashMap<>();
        }
        return playerRepository.findByIdIn(team.getPlayerIds())
                .stream()
                .collect(Collectors.toMap(
                        Player::getId,
                        p -> p.getFirstName() + " " + p.getLastName()
                ));
    }

    private boolean teamNameMatches(String apiName, String apiShortName, String searchName) {
        if (searchName == null) return false;
        String search = searchName.toLowerCase();
        String name = apiName != null ? apiName.toLowerCase() : "";
        String shortName = apiShortName != null ? apiShortName.toLowerCase() : "";

        return name.equals(search) ||
            shortName.equals(search) ||
            name.contains(search) ||
            search.contains(name);
    }

}

