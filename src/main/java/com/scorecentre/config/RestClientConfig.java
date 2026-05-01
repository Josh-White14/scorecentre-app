package com.scorecentre.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        SimpleClientHttpRequestFactory reqFactory = new SimpleClientHttpRequestFactory();
        reqFactory.setConnectTimeout(Duration.ofSeconds(3));
        reqFactory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                            .requestFactory(reqFactory)
                            .baseUrl("https://api.football-data.org/v4/competitions")
                            .defaultHeader("X-Auth-Token", System.getenv("FOOTBALL_DATA_API_KEY"));
    }
}