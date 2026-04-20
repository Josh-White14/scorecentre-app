package com.scorecentre.footballData;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;



/** 
 * This class send query requests to APIs 
 * 
 */

@Service
public class FootballDataService {

    private final RestClient restClient;
    
    // TODO: Implement cache since rates are limited.
    // Private final Map<...>... = new ConcurrentHashMap<>();
    // TODO: Implement logging 

    public FootballDataService(RestClient.Builder builder) {
        SimpleClientHttpRequestFactory reqFactory = new SimpleClientHttpRequestFactory();
        reqFactory.setConnectTimeout(Duration.ofSeconds(3));
        reqFactory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = builder.requestFactory(reqFactory)
                            .baseUrl("https://api.football-data.org/v4/competitions/PL/matches")
                            .defaultHeader(System.getenv("FOOTBALL-DATA_API_KEY")) // API KEY USED HERE
                            .build();
        }
    
    // TODO: CHANGE TO DTO Obj once complete
    public List<String> queryFBDATA(String uri) {
        //TODO: Implement Completable Future
        
        List<String> FBDATA = this.restClient.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(new ParameterizedTypeReference<List<String>>(){});

        return FBDATA;
            
        
    }

}
