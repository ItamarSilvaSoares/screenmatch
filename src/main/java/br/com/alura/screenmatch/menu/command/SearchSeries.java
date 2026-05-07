package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SearchSerieEngine;

@Nome("Buscar Séries")
public class SearchSeries extends Command {

  public SearchSeries(SearchSerieEngine searchSerieEngine) {
    super(OperationId.SEARCH_SERIE.getOperationId(), SearchSeries.class, searchSerieEngine);
  }

  @Override
  public void executar() {
    this.searchSerieEngine.search();
  }
}
