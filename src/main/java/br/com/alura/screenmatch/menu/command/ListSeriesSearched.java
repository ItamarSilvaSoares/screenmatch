package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
@Nome("Listar Séries Buscadas")
public class ListSeriesSearched extends Command {
  private final SerieService serieService;

  public ListSeriesSearched(SerieService serieService) {
    super(OperationId.LIST_SERIE.getOperationId(), ListSeriesSearched.class);
    this.serieService = serieService;
  }

  @Override
  public void executar() {

    this.serieService.findAll().stream()
        .sorted(Comparator.comparing(s -> s.getGenero().name()))
        .forEach(System.out::println);
  }
}
