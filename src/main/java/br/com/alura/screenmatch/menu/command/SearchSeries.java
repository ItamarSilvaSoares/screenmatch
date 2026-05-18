package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SearchSerieEngine;
import br.com.alura.screenmatch.service.SerieService;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
@Nome("Buscar Séries")
public class SearchSeries extends Command {
  private final SerieService serieService;
  private final SearchSerieEngine searchSerieEngine;

  public SearchSeries(SearchSerieEngine searchSerieEngine, SerieService serieService) {
    super(OperationId.SEARCH_SERIE.getOperationId(), SearchSeries.class);

    this.searchSerieEngine = searchSerieEngine;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    final Optional<Serie> serie = this.searchSerieEngine.searchSerie();

    serie.ifPresent(this.serieService::salvar);
  }
}
