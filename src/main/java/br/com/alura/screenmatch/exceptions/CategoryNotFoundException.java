package br.com.alura.screenmatch.exceptions;

import br.com.alura.screenmatch.exceptions.enums.ExceptionsErrorInfo;
import br.com.alura.screenmatch.exceptions.service.ProblemFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CategoryNotFoundException extends RuntimeException implements ICustomException {
  protected String serieName;
  public CategoryNotFoundException(String serieName) {
    String msg = "Nenhuma categoria encontrada para o texto fornecida: %s".formatted(serieName);
    this.serieName = serieName;
    super(msg);
  }

  @Override
  public ProblemDetail getProblemDetail() {
    String title = ExceptionsErrorInfo.CategoryNotFound.getTitle().formatted(this.serieName);
    String datils = ExceptionsErrorInfo.CategoryNotFound.getDatils().formatted(this.serieName);
    String type = ExceptionsErrorInfo.CategoryNotFound.getType();
    HttpStatus status = ExceptionsErrorInfo.CategoryNotFound.getHttpStatus();
    return ProblemFactory.handle(title, datils,type,status);
  }
}
