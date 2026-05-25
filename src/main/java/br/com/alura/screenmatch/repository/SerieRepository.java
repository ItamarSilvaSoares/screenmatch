package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SerieRepository
    extends JpaRepository<Serie, Long>, JpaSpecificationExecutor<Serie> {

  // Exemplos de derived queries
  //  Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
  //
  //  List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor,
  // double avaliacao);
  //
  //  List<Serie> findTop5ByOrderByAvaliacaoDesc();
  //
  //  List<Serie> findByGenero(Categoria categoria);
  //
  //  List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int
  // totalTemporadas, double avaliacao);

}
