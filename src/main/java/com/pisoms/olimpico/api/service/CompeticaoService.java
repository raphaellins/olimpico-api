package com.pisoms.olimpico.api.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pisoms.olimpico.api.model.Competicao;
import com.pisoms.olimpico.api.repository.CompeticaoRepository;
import com.pisoms.olimpico.api.service.exception.ConflitoCompeticaoException;
import com.pisoms.olimpico.api.service.exception.TempoCompeticaoException;
import com.pisoms.olimpico.api.service.exception.UltrapassaLimiteCompeticaoLocalException;
import com.pisoms.olimpico.api.type.EtapaType;

@Service
public class CompeticaoService {

	@Autowired
	private CompeticaoRepository competicaoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Verifica se a data de inicio, Modalidade e Local conflita com o periodo de
	 * alguma competicao ja cadastrada
	 * 
	 * Caso seja uma atualizacao a busca por competicoes e diferenciada
	 * 
	 * @param competicao
	 * @return boolean
	 */
	private boolean existeCompeticaoNoPeriodo(Competicao competicao) {
		List<Competicao> competicoes = new ArrayList<>();

		if (competicao.getId() == null) {
			competicoes = competicaoRepository.findCompeticaoByLocalDataInicioAndModalidade(
					competicao.getLocal().getId(), competicao.getDataInicio(),
					competicao.getModalidade().getId());
		} else {
			competicoes = competicaoRepository.findCompeticaoByLocalDataInicioAndModalidadeUpdate(competicao.getId(),
					competicao.getLocal().getId(), competicao.getDataInicio(),
					competicao.getModalidade().getId());
		}

		return !competicoes.isEmpty();
	}

	/**
	 * Salva a competicao, realizando validacoe
	 * 
	 * @param competicao
	 * @return Competicao
	 */
	public Competicao salvar(Competicao competicao) {
		validate(competicao);

		return competicaoRepository.save(competicao);
	}

	private void validate(Competicao competicao) {

		validaDatas(competicao);

		if (existeCompeticaoNoPeriodo(competicao)) {
			throw new ConflitoCompeticaoException();
		}

		Duration duracao = Duration.between(competicao.getDataInicio(), competicao.getDataTermino());
		Double segundosDeCompeticao = (double) duracao.getSeconds();

		// Um tempo de competição não pode ser menor que 30 minutos
		if (segundosDeCompeticao.compareTo(1800.0) < 0) {
			throw new TempoCompeticaoException();
		}

		if (ultrapassaLimiteCompeticaoLocal(competicao)) {
			throw new UltrapassaLimiteCompeticaoLocalException();
		}

		// Somente é permitido o mesmo competidor caso seja em fase FINAL ou SEMIFINAL
		if (!competicao.getEtapa().equals(EtapaType.FINAL) && !competicao.getEtapa().equals(EtapaType.SEMIFINAL)) {
			if (competicao.getCompetidorA().getId().equals(competicao.getCompetidorB().getId())) {
				throw new ValidationException("Não é possivel Competidores iguais para uma a mesma competição que não seja final ou semifinal.");
			}
		}

	}

	/**
	 * Validação padrão para uma Olimpiada que sera realizada no ano de 2020, e
	 * verificação se a data de termino de uma competição é menor que a data de
	 * inicio
	 * 
	 * @param competicao
	 */
	private void validaDatas(Competicao competicao) {
		LocalDateTime inicio = competicao.getDataInicio();
		LocalDateTime termino = competicao.getDataTermino();

		if (inicio.getYear() != 2020 || termino.getYear() != 2020) {
			throw new ValidationException("Somente valido competicoes para as 'Olimpiadas de Tokio 2020'");
		}

		if (termino.isBefore(inicio)) {
			throw new ValidationException("Data de termino da competicao deve ser maior que a data inicial");
		}
	}

	/**
	 * Verifica se ja existe um total de 4 competicoes em um mesmo dia no mesmo
	 * local
	 * 
	 * @param competicao
	 * @return boolean
	 */
	private boolean ultrapassaLimiteCompeticaoLocal(Competicao competicao) {
		LocalDateTime dataInicialDiaLocal = competicao.getDataInicio().with(LocalTime.MIN);
		LocalDateTime dataTerminalDiaLocal = competicao.getDataTermino().with(LocalTime.MAX);

		List<Competicao> competicoesDoDia = competicaoRepository.listByLocalDia(competicao.getLocal().getId(),
				dataInicialDiaLocal, dataTerminalDiaLocal);

		if (!competicoesDoDia.isEmpty() && competicoesDoDia.size() >= 4) {
			return true;
		}

		return false;
	}

	/**
	 * Lista competicoes por modalidade
	 * 
	 * @param codigo
	 * @return
	 */
	public List<Competicao> listarPorModalidade(Integer codigo) {
		List<Competicao> listByModalidade = competicaoRepository.listByModalidade(codigo);
		
		if(listByModalidade.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return listByModalidade;
	}

	/**
	 * Obtem uma competicao pelo codigo
	 * 
	 * @param codigo
	 * @return Competicao
	 */
	public Competicao obter(Integer codigo) {
		return competicaoRepository.findOne(codigo);
	}

	/**
	 * Deleta uma competicao pelo seu codigo se existir
	 * 
	 * @param codigo
	 */
	public void delete(Integer codigo) {
		Competicao competicao = competicaoRepository.findOne(codigo);

		if (competicao == null) {
			throw new EmptyResultDataAccessException(1);
		}

		competicaoRepository.delete(competicao);
	}

	public List<Competicao> listarTodos() {
		return competicaoRepository.findAll(new Sort(Sort.Direction.ASC, "dataInicio"));
	}

	/**
	 * Atualiza todos os dados da competicao e salva, validando os dados antes
	 * 
	 * @param codigo
	 * @param competicao
	 * @return Competicao
	 */
	public Competicao atualizar(Integer codigo, Competicao competicao) {
		Competicao competicaoSalva = competicaoRepository.findOne(codigo);

		if (competicaoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(competicao, competicaoSalva, "id");

		validate(competicaoSalva);

		return competicaoRepository.save(competicaoSalva);
	}
}
