package com.pisoms.olimpico.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pisoms.olimpico.api.model.Modalidade;
import com.pisoms.olimpico.api.repository.ModalidadeRepository;
import com.pisoms.olimpico.api.service.exception.RegistroIdenticoException;

@Service
public class ModalidadeService {

	@Autowired
	ModalidadeRepository modalidadeRepository;

	private String pattern(String nomeModalidade) {
		return nomeModalidade.trim().toUpperCase();
	}

	/**
	 * Salva uma modalidade aplicando pattern especifico
	 * 
	 * @param modalidade
	 * @return Modalidade
	 */
	public Modalidade salvar(Modalidade modalidade) {

		String nomeModalidade = pattern(modalidade.getNome());

		Modalidade localSearch = modalidadeRepository.findByNome(nomeModalidade);

		if (localSearch == null) {
			modalidade.setNome(nomeModalidade);
			return modalidadeRepository.save(modalidade);
		} else {
			throw new RegistroIdenticoException();
		}
	}

	/**
	 * Atualiza uma modalidade
	 * 
	 * @param codigo
	 * @param modalidade
	 * @return Modalidade
	 */
	public Modalidade atualizar(Integer codigo, Modalidade modalidade) {
		Modalidade modalidadeSalvo = modalidadeRepository.findOne(codigo);

		if (modalidadeSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}

		modalidadeSalvo.setNome(pattern(modalidade.getNome()));

		modalidadeRepository.save(modalidadeSalvo);

		return modalidadeSalvo;
	}

	/**
	 * Deleta uma Modalidade
	 * 
	 * @param codigo
	 */
	public void delete(Integer codigo) {
		Modalidade modalidade = modalidadeRepository.findOne(codigo);

		if (modalidade == null) {
			throw new EmptyResultDataAccessException(1);
		}

		modalidadeRepository.delete(modalidade);
	}

	/**
	 * Lista todas as Modalidades cadastradas
	 * @return List<Modalidade>
	 */
	public List<Modalidade> listarTodos() {
		return modalidadeRepository.findAll();
	}

	
	/**
	 * Obtem uma modalidade pelo codigo
	 * @param codigo
	 * @return Modalidade
	 */
	public Modalidade obter(Integer codigo) {
		return modalidadeRepository.findOne(codigo);
	}
}
