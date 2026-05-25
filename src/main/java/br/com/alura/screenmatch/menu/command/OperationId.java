package br.com.alura.screenmatch.menu.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationId {
  SEARCH_SERIE("1", "Pesquisar Séries"),
  SEARCH_EPISODE("2", "Pesquisar Episódios"),
  LIST_SERIE("3", "Listar Séries Pesquisadas"),
  SEARCH_SERIE_BY_NAME("4", "Buscar série por título"),
  SEARCH_BY_ACTOR("5", "Buscar Série Pelo Ator"),
  TOP_FIVE_SERIE("6", "Top 5 Séries"),
  SEARCH_BY_CATEGORIA("7", "Buscar séries por categoria"),
  SEARCH_BY_SEASON("8", "Buscar séries por temporadas e avaliação"),
  EXIT("0", "Sair");

  private final String operationId;
  private final String description;
}
