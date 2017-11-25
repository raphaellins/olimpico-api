package com.pisoms.olimpico.api.exceptionHandler;

public class Erro {
	
	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	
	public Erro(String msgU, String msgD) {
		this.mensagemUsuario = msgU;
		this.mensagemDesenvolvedor = msgD;
	}
	
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}
	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}
	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}
	public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
}
