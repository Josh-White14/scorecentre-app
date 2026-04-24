package com.scorecentre.footballData;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.scorecentre.footballData.DTOs.FootballDataDTOFactory;
import com.scorecentre.footballData.DTOs.MatchDTO;


@Service
public class FootballDataService {
    
    private final RestClient restClient;

    
    public FootballDataService(RestClient.Builder builder, @Value("${FOOTBALL_DATA_API_KEY}") String apiKey) {

        this.restClient = builder
                            .defaultHeader("X-Auth-Token", apiKey)
                            .build();
        }
    
    
    public MatchDTO queryFBDATA(URI uri) {
        try {
            ResponseEntity<Object> response = this.restClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Object.class);

            Object responseBody = response.getBody();

            if (responseBody instanceof Map) {
                Map<String, Object> mapResponse = (Map<String, Object>) responseBody;
                List<Map<String, Object>> matches = (List<Map<String, Object>>) mapResponse.get("matches");

                if (matches != null && !matches.isEmpty()) {
                    Map<String, Object> match = (Map<String, Object>) matches.get(0);
                    return FootballDataDTOFactory.createMatchDTO(match);
                }
            }

        } catch (RestClientException e) {
            System.err.println("Error making request to API: " + e.getMessage());
            e.printStackTrace();
        }
            return null;
    }
}

