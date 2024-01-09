package com.NexusSportHub.NexusSportHub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.nio.charset.StandardCharsets;

@Service
public class FootballApiService {

    private final WebClient webClient;

    @Value("${football.api-sports.key}")
    private String apiSportsKey;

    public FootballApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://v3.football.api-sports.io/")
                .defaultHeader("x-apisports-key", apiSportsKey)
                .build();
    }

    public Mono<String> getLeagues() {
        return webClient.get()
                .uri("/leagues")
                .header("x-apisports-key", apiSportsKey)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return new String(bytes, StandardCharsets.UTF_8);
                })
                .collectList()
                .map(list -> String.join("", list));
    }
}
