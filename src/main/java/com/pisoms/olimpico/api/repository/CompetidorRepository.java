package com.pisoms.olimpico.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pisoms.olimpico.api.model.Competidor;

public interface CompetidorRepository extends JpaRepository<Competidor, Integer>{
	
	@Query("select c from Competidor c where c.nome = :nome_competidor")
	public Competidor findByNome(@Param("nome_competidor") String nomeLocal);

}
