package br.com.alura.screenmatch.episode.controller;

import br.com.alura.screenmatch.episode.dto.EpisodioDto;
import br.com.alura.screenmatch.episode.service.EpisodeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EpisodeController {

  private final EpisodeService episodeService;

  @GetMapping("series/{id}/temporadas/{numero}")
  public ResponseEntity<List<EpisodioDto>> obterTemporadasPorNumero(@PathVariable Long id,
      @PathVariable int numero) {
    List<EpisodioDto> episodioDtoList = this.episodeService.obterTemporada(id, numero);
    return ResponseEntity.ok(episodioDtoList);
  }

  @GetMapping("series/{id}/temporadas/todas")
  public ResponseEntity<List<EpisodioDto>> obterTodasTemporadas(@PathVariable Long id) {
    List<EpisodioDto> episodioDtoList = this.episodeService.obterTodasTemporadas(id);
    return ResponseEntity.ok(episodioDtoList);
  }

  @GetMapping("/series/{id}/temporadas/top")
  public ResponseEntity<List<EpisodioDto>> obterTopEpisodes(@PathVariable Long id) {
    List<EpisodioDto> episodioDtoList = this.episodeService.searchTop5Episode(id);
    return ResponseEntity.ok(episodioDtoList);
  }

}
