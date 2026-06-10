package br.com.alura.screenmatch.episode.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.episode.repository.EpisodeRepository;
import br.com.alura.screenmatch.episode.service.specifications.EpisodeFilterService;
import br.com.alura.screenmatch.serie.entity.Serie;
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

  public List<Episodio> searchTop5Episode(Serie serie) {
    Pageable pageable = QueryServiceHelper.top5();
    Specification<Episodio> spec = EpisodeFilterService.filtroBySerie(serie);

    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Episodio> searchEpisodesByData(Serie serie, int dataLancamento) {
    Specification<Episodio> spec =
        Specification.where(EpisodeFilterService.filtroBySerie(serie))
            .and(EpisodeFilterService.filtroByDate(dataLancamento));
    Sort sort = QueryServiceHelper.sort(Direction.ASC, "dataLancamento");
    return this.repository.findAll(spec, sort);
  }
}
