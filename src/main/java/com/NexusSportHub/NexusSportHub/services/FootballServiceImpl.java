package com.NexusSportHub.NexusSportHub.services;

import com.NexusSportHub.NexusSportHub.dto.FootballDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FootballServiceImpl implements FootballServiceI{

    @Value("${api.key}") // La key en encuentra en el archivo application.properties
    private String apiKey;

    private final WebClient webClient;

    public FootballServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://football-results-of-today.p.rapidapi.com/api/v1/soccer")
                .defaultHeader("X-RapidAPI-Host", "football-results-of-today.p.rapidapi.com")
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .build();
    }
    @Override
    public ResponseEntity<FootballDTO[]> searchAllFootball() {
        return webClient.get().uri("/api/football").retrieve().toEntity(FootballDTO[].class).block();
    }

}
