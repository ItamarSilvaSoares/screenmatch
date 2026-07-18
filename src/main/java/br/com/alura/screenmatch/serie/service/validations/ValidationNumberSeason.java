package br.com.alura.screenmatch.serie.service.validations;

public class ValidationNumberSeason {

  public static int getNumberOfSeasons(int number) {
    int quantTemporadas = 50;

    if (number <= 0) {
      return quantTemporadas;
    }

    return number;
  }

}
