package com.NexusSportHub.NexusSportHub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;

@Service
public class FootballApiService {

    // creamos nuevo webclient para poder usarlo en los métodos
    private final WebClient footballWebClient;

    // pasamos la key definida en el archivo application.properties
    @Value("${football.api-sports.key}")
    private String footballApiSportsKey;

    public FootballApiService(WebClient.Builder webClientBuilder) {

        // configuramos la cabecera necesaria de la api para poder acceder a la
        // información
        this.footballWebClient = webClientBuilder
                .baseUrl("https://v3.football.api-sports.io/")
                .defaultHeader("x-apisports-key", footballApiSportsKey)
                .build();

    }

    // Hacemos un método que recibe el token decodificado del archivo JwtTokenDecode
    // para poder pasarlo
    // en la cabecera de cada request realizada por el usuario
    public Mono<String> getJwtId(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            return Mono.just(JwtTokenDecoder.decodeJwtId(token));
        } catch (RuntimeException e) {
            return Mono.error(e);
        }
    }

    public Mono<Object> getFootballLeagues(HttpServletRequest request) {
        // creamos un nuevo webclient con la url del proyecto externo conectado a la
        // mongo
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
        // hacemos un método get para poder acceder a la información de la api
        // lo configuramos para mostrar por pantalla y enviar los datos a la mongo
        // mediante el método post
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v3.football.api-sports.io/leagues");
                        product.setPath("football");
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
                                    System.out.println("Respuesta de la API de Football: " + responseBody);

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

    public Mono<Object> getFootballSeasons(HttpServletRequest request) {
        // creamos un nuevo webclient con la url del proyecto externo conectado a la
        // mongo
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
        // hacemos un método get para poder acceder a la información de la api
        // lo configuramos para mostrar por pantalla y enviar los datos a la mongo
        // mediante el método post
        return footballWebClient.get()
                .uri("leagues/seasons")
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v3.football.api-sports.io/leagues/seasons");
                        product.setPath("football");
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
                                    System.out.println("Respuesta de la API de Football: " + responseBody);

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

    public Mono<Object> getFootballCountries(HttpServletRequest request) {
        // creamos un nuevo webclient con la url del proyecto externo conectado a la
        // mongo
        WebClient externalWebClient = WebClient.create("http://localhost:8082/api/products");
        // hacemos un método get para poder acceder a la información de la api
        // lo configuramos para mostrar por pantalla y enviar los datos a la mongo
        // mediante el método post
        return footballWebClient.get()
                .uri("/countries")
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

                        // Crear objeto DataModel con los datos de la solicitud POST
                        Product product = new Product();
                        product.setUserId(decodedTokenInfo);
                        product.setApiUrl("https://v3.football.api-sports.io/countries");
                        product.setPath("football");
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
                                    System.out.println("Respuesta de la API de Football: " + responseBody);

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
