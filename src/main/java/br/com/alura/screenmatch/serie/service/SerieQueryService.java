package br.com.alura.screenmatch.serie.service;

import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.repository.SerieRepository;
import br.com.alura.screenmatch.util.QueryServiceHelper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SerieQueryService {

  private final SerieRepository repository;

  public SerieQueryService(SerieRepository repository) {
    this.repository = repository;
  }

  public void salvar(Serie serie) {
    this.repository.save(serie);

  }

  public List<Serie> findAll() {
    return this.repository.findAll();
  }

  public List<Serie> findByNameSerie(String nameSerie) {
    Specification<Serie> spec = SerieFilterService.tituloContensIgnoreCase(nameSerie);

    return this.repository.findAll(spec);

  }

  public List<Serie> findByNomeActor(String nomeActor, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieFilterService.atorContensIgnoreCase(nomeActor))
            .and(SerieFilterService.avaliacaoGreaterOrIgualTo(rating));
    return this.repository.findAll(spec);
  }

  public List<Serie> topFive() {
    Specification<Serie> spec = SerieFilterService.conjunctionZero();
    Pageable pageable = QueryServiceHelper.top5();
    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Serie> searchByCategory(Categoria categoria) {
    Specification<Serie> spec = SerieFilterService.categoriaFiltro(categoria);
    return this.repository.findAll(spec);
  }

  public List<Serie> searchBySeasonsAndRating(int seasons, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieFilterService.seasonLessThanOrEqualTo(seasons))
            .and(SerieFilterService.avaliacaoGreaterOrIgualTo(rating));

    Sort sort = QueryServiceHelper.sort(Direction.DESC, "avaliacao");

    return this.repository.findAll(spec, sort);
  }

  public List<Serie> topFiveOrderByDate() {
    return this.repository.findRecent(PageRequest.of(0, 5)).getContent();
  }

  public Optional<Serie> getSerieById(Long id) {
    Specification<Serie> spec = SerieFilterService.filterById(id);
    return this.repository.findOne(spec);
  }
}
