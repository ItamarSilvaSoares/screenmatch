package br.com.alura.screenmatch.episode.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
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

    return this.episodeQueryService.searchEpisodesByData(serie.get(), date);
  }

  public List<Episodio> searchTop5Episode(String nameSerie) {
    Optional<Serie> serie = this.serieService.findSerieByName(nameSerie);

    if (serie.isEmpty()) {
      return List.of();
    }

    return this.episodeQueryService.searchTop5Episode(serie.get());
  }

  public List<Episodio> searchByEpisodeName(String episodeName) {
    return this.episodeQueryService.searchByEpisodeName(episodeName);
  }
}
