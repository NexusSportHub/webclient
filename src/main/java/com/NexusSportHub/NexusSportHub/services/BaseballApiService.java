package com.NexusSportHub.NexusSportHub.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class BaseballApiService extends ApiService {

    public BaseballApiService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "https://v1.baseball.api-sports.io/", null);
    }

    @Override
    public Mono<Object> getLeagues(HttpServletRequest request) {
        return fetchData(request, "/leagues");
    }

    @Override
    public Mono<Object> getSeasons(HttpServletRequest request) {
        return fetchData(request, "/seasons");
    }

    @Override
    public Mono<Object> getCountries(HttpServletRequest request) {
        return fetchData(request, "/countries");
    }
}

