package br.com.alura.screenmatch;

import br.com.alura.screenmatch.menu.Menu;
import io.github.cdimascio.dotenv.Dotenv;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
  private final Menu menu;

  public ScreenmatchApplication(Menu menu) {
    this.menu = menu;
  }

  static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();

    System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
    System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    System.setProperty("DB_URL", dotenv.get("DB_URL"));
    System.setProperty("DB_NAME", dotenv.get("DB_NAME"));

    SpringApplication.run(ScreenmatchApplication.class, args);
  }

  @Override
  public void run(String @NonNull ... args) {
    this.menu.exibirMenu();
  }
}
