package com.NexusSportHub.NexusSportHub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FootballApiService {

    private final WebClient webClient;
    private final String apiKey;

    public FootballApiService(WebClient.Builder webClientBuilder, @Value("${football.api.url}") String apiUrl, @Value("${football.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.apiKey = apiKey;
    }

    public String getLeagues() {
        return webClient.get()
                .uri("/leagues")
                .headers(headers -> headers.setBearerAuth(apiKey))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
