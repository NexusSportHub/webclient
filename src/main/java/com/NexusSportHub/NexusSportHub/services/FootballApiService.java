package com.NexusSportHub.NexusSportHub.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class FootballApiService extends ApiService {

    public FootballApiService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "https://v3.football.api-sports.io/", null);
    }

    @Override
    public Mono<Object> getLeagues(HttpServletRequest request) {
        return fetchData(request, "/leagues");
    }

    @Override
    public Mono<Object> getSeasons(HttpServletRequest request) {
        return fetchData(request, "/leagues/seasons");
    }

    @Override
    public Mono<Object> getCountries(HttpServletRequest request) {
        return fetchData(request, "/countries");
    }
}
