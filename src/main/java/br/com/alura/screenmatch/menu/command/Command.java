package br.com.alura.screenmatch.menu.command;

import java.util.Scanner;
import java.util.function.Function;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Command {
  private final Scanner scanner;
  @Getter protected String operationId;
  protected String description;

  protected Command(Scanner scanner, String operationId, String description) {
    this.scanner = scanner;
    this.operationId = operationId;
    this.description = description;
  }

  protected String getString() {
    return scanner.nextLine();
  }

  protected int getInt() {
    return parseInput(Integer::parseInt, 0);
  }

  protected double getDouble() {
    return parseInput(Double::parseDouble, 0.0);
  }

  private <T> T parseInput(Function<String, T> parser, T defaultValue) {
    String value = this.scanner.nextLine().trim();

    if (value.isEmpty()) {
      log.info("Entrada vazia");
      return defaultValue;
    }

    try {
      return parser.apply(value);
    } catch (NumberFormatException e) {
      log.info("Valor inválido: {}", value);
      return defaultValue;
    }
  }

  public abstract void executar();

  @Override
  public String toString() {
    return this.operationId + " - " + this.description;
  }
}
