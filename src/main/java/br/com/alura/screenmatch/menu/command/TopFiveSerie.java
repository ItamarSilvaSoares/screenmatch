package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TopFiveSerie extends Command {
  private final SerieService serieService;

  protected TopFiveSerie(SerieService serieService) {
    super(OperationId.TOP_FIVE_SERIE.getOperationId(), OperationId.TOP_FIVE_SERIE.getDescription());
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    List<Serie> topFive = this.serieService.topFive();

    System.out.println("Top Five Series");
    topFive.forEach(System.out::println);
  }
}
