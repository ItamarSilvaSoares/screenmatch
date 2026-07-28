package br.com.alura.screenmatch.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionsErrorInfo implements IErrorInfo {
  CategoryNotFound(
      HttpStatus.BAD_REQUEST,
      "Categoria: '%s' não encontrada.",
      "Categoria: '%s' não cadastrada.",
      HttpStatus.BAD_REQUEST.getReasonPhrase());

  private final HttpStatus httpStatus;
  private final String title;
  private final String datils;
  private final String type;

}
