package br.com.alura.screenmatch.service;

public class SeasonHelper {
  private final Integer seasonNumber;
  private final String nomeSerie;

  private SeasonHelper(Integer seasonNumber, String nomeSerie) {
    this.seasonNumber = seasonNumber;
    this.nomeSerie = nomeSerie;
  }

  public static SeasonHelper none(String nomeSerie) {
    return new SeasonHelper(null, nomeSerie);
  }

  public static SeasonHelper of(int seasonNumber, String nomeSerie) {
    return new SeasonHelper(seasonNumber, nomeSerie);
  }

  public boolean hasSeason() {
    return seasonNumber != null;
  }

  public Integer getSeasonNumber() {
    return seasonNumber;
  }

  public String getNomeSerie() {
    return nomeSerie;
  }
}
