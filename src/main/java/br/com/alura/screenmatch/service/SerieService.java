package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repositories.SerieRepo;
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
  private SerieRepo serieRepo;
  private final SearchSerieEngine searchSerieEngine;

  public void save(Serie serie) {
    try {
      this.serieRepo.salvar(serie);

      log.info("Serie salva com sucesso");

    } catch (DataIntegrityViolationException e) {
      String msg =
          String.format(
              "Erro ao salvar serie de dados, a serie %s já está no sistema.", serie.getTitulo());
      log.error(msg);
    }
  }

  public List<Serie> findAll() {
    return this.serieRepo.findAll();
  }

  public Optional<Serie> findSerieByName(String nomeSerie) {

    Optional<Serie> serie = this.serieRepo.findByNameSerie(nomeSerie);

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

            this.serieRepo.salvar(s);
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

    return this.serieRepo.searchByCategory(categoria);
  }

  public List<Serie> searchBySeasonsAndRating(int quantTemporadas, double avaliacaoMinima) {

    return this.serieRepo.searchBySeasonsAndRating(
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
    return this.serieRepo.findByNomeActor(nome, rating);
  }

  public List<Serie> topFive() {
    return this.serieRepo.topFive();
  }
}
