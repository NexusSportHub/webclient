package com.NexusSportHub.NexusSportHub.controller;

import com.NexusSportHub.NexusSportHub.dto.FootballDTO;
import com.NexusSportHub.NexusSportHub.services.FootballServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private FootballServiceI FootballService;

    //@Autowired
    //private BookServiceImpl bookService;

    @GetMapping(path = "/football")
    public ResponseEntity<FootballDTO[]> getfootball() {
        return FootballService.searchAllFootball();
    }


}
