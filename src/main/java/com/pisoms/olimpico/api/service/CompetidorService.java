package com.pisoms.olimpico.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pisoms.olimpico.api.model.Competidor;
import com.pisoms.olimpico.api.repository.CompetidorRepository;
import com.pisoms.olimpico.api.service.exception.RegistroIdenticoException;

@Service
public class CompetidorService {
	
	@Autowired
	CompetidorRepository competidorRepository;

	/**
	 * Salva um local aplicando um pattern especifico
	 * 
	 * @param local
	 * @return Local
	 */
	public Competidor salvar(Competidor competidor) {
		
		String nomeCompetidor = competidor.getNome();
		
		Competidor competidorSearch = competidorRepository.findByNome(nomeCompetidor);
		
		if(competidorSearch == null) {
			competidor.setNome(nomeCompetidor);
			return competidorRepository.save(competidor);
		}else {
			throw new RegistroIdenticoException();
		}
	}
	
	
	/**
	 * Atualiza o Local caso exista
	 * 
	 * @param codigo
	 * @param local
	 * @return Local
	 */
	public Competidor atualizar(Integer codigo, Competidor competidor) {
		Competidor competidorSalvo = competidorRepository.findOne(codigo);
		
		if(competidorSalvo ==  null) {
			throw new EmptyResultDataAccessException(codigo);
		}
		
		competidorSalvo.setNome(competidor.getNome());
		
		competidorRepository.save(competidorSalvo);
		
		return competidorSalvo;
	}

	/**
	 * Lista todos os Locais cadastrados
	 * @return List<Local>
	 */
	public List<Competidor> listarTodos() {
		return competidorRepository.findAll();
	}

	/**
	 * Obtem um Local apartir do codigo de cadastro
	 * @param codigo
	 * @return Local
	 */
	public Competidor obter(Integer codigo) {
		return competidorRepository.findOne(codigo);
	}

	
	/**
	 * Deleta um Local
	 * @param codigo
	 */
	public void delete(Integer codigo) {
		Competidor competidor = competidorRepository.findOne(codigo);

		if (competidor == null) {
			throw new EmptyResultDataAccessException(1);
		}

		competidorRepository.delete(competidor);
	}
}
