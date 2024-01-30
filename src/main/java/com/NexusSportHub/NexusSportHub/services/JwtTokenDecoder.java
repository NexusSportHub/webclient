package com.NexusSportHub.NexusSportHub.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtTokenDecoder {

    public static String decodeJwtId(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            Base64.Decoder decoder = Base64.getDecoder();
            String[] array = token.split("\\.");

            if (array.length >= 2) {
                String decodedJson = new String(decoder.decode(array[1]), StandardCharsets.UTF_8);
                try {
                    // Parsear el JSON y extraer el campo "sub" (ID)
                    JsonNode jsonNode = new ObjectMapper().readTree(decodedJson);
                    return jsonNode.get("sub").asText();
                } catch (Exception e) {
                    throw new RuntimeException("Error al parsear el token JWT", e);
                }
            } else {
                throw new RuntimeException("Token JWT no tiene el formato esperado");
            }
        } else {
            throw new RuntimeException("No se encontró el token en el encabezado");
        }
    }
}
