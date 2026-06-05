package br.com.alura.screenmatch.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {

    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    Map<String, Object> props = new HashMap<>();

    dotenv.entries().forEach(entry -> props.put(entry.getKey(), entry.getValue()));

    environment.getPropertySources().addFirst(new MapPropertySource("dotenv", props));
  }
}
