package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SearchSerieEngine;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreatListCommand {
  public static Map<String, Command> create() {
    Map<String, Command> comandos = new LinkedHashMap<>();

    SearchSerieEngine searchSerieEngine = new SearchSerieEngine();

    comandos.put(OperationId.SEARCH_SERIE.getOperationId(), new SearchSeries(searchSerieEngine));
    comandos.put(
        OperationId.SEARCH_EPISODE.getOperationId(), new SearchEpisodes(searchSerieEngine));
    comandos.put(
        OperationId.LIST_SERIE.getOperationId(), new ListSeriesSearched(searchSerieEngine));

    comandos.put(OperationId.EXIT.getOperationId(), new Exit());

    return comandos;
  }
}
