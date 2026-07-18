package br.com.alura.screenmatch.episode.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.alura.screenmatch.episode.dto.EpisodioDto;
import br.com.alura.screenmatch.episode.entity.DadosEpisodio;
import br.com.alura.screenmatch.episode.entity.Episodio;
import br.com.alura.screenmatch.episode.service.EpisodeService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EpisodeController.class)
@AutoConfigureMockMvc
class EpisodeControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private EpisodeService episodeService;

  private List<EpisodioDto> episodioDto;

  @BeforeEach
  void setUp() {
    this.episodioDto = getEpisodioDtoList();
  }

  private List<EpisodioDto> getEpisodioDtoList() {
    DadosEpisodio dadosEpisodio = new DadosEpisodio("titulo qualquer", 1, "5", "2026-01-01");
    Episodio episodio = new Episodio(2, dadosEpisodio);
    return List.of(
        new EpisodioDto(episodio)
    );
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET', para obter uma temporada com id da serie e season")
  void deveRetornarTemporadaPorNumero() throws Exception {
    // ARRANGE
    EpisodioDto dto = this.episodioDto.getFirst();

    // ACT
    when(episodeService.obterTemporada(1L, 2))
        .thenReturn(this.episodioDto);

    // ASSERT

    mvc.perform(get("/series/{id}/temporadas/{numero}", 1L, 2))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(this.episodioDto.size()))
        .andExpect(jsonPath("$[0].titulo").value(dto.titulo()))
        .andExpect(jsonPath("$[0].temporada").value(dto.temporada()))
        .andExpect(jsonPath("$[0].id").doesNotExist()) // campo null
        .andDo(print()); // útil para debug

    verify(episodeService, times(1)).obterTemporada(1L, 2);
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET' todas as temporadas")
  void deveRetornarTodasTemporada() throws Exception {
    // ARRANGE
    EpisodioDto dto = this.episodioDto.getFirst();

    // ACT
    when(episodeService.obterTodasTemporadas(1L))
        .thenReturn(this.episodioDto);

    // ASSERT

    mvc.perform(get("/series/{id}/temporadas/todas", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(this.episodioDto.size()))
        .andExpect(jsonPath("$[0].titulo").value(dto.titulo()))
        .andExpect(jsonPath("$[0].temporada").value(dto.temporada()))
        .andExpect(jsonPath("$[0].id").doesNotExist()) // campo null
        .andDo(print()); // útil para debug

    verify(episodeService, times(1)).obterTodasTemporadas(1L);
  }

  @Test
  @DisplayName("Deve retornar status 200 para requisição 'GET'. top 5 episódios de uma serie")
  void deverRetornarTopEpisodio() throws Exception {
    // ARRANGE
    EpisodioDto dto = this.episodioDto.getFirst();

    // ACT
    when(episodeService.searchTop5Episode(1L))
        .thenReturn(this.episodioDto);

    // ASSERT

    mvc.perform(get("/series/{id}/temporadas/top", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(this.episodioDto.size()))
        .andExpect(jsonPath("$[0].titulo").value(dto.titulo()))
        .andExpect(jsonPath("$[0].temporada").value(dto.temporada()))
        .andExpect(jsonPath("$[0].id").doesNotExist()) // campo null
        .andDo(print()); // útil para debug

    verify(episodeService, times(1)).searchTop5Episode(1L);
  }
}