package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NexusSportHub.NexusSportHub.services.BaseballApiService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${virtual.machine.ip}:${react.port}") // Utiliza la variable de entorno para la IP
public class BaseballController {

    private final BaseballApiService baseballApiService;

    @Autowired
    public BaseballController(BaseballApiService baseballApiService) {

        this.baseballApiService = baseballApiService;

    }

    @GetMapping("baseball/leagues")
    public Mono<Object> getbaseballcompetitions(HttpServletRequest request) {
        return baseballApiService.getLeagues(request);
    }

    @GetMapping("baseball/seasons")
    public Mono<Object> getbaseballseasons(HttpServletRequest request) {
        return baseballApiService.getSeasons(request);
    }
    
    @GetMapping("baseball/countries")
    public Mono<Object> getbaseballcountries(HttpServletRequest request) {
        return baseballApiService.getCountries(request);
    }
}
