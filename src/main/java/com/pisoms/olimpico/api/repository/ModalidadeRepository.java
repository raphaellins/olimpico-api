package com.pisoms.olimpico.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pisoms.olimpico.api.model.Modalidade;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Integer>{
	
	@Query("select m from Modalidade m where m.nome = :nome_modalidade")
	public Modalidade findByNome(@Param("nome_modalidade") String nomeModalidade);

}
