package br.com.alura.screenmatch.menu.command;

public enum OperationId {
  SEARCH_SERIE("1"),
  SEARCH_EPISODE("2"),
  LIST_SERIE("3"),
  EXIT("0")
  ;

  OperationId(String operationId) {
    this.operationId = operationId;
  }

  private final String operationId;

  public String getOperationId() {
    return operationId;
  }

}
