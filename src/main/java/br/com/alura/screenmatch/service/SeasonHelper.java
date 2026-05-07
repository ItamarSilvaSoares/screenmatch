package br.com.alura.screenmatch.service;

public class SeasonHelper {
  private final Integer seasonNumber;

  private SeasonHelper(Integer seasonNumber) {
    this.seasonNumber = seasonNumber;
  }

  public static SeasonHelper none() {
    return new SeasonHelper(null);
  }

  public static SeasonHelper of(int seasonNumber) {
    return new SeasonHelper(seasonNumber);
  }

  public boolean hasSeason() {
    return seasonNumber != null;
  }

  public Integer getSeasonNumber() {
    return seasonNumber;
  }

}
