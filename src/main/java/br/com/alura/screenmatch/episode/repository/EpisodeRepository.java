package br.com.alura.screenmatch.episode.repository;

import br.com.alura.screenmatch.episode.entity.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository
    extends JpaRepository<Episodio, Long>, JpaSpecificationExecutor<Episodio> {

}
