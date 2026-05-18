package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SearchSerieEngine;
import br.com.alura.screenmatch.service.SeasonHelper;
import br.com.alura.screenmatch.service.SerieService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
@Nome("Buscar Episódios")
public class SearchEpisodes extends Command {
  private final SerieService serieService;
  private final SearchSerieEngine searchSerieEngine;

  public SearchEpisodes(SerieService serieService, SearchSerieEngine searchSerieEngine) {
    super(OperationId.SEARCH_EPISODE.getOperationId(), SearchEpisodes.class);
    this.serieService = serieService;
    this.searchSerieEngine = searchSerieEngine;
  }

  @Override
  public void executar() {
    List<Serie> serieList =
        this.serieService.findAll().stream()
            .sorted(Comparator.comparing(Serie::getTitulo))
            .peek(System.out::println)
            .toList();

    String nomeSerie =
        searchSerieEngine.getInput("Digite o nome da série para a busca os episódios");

    Optional<Serie> optionalSerie =
        serieList.stream()
            .filter(
                s -> s.getTitulo().toLowerCase().trim().contains(nomeSerie.toLowerCase().trim()))
            .findFirst();

    List<DadosTemporada> temporadas = new ArrayList<>();

    if (optionalSerie.isPresent()) {
      Serie serie = optionalSerie.get();
      for (int i = 1; i < serie.getTotalTemporadas(); i++) {
        DadosTemporada dadosTemporada =
            searchSerieEngine.searchEpisode(SeasonHelper.of(i, serie.getTitulo()));
        temporadas.add(dadosTemporada);
      }
      List<Episodio> episodios =
          temporadas.stream()
              .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.season(), e)))
              .toList();

      serie.setEpisodios(episodios);

      this.serieService.salvar(serie);

    } else {
      System.out.println("Série não encontrada");
    }
  }
}
