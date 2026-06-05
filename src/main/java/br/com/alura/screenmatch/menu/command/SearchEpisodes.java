package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsoleReader;
import br.com.alura.screenmatch.service.SerieService;
import java.util.Comparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchEpisodes extends Command {
  private final SerieService serieService;
  private final ConsoleReader reader;

  public SearchEpisodes(SerieService serieService, ConsoleReader console) {
    super(OperationId.SEARCH_EPISODE.getOperationId(), OperationId.SEARCH_EPISODE.getDescription());
    this.serieService = serieService;
    this.reader = console;
  }

  @Override
  public void executar() {
    this.serieService.findAll().stream()
        .sorted(Comparator.comparing(Serie::getTitulo))
        .forEach(System.out::println);

    System.out.println("Digite o nome da série para a busca os episódios");

    String nomeSerie = this.reader.getString();

    this.serieService.searchEpisodesSeries(nomeSerie);
  }
}
