package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.ConverteDados;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Serie;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Slf4j
@Component
public class SearchSerieEngine {
  private final ConsoleReader reader;
  private final ConsumoAPI consumo;
  private final ConverteDados conversor;

  @Value("${API_KEY}")
  private final String API_KEY;

  @Value("${OMDB_API_URL}")
  private final String API_URL;

  public SearchSerieEngine(ConsoleReader reader, ConsumoAPI consumo, ConverteDados conversor) {

    this.reader = reader;
    this.consumo = consumo;
    this.conversor = conversor;
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
    return this.reader.getString();
  }

  public DadosTemporada searchEpisode(SeasonHelper seasonHelper) {
    JsonNode json = this.consumo.obterDados(getUrl(seasonHelper));
    return this.conversor.obterDados(json.toString(), DadosTemporada.class);
  }

  private String getUrl(SeasonHelper seasonSearch) {
    String season =
        Optional.ofNullable(seasonSearch.getSeasonNumber()).map(num -> "&season=" + num).orElse("");

    return this.API_URL + seasonSearch.getNomeSerie().replace(" ", "+") + season + this.API_KEY;
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
