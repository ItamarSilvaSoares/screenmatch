package br.com.alura.screenmatch.util.record;


public record SeasonHelper(Integer seasonNumber, String nomeSerie) {

  public static SeasonHelper none(String nomeSerie) {
    return new SeasonHelper(null, nomeSerie);
  }

  public static SeasonHelper of(int seasonNumber, String nomeSerie) {
    return new SeasonHelper(seasonNumber, nomeSerie);
  }

}
