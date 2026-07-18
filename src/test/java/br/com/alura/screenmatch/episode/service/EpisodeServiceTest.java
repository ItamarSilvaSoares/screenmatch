package br.com.alura.screenmatch.episode.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import br.com.alura.screenmatch.episode.dto.EpisodioDto;
import br.com.alura.screenmatch.episode.entity.DadosEpisodio;
import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

  @InjectMocks
  EpisodeService episodeService;

  @Mock
  private SerieService serieService;

  @Mock
  private EpisodeQueryService episodeQueryService;

  @Mock
  private Serie serie;

  private void assertEpisodio(Episodio expected, List<Episodio> episodios) {
    Episodio episodio = episodios.getFirst();
    assertEquals(1, episodios.size());
    assertEquals(expected.getId(), episodio.getId());
    assertEquals(expected.getTitulo(), episodio.getTitulo());
    assertEquals(expected.getNumeroEpisodio(), episodio.getNumeroEpisodio());
    assertEquals(expected.getDataLancamento(), episodio.getDataLancamento());
    assertEquals(expected.getTemporada(), episodio.getTemporada());
    assertEquals(expected.getAvaliacao(), episodio.getAvaliacao());
    assertEquals(expected.getSerie(), episodio.getSerie());
    assertEquals(expected.toString(), episodio.toString());
  }

  private void assertEpisodioDto(List<EpisodioDto> episodios, Episodio expected) {
    EpisodioDto episodioDto = episodios.getFirst();
    assertEquals(1, episodios.size());
    assertEquals(expected.getTitulo(), episodioDto.titulo());
    assertEquals(expected.getNumeroEpisodio(), episodioDto.numeroEpisodio());
    assertEquals(expected.getAvaliacao(), episodioDto.avaliacao());
    assertEquals(expected.getTemporada(), episodioDto.temporada());
  }

  @Test
  @DisplayName("Deve retornar uma lista de episodio quando se passa um titulo e uma data")
  void shouldReturnListEpisodio() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);
    given(this.serieService.findSerieByName(this.serie.getTitulo())).willReturn(
        Optional.of(this.serie));
    given(this.episodeQueryService.searchEpisodesByData(this.serie.getTitulo(), 2000)).willReturn(
        List.of(expected));
    // Act
    List<Episodio> episodios = this.episodeService.searchEpisodesByData(this.serie.getTitulo(),
        2000);

    // Assert
    Episodio episodio = episodios.getFirst();
    verify(this.episodeQueryService).searchEpisodesByData(this.serie.getTitulo(), 2000);
    assertEpisodio(episodio, episodios);
  }

  @Test
  @DisplayName("Deve retornar uma lista vazia quando não se encontra uma serie")
  void shouldReturnListEmpty() {
    // Arrange
    given(this.serieService.findSerieByName(this.serie.getTitulo())).willReturn(
        Optional.empty());

    // Act
    List<Episodio> episodios = this.episodeService.searchEpisodesByData(this.serie.getTitulo(),
        2000);

    // Assert

    verifyNoInteractions(this.episodeQueryService);
    verify(this.episodeQueryService, never()).searchEpisodesByData(any(), anyInt());
    assertEquals(0, episodios.size());
  }

  @Test
  @DisplayName("Deve retornar uma lista de Episodio, ao pesquisar pelo nome da serie")
  void shouldReturnListEpisode() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);
    given(this.serieService.findSerieByName(expected.getTitulo())).willReturn(
        Optional.of(this.serie));

    given(this.episodeQueryService.searchTop5Episode(this.serie.getTitulo())).willReturn(
        List.of(expected));
    // Act
    List<Episodio> episodios = this.episodeService.searchTop5Episode(expected.getTitulo());
    Episodio episodio = episodios.getFirst();
    // Assert
    verify(this.episodeQueryService).searchTop5Episode(this.serie.getTitulo());

    assertEpisodio(episodio, episodios);
  }

  @Test
  @DisplayName("Deve retornar uma lista de EpisodioDto, ao pesquisar pelo id da serie")
  void shouldReturnListEpisodeDto() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);
    SerieDto dtoEmpty = SerieDto.empty(1);
    given(this.serieService.getById(dtoEmpty.id())).willReturn(dtoEmpty);

    given(this.episodeQueryService.searchTop5Episode(dtoEmpty.titulo())).willReturn(
        List.of(expected));
    // Act
    List<EpisodioDto> episodios = this.episodeService.searchTop5Episode(dtoEmpty.id());

    // Assert
    verify(this.episodeQueryService).searchTop5Episode(dtoEmpty.titulo());

    assertEpisodioDto(episodios, expected);

    assertThrows(NoSuchFieldException.class, () ->
        EpisodioDto.class.getDeclaredField("id"));
  }

  @Test
  @DisplayName("Deve retornar uma lista vazia quando não se encontra uma serie, ao busca o top 5 episódios")
  void shouldReturnListEmptyTop5Episodes() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);
    given(this.serieService.findSerieByName(expected.getTitulo())).willReturn(
        Optional.empty());

    // Act
    List<Episodio> episodios = this.episodeService.searchTop5Episode(expected.getTitulo());

    // Assert
    verifyNoInteractions(this.episodeQueryService);
    verify(this.episodeQueryService, never()).searchTop5Episode(any());
    assertEquals(0, episodios.size());
  }


  @Test
  @DisplayName("Deve retorna uma lista de Episode ao pesquisar pelo nome do episode")
  void shouldReturnListEpisodeByName() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);

    given(this.episodeQueryService.searchByEpisodeName(expected.getTitulo())).willReturn(
        List.of(expected));
    // Act
    List<Episodio> episodios = this.episodeService.searchByEpisodeName(expected.getTitulo());

    // Assert
    verify(this.episodeQueryService).searchByEpisodeName(expected.getTitulo());
    assertEpisodio(expected, episodios);
  }

  @Test
  @DisplayName("Deve retorna uma lista de EpisodeDto, ao buscar pela todas as temporadas")
  void obterTodasTemporadas() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);

    given(this.episodeQueryService.searchEpisodesBySereiId(expected.getId())).willReturn(
        List.of(expected));
    // Act
    List<EpisodioDto> episodios = this.episodeService.obterTodasTemporadas(expected.getId());

    // Assert
    verify(this.episodeQueryService).searchEpisodesBySereiId(expected.getId());
    assertEpisodioDto(episodios, expected);

    assertThrows(NoSuchFieldException.class, () ->
        EpisodioDto.class.getDeclaredField("id"));
  }

  @Test
  @DisplayName("Deve retorna uma lista de EpisodeDto ao buscar todos os episodes de uma a temporada")
  void obterTemporada() {
    // Arrange
    DadosEpisodio dadosEpisodio = new DadosEpisodio("Titulo Qualquer", 1, "5", "01-01-2000");
    Episodio expected = new Episodio(1, dadosEpisodio);

    given(this.episodeQueryService.searchEpisodesBySereiIdAndSeason(expected.getId(),
        expected.getTemporada())).willReturn(
        List.of(expected));
    // Act
    List<EpisodioDto> episodios = this.episodeService.obterTemporada(expected.getId(),
        expected.getTemporada());

    // Assert
    verify(this.episodeQueryService).searchEpisodesBySereiIdAndSeason(expected.getId(),
        expected.getTemporada());
    assertEpisodioDto(episodios, expected);

    assertThrows(NoSuchFieldException.class, () ->
        EpisodioDto.class.getDeclaredField("id"));
  }
}