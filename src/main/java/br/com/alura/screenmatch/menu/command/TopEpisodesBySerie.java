package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.episode.service.EpisodeService;
import br.com.alura.screenmatch.util.date.ConsoleReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopEpisodesBySerie extends Command {

  private final EpisodeService episodeService;
  private final ConsoleReader reader;

  public TopEpisodesBySerie(EpisodeService episodeService, ConsoleReader reader) {
    super(
        OperationId.TOP_EPISODES_BY_SERIE.getOperationId(),
        OperationId.TOP_EPISODES_BY_SERIE.getDescription());
    this.episodeService = episodeService;
    this.reader = reader;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.reader.getString();

    this.episodeService
        .searchTop5Episode(nome)
        .forEach(
            e ->
                System.out.printf(
                    "Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                    e.getSerie().getTitulo(),
                    e.getTemporada(),
                    e.getNumeroEpisodio(),
                    e.getTitulo(),
                    e.getAvaliacao()));
  }
}
