package com.NexusSportHub.NexusSportHub.services;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@Service
public abstract class ApiService {

    protected final WebClient webClient;
    protected final String apiUrl;
    protected final String apiPath;

    @Value("${basketball.api-sports.key}")
    private String basketballApiSportsKey;

    @Value("${baseball.api-sports.key}")
    private String baseballApiSportsKey;

    @Value("${football.api-sports.key}")
    private String footballApiSportsKey;

    @Value("${rugby.api-sports.key}")
    private String rugbyApiSportsKey;

    @Value("${virtual.machine.ip}")
    private String virtualMachineIp; // Nueva variable de entorno para la IP de la máquina virtual


    public ApiService(WebClient.Builder webClientBuilder, String apiUrl, String apiKey) {
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("x-apisports-key", apiKey)
                .build();
        this.apiUrl = apiUrl;
        this.apiPath = extractApiPath(apiUrl); // Utilizar método para extraer el tipo de deporte
    }

    public Mono<String> getJwtId(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            return Mono.just(JwtTokenDecoder.decodeJwtId(token));
        } catch (RuntimeException e) {
            return Mono.error(e);
        }
    }

    public abstract Mono<Object> getLeagues(HttpServletRequest request);

    public abstract Mono<Object> getSeasons(HttpServletRequest request);

    public abstract Mono<Object> getCountries(HttpServletRequest request);

    protected Mono<Object> fetchData(HttpServletRequest request, String endpoint) {
         WebClient externalWebClient = WebClient.create("http://" + virtualMachineIp + ":8082/api/products"); // Utilizar la variable de entorno para la IP
        return webClient.get()
                .uri(endpoint)
                .header("x-apisports-key", getApiKey())
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    Mono<String> decodedTokenInfoMono = getJwtId(request);
                    return decodedTokenInfoMono.flatMap(decodedTokenInfo -> {
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl(apiUrl + endpoint);
                        product.setPath(apiPath);
                        product.setStatus(false);
                        product.setDate(Date.from(Instant.now()));
                        product.setPaidDate(null);
                        product.setApiResponse(responseBody);
                        product.setpaymentMethod(null);
                        return externalWebClient.post()
                                .uri("/insert")
                                .body(BodyInserters.fromValue(product))
                                .retrieve()
                                .bodyToMono(Product.class)
                                .flatMap(response -> {
                                    System.out.println("Respuesta de la API externa: " + response);
                                    System.out.println("Respuesta de la API: " + responseBody);
                                    return Mono.just(ResponseEntity.ok()
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                            .body(responseBody + "\n\n" + decodedTokenInfo));
                                })
                                .onErrorResume(e -> {
                                    e.printStackTrace();
                                    return Mono.empty();
                                });
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.isEmpty() ? "Sin datos" : responseEntities.get(0));
    }

    protected String getApiKey() {
        if (apiUrl.contains("basketball")) {
            return basketballApiSportsKey;
        } else if (apiUrl.contains("football")) {
            return footballApiSportsKey;
        } else if (apiUrl.contains("rugby")) {
            return rugbyApiSportsKey;
        } else {
            return baseballApiSportsKey;

        }
    }

    // Método para extraer el tipo de deporte de la URL
    private String extractApiPath(String apiUrl) {
        if (apiUrl.contains("basketball")) {
            return "basketball";
        } else if (apiUrl.contains("football")) {
            return "football";
        } else if (apiUrl.contains("rugby")) {
            return "rugby";
        } else {
            return "baseball";
        }
    }
}
