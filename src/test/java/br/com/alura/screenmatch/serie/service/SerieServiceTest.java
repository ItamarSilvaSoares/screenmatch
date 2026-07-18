package br.com.alura.screenmatch.serie.service;

import br.com.alura.screenmatch.util.http.SeriesApiClient;
import org.junit.jupiter.api.BeforeEach;
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
  void save() {
  }

  @Test
  void findAll() {
  }

  @Test
  void findSerieByName() {
  }

  @Test
  void searchEpisodesSeries() {
  }

  @Test
  void searchByCategory() {
  }

  @Test
  void searchBySeasonsAndRating() {
  }

  @Test
  void findSeriesByNomeActor() {
  }

  @Test
  void topFive() {
  }

  @Test
  void topFiveOrderByDate() {
  }

  @Test
  void getById() {
  }

  @Test
  void getSeriesByCategory() {
  }
}