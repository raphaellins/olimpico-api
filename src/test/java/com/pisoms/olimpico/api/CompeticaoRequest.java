package com.pisoms.olimpico.api;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pisoms.olimpico.api.model.Competidor;
import com.pisoms.olimpico.api.model.Local;
import com.pisoms.olimpico.api.model.Modalidade;
import com.pisoms.olimpico.api.type.EtapaType;

public class CompeticaoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "modalidade")
	private Modalidade modalidade;

	@JsonProperty(value = "local")
	private Local local;

	@JsonProperty(value = "dataInicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
	private Date dataInicio;

	@JsonProperty(value = "dataTermino")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
	private Date dataTermino;

	@JsonProperty(value = "competidorA")
	private Competidor competidorA;

	@JsonProperty(value = "competidorB")
	private Competidor competidorB;

	@JsonProperty(value = "etapa")
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	private EtapaType etapa;

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
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