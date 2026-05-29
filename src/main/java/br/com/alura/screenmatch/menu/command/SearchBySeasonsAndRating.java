package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class SearchBySeasonsAndRating extends Command {
  private final SerieService serieService;

  protected SearchBySeasonsAndRating(Scanner scanner, SerieService serieService) {
    super(
        scanner,
        OperationId.SEARCH_BY_SEASON.getOperationId(),
        OperationId.SEARCH_BY_SEASON.getDescription());
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Você quer ver séries com até quantas temporadas?");
    int quantTemporadas = getNumberOfSeasons();
    System.out.println("Qual é a avaliação minima que a serie tem que ter?");
    double avaliacaoMinima = getRatingSerie();

    List<Serie> serieList =
        this.serieService.searchBySeasonsAndRating(quantTemporadas, avaliacaoMinima);
    serieList.forEach(System.out::println);
  }

  private int getNumberOfSeasons() {
    int quantTemporadas = 50;

    int number = this.getInt();

    if (number == 0) {
      return quantTemporadas;
    }

    return number;
  }

  private double getRatingSerie() {
    return this.getDouble();
  }
}
