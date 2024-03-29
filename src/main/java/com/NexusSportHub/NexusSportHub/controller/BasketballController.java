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
@CrossOrigin(origins = "${virtual.machine.ip}:${react.port}") // Utiliza la variable de entorno para la IP
public class BasketballController {

    private final BasketballApiService basketballApiService;


    @Autowired
    public BasketballController(BasketballApiService basketballApiService) {
        this.basketballApiService = basketballApiService;

    }

    @GetMapping("/basketball/leagues")
    public Mono<Object> getBasketballLeagues(HttpServletRequest request) {
        return basketballApiService.getLeagues(request);
    }

    @GetMapping("/basketball/seasons")
    public Mono<Object> getBasketballSeasons(HttpServletRequest request) {
        return basketballApiService.getSeasons(request);
    }

    @GetMapping("/basketball/countries")
    public Mono<Object> getBasketballCountries(HttpServletRequest request) {
        return basketballApiService.getCountries(request);
    }
    
}
