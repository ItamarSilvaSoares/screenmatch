package br.com.alura.screenmatch.repository.specifications;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import jakarta.persistence.criteria.Expression;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class SerieSpecs {
  public static Specification<Serie> tituloContensIgnoreCase(String titulo) {
    return (root, _, criteriaBuilder) ->
        Optional.ofNullable(titulo)
            .filter(t -> !t.isBlank())
            .map(
                t ->
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titulo")), "%" + t.toLowerCase() + "%"))
            .orElseGet(criteriaBuilder::conjunction);
  }

  public static Specification<Serie> seasonLessThanOrEqualTo(int seasons) {
    return (root, _, criteriaBuilder) ->
        Optional.of(seasons)
            .filter(i -> !(i < 1))
            .map(i -> criteriaBuilder.lessThanOrEqualTo(root.get("totalTemporadas"), i))
            .orElseGet(criteriaBuilder::conjunction);
  }

  public static Specification<Serie> atorContensIgnoreCase(String actorName) {
    return (root, _, criteriaBuilder) ->
        Optional.ofNullable(actorName)
            .filter(n -> !n.isBlank())
            .map(
                n -> {
                  Expression<String> atoresComoString =
                      criteriaBuilder.function(
                          "array_to_string",
                          String.class,
                          root.get("atores"),
                          criteriaBuilder.literal(","));
                  return criteriaBuilder.like(
                      criteriaBuilder.lower(atoresComoString), "%" + n.toLowerCase() + "%");
                })
            .orElseGet(criteriaBuilder::conjunction);
  }

  public static Specification<Serie> avaliacaoGreaterOrIgualTo(Double rating) {
    return (root, _, criteriaBuilder) ->
        Optional.of(rating)
            .filter(s -> !s.isNaN())
            .map(r -> criteriaBuilder.greaterThanOrEqualTo(root.get("avaliacao"), r))
            .orElseGet(criteriaBuilder::conjunction);
  }

  public static Specification<Serie> categoriaFiltro(Categoria categoria) {
    return (root, _, criteriaBuilder) ->
        Optional.ofNullable(categoria)
            .map(c -> criteriaBuilder.and(criteriaBuilder.equal(root.get("genero"), c)))
            .orElseGet(criteriaBuilder::conjunction);
  }

  public static Specification<Serie> conjunctionZero() {
    return (_, _, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
