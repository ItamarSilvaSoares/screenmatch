package br.com.alura.screenmatch.menu.command;

import lombok.Getter;

public abstract class Command {
  @Getter protected String operationId;
  protected String description;

  protected Command(String operationId, String description) {
    this.operationId = operationId;
    this.description = description;
  }

    public abstract void executar();

  @Override
  public String toString() {
    return this.operationId + " - " + this.description;
  }
}
