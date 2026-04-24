package com.scorecentre.footballData;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class FootballDataService {
    
    private final RestClient restClient;

    Dotenv dotenv = Dotenv.load();
    
    public FootballDataService(RestClient.Builder builder) {
        String apiKey = dotenv.get("FOOTBALL_DATA_API_KEY");

        this.restClient = builder
                            .defaultHeader("X-Auth-Token", apiKey)
                            .build();
        }
    
    public List<String> queryFBDATA(URI uri) {
        try {
        ResponseEntity<Object> response = this.restClient.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(Object.class);

            Object responseBody = response.getBody();

            if (responseBody instanceof List) {
                return (List<String>) responseBody;
            } else if (responseBody instanceof Map) {
                // Handle the map as needed
                System.out.println("Response is a map: " + responseBody);
            } else {
                System.out.println("Unexpected response type: " + responseBody.getClass().getName());
            }

        } catch (RestClientException e) {
            System.err.println("Error making request to API: " + e.getMessage());
            e.printStackTrace();
    }

        return Collections.emptyList();
}
}

