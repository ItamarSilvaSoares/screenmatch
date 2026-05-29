package br.com.alura.screenmatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "episodios")
public class Episodio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private Integer temporada;
  private String titulo;
  private Integer numeroEpisodio;
  private Double avaliacao;
  private LocalDate dataLancamento;
  @ManyToOne private Serie serie;

  public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
    this.temporada = numeroTemporada;
    this.titulo = dadosEpisodio.titulo();
    this.numeroEpisodio = dadosEpisodio.numeroEpisodio();

    try {
      this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
    } catch (NumberFormatException ex) {
      this.avaliacao = 0.0;
    }

    try {
      this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
    } catch (DateTimeParseException ex) {
      this.dataLancamento = null;
    }
  }

  @Override
  public String toString() {
    return "temporada: "
        + temporada
        + ", titulo: '"
        + titulo
        + '\''
        + ", numeroEpisodio: "
        + numeroEpisodio
        + ", avaliacao: "
        + avaliacao
        + ", dataLançamento: "
        + dataLancamento;
  }
}
