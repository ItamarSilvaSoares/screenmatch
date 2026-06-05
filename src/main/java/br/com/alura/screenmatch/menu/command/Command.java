package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.ConsoleReader;
import java.util.Scanner;
import java.util.function.Function;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
