package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.ConsoleReader;
import br.com.alura.screenmatch.service.EpisodeService;
import org.springframework.stereotype.Component;

@Component
public class SearchEpisodesByDate extends Command {
  private final EpisodeService episodeService;
  private final ConsoleReader reader;

  protected SearchEpisodesByDate(EpisodeService episodeService, ConsoleReader consoleReader) {
    super(
        OperationId.SEARCH_EPISODE_BY_DATE.getOperationId(),
        OperationId.SEARCH_EPISODE_BY_DATE.getDescription());
    this.episodeService = episodeService;
    this.reader = consoleReader;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.reader.getString();

    System.out.println("Digite o ano limite de lançamento");
    int date = this.reader.getInt();

    this.episodeService.searchEpisodesByData(nome, date).forEach(System.out::println);
  }
}
