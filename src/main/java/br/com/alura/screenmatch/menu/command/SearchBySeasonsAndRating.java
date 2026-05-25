package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class SearchBySeasonsAndRating extends Command {
  private final Scanner scanner;
  private final SerieService serieService;

  protected SearchBySeasonsAndRating(Scanner scanner, SerieService serieService) {
    super(
        OperationId.SEARCH_BY_SEASON.getOperationId(),
        OperationId.SEARCH_BY_SEASON.getDescription());
    this.scanner = scanner;
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
    try {
      String number = this.scanner.nextLine();
      return Integer.parseInt(number);
    } catch (NumberFormatException e) {
      return quantTemporadas;
    }
  }

  private double getRatingSerie() {
    double avaliacaoMinima = 0.0;
    try {
      String rating = this.scanner.nextLine();
      return Double.parseDouble(rating);
    } catch (NumberFormatException e) {
      return avaliacaoMinima;
    }
  }
}
