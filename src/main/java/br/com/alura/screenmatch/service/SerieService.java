package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.exceptions.NotFoundSerieException;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.repository.specifications.SerieSpecs;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SerieService {
  private final SerieRepository repository;

  public SerieService(SerieRepository repository) {
    this.repository = repository;
  }

  public void salvar(Serie series) {

    try {
      this.repository.save(series);

      log.info("Serie salva com sucesso");

    } catch (DataIntegrityViolationException e) {
      String msg =
          String.format(
              "Erro ao salvar serie de dados, a serie %s já está no sistema.", series.getTitulo());
      log.error(msg);
    }
  }

  public List<Serie> findAll() {
    return this.repository.findAll();
  }

  public Serie findByNameSerie(String nameSerie) {
    Specification<Serie> spec = SerieSpecs.tituloContensIgnoreCase(nameSerie);
    Optional<Serie> optionalSerie = this.repository.findOne(spec);

    return optionalSerie.orElseThrow(() -> new NotFoundSerieException(nameSerie));
  }

  public List<Serie> findByNomeActor(String nomeActor, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieSpecs.atorContensIgnoreCase(nomeActor))
            .and(SerieSpecs.avaliacaoGreaterOrIgualTo(rating));
    return this.repository.findAll(spec);
  }

  public List<Serie> topFive() {
    Specification<Serie> spec = SerieSpecs.conjunctionZero();
    Pageable pageable = PageRequest.of(0, 5, sortByRating(Direction.DESC));
    return this.repository.findAll(spec, pageable).getContent();
  }

  public List<Serie> searchByCategory(Categoria categoria) {
    Specification<Serie> spec = SerieSpecs.categoriaFiltro(categoria);
    return this.repository.findAll(spec);
  }

  private Sort sortByRating(Sort.Direction direction) {
    if (direction == Sort.Direction.DESC) {
      return Sort.by(Sort.Order.desc("avaliacao").nullsLast());
    }
    return Sort.by(Sort.Order.asc("avaliacao").nullsLast());
  }

  public List<Serie> searchBySeasonsAndRating(int seasons, Double rating) {
    Specification<Serie> spec =
        Specification.where(SerieSpecs.seasonLessThanOrEqualTo(seasons))
            .and(SerieSpecs.avaliacaoGreaterOrIgualTo(rating));

    Sort sort = sortByRating(Direction.DESC);

    return this.repository.findAll(spec, sort);
  }
}
