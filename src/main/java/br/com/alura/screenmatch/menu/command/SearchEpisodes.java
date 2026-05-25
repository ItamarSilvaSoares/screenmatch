package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.exceptions.NotFoundSerieException;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SearchSerieEngine;
import br.com.alura.screenmatch.service.SeasonHelper;
import br.com.alura.screenmatch.service.SerieService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchEpisodes extends Command {
  private final SerieService serieService;
  private final SearchSerieEngine searchSerieEngine;

  public SearchEpisodes(SerieService serieService, SearchSerieEngine searchSerieEngine) {
    super(OperationId.SEARCH_EPISODE.getOperationId(), OperationId.SEARCH_EPISODE.getDescription());
    this.serieService = serieService;
    this.searchSerieEngine = searchSerieEngine;
  }

  @Override
  public void executar() {
    this.serieService.findAll().stream()
        .sorted(Comparator.comparing(Serie::getTitulo))
        .forEach(System.out::println);

    String nomeSerie =
        searchSerieEngine.getInput("Digite o nome da série para a busca os episódios");

    try {
      Serie serie = this.serieService.findByNameSerie(nomeSerie);
      List<DadosTemporada> temporadas = new ArrayList<>();

      for (int i = 1; i < serie.getTotalTemporadas(); i++) {

        DadosTemporada dadosTemporada =
            searchSerieEngine.searchEpisode(SeasonHelper.of(i, serie.getTitulo()));
        temporadas.add(dadosTemporada);
        List<Episodio> episodios =
            temporadas.stream()
                .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.season(), e)))
                .toList();

        serie.setEpisodios(episodios);

        this.serieService.salvar(serie);
      }
    } catch (NotFoundSerieException e) {
      log.error(e.getMessage());
    }
  }
}
