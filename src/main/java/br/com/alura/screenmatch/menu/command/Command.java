package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SearchSerieEngine;

public abstract class Command {
  protected String operationId;
  protected String description;
  protected SearchSerieEngine searchSerieEngine;

  public Command(String operationId, Class<?> clazz, SearchSerieEngine searchSerieEngine) {
    this.operationId = operationId;
    this.description = getDescription(clazz);
    this.searchSerieEngine = searchSerieEngine;
  }

  private String getDescription(Class<?> clazz) {
    Nome nome = clazz.getAnnotation(Nome.class);

    return nome != null ? nome.value() : formatDescription(clazz.getSimpleName());
  }

  private String formatDescription(String description) {
    return description.replaceAll("(?<!^)([A-Z])", " $1");
  }

  public abstract void executar();

  @Override
  public String toString() {
    return this.operationId + " - " + this.description;
  }
}
