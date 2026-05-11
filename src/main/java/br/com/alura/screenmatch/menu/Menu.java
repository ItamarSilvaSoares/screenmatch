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
  private final Scanner leitura = new Scanner(System.in);
  private final ConsumoAPI consumo = new ConsumoAPI();
  private String nomeSerie;

  public Menu(CreatListCommand listCommand) {
    this.listCommand = listCommand;
  }

  public void exibirMenu() {
    Map<String, Command> comandos = this.listCommand.create();

    String opcao;

    do {
      comandos.forEach((_, c) -> System.out.println(c));
      opcao = leitura.nextLine();
      Command comando = comandos.get(opcao);

      if (comando != null) {
        comando.executar();
        continue;
      }

      System.out.println("\nOpção Invalida\n");

    } while (!opcao.equals(OperationId.EXIT.getOperationId()));
  }

  @Deprecated
  public void exibirMenuOld() {
    IO.println("Digite o nome da série para a busca");
    this.nomeSerie = leitura.nextLine().trim();

    var json = consumo.obterDados(getUrl(""));
    ConverteDados conversor = new ConverteDados();
    DadosSerie dados = conversor.obterDados(json.asString(), DadosSerie.class);

    IO.println(dados);

    List<DadosTemporada> temporadas = new ArrayList<>();

    for (int i = 1; i <= dados.totalTemporadas(); i++) {
      json = consumo.obterDados(getUrl("&season=" + i));
      DadosTemporada dadosTemporada = conversor.obterDados(json.asString(), DadosTemporada.class);
      temporadas.add(dadosTemporada);
    }
    temporadas.forEach(IO::println);

    temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    List<DadosEpisodio> dadosEpisodios =
        temporadas.stream().flatMap(t -> t.episodios().stream()).toList();

    System.out.println("\nTop 5 episódios");
    dadosEpisodios.stream()
        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
        .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
        .limit(5)
        .forEach(System.out::println);

    List<Episodio> episodios =
        temporadas.stream()
            .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.season(), d)))
            .toList();

    episodios.forEach(System.out::println);

    System.out.println("A partir de que ano você deseja ver os episódios? ");
    var ano = leitura.nextInt();
    leitura.nextLine();

    LocalDate dataBusca = LocalDate.of(ano, 1, 1);

    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    episodios.stream()
        .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
        .forEach(
            e ->
                System.out.println(
                    "Temporada:  "
                        + e.getTemporada()
                        + " Episódio: "
                        + e.getTitulo()
                        + " Data lançamento: "
                        + e.getDataLancamento().format(formatador)));

    Map<Integer, Double> avaliacoesPorTemporada =
        episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(
                Collectors.groupingBy(
                    Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
    System.out.println(avaliacoesPorTemporada);

    DoubleSummaryStatistics est =
        episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
    System.out.println("Média: " + est.getAverage());
    System.out.println("Melhor episódio: " + est.getMax());
    System.out.println("Pior episódio: " + est.getMin());
    System.out.println("Quantidade: " + est.getCount());
  }

  private String getUrl(String toConcatenate) {
    Dotenv dotenv = Dotenv.load();
    String ENDERECO = "https://www.omdbapi.com/?t=";
    String API_KEY = dotenv.get("API_KEY");
    return ENDERECO + this.nomeSerie.replace(" ", "+") + toConcatenate + API_KEY;
  }
}
