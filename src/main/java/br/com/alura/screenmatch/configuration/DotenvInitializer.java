package br.com.alura.screenmatch.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

public class DotenvInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext context) {
    Dotenv dotenv = Dotenv.load();

    Map<String, Object> props = new HashMap<>();

    dotenv.entries().forEach(entry ->
        props.put(entry.getKey(), entry.getValue()));

    context.getEnvironment()
        .getPropertySources()
        .addFirst(new MapPropertySource("dotenv", props));
  }
}
