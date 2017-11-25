package com.pisoms.olimpico.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pisoms.olimpico.api.model.Local;
import com.pisoms.olimpico.api.repository.LocalRepository;
import com.pisoms.olimpico.api.service.exception.RegistroIdenticoException;

@Service
public class LocalService {
	
	@Autowired
	LocalRepository localRepository;

	private String pattern(String nomeLocal) {
		return nomeLocal.trim().toUpperCase();
	}
	
	
	/**
	 * Salva um local aplicando um pattern especifico
	 * 
	 * @param local
	 * @return Local
	 */
	public Local salvar(Local local) {
		
		String nomeLocal = pattern(local.getNome());
		
		Local localSearch = localRepository.findByNome(nomeLocal);
		
		if(localSearch == null) {
			local.setNome(nomeLocal);
			return localRepository.save(local);
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
	public Local atualizar(Integer codigo, Local local) {
		Local localSalvo = localRepository.findOne(codigo);
		
		if(localSalvo ==  null) {
			throw new EmptyResultDataAccessException(codigo);
		}
		
		localSalvo.setNome(pattern(local.getNome()));
		
		localRepository.save(localSalvo);
		
		return localSalvo;
	}

	/**
	 * Lista todos os Locais cadastrados
	 * @return List<Local>
	 */
	public List<Local> listarTodos() {
		return localRepository.findAll();
	}

	/**
	 * Obtem um Local apartir do codigo de cadastro
	 * @param codigo
	 * @return Local
	 */
	public Local obter(Integer codigo) {
		return localRepository.findOne(codigo);
	}

	
	/**
	 * Deleta um Local
	 * @param codigo
	 */
	public void delete(Integer codigo) {
		Local local = localRepository.findOne(codigo);

		if (local == null) {
			throw new EmptyResultDataAccessException(1);
		}

		localRepository.delete(local);
	}
}
