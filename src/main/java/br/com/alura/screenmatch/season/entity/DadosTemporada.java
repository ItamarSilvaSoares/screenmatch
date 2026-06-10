package br.com.alura.screenmatch.season.entity;

import br.com.alura.screenmatch.episode.entity.DadosEpisodio;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

public record DadosTemporada(
    @JsonAlias("Season") Integer season,
    @JsonAlias("Episodes") List<DadosEpisodio> episodios) {

}
