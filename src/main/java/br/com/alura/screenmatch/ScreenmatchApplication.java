package br.com.alura.screenmatch;

import br.com.alura.screenmatch.menu.Menu;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String @NonNull ... args) {
		Menu menu = new Menu();
		menu.exibirMenu();

	}
}
