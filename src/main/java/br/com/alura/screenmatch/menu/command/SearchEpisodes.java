package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SearchSerieEngine;
import org.springframework.stereotype.Component;

@Component
@Nome("Buscar Episódios")
public class SearchEpisodes extends Command {

  public SearchEpisodes(SearchSerieEngine searchSerieEngine) {
    super(OperationId.SEARCH_EPISODE.getOperationId(), SearchEpisodes.class, searchSerieEngine);
  }

  @Override
  public void executar() {}
}
