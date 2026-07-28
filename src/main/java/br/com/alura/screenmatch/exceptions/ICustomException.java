package br.com.alura.screenmatch.exceptions;

import org.springframework.http.ProblemDetail;

public interface ICustomException {
  ProblemDetail getProblemDetail();
}
