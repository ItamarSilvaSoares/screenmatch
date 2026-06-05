package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsoleReader;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SearchSerieByCategory extends Command {
  private final SerieService serieService;
  private final ConsoleReader reader;

  protected SearchSerieByCategory(SerieService serieService, ConsoleReader reader) {
    super(
        OperationId.SEARCH_BY_CATEGORIA.getOperationId(),
        OperationId.SEARCH_BY_CATEGORIA.getDescription());

    this.serieService = serieService;
    this.reader = reader;
  }

  @Override
  public void executar() {
    System.out.println("Digite o nome do categoria: ");
    String text = this.reader.getString();

    List<Serie> serieList = this.serieService.searchByCategory(text);
    System.out.println("Lista de serie: ");
    serieList.forEach(System.out::println);
  }
}
