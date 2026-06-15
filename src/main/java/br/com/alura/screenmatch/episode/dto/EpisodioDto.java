package br.com.alura.screenmatch.episode.dto;

import java.io.Serializable;

/**
 * DTO for {@link br.com.alura.screenmatch.episode.entity.Episodio}
 */
public record EpisodioDto(Integer temporada, String titulo, Integer numeroEpisodio,
                          Double avaliacao) implements
    Serializable {

}