package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NexusSportHub.NexusSportHub.services.BasketballApiService;


import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") //Permitimos el acceso al puerto donde se ejecuta el proyecto react
public class BasketballController {

    private final BasketballApiService basketballApiService;


    @Autowired
    public BasketballController(BasketballApiService basketballApiService) {
        this.basketballApiService = basketballApiService;

    }

    @GetMapping("/basketball/leagues")
    public Mono<Object> getBasketballLeagues(HttpServletRequest request) {
        return basketballApiService.getBasketballLeagues(request);
    }

    @GetMapping("/basketball/seasons")
    public Mono<Object> getBasketballSeasons(HttpServletRequest request) {
        return basketballApiService.getBasketballSeasons(request);
    }

    @GetMapping("/basketball/countries")
    public Mono<Object> getBasketballCountries(HttpServletRequest request) {
        return basketballApiService.getBasketballCountries(request);
    }
    
}
