package br.com.alura.screenmatch.episode.controller;

import br.com.alura.screenmatch.episode.dto.EpisodioDto;
import br.com.alura.screenmatch.episode.service.EpisodeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EpisodeController {

  private final EpisodeService episodeService;

  @GetMapping("series/{id}/temporadas/{numero}")
  public List<EpisodioDto> obterTemporadasPorNumero(@PathVariable Long id,
      @PathVariable int numero) {
    return this.episodeService.obterTemporada(id, numero);
  }

  @GetMapping("series/{id}/temporadas/todas")
  public List<EpisodioDto> obterTodasTemporadas(@PathVariable Long id) {
    return this.episodeService.obterTodasTemporadas(id);
  }

  @GetMapping("/series/{id}/temporadas/top")
  public List<EpisodioDto> obterTopEpisodes(@PathVariable Long id) {
    return this.episodeService.searchTop5Episode(id);
  }

}
