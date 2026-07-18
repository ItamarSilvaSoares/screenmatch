package br.com.alura.screenmatch.exceptions.service;

import br.com.alura.screenmatch.exceptions.enums.ConstraintErrorInfo;
import java.net.URI;
import org.springframework.http.ProblemDetail;

public class ProblemFactory {

  public static ProblemDetail create(
      ConstraintErrorInfo constraint,
      String uri) {

    ProblemDetail problem = ProblemDetail.forStatus(constraint.getHttpStatus());
    problem.setTitle(constraint.getTitle());
    problem.setDetail(constraint.getDatils());
    problem.setType(URI.create(constraint.getDatils()));
    problem.setInstance(URI.create(uri));

    return problem;
  }
}
