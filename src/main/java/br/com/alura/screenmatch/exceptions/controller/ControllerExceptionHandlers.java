package br.com.alura.screenmatch.exceptions.controller;

import br.com.alura.screenmatch.exceptions.enums.ConstraintErrorInfo;
import br.com.alura.screenmatch.exceptions.service.ProblemFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandlers {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ProblemDetail> dataIntegrityViolation(DataIntegrityViolationException e,
      HttpServletRequest request) {

    Throwable cause = e;

    log.error("DataIntegrityViolationException", e);

    while (cause != null) {
      if (cause instanceof ConstraintViolationException cve) {

        return ConstraintErrorInfo.fromValue(cve.getConstraintName())
            .map(constraint -> switch (constraint) {
              case UK_SERIE_TITULO ->
                  ResponseEntity.status(ConstraintErrorInfo.UK_SERIE_TITULO.getHttpStatus())
                      .body(ProblemFactory.create(
                          ConstraintErrorInfo.UK_SERIE_TITULO,
                          request.getRequestURI()));
              case DEFAULT -> null;
            })
            .orElseGet(() -> ResponseEntity.badRequest().body(ProblemFactory.create(
                ConstraintErrorInfo.DEFAULT,
                request.getRequestURI())));

      }

      cause = cause.getCause();
    }

    return ResponseEntity.internalServerError().build();
  }

}
