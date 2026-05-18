package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.translation.ConsultaMyMemory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String titulo;

  private Integer totalTemporadas;

  private Double avaliacao;

  @Enumerated(EnumType.STRING)
  private Categoria genero;

  @Column(columnDefinition = "text[]")
  private String[] atores;

  private String poster;

  private String sinopse;

  @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  //  @Setter(AccessLevel.NONE)
  private List<Episodio> episodios = new ArrayList<>();

  public Serie() {}

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
        + '\''
        + ", episódios: '"
        + episodios
        + '\'';
  }

  public void setEpisodios(List<Episodio> episodios) {
    if (episodios != null) {
      episodios.forEach(e -> e.setSerie(this));
    }
    this.episodios = episodios;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Integer getTotalTemporadas() {
    return totalTemporadas;
  }

  public void setTotalTemporadas(Integer totalTemporadas) {
    this.totalTemporadas = totalTemporadas;
  }

  public Double getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(Double avaliacao) {
    this.avaliacao = avaliacao;
  }

  public Categoria getGenero() {
    return genero;
  }

  public void setGenero(Categoria genero) {
    this.genero = genero;
  }

  public String[] getAtores() {
    return atores;
  }

  public void setAtores(String[] atores) {
    this.atores = atores;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public List<Episodio> getEpisodios() {
    return episodios;
  }
}
