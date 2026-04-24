package com.scorecentre.footballData;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Service
public class FootballDataService {
    
    private final RestClient restClient;

    
    public FootballDataService(RestClient.Builder builder, @Value("${FOOTBALL_DATA_API_KEY}") String apiKey) {

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
                Map<String, String> mapResponse = (Map<String, String>) responseBody;
                System.out.println(responseBody);
            
                
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

