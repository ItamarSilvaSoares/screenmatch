package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SearchSerieEngine;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.stereotype.Component;

@Component
@Nome("Buscar Séries")
public class SearchSeries extends Command {
  private final SearchSerieEngine searchSerieEngine;
  private final SerieService serieService;

  public SearchSeries(SearchSerieEngine searchSerieEngine, SerieService serieService) {
    super(OperationId.SEARCH_SERIE.getOperationId(), SearchSeries.class, searchSerieEngine);

    this.searchSerieEngine = searchSerieEngine;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    this.searchSerieEngine.search();
    this.serieService.salvar(this.searchSerieEngine.getDadosSeries());
  }
}
