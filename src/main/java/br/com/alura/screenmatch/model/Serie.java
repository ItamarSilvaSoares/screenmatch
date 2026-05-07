package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.translation.ConsultaMyMemory;
import java.util.Arrays;
import java.util.OptionalDouble;

public class Serie {
  private final String titulo;
  private final Integer totalTemporadas;
  private final Double avaliacao;
  private final Categoria genero;
  private final String[] atores;
  private final String poster;
  private final String sinopse;

  public Serie(DadosSerie dados) {
    this.titulo = dados.titulo();
    this.totalTemporadas = dados.totalTemporadas();
    this.avaliacao = OptionalDouble.of(Double.parseDouble(dados.avaliacao())).orElse(0.0);
    this.genero = Categoria.fromString(dados.genero().split(",")[0].trim());
    this.atores = dados.atores().split(",\\s*");

    String sinopseTraduzida = ConsultaMyMemory.obterTraducao(dados.sinopse()).trim();
    this.sinopse = sinopseTraduzida.isEmpty() ? dados.sinopse() : sinopseTraduzida;
    this.poster = dados.poster();
  }

  public String getTitulo() {
    return titulo;
  }

  public Integer getTotalTemporadas() {
    return totalTemporadas;
  }

  public Double getAvaliacao() {
    return avaliacao;
  }

  public Categoria getGenero() {
    return genero;
  }

  public String[] getAtores() {
    return atores;
  }

  public String getPoster() {
    return poster;
  }

  public String getSinopse() {
    return sinopse;
  }

  @Override
  public String toString() {
    return "gênero: "
        + genero
        + ", titulo: '"
        + titulo
        + '\''
        + ", totalTemporadas: "
        + totalTemporadas
        + ", avaliação: "
        + avaliacao
        + ", atores: "
        + Arrays.toString(atores)
        + ", poster: '"
        + poster
        + '\''
        + ", sinopse: '"
        + sinopse
        + '\'';
  }
}
