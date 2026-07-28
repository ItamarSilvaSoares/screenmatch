package br.com.alura.screenmatch.serie.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.season.entity.DadosTemporada;
import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.validations.ValidationNumberSeason;
import br.com.alura.screenmatch.util.http.SeriesApiClient;
import br.com.alura.screenmatch.util.record.SeasonHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SerieService {

  private final SeriesApiClient seriesApiClient;
  private final SerieQueryService serieQueryService;

  public void save(Serie serie) {
    this.serieQueryService.salvar(serie);

    log.info("Serie salva com sucesso");
  }

  public List<SerieDto> findAll() {
    return toDto(this.serieQueryService.findAll());

  }

  public Optional<Serie> findSerieByName(String nomeSerie) {

    List<Serie> series = this.serieQueryService.findByNameSerie(nomeSerie);

    if (series.size() > 1) {
      log.warn("Mais de um resultado encontrado para o nome {}: {}", nomeSerie, series);
      return Optional.empty();
    }

    Optional<Serie> result = series.stream().findFirst();

    if (result.isEmpty()) {
      log.warn("Serie: {} não encontrada", nomeSerie);
    }

    return result;
  }

  public void searchEpisodesSeries(String nomeSerie) {
    Optional<Serie> serie = findSerieByName(nomeSerie);

    serie.ifPresent(
        s -> {
          List<DadosTemporada> temporadas = new ArrayList<>();
          for (int i = 1; i <= s.getTotalTemporadas(); i++) {
            temporadas.add(this.seriesApiClient.searchEpisode(SeasonHelper.of(i, s.getTitulo())));
          }

          List<Episodio> episodios =
              temporadas.stream()
                  .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.season(), e)))
                  .toList();

          s.setEpisodios(episodios);
          this.serieQueryService.salvar(s);
        });
  }

  public List<Serie> searchByCategory(String text) {
    Categoria categoria = Categoria.fromPortugues(text);

    return this.serieQueryService.searchByCategory(categoria);
  }

  public List<Serie> searchBySeasonsAndRating(int quantTemporadas, double avaliacaoMinima) {

    return this.serieQueryService.searchBySeasonsAndRating(
        ValidationNumberSeason.getNumberOfSeasons(quantTemporadas), avaliacaoMinima);
  }

  public List<Serie> findSeriesByNomeActor(String nome, double rating) {
    return this.serieQueryService.findByNomeActor(nome, rating);
  }

  public List<SerieDto> topFive() {
    return toDto(this.serieQueryService.topFive());
  }

  public List<SerieDto> topFiveOrderByDate() {
    return toDto(this.serieQueryService.topFiveOrderByDate());
  }

  public SerieDto getById(Long id) {
    Optional<Serie> optionalSerie = this.serieQueryService.getSerieById(id);

    return optionalSerie.map(s -> toDto(List.of(s)).getFirst()).orElse(SerieDto.empty(id));

  }

  public List<SerieDto> getSeriesByCategory(String nomeGenero) {
    Categoria categoria = Categoria.fromPortugues(nomeGenero);
    List<Serie> series = this.serieQueryService.searchByCategory(categoria);
    return toDto(series);
  }

  private List<SerieDto> toDto(List<Serie> series) {
    if (series.isEmpty()) {
      return Collections.emptyList();
    }

    return series.stream()
        .map(SerieDto::new)
        .toList();
  }

}

