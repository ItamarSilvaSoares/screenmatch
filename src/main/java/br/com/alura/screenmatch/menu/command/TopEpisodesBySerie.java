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
public class TopEpisodesBySerie extends Command {
  private final EpisodeService episodeService;
  private final SerieService serieService;

  public TopEpisodesBySerie(
      Scanner scanner, EpisodeService episodeService, SerieService serieService) {
    super(
        scanner,
        OperationId.TOP_EPISODES_BY_SERIE.getOperationId(),
        OperationId.TOP_EPISODES_BY_SERIE.getDescription());
    this.episodeService = episodeService;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.getString();

    try {
      Serie serie = this.serieService.findByNameSerie(nome);

      List<Episodio> episodios = this.episodeService.searchTop5Episode(serie);

      episodios.forEach(
          e ->
              System.out.printf(
                  "Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                  e.getSerie().getTitulo(),
                  e.getTemporada(),
                  e.getNumeroEpisodio(),
                  e.getTitulo(),
                  e.getAvaliacao()));

    } catch (NotFoundSerieException e) {
      log.error(e.getMessage());
    }
  }
}
