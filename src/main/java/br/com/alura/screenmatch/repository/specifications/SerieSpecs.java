package br.com.alura.screenmatch.repository.specifications;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import jakarta.persistence.criteria.Expression;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class SerieSpecs {
  public static Specification<Serie> tituloContensIgnoreCase(String titulo) {
    return (root, _, cb) ->
        Optional.ofNullable(titulo)
            .filter(t -> !t.isBlank())
            .map(t -> cb.like(cb.lower(root.get("titulo")), "%" + t.toLowerCase() + "%"))
            .orElseGet(cb::conjunction);
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
            .map(c -> cb.and(cb.equal(root.get("genero"), c)))
            .orElseGet(cb::conjunction);
  }

  public static Specification<Serie> conjunctionZero() {
    return (_, _, cb) -> cb.conjunction();
  }
}
