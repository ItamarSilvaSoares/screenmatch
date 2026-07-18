package br.com.alura.screenmatch.util.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class HttpClient {

  public JsonNode obterDados(String uri) {
    HttpResponse<String> response;
    try (java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient()) {
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
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
