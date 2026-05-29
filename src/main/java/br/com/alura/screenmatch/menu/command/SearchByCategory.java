package br.com.alura.screenmatch.menu.command;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchByCategory extends Command {
  private final SerieService serieService;

  protected SearchByCategory(Scanner scanner, SerieService serieService) {
    super(
        scanner,
        OperationId.SEARCH_BY_CATEGORIA.getOperationId(),
        OperationId.SEARCH_BY_CATEGORIA.getDescription());

    this.serieService = serieService;
  }

  @Override
  public void executar() {
    System.out.println("Digite o nome do categoria: ");
    String text = this.getString();

    try {
      Categoria categoria = Categoria.fromPortugues(text);

      List<Serie> serieList = this.serieService.searchByCategory(categoria);

      System.out.println("Lista de serie: ");
      serieList.forEach(System.out::println);

    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
    }
  }
}
