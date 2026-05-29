package br.com.alura.screenmatch.repository.specifications;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import jakarta.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class EpisodeSpecs {
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

  public static Specification<Episodio> filtroBySerie(Serie serie) {
    return (root, _, cb) -> {
      Join<Episodio, Serie> joinSerie = root.join("serie");

      return Optional.ofNullable(serie)
          .map(s -> cb.equal(joinSerie.get("titulo"), s.getTitulo()))
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
}
