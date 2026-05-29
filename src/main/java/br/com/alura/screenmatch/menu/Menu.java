package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.menu.command.Command;
import br.com.alura.screenmatch.menu.command.CreatListCommand;
import br.com.alura.screenmatch.menu.command.OperationId;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Menu {
  private final CreatListCommand listCommand;
  private final Scanner scanner;

  public Menu(CreatListCommand listCommand, Scanner scanner) {
    this.listCommand = listCommand;
    this.scanner = scanner;
  }

  public void exibirMenu() {
    Map<String, Command> comandos = this.listCommand.create();

    String opcao;

    do {
      comandos.entrySet().stream()
          .sorted(Comparator.comparing(e -> Integer.parseInt(e.getKey())))
          .forEach((c) -> System.out.println(c.getValue()));

      opcao = this.scanner.nextLine();

      Command comando = comandos.get(opcao);

      if (comando != null) {
        comando.executar();
        continue;
      }

      System.out.println("\nOpção Invalida\n");

    } while (!opcao.equals(OperationId.EXIT.getOperationId()));
  }
}
