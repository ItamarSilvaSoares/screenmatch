package br.com.alura.screenmatch.serie.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alura.screenmatch.episode.entity.DadosEpisodio;
import br.com.alura.screenmatch.season.entity.DadosTemporada;
import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.DadosSerie;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.util.http.SeriesApiClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SerieServiceTest {

  @InjectMocks
  private SerieService serieService;

  @Mock
  private SeriesApiClient seriesApiClient;

  @Mock
  private SerieQueryService serieQueryService;

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Deve chamar o método salvar corretamente")
  void shouldSaveCorrectly() {
    // Arrange
    Serie serie = getSeries().getFirst();
    // Act
    this.serieService.save(serie);

    // Assert
    verify(this.serieQueryService).salvar(serie);
  }

  @Test
  @DisplayName("Deve retornar ma lista de SerieDto ao buscar todas as séries")
  void shouldReturnSeriesDtoFindAll() {
    // Arrange
    given(this.serieQueryService.findAll()).willReturn(getSeries());

    // Act
    List<SerieDto> serieDtos = this.serieService.findAll();

    SerieDto serieDto = serieDtos.getFirst();
    // Assert
    assertEquals(1, serieDtos.size());
    assertEquals("título", serieDto.titulo());
    assertEquals("ator1, ator2", serieDto.atores());
    assertEquals("poster", serieDto.poster());
    assertEquals(5.0, serieDto.avaliacao());
    assertEquals(Categoria.ACAO, serieDto.genero());
    assertEquals(1, serieDto.totalTemporadas());
    verify(this.serieQueryService).findAll();
  }

  @Test
  @DisplayName("Deve retornar um Optional empty quando se encontra duas series de mesmo nome")
  void shouldReturnEmptyWhenFoundTwoSeriesWithSameName() {
    // Arrange
    List<Serie> series = getSeries();
    series.addAll(getSeries());

    given(this.serieQueryService.findByNameSerie("titulo")).willReturn(series);
    // Act
    Optional<Serie> list = this.serieService.findSerieByName("titulo");

    // Assert
    assertTrue(list.isEmpty());
    verify(this.serieQueryService).findByNameSerie("titulo");
  }

  @Test
  @DisplayName("Deve retornar um Optional com uma série quando se procura uam serie com um nome")
  void shouldReturnOptionalWhenFound() {
    // Arrange
    given(this.serieQueryService.findByNameSerie("titulo")).willReturn(getSeries());
    // Act
    Optional<Serie> list = this.serieService.findSerieByName("titulo");

    assertTrue(list.isPresent());

    Serie serie = list.get();

    // Assert
    assertEquals("título", serie.getTitulo());
    assertEquals("[ator1, ator2]", serie.getAtores().toString());
    assertEquals("poster", serie.getPoster());
    assertEquals(5.0, serie.getAvaliacao());
    assertEquals(Categoria.ACAO, serie.getGenero());
    assertEquals(1, serie.getTotalTemporadas());
    verify(this.serieQueryService).findByNameSerie("titulo");
  }

  @Test
  @DisplayName("Deve salvar uma série com seus episódios")
  void searchEpisodesSeries() {
    // Arrange
    Serie serie = new Serie();
    serie.setTitulo("Dark");
    serie.setTotalTemporadas(2);

    DadosEpisodio episodio1 = new DadosEpisodio("Ep1", 1, "3", "2000");
    DadosEpisodio episodio2 = new DadosEpisodio("Ep2", 2, "3", "2000");

    DadosTemporada temporada1 = new DadosTemporada(1, List.of(episodio1));
    DadosTemporada temporada2 =
        new DadosTemporada(2, List.of(episodio2));

    when(this.serieQueryService.findByNameSerie("Dark"))
        .thenReturn(List.of(serie));

    when(this.seriesApiClient.searchEpisode(any()))
        .thenReturn(temporada1)
        .thenReturn(temporada2);


    //Act
    this.serieService.searchEpisodesSeries("Dark");

    // Assert
    verify(serieQueryService).salvar(serie);
    verify(seriesApiClient, times(2)).searchEpisode(any());

    assertEquals(2, serie.getEpisodios().size());
  }

  @Test
  @DisplayName("Deve buscar séries pela categoria")
  void searchByCategory() {
    // Arrange
    given(this.serieQueryService.searchByCategory(Categoria.ACAO)).willReturn(getSeries());
    // Act
    this.serieService.searchByCategory("Ação");

    // Assert
    verify(this.serieQueryService).searchByCategory(Categoria.ACAO);
  }

  @Test
  @DisplayName("Deve chamar a função 'searchBySeasonsAndRating' corretamente com valores passados")
  void shouldCallTheFunctionCorrectlySearchBySeasonsAndRating() {
    // Arrange
    given(this.serieQueryService.searchBySeasonsAndRating(1, 5.0)).willReturn(getSeries());
    // Act
    this.serieService.searchBySeasonsAndRating(1, 5.0);

    // Assert
    verify(this.serieQueryService).searchBySeasonsAndRating(1, 5.0);
  }

  @Test
  @DisplayName("Deve chamar a função 'searchBySeasonsAndRating' com valor diferente do valor passado")
  void shouldCallTheFunctionWithValueDifferent() {
    // Arrange
    when(this.serieQueryService.searchBySeasonsAndRating(anyInt(), anyDouble())).thenReturn(getSeries());
    // Act
    this.serieService.searchBySeasonsAndRating(-1, 5.0);

    // Assert
    verify(this.serieQueryService).searchBySeasonsAndRating(50, 5.0);
  }

  @Test
  @DisplayName("Deve chamar a função 'findByNomeActor' corretamente com os valeres informados")
  void shouldCallTheFunctionCorrectlyFindByNomeActor() {
    // Arrange
    given(this.serieQueryService.findByNomeActor(any(), anyDouble())).willReturn(getSeries());
    // Act
    this.serieService.findSeriesByNomeActor("actor name", 5.0);

    // Assert
    verify(this.serieQueryService).findByNomeActor("actor name", 5.0);
  }

  @Test
  @DisplayName("Deve chamar a função 'TopFive' corretamente")
  void shouldCallTheFunctionCorrectlyTopFive() {
    // Arrange
    List<Serie> expected = getSeries();
    given(this.serieQueryService.topFive()).willReturn(expected);
    // Act
    List<SerieDto> result = this.serieService.topFive();

    // Assert
    Serie serie1 = expected.getFirst();
    SerieDto serieDto = result.getFirst();
    assertEquals(expected.size(), result.size());
    assertEquals("ator1, ator2", serieDto.atores());
    assertEquals(serie1.getAvaliacao(), serieDto.avaliacao());
    assertEquals(serie1.getGenero(), serieDto.genero());
    assertEquals(serie1.getPoster(), serieDto.poster());
    assertEquals(serie1.getSinopse(), serieDto.sinopse());
    assertEquals(serie1.getTitulo(), serieDto.titulo());
    assertEquals(serie1.getTotalTemporadas(), serieDto.totalTemporadas());
    verify(this.serieQueryService).topFive();
  }

  @Test
  @DisplayName("Deve chamar a função 'topFiveOrderByDate' corretamente")
  void shouldCallTheFunctionCorrectlyTopFiveOrderByDate() {
    // Arrange
    List<Serie> expected = getSeries();
    given(this.serieQueryService.topFiveOrderByDate()).willReturn(expected);
    // Act
    List<SerieDto> result = this.serieService.topFiveOrderByDate();

    // Assert
    assertEquals(expected.size(), result.size());
    verify(this.serieQueryService).topFiveOrderByDate();
  }

  @Test
  @DisplayName("Deve chamar a função 'getById' corretamente")
  void shouldCallTheFunctionCorrectlyGetById() {
    // Arrange
    List<Serie> expected = getSeries();
    Serie serie = expected.getFirst();
    given(this.serieQueryService.getSerieById(anyLong())).willReturn(Optional.of(serie));
    // Act
    this.serieService.getById(1L);

    // Assert
    verify(this.serieQueryService).getSerieById(anyLong());
  }

  @Test
  @DisplayName("Deve retornar um 'SerieDto' empty quando a função 'getById' não encontra algo")
  void getById() {
    // Arrange

    given(this.serieQueryService.getSerieById(anyLong())).willReturn(Optional.empty());
    // Act
    SerieDto result = this.serieService.getById(2L);

    // Assert
    verify(this.serieQueryService).getSerieById(anyLong());
    assertEquals("", result.atores());
    assertEquals(0.0, result.avaliacao());
    assertNull(result.genero());
    assertEquals("", result.poster());
    assertEquals("", result.sinopse());
    assertEquals("Not found", result.titulo());
    assertEquals(0, result.totalTemporadas());
    assertEquals(2L, result.id());
    verify(this.serieQueryService).getSerieById(anyLong());
  }

  @Test
  @DisplayName("Deve chamar a função 'getSeriesByCategory' corretamente")
  void shouldCallTheFunctionCorrectlyGetSeriesByCategory() {
    // Arrange
    given(this.serieQueryService.searchByCategory(any())).willReturn(getSeries());
    // Act
    this.serieService.getSeriesByCategory("Ação");

    // Assert
    verify(this.serieQueryService).searchByCategory(Categoria.ACAO);
  }

  private List<Serie> getSeries() {
    DadosSerie dadosSerie = new DadosSerie(
        "título",
        1,
        "5",
        "action",
        "ator1, ator2",
        "poster",
        "sinopse");
    Serie serie = new Serie(dadosSerie);
    List<Serie> series = new ArrayList<>();
    series.add(serie);
    return series;

  }
}