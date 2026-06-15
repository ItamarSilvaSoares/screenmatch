package br.com.alura.screenmatch.serie.dto;

import br.com.alura.screenmatch.serie.entity.Categoria;
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