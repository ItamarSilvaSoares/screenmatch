package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import br.com.alura.screenmatch.util.SearchSerieEngine;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SearchSeries extends Command {

  private final SerieService serieService;
  private final SearchSerieEngine searchSerieEngine;

  public SearchSeries(SearchSerieEngine searchSerieEngine, SerieService serieService) {
    super(OperationId.SEARCH_SERIE.getOperationId(), OperationId.SEARCH_SERIE.getDescription());

    this.searchSerieEngine = searchSerieEngine;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    final Optional<Serie> serie = this.searchSerieEngine.searchSerie();

    serie.ifPresent(this.serieService::save);
  }
}
