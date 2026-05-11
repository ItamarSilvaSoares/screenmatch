package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.ConverteDados;
import br.com.alura.screenmatch.model.DadosSerie;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
public class SearchSerieEngine {
  private static final Logger log = LoggerFactory.getLogger(SearchSerieEngine.class);
  private final Scanner scanner = new Scanner(System.in);
  private final ConsumoAPI consumo = new ConsumoAPI();
  private final String API_KEY = Dotenv.load().get("API_KEY");
  private final ConverteDados conversor = new ConverteDados();
  private final List<DadosSerie> dadosSeries = new ArrayList<>();

  public List<DadosSerie> getDadosSeries() {
    return dadosSeries;
  }

  public void search() {
    IO.println("Digite o nome da série para a busca");
    String nomeSerie = scanner.nextLine();

    JsonNode json = consumo.obterDados(getUrl(nomeSerie, SeasonHelper.none()));

    if (isSerieValid(json)) {
      DadosSerie dadosSerie = this.conversor.obterDados(json.toString(), DadosSerie.class);

      this.dadosSeries.add(dadosSerie);
    }
  }

  private String getUrl(String nomeSerie, SeasonHelper seasonSearch) {
    String ENDERECO = "https://www.omdbapi.com/?t=";
    String season =
        Optional.ofNullable(seasonSearch.getSeasonNumber()).map(num -> "&season=" + num).orElse("");
    return ENDERECO + nomeSerie.replace(" ", "+") + season + API_KEY;
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
