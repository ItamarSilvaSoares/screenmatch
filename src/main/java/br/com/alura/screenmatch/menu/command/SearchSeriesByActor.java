package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SerieService;
import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Nome("Buscar Série Pelo Ator")
public class SearchSeriesByActor extends Command {
  private final Scanner scanner;
  private final SerieService serieService;

  SearchSeriesByActor(Scanner scanner, SerieService serieService) {
    super(
        OperationId.SEARCH_BY_ACTOR.getOperationId(), OperationId.SEARCH_BY_ACTOR.getDescription());
    this.scanner = scanner;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Digite o nome de ator para abusca: ");
    String nome = scanner.nextLine();
    System.out.println("Deseja filtra as series por notas? (s/N)");
    String filtro = scanner.nextLine();
    filtro = filtro.isEmpty() ? "n" : filtro;

    double rating = Double.NaN;

    if (filtro.equalsIgnoreCase("s") || filtro.equalsIgnoreCase("sim")) {
      try {
        System.out.println("Digite o nota minima: ");
        rating = this.scanner.nextDouble();

      } catch (InputMismatchException erro) {
        log.warn("Entrada do usuário invalida!: {}", erro.getMessage());
      }
    }
    this.serieService.findByNomeActor(nome, rating).forEach(System.out::println);
  }
}
