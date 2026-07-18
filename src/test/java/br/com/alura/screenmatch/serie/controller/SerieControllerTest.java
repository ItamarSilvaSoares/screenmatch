package br.com.alura.screenmatch.serie.controller;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.alura.screenmatch.serie.dto.SerieDto;
import br.com.alura.screenmatch.serie.entity.Categoria;
import br.com.alura.screenmatch.serie.entity.DadosSerie;
import br.com.alura.screenmatch.serie.entity.Serie;
import br.com.alura.screenmatch.serie.service.SerieService;
import br.com.alura.screenmatch.util.http.translation.ConsultaMyMemory;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SerieController.class)
@AutoConfigureMockMvc
class SerieControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private SerieService service;

  private DadosSerie getDadosSerie() {
    return new DadosSerie("Titulo Qualquer", 1, "5", "Action", "ninguém", "https://null",
        "Não tem");

  }

  private void asserts(String uri, List<SerieDto> serieDtoList) throws Exception {
    SerieDto dto = serieDtoList.getFirst();
    mvc.perform(get(uri))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(serieDtoList.size()))
        .andExpect(jsonPath("$[0].titulo").value(dto.titulo()))
        .andExpect(jsonPath("$[0].totalTemporadas").value(dto.totalTemporadas()))
        .andExpect(jsonPath("$[0].avaliacao").value(dto.avaliacao()))
        .andExpect(jsonPath("$[0].atores").value(dto.atores()))
        .andExpect(jsonPath("$[0].poster").value(dto.poster()))
        .andExpect(jsonPath("$[0].genero").value(Categoria.ACAO.toString()))
        .andExpect(jsonPath("$[0].id").value(dto.id()))
        .andDo(print()); // útil para debug
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter todas as series")
  void deveRetornarOkFindAll() throws Exception {
    // ARRANGE
    DadosSerie dadosSerie = getDadosSerie();

    // ACT
    Serie serie;
    try (MockedStatic<ConsultaMyMemory> mockedStatic = mockStatic(ConsultaMyMemory.class)) {
      mockedStatic.when(() -> ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()))
          .thenReturn(dadosSerie.sinopse());

      serie = new Serie(dadosSerie);
    }

    List<SerieDto> serieDtoList = List.of(new SerieDto(serie));

    when(this.service.findAll()).thenReturn(serieDtoList);

    // ASSERT

    asserts("/series", serieDtoList);

    verify(this.service, times(1)).findAll();
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter as top 5 series")
  void deveRetornarOkTop5() throws Exception {
    // ARRANGE
    DadosSerie dadosSerie = getDadosSerie();

    // ACT
    Serie serie;
    try (MockedStatic<ConsultaMyMemory> mockedStatic = mockStatic(ConsultaMyMemory.class)) {
      mockedStatic.when(() -> ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()))
          .thenReturn(dadosSerie.sinopse());

      serie = new Serie(dadosSerie);
    }

    List<SerieDto> serieDtoList = List.of(new SerieDto(serie));

    when(this.service.topFive()).thenReturn(serieDtoList);

    // ASSERT
    asserts("/series/top5", serieDtoList);

    verify(this.service, times(1)).topFive();
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter as series mais recentes")
  void deveRetornarOkTop5Lancamentos() throws Exception {
    // ARRANGE
    DadosSerie dadosSerie = getDadosSerie();

    // ACT
    Serie serie;
    try (MockedStatic<ConsultaMyMemory> mockedStatic = mockStatic(ConsultaMyMemory.class)) {
      mockedStatic.when(() -> ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()))
          .thenReturn(dadosSerie.sinopse());

      serie = new Serie(dadosSerie);
    }

    List<SerieDto> serieDtoList = List.of(new SerieDto(serie));

    when(this.service.topFiveOrderByDate()).thenReturn(serieDtoList);

    // ASSERT
    asserts("/series/lancamentos", serieDtoList);

    verify(this.service, times(1)).topFiveOrderByDate();
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter uma serie pelo id")
  void deveRetornarOkPorId() throws Exception {
    // ARRANGE
    DadosSerie dadosSerie = getDadosSerie();

    // ACT
    Serie serie;
    try (MockedStatic<ConsultaMyMemory> mockedStatic = mockStatic(ConsultaMyMemory.class)) {
      mockedStatic.when(() -> ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()))
          .thenReturn(dadosSerie.sinopse());

      serie = new Serie(dadosSerie);
    }

    List<SerieDto> serieDtoList = List.of(new SerieDto(serie));

    SerieDto dto = serieDtoList.getFirst();

    when(this.service.getById(1L)).thenReturn(dto);

    // ASSERT
    mvc.perform(get("/series/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(8))
        .andExpect(jsonPath("$.titulo").value(dto.titulo()))
        .andExpect(jsonPath("$.totalTemporadas").value(dto.totalTemporadas()))
        .andExpect(jsonPath("$.avaliacao").value(dto.avaliacao()))
        .andExpect(jsonPath("$.atores").value(dto.atores()))
        .andExpect(jsonPath("$.poster").value(dto.poster()))
        .andExpect(jsonPath("$.genero").value(Categoria.ACAO.toString()))
        .andExpect(jsonPath("$.id").value(dto.id()))
        .andDo(print()); // útil para debug

    verify(this.service, times(1)).getById(1L);
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter series pelo gênero")
  void obterPorNomeGenero() throws Exception {
    // ARRANGE
    DadosSerie dadosSerie = getDadosSerie();

    // ACT
    Serie serie;
    try (MockedStatic<ConsultaMyMemory> mockedStatic = mockStatic(ConsultaMyMemory.class)) {
      mockedStatic.when(() -> ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()))
          .thenReturn(dadosSerie.sinopse());

      serie = new Serie(dadosSerie);
    }

    List<SerieDto> serieDtoList = List.of(new SerieDto(serie));

    when(this.service.getSeriesByCategory(Categoria.ACAO.toString())).thenReturn(serieDtoList);

    // ASSERT
    asserts(("/series/categoria/%s".formatted(Categoria.ACAO)), serieDtoList);

    verify(this.service, times(1))
        .getSeriesByCategory(Categoria.ACAO.toString());
  }
}