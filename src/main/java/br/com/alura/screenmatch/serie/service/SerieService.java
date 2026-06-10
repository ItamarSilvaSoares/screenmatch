package br.com.alura.screenmatch.serie.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.season.entity.DadosTemporada;
import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.util.SearchSerieEngine;
import br.com.alura.screenmatch.util.record.SeasonHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SerieService {

  private final SearchSerieEngine searchSerieEngine;
  private SerieQueryService serieQueryService;

  public void save(Serie serie) {
    try {
      this.serieQueryService.salvar(serie);

      log.info("Serie salva com sucesso");

    } catch (DataIntegrityViolationException e) {
      String msg =
          String.format(
              "Erro ao salvar serie de dados, a serie %s já está no sistema.", serie.getTitulo());
      log.error(msg);
    }
  }

  public List<Serie> findAll() {
    return this.serieQueryService.findAll();
  }

  public Optional<Serie> findSerieByName(String nomeSerie) {

    Optional<Serie> serie = this.serieQueryService.findByNameSerie(nomeSerie);

    if (serie.isEmpty()) {
      log.warn("Serie: {} não encontrado", nomeSerie);
    }

    return serie;
  }

  public void searchEpisodesSeries(String nomeSerie) {
    Optional<Serie> serie = findSerieByName(nomeSerie);

    serie.ifPresent(
        s -> {
          List<DadosTemporada> temporadas = new ArrayList<>();
          for (int i = 1; i < s.getTotalTemporadas(); i++) {

            DadosTemporada dadosTemporada =
                searchSerieEngine.searchEpisode(SeasonHelper.of(i, s.getTitulo()));
            temporadas.add(dadosTemporada);
            List<Episodio> episodios =
                temporadas.stream()
                    .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.season(), e)))
                    .toList();

            s.setEpisodios(episodios);

            this.serieQueryService.salvar(s);
          }
        });
  }

  public List<Serie> searchByCategory(String text) {
    Categoria categoria = Categoria.EMPTY;
    try {
      categoria = Categoria.fromPortugues(text);

    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
    }

    return this.serieQueryService.searchByCategory(categoria);
  }

  public List<Serie> searchBySeasonsAndRating(int quantTemporadas, double avaliacaoMinima) {

    return this.serieQueryService.searchBySeasonsAndRating(
        getNumberOfSeasons(quantTemporadas), avaliacaoMinima);
  }

  private int getNumberOfSeasons(int number) {
    int quantTemporadas = 50;

    if (number == 0) {
      return quantTemporadas;
    }

    return number;
  }

  public List<Serie> findSeriesByNomeActor(String nome, double rating) {
    return this.serieQueryService.findByNomeActor(nome, rating);
  }

  public List<Serie> topFive() {
    return this.serieQueryService.topFive();
  }
}
