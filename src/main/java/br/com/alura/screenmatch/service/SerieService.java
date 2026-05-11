package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SerieService {
  private final SerieRepository repository;

  public SerieService(SerieRepository repository) {
    this.repository = repository;
  }

  public void salvar(List<DadosSerie> series) {
    series.stream().map(Serie::new).forEach(repository::save);
  }
}
