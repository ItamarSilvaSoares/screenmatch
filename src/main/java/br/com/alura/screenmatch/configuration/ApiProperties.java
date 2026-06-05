package br.com.alura.screenmatch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public record ApiProperties(String apiKey, String apiUrl) {}
