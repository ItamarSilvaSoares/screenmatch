package br.com.alura.screenmatch.exceptions.service;

import br.com.alura.screenmatch.exceptions.enums.ConstraintErrorInfo;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProblemFactory {

  public static ProblemDetail constraintError(
      ConstraintErrorInfo constraint,
      String uri) {

    ProblemDetail problem = ProblemDetail.forStatus(constraint.getHttpStatus());
    problem.setTitle(constraint.getTitle());
    problem.setDetail(constraint.getDatils());
    problem.setType(URI.create(constraint.getType()));
    problem.setInstance(URI.create(uri));

    return problem;
  }

  public static ProblemDetail handle(String title, String detail, String type, HttpStatus httpStatus) {
    ProblemDetail problem = ProblemDetail.forStatus(httpStatus);
    problem.setTitle(title);
    problem.setDetail(detail);
    problem.setType(URI.create(type));
    return problem;
  }

}
