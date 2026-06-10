package br.com.alura.screenmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ScreenmatchApplication {

  static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplication.class, args);
  }

}
