package com.pisoms.olimpico.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pisoms.olimpico.api.model.Local;

public interface LocalRepository extends JpaRepository<Local, Integer>{
	
	@Query("select l from Local l where l.nome = :nome_local")
	public Local findByNome(@Param("nome_local") String nomeLocal);

}
