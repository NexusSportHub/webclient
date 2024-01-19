package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.NexusSportHub.NexusSportHub.services.FootballApiService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") //Permitimos el acceso al puerto donde se ejecuta el proyecto react
public class FootballController {

    private final FootballApiService footballApiService;


    @Autowired
    public FootballController(FootballApiService footballApiService) {
        this.footballApiService = footballApiService;

    }

    @GetMapping("/football/leagues")
    public Mono<String> getFootballLeagues() {
        return footballApiService.getFootballLeagues();
    }

    @GetMapping("/basketball/leagues")
    public Mono<Object> getBasketballLeagues(HttpServletRequest request) {
        return footballApiService.getBasketballLeagues(request);
    }

    @GetMapping("baseball/leagues")
    public Mono<String> getbaseballcompetitions() {
        return footballApiService.getBaseballLeagues();
    }

    @GetMapping("rugby/leagues")
    public Mono<String> getrugbyleagues() {
        return footballApiService.getRugbyLeagues();
    }

    

    

}

