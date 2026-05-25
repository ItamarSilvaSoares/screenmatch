package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.ConverteDados;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Serie;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Slf4j
@Component
public class SearchSerieEngine {
  private final Scanner scanner;
  private final ConsumoAPI consumo = new ConsumoAPI();
  private final Dotenv dotenv = Dotenv.load();
  private final ConverteDados conversor = new ConverteDados();

  public SearchSerieEngine(Scanner scanner) {
    this.scanner = scanner;
  }

  public Optional<Serie> searchSerie() {
    String nomeSerie = getInput("Digite o nome da série para a busca");

    JsonNode json = consumo.obterDados(getUrl(SeasonHelper.none(nomeSerie)));

    if (isSerieValid(json)) {
      DadosSerie dadosSerie = this.conversor.obterDados(json.toString(), DadosSerie.class);

      return Optional.of(new Serie(dadosSerie));
    }
    return Optional.empty();
  }

  public String getInput(String informativeText) {
    IO.println(informativeText);
    return this.scanner.nextLine();
  }

  public DadosTemporada searchEpisode(SeasonHelper seasonHelper) {
    JsonNode json = this.consumo.obterDados(getUrl(seasonHelper));
    return this.conversor.obterDados(json.toString(), DadosTemporada.class);
  }

  private String getUrl(SeasonHelper seasonSearch) {
    String URL = dotenv.get("OMDB_API_URL");
    String API_KEY = dotenv.get("API_KEY");
    String season =
        Optional.ofNullable(seasonSearch.getSeasonNumber()).map(num -> "&season=" + num).orElse("");

    return URL + seasonSearch.getNomeSerie().replace(" ", "+") + season + API_KEY;
  }

  private boolean isSerieValid(JsonNode jsonNode) {
    if ("False".equals(jsonNode.path("Response").asString())) {
      log.warn("Série não encontrada");

      return false;
    }

    if (!"series".equalsIgnoreCase(jsonNode.path("Type").asString())) {
      log.warn("O titulo buscado não é uma série: {}", jsonNode.get("Title").asString());
      return false;
    }

    log.info("Série válida");

    return true;
  }
}
