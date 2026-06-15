package br.com.alura.screenmatch.serie.controller;

import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.service.SerieService;
import java.util.List;
import lombok.AllArgsConstructor;
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
  public List<SerieDto> listar() {
    return this.serieService.findAll()
        ;
  }

  @GetMapping("/top5")
  public List<SerieDto> listarTop5() {
    return this.serieService.topFive();
  }

  @GetMapping("/lancamentos")
  public List<SerieDto> obterLancamentos() {
    return this.serieService.topFiveOrderByDate();
  }

  @GetMapping("/{id}")
  public SerieDto obterPorId(@PathVariable Long id) {
    return this.serieService.getById(id);
  }

  @GetMapping("/categoria/{nomeGenero}")
  public List<SerieDto> obterPorNomeGenero(@PathVariable String nomeGenero) {
    return this.serieService.getSeriesByCategory(nomeGenero);
  }

}
