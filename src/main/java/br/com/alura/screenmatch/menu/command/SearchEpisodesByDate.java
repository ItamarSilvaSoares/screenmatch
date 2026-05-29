package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.exceptions.NotFoundSerieException;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.EpisodeService;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchEpisodesByDate extends Command {
  private final EpisodeService episodeService;
  private final SerieService serieService;

  protected SearchEpisodesByDate(
      Scanner scanner, EpisodeService episodeService, SerieService serieService) {
    super(
        scanner,
        OperationId.SEARCH_EPISODE_BY_DATE.getOperationId(),
        OperationId.SEARCH_EPISODE_BY_DATE.getDescription());
    this.episodeService = episodeService;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.getString();

    System.out.println("Digite o ano limite de lançamento");
    int date = this.getInt();

    try {
      Serie serie = this.serieService.findByNameSerie(nome);

      List<Episodio> episodios = this.episodeService.searchEpisodesByData(serie, date);

      episodios.forEach(System.out::println);

    } catch (NotFoundSerieException e) {
      log.error(e.getMessage());
    }
  }
}
