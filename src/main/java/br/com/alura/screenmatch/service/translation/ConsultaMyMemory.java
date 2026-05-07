package br.com.alura.screenmatch.service.translation;

import br.com.alura.screenmatch.service.ConsumoAPI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ConsultaMyMemory {
  private static final Logger log = LoggerFactory.getLogger(ConsultaMyMemory.class);

  public static String obterTraducao(String text) {
    ObjectMapper mapper = new ObjectMapper();

    ConsumoAPI consumo = new ConsumoAPI();

    String texto = URLEncoder.encode(text, StandardCharsets.UTF_8);
    String langPair = URLEncoder.encode("en|pt-br", StandardCharsets.UTF_8);

    String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langPair;

    JsonNode json = consumo.obterDados(url);

    if (json.get("responseStatus").asInt() == 200) {

      return mapper
          .readValue(json.toString(), DadosTraducao.class)
          .dadosResposta()
          .textoTraduzido();
    }

    log.error("Ocorreu um erro ao buscar a tradução: {}", json);

    return "";
  }
}
