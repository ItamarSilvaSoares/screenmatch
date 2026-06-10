package br.com.alura.screenmatch.serie.controller;

import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SerieController {

  private SerieService serieService;


  @GetMapping("/series")
  public List<Serie> listar() {
    System.out.println("Listando todos os series.");
    return this.serieService.findAll();
  }

}
