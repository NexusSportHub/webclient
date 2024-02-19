package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.NexusSportHub.NexusSportHub.services.FootballApiService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Permitimos el acceso al puerto donde se ejecuta el proyecto react
public class FootballController {

    private final FootballApiService footballApiService;

    @Autowired
    public FootballController(FootballApiService footballApiService) {
        this.footballApiService = footballApiService;

    }

    @GetMapping("/football/leagues")
    public Mono<Object> getFootballLeagues(HttpServletRequest request) {
        return footballApiService.getLeagues(request);
    }

    @GetMapping("/football/seasons")
    public Mono<Object> getFootballSeasons(HttpServletRequest request) {
        return footballApiService.getSeasons(request);
    }

    @GetMapping("/football/countries")
    public Mono<Object> getFootballCountries(HttpServletRequest request) {
        return footballApiService.getCountries(request);
    }

}
