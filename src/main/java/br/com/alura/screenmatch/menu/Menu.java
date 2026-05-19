package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.menu.command.Command;
import br.com.alura.screenmatch.menu.command.CreatListCommand;
import br.com.alura.screenmatch.menu.command.OperationId;
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
      comandos.forEach((_, c) -> System.out.println(c));

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
