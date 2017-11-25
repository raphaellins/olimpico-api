package com.pisoms.olimpico.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pisoms.olimpico.api.type.EtapaType;

@Entity
@Table(name = "competicao")
public class Competicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "modalidade_id", referencedColumnName = "id")
	private Modalidade modalidade;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "local_id", referencedColumnName = "id")
	private Local local;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "competidor_1_id", referencedColumnName = "id")
	private Competidor competidorB;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "competidor_2_id", referencedColumnName = "id")
	private Competidor competidorA;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EtapaType etapa;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "data_termino")
	private LocalDateTime dataTermino;

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDateTime dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Competidor getCompetidorA() {
		return competidorA;
	}

	public void setCompetidorA(Competidor competidorA) {
		this.competidorA = competidorA;
	}

	public Competidor getCompetidorB() {
		return competidorB;
	}

	public void setCompetidorB(Competidor competidorB) {
		this.competidorB = competidorB;
	}

	public EtapaType getEtapa() {
		return etapa;
	}

	public void setEtapa(EtapaType etapa) {
		this.etapa = etapa;
	}
}
