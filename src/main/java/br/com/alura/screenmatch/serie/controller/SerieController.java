package br.com.alura.screenmatch.serie.controller;

import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.service.SerieService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/series")
public class SerieController {

  private SerieService serieService;

  @GetMapping
  public ResponseEntity<List<SerieDto>> listar() {
    List<SerieDto> serieDtoList = this.serieService.findAll();
    return ResponseEntity.ok().body(serieDtoList);

  }

  @GetMapping("/top5")
  public ResponseEntity<List<SerieDto>> listarTop5() {
    List<SerieDto> serieDtoList = this.serieService.topFive();
    return ResponseEntity.ok().body(serieDtoList);
  }

  @GetMapping("/lancamentos")
  public ResponseEntity<List<SerieDto>> obterLancamentos() {
    List<SerieDto> serieDtoList = this.serieService.topFiveOrderByDate();
    return ResponseEntity.ok().body(serieDtoList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SerieDto> obterPorId(@PathVariable Long id) {
    SerieDto serieDtoList = this.serieService.getById(id);
    return ResponseEntity.ok().body(serieDtoList);
  }

  @GetMapping("/categoria/{nomeGenero}")
  public ResponseEntity<List<SerieDto>> obterPorNomeGenero(@PathVariable String nomeGenero) {
    List<SerieDto> serieDtoList = this.serieService.getSeriesByCategory(nomeGenero);
    return ResponseEntity.ok().body(serieDtoList);
  }

}
