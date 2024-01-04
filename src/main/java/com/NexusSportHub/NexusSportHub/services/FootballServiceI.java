package com.NexusSportHub.NexusSportHub.services;


import com.NexusSportHub.NexusSportHub.dto.FootballDTO;
import org.springframework.http.ResponseEntity;

public interface FootballServiceI {

    ResponseEntity<FootballDTO[]> searchAllFootball();
}

