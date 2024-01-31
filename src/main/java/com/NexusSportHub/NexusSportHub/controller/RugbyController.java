package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.NexusSportHub.NexusSportHub.services.RugbyApiService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") //Permitimos el acceso al puerto donde se ejecuta el proyecto react
public class RugbyController {

    private final RugbyApiService rugbyApiService;

    @Autowired
    public RugbyController(RugbyApiService rugbyApiService) {

        this.rugbyApiService = rugbyApiService;

    }

    @GetMapping("rugby/leagues")
    public Mono<Object> getrugbyleagues(HttpServletRequest request) {
        return rugbyApiService.getRugbyLeagues(request);
    }

    @GetMapping("rugby/seasons")
    public Mono<Object> getrugbyseasons(HttpServletRequest request) {
        return rugbyApiService.getRugbySeasons(request);
    }

    @GetMapping("rugby/countries")
    public Mono<Object> getrugbycountries(HttpServletRequest request) {
        return rugbyApiService.getRugbyCountries(request);
    }
    
}
