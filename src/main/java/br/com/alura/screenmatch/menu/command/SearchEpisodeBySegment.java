package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.EpisodeService;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class SearchEpisodeBySegment extends Command {

  private final EpisodeService episodeService;

  public SearchEpisodeBySegment(Scanner scanner, EpisodeService episodeService) {
    super(
        scanner,
        OperationId.SEARCH_EPISODE_BY_SEGMENT.getOperationId(),
        OperationId.SEARCH_EPISODE_BY_SEGMENT.getDescription());

    this.episodeService = episodeService;
  }

  @Override
  public void executar() {
    System.out.println("Qual o nome do episódio para busca?");
    String trechoEpisodio = this.getString();
    List<Episodio> serieList = this.episodeService.searchByEpisodeName(trechoEpisodio);
    serieList.forEach(
        e ->
            System.out.printf(
                "Série: %s Temporada %s - Episódio %s - %s\n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()));
  }
}
