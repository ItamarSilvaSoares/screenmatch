package br.com.alura.screenmatch.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class ConsumoAPI {

  public JsonNode obterDados(String endereco) {
    HttpResponse<String> response;
    try (HttpClient client = HttpClient.newHttpClient()) {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(response.body());
  }
}
