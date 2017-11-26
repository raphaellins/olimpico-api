package com.pisoms.olimpico.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * Evento chamado quando algum recurso é criado com sucesso
 */
public class RecursoCriadoEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	private Integer codigo;
	
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Integer getCodigo() {
		return codigo;
	}

}
