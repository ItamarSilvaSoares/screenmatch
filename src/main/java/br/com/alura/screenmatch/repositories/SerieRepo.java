package br.com.alura.screenmatch.repositories;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repositories.repository.SerieRepository;
import br.com.alura.screenmatch.repositories.specifications.SerieSpecs;
import br.com.alura.screenmatch.service.ServiceHelper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SerieRepo {
  private final SerieRepository repository;

  public SerieRepo(SerieRepository repository) {
    this.repository = repository;
  }

  public void salvar(Serie serie) {
          this.repository.save(serie);

  }

  public List<Serie> findAll() {
    return this.repository.findAll();
  }

  public Optional<Serie> findByNameSerie(String nameSerie) {
    Specification<Serie> spec = SerieSpecs.tituloContensIgnoreCase(nameSerie);

    return this.repository.findOne(spec);
  }

  public List<Serie> findByNomeActor(String nomeActor, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieSpecs.atorContensIgnoreCase(nomeActor))
            .and(SerieSpecs.avaliacaoGreaterOrIgualTo(rating));
    return this.repository.findAll(spec);
  }

  public List<Serie> topFive() {
    Specification<Serie> spec = SerieSpecs.conjunctionZero();
    Pageable pageable = ServiceHelper.top5();
    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Serie> searchByCategory(Categoria categoria) {
    Specification<Serie> spec = SerieSpecs.categoriaFiltro(categoria);
    return this.repository.findAll(spec);
  }

  public List<Serie> searchBySeasonsAndRating(int seasons, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieSpecs.seasonLessThanOrEqualTo(seasons))
            .and(SerieSpecs.avaliacaoGreaterOrIgualTo(rating));

    Sort sort = ServiceHelper.sort(Direction.DESC, "avaliacao");

    return this.repository.findAll(spec, sort);
  }
}
