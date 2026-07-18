package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import br.com.alura.screenmatch.util.http.SeriesApiClient;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SearchSeries extends Command {

  private final SerieService serieService;
  private final SeriesApiClient seriesApiClient;

  public SearchSeries(SeriesApiClient seriesApiClient, SerieService serieService) {
    super(OperationId.SEARCH_SERIE.getOperationId(), OperationId.SEARCH_SERIE.getDescription());

    this.seriesApiClient = seriesApiClient;
    this.serieService = serieService;
  }

  @Override
  public void executar() {
    final Optional<Serie> serie = this.seriesApiClient.searchSerie();

    serie.ifPresent(this.serieService::save);
  }
}
