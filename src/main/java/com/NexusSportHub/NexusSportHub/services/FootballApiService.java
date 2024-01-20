package com.NexusSportHub.NexusSportHub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;



@Service
public class FootballApiService {

    private final WebClient basketballWebClient;
    private final WebClient footballWebClient;
    private final WebClient baseballWebClient;
    private final WebClient rugbyWebClient;

    @Value("${football.api-sports.key}")
    private String footballApiSportsKey;

    @Value("${basketball.api-sports.key}")
    private String basketballApiSportsKey;

    @Value("${baseball.api-sports.key}")
    private String baseballApiSportsKey;

    @Value("${rugby.api-sports.key}")
    private String rugbyApiSportsKey;

    public FootballApiService(WebClient.Builder webClientBuilder) {
        this.basketballWebClient = webClientBuilder
                .baseUrl("https://v1.basketball.api-sports.io/")
                .defaultHeader("x-apisports-key", basketballApiSportsKey)
                .build();
        this.footballWebClient = webClientBuilder
                .baseUrl("https://v3.football.api-sports.io/")
                .defaultHeader("x-apisports-key", footballApiSportsKey)
                .build();
        this.baseballWebClient = webClientBuilder
                .baseUrl("https://v1.baseball.api-sports.io")
                .defaultHeader("x-apisports-key", baseballApiSportsKey)
                .build();
        this.rugbyWebClient = webClientBuilder
                .baseUrl("https://v1.rugby.api-sports.io")
                .defaultHeader("x-apisports-key", rugbyApiSportsKey)
                .build();
    }

    // Decodificamos el token JWT del proyecto react
   public Mono<String> getJwtId(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);

        Base64.Decoder decoder = Base64.getDecoder();
        String[] array = token.split("\\.");

        if (array.length >= 2) {
            String decodedJson = new String(decoder.decode(array[1]), StandardCharsets.UTF_8);
            try {
                // Parsear el JSON y extraer el campo "sub" (ID)
                JsonNode jsonNode = new ObjectMapper().readTree(decodedJson);
                String jwtId = jsonNode.get("sub").asText();
                return Mono.just(jwtId);
            } catch (IOException e) {
                return Mono.error(new RuntimeException("Error al parsear el token JWT"));
            }
        } else {
            return Mono.error(new RuntimeException("Token JWT no tiene el formato esperado"));
        }
    } else {
        return Mono.error(new RuntimeException("No se encontró el token en el encabezado"));
    }
}


    // La información a mostrar por pantalla es muy grande y no hay memoria
    // suficiente para mostrarlo
    // se hace la línea de map, pasa el tipo de dato a uno más pequeño
    // para así poder mostrarlo por pantalla
    public Mono<Object> getBasketballLeagues(HttpServletRequest request) {
        return basketballWebClient.get()
                .uri("/leagues")
                .header("x-rapidapi-key", basketballApiSportsKey)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
    
                    // Decodificar el token JWT y obtener información
                    Mono<String> decodedTokenInfoMono = getJwtId(request);
    
                    // Utilizar flatMap para combinar la información del token con la respuesta
                    return decodedTokenInfoMono.flatMap(decodedTokenInfo -> {
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        return Mono.just(ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(responseBody + "\n\n" + decodedTokenInfo));
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.get(0));
    }

    public Mono<Object> getFootballLeagues(HttpServletRequest request) {
        return footballWebClient.get()
                .uri("/leagues")
                .header("x-apisports-key", footballApiSportsKey)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    // Decodificar el token JWT y obtener información
                    Mono<String> decodedTokenInfoMono = getJwtId(request);
    
                    // Utilizar flatMap para combinar la información del token con la respuesta
                    return decodedTokenInfoMono.flatMap(decodedTokenInfo -> {
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        return Mono.just(ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(responseBody + "\n\n" + decodedTokenInfo));
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.get(0));
    }

    public Mono<Object> getBaseballLeagues(HttpServletRequest request) {
        return baseballWebClient.get()
                .uri("/leagues")
                .header("x-apisports-key", baseballApiSportsKey)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    // Decodificar el token JWT y obtener información
                    Mono<String> decodedTokenInfoMono = getJwtId(request);
    
                    // Utilizar flatMap para combinar la información del token con la respuesta
                    return decodedTokenInfoMono.flatMap(decodedTokenInfo -> {
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        return Mono.just(ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(responseBody + "\n\n" + decodedTokenInfo));
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.get(0));
    }

    public Mono<Object> getRugbyLeagues(HttpServletRequest request) {
        return rugbyWebClient.get()
                .uri("/leagues")
                .header("x-apisports-key", rugbyApiSportsKey)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    // Decodificar el token JWT y obtener información
                    Mono<String> decodedTokenInfoMono = getJwtId(request);
    
                    // Utilizar flatMap para combinar la información del token con la respuesta
                    return decodedTokenInfoMono.flatMap(decodedTokenInfo -> {
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        return Mono.just(ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(responseBody + "\n\n" + decodedTokenInfo));
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.get(0));
    }
}
