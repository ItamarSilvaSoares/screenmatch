package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.episode.service.EpisodeService;
import br.com.alura.screenmatch.util.ConsoleReader;
import org.springframework.stereotype.Component;

@Component
public class SearchEpisodeBySegment extends Command {

  private final ConsoleReader reader;

  private final EpisodeService episodeService;

  public SearchEpisodeBySegment(ConsoleReader console, EpisodeService episodeService) {
    super(
        OperationId.SEARCH_EPISODE_BY_SEGMENT.getOperationId(),
        OperationId.SEARCH_EPISODE_BY_SEGMENT.getDescription());

    this.reader = console;
    this.episodeService = episodeService;
  }

  @Override
  public void executar() {
    System.out.println("Qual o nome do episódio para busca?");
    String episodeName = this.reader.getString();
    this.episodeService
        .searchByEpisodeName(episodeName)
        .forEach(
            e ->
                System.out.printf(
                    "Série: %s Temporada %s - Episódio %s - %s\n",
                    e.getSerie().getTitulo(),
                    e.getTemporada(),
                    e.getNumeroEpisodio(),
                    e.getTitulo()));
  }
}
