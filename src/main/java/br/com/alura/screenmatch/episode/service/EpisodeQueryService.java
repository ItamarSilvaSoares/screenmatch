package br.com.alura.screenmatch.episode.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.episode.repository.EpisodeRepository;
import br.com.alura.screenmatch.util.QueryServiceHelper;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EpisodeQueryService {

  private final EpisodeRepository repository;

  public EpisodeQueryService(EpisodeRepository episodeRepository) {
    this.repository = episodeRepository;
  }

  public List<Episodio> searchByEpisodeName(String episodeName) {
    Specification<Episodio> spec = EpisodeFilterService.filtroByEpisode(episodeName);
    Sort sort = QueryServiceHelper.sort(Direction.ASC, "temporada");
    return this.repository.findAll(spec, sort);
  }

  public List<Episodio> searchTop5Episode(String serieName) {
    Pageable pageable = QueryServiceHelper.top5();
    Specification<Episodio> spec = EpisodeFilterService.filtroBySerie(serieName);

    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Episodio> searchEpisodesByData(String serieName, int dataLancamento) {
    Specification<Episodio> spec =
        Specification.where(EpisodeFilterService.filtroBySerie(serieName))
            .and(EpisodeFilterService.filtroByDate(dataLancamento));
    Sort sort = QueryServiceHelper.sort(Direction.ASC, "dataLancamento");
    return this.repository.findAll(spec, sort);
  }

  public List<Episodio> searchEpisodesBySereiIdAndSeason(long serieId, int season) {
    Specification<Episodio> spec = Specification.where(
            EpisodeFilterService.filtroBySerieId(serieId))
        .and(EpisodeFilterService.filterBySeason(season));

    return this.repository.findAll(spec);
  }

  public List<Episodio> searchEpisodesBySereiId(long serieId) {
    Specification<Episodio> spec = EpisodeFilterService.filtroBySerieId(serieId);

    return this.repository.findAll(spec);
  }


}
