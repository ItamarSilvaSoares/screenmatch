package br.com.alura.screenmatch.util.http.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alura.screenmatch.util.http.HttpClient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedConstruction;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

class ConsultaMyMemoryTest {

  private HttpClient httpClientMock;
  private ObjectMapper objectMapperMock;

  @BeforeEach
  void setUp() {
    httpClientMock = mock(HttpClient.class);
    objectMapperMock = mock(ObjectMapper.class);
  }

  private void configurarMocksDeSucesso(
      List<JsonNode> jsonNodes,
      DadosTraducao dadosTraducao,
      DadosResposta dadosResposta,
      String traducaoEsperada
  ) {
    when(jsonNodes.getFirst().get("responseStatus")).thenReturn(jsonNodes.get(1));
    when(jsonNodes.get(1).asInt()).thenReturn(200);

    when(dadosResposta.textoTraduzido()).thenReturn(traducaoEsperada);
    when(dadosTraducao.dadosResposta()).thenReturn(dadosResposta);

    when(httpClientMock.obterDados(anyString())).thenReturn(jsonNodes.getFirst());
    when(objectMapperMock.readValue(anyString(), eq(DadosTraducao.class)))
        .thenReturn(dadosTraducao);
  }

  @Test
  @DisplayName("Deve retornar tradução com sucesso")
  void deveRetornarTraducaoComSucesso() {
    // Arrange
    String textoOriginal = "Hello world";
    String traducaoEsperada = "Olá mundo";

    String urlEsperada = "https://api.mymemory.translated.net/get?q=Hello+world";

    DadosTraducao dadosTraducao = mock(DadosTraducao.class);
    DadosResposta dadosResposta = mock(DadosResposta.class);

    JsonNode jsonRootMock = mock(JsonNode.class);
    JsonNode responseStatusMock = mock(JsonNode.class);

    // ACT
    List<JsonNode> jsonNodes = List.of(jsonRootMock, responseStatusMock);

    configurarMocksDeSucesso(jsonNodes, dadosTraducao, dadosResposta, traducaoEsperada);

    try (
        MockedConstruction<HttpClient> httpClientMocked = mockConstruction(HttpClient.class,
            (mock, _) -> when(mock.obterDados(anyString()))
                .thenReturn(jsonRootMock));

        MockedConstruction<ObjectMapper> mapperMocked = mockConstruction(ObjectMapper.class,
            (mock, _)
                -> when(mock.readValue(anyString(), eq(DadosTraducao.class)))
                .thenReturn(dadosTraducao))
    ) {
      // ASSERT
      String resultado = ConsultaMyMemory.obterTraducao(textoOriginal);

      assertEquals(traducaoEsperada, resultado);

      HttpClient httpClientInstancia = httpClientMocked.constructed().getFirst();

      verify(httpClientInstancia, times(1))
          .obterDados(ArgumentMatchers.startsWith(urlEsperada));

      assertEquals(1, httpClientMocked.constructed().size());
      assertEquals(1, mapperMocked.constructed().size());
    }
  }
}