package br.com.alura.screenmatch.episode.dto;

import br.com.alura.screenmatch.episode.entity.Episodio;
import java.io.Serializable;

/**
 * DTO for {@link br.com.alura.screenmatch.episode.entity.Episodio}
 */
public record EpisodioDto(
    Integer temporada,
    String titulo,
    Integer numeroEpisodio,
    Double avaliacao) implements
    Serializable {

  public EpisodioDto(Episodio episodio) {
    this(episodio.getTemporada(),
        episodio.getTitulo(),
        episodio.getNumeroEpisodio(),
        episodio.getAvaliacao());
  }

}