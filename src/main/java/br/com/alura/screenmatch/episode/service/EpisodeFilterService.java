package br.com.alura.screenmatch.episode.service;

import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.serie.entity.Serie;
import jakarta.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class EpisodeFilterService {

  public static Specification<Episodio> filtroByEpisode(String nameEpisode) {
    return (root, _, cb) ->
        Optional.ofNullable(nameEpisode)
            .filter(n -> !n.isBlank())
            .map(
                n ->
                    cb.like(
                        cb.lower(root.get("titulo")), // root já é Episodio
                        "%" + n.toLowerCase().trim() + "%"))
            .orElseGet(cb::conjunction);
  }

  public static Specification<Episodio> filtroBySerie(String serieName) {
    return (root, _, cb) -> {
      Join<Episodio, Serie> joinSerie = root.join("serie");

      return Optional.ofNullable(serieName)
          .map(s -> cb.equal(joinSerie.get("titulo"), s))
          .orElseGet(cb::conjunction);
    };
  }

  public static Specification<Episodio> filtroByDate(int anoLancamento) {
    return (root, _, cb) -> {
      LocalDate inicio = LocalDate.of(anoLancamento, 1, 1);
      LocalDate fim = LocalDate.now();

      return Optional.of(anoLancamento)
          .filter(i -> !(i == 0))
          .map(_ -> cb.between(root.get("dataLancamento"), inicio, fim))
          .orElseGet(cb::conjunction);
    };
  }

  public static Specification<Episodio> filtroBySerieId(Long serieId) {
    return (root, _, cb) -> {
      Join<Episodio, Serie> joinSerie = root.join("serie");

      return Optional.ofNullable(serieId)
          .map(s -> cb.equal(joinSerie.get("id"), s))
          .orElseGet(cb::conjunction);
    };
  }

  public static Specification<Episodio> filterBySeason(int episodeId) {
    return ((root, _, cb) -> Optional.of(episodeId)
        .filter(i -> !(i == 0))
        .map(i -> cb.equal(root.get("temporada"), i))
        .orElseGet(cb::conjunction));
  }
}
