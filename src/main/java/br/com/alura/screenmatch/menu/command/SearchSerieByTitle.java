package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsoleReader;
import br.com.alura.screenmatch.service.SerieService;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SearchSerieByTitle extends Command {
  private final SerieService serieService;
  private final ConsoleReader reader;

  protected SearchSerieByTitle(SerieService serieService, ConsoleReader reader) {
    super(
        OperationId.SEARCH_SERIE_BY_NAME.getOperationId(),
        OperationId.SEARCH_SERIE_BY_NAME.getDescription());
    this.serieService = serieService;
    this.reader = reader;
  }

  @Override
  public void executar() {
    System.out.println("Escolha um série pelo nome: ");
    String nome = this.reader.getString();

    Optional<Serie> serie = this.serieService.findSerieByName(nome);

    serie.ifPresent(System.out::println);
  }
}
