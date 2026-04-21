package com.scorecentre.footballData;

import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/** 
 * This class send query requests to Footy-Data's APIs 
 */

@Service
public class FootballDataService {
    
    private final RestClient restClient;
    
    // TODO: Implement cache since rates are limited
    // Private final Map<...>... = new ConcurrentHashMap<>();
    // TODO: Implement logging 

    public FootballDataService(RestClient.Builder builder) {

        this.restClient = builder
                            .defaultHeader("X-Auth-Token", System.getenv("FOOTBALL_DATA_API_KEY"))
                            .build();
        }
    
    // TODO: CHANGE TO DTO Obj once complete
    public List<String> queryFBDATA(URI uri) {
        //TODO: Implement Completable Future
        
        ResponseEntity<Object> response = this.restClient.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(Object.class);

            Object responseBody = response.getBody();

            if (responseBody instanceof List) {
                List<String> stringList = (List<String>) responseBody;
                return stringList;
            } else if (responseBody instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) responseBody;
            } else {
                System.out.println("lol");
            }

        return Collections.emptyList();
    }
}

