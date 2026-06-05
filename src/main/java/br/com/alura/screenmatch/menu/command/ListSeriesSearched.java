package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.service.SerieService;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class ListSeriesSearched extends Command {
  private final SerieService serieService;

  public ListSeriesSearched(SerieService serieService) {
    super(OperationId.LIST_SERIE.getOperationId(), OperationId.LIST_SERIE.getDescription());
    this.serieService = serieService;
  }

  @Override
  public void executar() {

    this.serieService.findAll().stream()
        .sorted(Comparator.comparing(s -> s.getGenero().name()))
        .forEach(System.out::println);
  }
}
