package br.com.alura.screenmatch.model;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ConverteDados implements IConverteDados {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T obterDados(String json, Class<T> classe) {
    return this.mapper.readValue(json, classe);
  }
}
