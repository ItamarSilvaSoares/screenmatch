package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.ConsoleReader;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.stereotype.Component;

@Component
public class SearchSerieBySeasonsAndRating extends Command {
  private final SerieService serieService;
  private final ConsoleReader reader;

  protected SearchSerieBySeasonsAndRating(SerieService serieService, ConsoleReader reader) {
    super(
        OperationId.SEARCH_BY_SEASON.getOperationId(),
        OperationId.SEARCH_BY_SEASON.getDescription());
    this.serieService = serieService;
    this.reader = reader;
  }

  @Override
  public void executar() {
    System.out.println("Você quer ver séries com até quantas temporadas?");
    int quantTemporadas = this.reader.getInt();
    System.out.println("Qual é a avaliação minima que a serie tem que ter?");
    double avaliacaoMinima = this.reader.getDouble();

    this.serieService
        .searchBySeasonsAndRating(quantTemporadas, avaliacaoMinima)
        .forEach(System.out::println);
  }
}
