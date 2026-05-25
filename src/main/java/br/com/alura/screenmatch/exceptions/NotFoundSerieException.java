package br.com.alura.screenmatch.exceptions;

public class NotFoundSerieException extends RuntimeException {
  public NotFoundSerieException(String message) {
    String msg = "Serie not found: " + message;
    super(msg);
  }
}
