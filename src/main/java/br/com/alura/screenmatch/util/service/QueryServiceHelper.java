package br.com.alura.screenmatch.util.service;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class QueryServiceHelper {

  public static Sort sort(Sort.Direction direction, String propertyName) {
    if (direction == Sort.Direction.DESC) {
      return Sort.by(Sort.Order.desc(propertyName).nullsLast());
    }
    return Sort.by(Sort.Order.asc(propertyName).nullsLast());
  }

  public static @NonNull Pageable top5() {
    return PageRequest.of(0, 5, QueryServiceHelper.sort(Direction.DESC, "avaliacao"));
  }
}
