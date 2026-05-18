package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SerieService {
  private static final Logger log = LoggerFactory.getLogger(SerieService.class);
  private final SerieRepository repository;

  public SerieService(SerieRepository repository) {
    this.repository = repository;
  }

  public void salvar(Serie series) {

    try {
      this.repository.save(series);

      log.info("Serie salva com sucesso");

    } catch (DataIntegrityViolationException e) {
      String msg =
          String.format(
              "Erro ao salvar serie de dados, a serie %s já está no sistema.", series.getTitulo());
      log.error(msg);
    }
  }

  public List<Serie> findAll() {
    return this.repository.findAll();
  }
}
