package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.exceptions.NotFoundSerieException;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j // Cria a variável 'log' automaticamente para esta classe
public class SearchSerieByTitle extends Command {
  private final Scanner scanner;
  private final SerieService serieService;

  protected SearchSerieByTitle(Scanner scanner, SerieService serieService) {
    super(
        OperationId.SEARCH_SERIE_BY_NAME.getOperationId(),
        OperationId.SEARCH_SERIE_BY_NAME.getDescription());
    this.scanner = scanner;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.scanner.nextLine();

    try {
      Serie serie = this.serieService.findByNameSerie(nome);

      log.info("Serie {} foi encontrado", nome);

      System.out.println(serie);
    } catch (NotFoundSerieException e) {
      log.error(e.getMessage());
    }
  }
}
