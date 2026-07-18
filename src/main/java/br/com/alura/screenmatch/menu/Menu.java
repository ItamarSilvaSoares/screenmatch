package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.menu.command.Command;
import br.com.alura.screenmatch.menu.command.CreatListCommand;
import br.com.alura.screenmatch.menu.command.OperationId;
import br.com.alura.screenmatch.util.date.ConsoleReader;
import java.util.Comparator;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Menu {

  private final CreatListCommand listCommand;
  private final ConsoleReader reader;

  public void exibirMenu() {
    Map<String, Command> comandos = this.listCommand.create();

    String opcao;

    do {
      comandos.entrySet().stream()
          .sorted(Comparator.comparing(e -> Integer.parseInt(e.getKey())))
          .forEach((c) -> System.out.println(c.getValue()));

      opcao = this.reader.getString();

      Command comando = comandos.get(opcao);

      if (comando != null) {
        comando.executar();
        continue;
      }

      System.out.println("\nOpção Invalida\n");

    } while (!opcao.equals(OperationId.EXIT.getOperationId()));
  }
}
