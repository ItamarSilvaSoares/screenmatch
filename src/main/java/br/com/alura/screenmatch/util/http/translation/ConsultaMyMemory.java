package br.com.alura.screenmatch.util.http.translation;

import br.com.alura.screenmatch.util.http.HttpClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class ConsultaMyMemory {

  public static String obterTraducao(String text) {
    ObjectMapper mapper = new ObjectMapper();

    HttpClient consumo = new HttpClient();

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
