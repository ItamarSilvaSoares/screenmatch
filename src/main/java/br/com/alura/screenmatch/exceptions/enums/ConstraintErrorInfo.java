package br.com.alura.screenmatch.exceptions.enums;

import br.com.alura.screenmatch.serie.entity.DatabaseConstraints;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ConstraintErrorInfo {
  UK_SERIE_TITULO(DatabaseConstraints.UK_SERIE_TITULO,
      HttpStatus.CONFLICT,
      "Série já cadastrada",
      "Conflito de dados, a série fornecida já está no banco de dados.",
      HttpStatus.CONFLICT.getReasonPhrase()),

  DEFAULT("Default",
      HttpStatus.BAD_REQUEST,
      "Algo de errado na requisição",
      "Violação de integridade",
      HttpStatus.BAD_REQUEST.getReasonPhrase());

  private final String constraint;
  private final HttpStatus httpStatus;
  private final String title;
  private final String datils;
  private final String type;

  public static Optional<ConstraintErrorInfo> fromValue(String value) {
    return Arrays.stream(values())
        .filter(c -> c.constraint.equals(value))
        .findFirst();
  }
}
