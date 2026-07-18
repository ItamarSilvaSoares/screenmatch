package br.com.alura.screenmatch.serie.dto;

import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.Serie;
import java.io.Serializable;

/**
 * DTO for {@link br.com.alura.screenmatch.serie.entity.Serie}
 */
public record SerieDto(
    long id,
    String titulo,
    Integer totalTemporadas,
    Double avaliacao,
    Categoria genero,
    String atores,
    String poster,
    String sinopse
) implements Serializable {

  public SerieDto(Serie serie) {
    this(
        serie.getId(),
        serie.getTitulo(),
        serie.getTotalTemporadas(),
        serie.getAvaliacao(),
        serie.getGenero(),
        String.join(", ", serie.getAtores()),
        serie.getPoster(),
        serie.getSinopse()
    );
  }

  public static SerieDto empty(long id) {
    return new SerieDto(
        id,
        "Not found",
        0,
        0.0,
        null,
        "",
        "",
        ""
    );
  }

}