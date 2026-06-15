package br.com.alura.screenmatch.config;

import java.util.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScannerConfig {

  @Bean
  public Scanner scanner() {
    return new Scanner(System.in);
  }
}
