package br.com.alura.screenmatch.menu.command;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreatListCommand {
  public static Map<String, Command> create() {
    Map<String, Command> comandos = new LinkedHashMap<>();

    comandos.put(OperationId.SEARCH_SERIE.getOperationId(), new BuscarSeries());
    comandos.put(OperationId.SEARCH_EPISODE.getOperationId(), new BuscarEpisodios());
    comandos.put(OperationId.LIST_SERIE.getOperationId(), new ListarSeriesBuscadas());
    comandos.put(OperationId.EXIT.getOperationId(), new Sair());

    return comandos;
  }
}
