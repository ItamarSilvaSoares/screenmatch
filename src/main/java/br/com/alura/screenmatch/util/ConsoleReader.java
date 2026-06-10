package br.com.alura.screenmatch.util;

import java.util.Scanner;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsoleReader {

  private final Scanner scanner;

  public ConsoleReader(Scanner scanner) {
    this.scanner = scanner;
  }

  public String getString() {
    return scanner.nextLine().trim();
  }

  public int getInt() {
    return parseInput(Integer::parseInt, 0);
  }

  public double getDouble() {
    return parseInput(Double::parseDouble, 0.0);
  }

  private <T> T parseInput(Function<String, T> parser, T defaultValue) {
    String value = scanner.nextLine().trim();

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
}
