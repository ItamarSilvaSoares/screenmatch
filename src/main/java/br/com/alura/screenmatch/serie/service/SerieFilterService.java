package br.com.alura.screenmatch.serie.service;

import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.Serie;
import jakarta.persistence.criteria.Expression;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class SerieFilterService {

  public static Specification<Serie> tituloContensIgnoreCase(String titulo) {
    return (root, _, cb) ->
        Optional.ofNullable(titulo)
            .filter(t -> !t.isBlank())
            .map(t -> cb.like(cb.lower(root.get("titulo")), "%" + t.toLowerCase() + "%"))
            .orElseGet(cb::conjunction);
  }

  public static Specification<Serie> filterById(long idSerie) {
    return (root, _, cb) ->
        Optional.of(idSerie)
            .filter(l -> !(l < 0))
            .map(l -> cb.equal(root.get("id"), l))
            .orElseGet(() -> cb.equal(root.get("id"), 0));
  }

  public static Specification<Serie> seasonLessThanOrEqualTo(int seasons) {
    return (root, _, cb) ->
        Optional.of(seasons)
            .filter(i -> !(i < 1))
            .map(i -> cb.lessThanOrEqualTo(root.get("totalTemporadas"), i))
            .orElseGet(cb::conjunction);
  }

  public static Specification<Serie> atorContensIgnoreCase(String actorName) {
    return (root, _, cb) ->
        Optional.ofNullable(actorName)
            .filter(n -> !n.isBlank())
            .map(
                n -> {
                  Expression<String> atoresComoString =
                      cb.function(
                          "array_to_string", String.class, root.get("atores"), cb.literal(","));
                  return cb.like(cb.lower(atoresComoString), "%" + n.toLowerCase() + "%");
                })
            .orElseGet(cb::conjunction);
  }

  public static Specification<Serie> avaliacaoGreaterOrIgualTo(Double rating) {
    return (root, _, cb) ->
        Optional.of(rating)
            .filter(s -> !s.isNaN() | !(s == 0.0))
            .map(r -> cb.greaterThanOrEqualTo(root.get("avaliacao"), r))
            .orElseGet(cb::conjunction);
  }

  public static Specification<Serie> categoriaFiltro(Categoria categoria) {
    return (root, _, cb) ->
        Optional.ofNullable(categoria)
            .filter(c -> c != Categoria.EMPTY)
            .map(c -> cb.and(cb.equal(root.get("genero"), c)))
            .orElseGet(cb::conjunction);
  }


  public static Specification<Serie> conjunctionZero() {
    return (_, _, cb) -> cb.conjunction();
  }
}
