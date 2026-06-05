package br.com.alura.screenmatch.repositories;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repositories.repository.EpisodeRepository;
import br.com.alura.screenmatch.repositories.specifications.EpisodeSpecs;
import br.com.alura.screenmatch.service.ServiceHelper;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EpisodeRepo {
  private final EpisodeRepository repository;

  public EpisodeRepo(EpisodeRepository episodeRepository) {
    this.repository = episodeRepository;
  }

  public List<Episodio> searchByEpisodeName(String episodeName) {
    Specification<Episodio> spec = EpisodeSpecs.filtroByEpisode(episodeName);
    Sort sort = ServiceHelper.sort(Direction.ASC, "temporada");
    return this.repository.findAll(spec, sort);
  }

  public List<Episodio> searchTop5Episode(Serie serie) {
    Pageable pageable = ServiceHelper.top5();
    Specification<Episodio> spec = EpisodeSpecs.filtroBySerie(serie);

    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Episodio> searchEpisodesByData(Serie serie, int dataLancamento) {
    Specification<Episodio> spec =
        Specification.where(EpisodeSpecs.filtroBySerie(serie))
            .and(EpisodeSpecs.filtroByDate(dataLancamento));
    Sort sort = ServiceHelper.sort(Direction.ASC, "dataLancamento");
    return this.repository.findAll(spec, sort);
  }
}
