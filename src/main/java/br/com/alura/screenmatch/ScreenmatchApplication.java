package br.com.alura.screenmatch;

import br.com.alura.screenmatch.menu.Menu;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ScreenmatchApplication implements CommandLineRunner {
  private final Menu menu;


  public ScreenmatchApplication(Menu menu) {
    this.menu = menu;

  }

  static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplication.class, args);
  }

  @Override
  public void run(String @NonNull ... args) {

    this.menu.exibirMenu();
  }
}
