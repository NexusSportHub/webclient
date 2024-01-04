package com.NexusSportHub.NexusSportHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.NexusSportHub.NexusSportHub.services.FootballApiService;

@RestController
@RequestMapping("/api")
public class FootballController {

    private final FootballApiService footballApiService;

    @Autowired
    public FootballController(FootballApiService footballApiService) {
        this.footballApiService = footballApiService;
    }

    @GetMapping("/leagues")
    public String getLeagues() {
        return footballApiService.getLeagues();
    }
}
