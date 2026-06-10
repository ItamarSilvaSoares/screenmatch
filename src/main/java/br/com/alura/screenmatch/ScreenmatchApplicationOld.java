package br.com.alura.screenmatch;

import br.com.alura.screenmatch.menu.Menu;
import lombok.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationPropertiesScan
@Deprecated
//@SpringBootApplication
public class ScreenmatchApplicationOld implements CommandLineRunner {

  private final Menu menu;


  public ScreenmatchApplicationOld(Menu menu) {
    this.menu = menu;

  }

  static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplicationOld.class, args);
  }

  @Override
  public void run(String @NonNull ... args) {

    this.menu.exibirMenu();
  }
}
