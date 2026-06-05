package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repositories.EpisodeRepo;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EpisodeService {
  private EpisodeRepo episodeRepo;
  private final SerieService serieService;


  public List<Episodio> searchEpisodesByData(String serieName, int date) {
    Optional<Serie> serie = this.serieService.findSerieByName(serieName);

    if (serie.isEmpty()) {
      return List.of();
    }

    return this.episodeRepo.searchEpisodesByData(serie.get(), date);
  }

  public List<Episodio> searchTop5Episode(String nameSerie) {
  Optional<Serie> serie = this.serieService.findSerieByName(nameSerie);

  if (serie.isEmpty()) {
  return List.of();
  }

  return this.episodeRepo.searchTop5Episode(serie.get());
  }

  public List<Episodio> searchByEpisodeName(String episodeName) {
    return this.episodeRepo.searchByEpisodeName(episodeName);
  }
}
