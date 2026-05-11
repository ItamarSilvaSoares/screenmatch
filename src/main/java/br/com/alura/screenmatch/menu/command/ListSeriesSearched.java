package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SearchSerieEngine;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
@Nome("Listar Séries Buscadas")
public class ListSeriesSearched extends Command {

  public ListSeriesSearched(SearchSerieEngine searchSerieEngine) {
    super(OperationId.LIST_SERIE.getOperationId(), ListSeriesSearched.class, searchSerieEngine);
  }

  @Override
  public void executar() {

    this.searchSerieEngine.getDadosSeries().stream()
        .map(Serie::new)
        .sorted(Comparator.comparing(Serie::getGenero))
        .forEach(System.out::println);
  }
}
