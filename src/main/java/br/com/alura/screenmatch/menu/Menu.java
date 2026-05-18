package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.menu.command.Command;
import br.com.alura.screenmatch.menu.command.CreatListCommand;
import br.com.alura.screenmatch.menu.command.OperationId;
import br.com.alura.screenmatch.model.ConverteDados;
import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Menu {
  private final CreatListCommand listCommand;
  private final ConsumoAPI consumo = new ConsumoAPI();
  private String nomeSerie;

  public Menu(CreatListCommand listCommand) {
    this.listCommand = listCommand;
  }

  public void exibirMenu() {
    Map<String, Command> comandos = this.listCommand.create();

    Scanner leitura = new Scanner(System.in);
    String opcao = "";

    do {
      comandos.forEach((_, c) -> System.out.println(c));

      if (leitura.hasNextLine()) {
        opcao = leitura.nextLine();
      }

      Command comando = comandos.get(opcao);

      if (comando != null) {
        comando.executar();
        continue;
      }

      System.out.println("\nOpção Invalida\n");

    } while (!opcao.equals(OperationId.EXIT.getOperationId()));
  }
}
