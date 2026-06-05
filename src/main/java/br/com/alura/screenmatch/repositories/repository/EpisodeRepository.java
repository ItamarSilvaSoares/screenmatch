package br.com.alura.screenmatch.repositories.repository;

import br.com.alura.screenmatch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository
    extends JpaRepository<Episodio, Long>, JpaSpecificationExecutor<Episodio> {}
