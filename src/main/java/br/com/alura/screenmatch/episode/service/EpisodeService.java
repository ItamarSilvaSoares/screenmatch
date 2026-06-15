package br.com.alura.screenmatch.episode.service;

import br.com.alura.screenmatch.episode.dto.EpisodioDto;
import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EpisodeService {

  private final SerieService serieService;
  private EpisodeQueryService episodeQueryService;

  public List<Episodio> searchEpisodesByData(String serieName, int date) {
    Optional<Serie> serie = this.serieService.findSerieByName(serieName);

    if (serie.isEmpty()) {
      return List.of();
    }

    return this.episodeQueryService.searchEpisodesByData(serie.get().getTitulo(), date);
  }

  public List<EpisodioDto> searchTop5Episode(long serieId) {
    SerieDto serie = this.serieService.getById(serieId);

    return toEpisodioDto(this.episodeQueryService.searchTop5Episode(serie.titulo()));
  }

  public List<Episodio> searchTop5Episode(String nameSerie) {
    Optional<Serie> serie = this.serieService.findSerieByName(nameSerie);

    if (serie.isEmpty()) {
      return List.of();
    }

    return this.episodeQueryService.searchTop5Episode(serie.get().getTitulo());
  }

  public List<Episodio> searchByEpisodeName(String episodeName) {
    return this.episodeQueryService.searchByEpisodeName(episodeName);
  }

  public List<EpisodioDto> obterTodasTemporadas(Long id) {
    return toEpisodioDto(this.episodeQueryService.searchEpisodesBySereiId(id));
  }

  public List<EpisodioDto> obterTemporada(long id, int season) {
    List<Episodio> serie = this.episodeQueryService.searchEpisodesBySereiIdAndSeason(id, season);

    return toEpisodioDto(serie);

  }

  private List<EpisodioDto> toEpisodioDto(List<Episodio> episodios) {
    if (episodios.isEmpty()) {
      return Collections.emptyList();
    }
    return episodios.stream()
        .map(e -> new EpisodioDto(
            e.getTemporada(),
            e.getTitulo(),
            e.getNumeroEpisodio(),
            e.getAvaliacao()))
        .toList();
  }
}
