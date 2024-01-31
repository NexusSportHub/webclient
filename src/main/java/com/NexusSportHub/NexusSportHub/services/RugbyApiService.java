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
public class RugbyApiService {

    private final WebClient rugbyWebClient;

    @Value("${rugby.api-sports.key}")
    private String rugbyApiSportsKey;

    public RugbyApiService (WebClient.Builder webClientBuilder) {

        this.rugbyWebClient = webClientBuilder
                .baseUrl("https://v1.rugby.api-sports.io")
                .defaultHeader("x-apisports-key", rugbyApiSportsKey)
                .build();
    }

    public Mono<String> getJwtId(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            return Mono.just(JwtTokenDecoder.decodeJwtId(token));
        } catch (RuntimeException e) {
            return Mono.error(e);
        }
    }

    public Mono<Object> getRugbyLeagues(HttpServletRequest request) {
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v1.rugby.api-sports.io/leagues");
                        product.setPath("rugby");
                        product.setStatus(false);
                        product.setDate(Date.from(Instant.now()));
                        product.setPaidDate(new Date(0));
                        product.setApiResponse(responseBody);
                        product.setpaymentMethod("null");

                        // Realizar la solicitud POST a la API externa utilizando WebClient
                        return externalWebClient.post()
                                .uri("/insert")
                                .body(BodyInserters.fromValue(product))
                                .retrieve()
                                .bodyToMono(Product.class)
                                .flatMap(response -> {
                                    // Loguear la respuesta de la API externa si es necesario
                                    System.out.println("Respuesta de la API externa: " + response);

                                    // Mostrar la información de la API por pantalla
                                    System.out.println("Respuesta de la API de Rugby: " + responseBody);

                                    // Retornar la respuesta de la API externa si es relevante
                                    return Mono.just(ResponseEntity.ok()
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                            .body(responseBody + "\n\n" + decodedTokenInfo));
                                })
                                .onErrorResume(e -> {
                                    // Manejar el error según sea necesario
                                    e.printStackTrace(); // Loguear el error
                                    return Mono.empty();
                                });
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.isEmpty() ? "Sin datos" : responseEntities.get(0));
    }

    public Mono<Object> getRugbySeasons(HttpServletRequest request) {
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
        return rugbyWebClient.get()
                .uri("/seasons")
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v1.rugby.api-sports.io/seasons");
                        product.setPath("rugby");
                        product.setStatus(false);
                        product.setDate(Date.from(Instant.now()));
                        product.setPaidDate(new Date(0));
                        product.setApiResponse(responseBody);
                        product.setpaymentMethod("null");

                        // Realizar la solicitud POST a la API externa utilizando WebClient
                        return externalWebClient.post()
                                .uri("/insert")
                                .body(BodyInserters.fromValue(product))
                                .retrieve()
                                .bodyToMono(Product.class)
                                .flatMap(response -> {
                                    // Loguear la respuesta de la API externa si es necesario
                                    System.out.println("Respuesta de la API externa: " + response);

                                    // Mostrar la información de la API por pantalla
                                    System.out.println("Respuesta de la API de Rugby: " + responseBody);

                                    // Retornar la respuesta de la API externa si es relevante
                                    return Mono.just(ResponseEntity.ok()
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                            .body(responseBody + "\n\n" + decodedTokenInfo));
                                })
                                .onErrorResume(e -> {
                                    // Manejar el error según sea necesario
                                    e.printStackTrace(); // Loguear el error
                                    return Mono.empty();
                                });
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.isEmpty() ? "Sin datos" : responseEntities.get(0));
    }

    public Mono<Object> getRugbyCountries(HttpServletRequest request) {
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
        return rugbyWebClient.get()
                .uri("/countries")
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v1.rugby.api-sports.io/countries");
                        product.setPath("rugby");
                        product.setStatus(false);
                        product.setDate(Date.from(Instant.now()));
                        product.setPaidDate(new Date(0));
                        product.setApiResponse(responseBody);
                        product.setpaymentMethod("null");

                        // Realizar la solicitud POST a la API externa utilizando WebClient
                        return externalWebClient.post()
                                .uri("/insert")
                                .body(BodyInserters.fromValue(product))
                                .retrieve()
                                .bodyToMono(Product.class)
                                .flatMap(response -> {
                                    // Loguear la respuesta de la API externa si es necesario
                                    System.out.println("Respuesta de la API externa: " + response);

                                    // Mostrar la información de la API por pantalla
                                    System.out.println("Respuesta de la API de Rugby: " + responseBody);

                                    // Retornar la respuesta de la API externa si es relevante
                                    return Mono.just(ResponseEntity.ok()
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                            .body(responseBody + "\n\n" + decodedTokenInfo));
                                })
                                .onErrorResume(e -> {
                                    // Manejar el error según sea necesario
                                    e.printStackTrace(); // Loguear el error
                                    return Mono.empty();
                                });
                    });
                })
                .collectList()
                .map(responseEntities -> responseEntities.isEmpty() ? "Sin datos" : responseEntities.get(0));
    }

    
}
