package br.com.alura.screenmatch.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApiProperties(@NotBlank String apiKey, @NotBlank String apiUrl) {

}
