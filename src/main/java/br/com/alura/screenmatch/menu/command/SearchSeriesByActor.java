package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.serie.service.SerieService;
import br.com.alura.screenmatch.util.ConsoleReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Nome("Buscar Série Pelo Ator")
public class SearchSeriesByActor extends Command {

  private final SerieService serieService;
  private final ConsoleReader reader;

  SearchSeriesByActor(SerieService serieService, ConsoleReader reader) {
    super(

        OperationId.SEARCH_BY_ACTOR.getOperationId(),
        OperationId.SEARCH_BY_ACTOR.getDescription());
    this.serieService = serieService;
    this.reader = reader;
  }

  @Override
  public void executar() {
    System.out.println("Digite o nome de ator para abusca: ");
    String nome = this.reader.getString();
    System.out.println("Deseja filtra as series por notas? (s/N)");
    String filtro = this.reader.getString();
    filtro = filtro.isEmpty() ? "n" : filtro;

    double rating = Double.NaN;

    if (filtro.equalsIgnoreCase("s") || filtro.equalsIgnoreCase("sim")) {

      System.out.println("Digite o nota minima: ");
      rating = this.reader.getDouble();
    }

    this.serieService.findSeriesByNomeActor(nome, rating).forEach(System.out::println);
  }
}
