package com.pisoms.olimpico.api.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pisoms.olimpico.api.service.exception.ConflitoCompeticaoException;
import com.pisoms.olimpico.api.service.exception.RegistroIdenticoException;
import com.pisoms.olimpico.api.service.exception.TempoCompeticaoException;
import com.pisoms.olimpico.api.service.exception.UltrapassaLimiteCompeticaoLocalException;

/**
 * Classe que trata algumas excessões e monta uma melhor mensagem
 *
 */
@ControllerAdvice
public class OlimpicoApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criaListaDeErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ RegistroIdenticoException.class })
	public ResponseEntity<Object> handleRegistroIdenticoException(RegistroIdenticoException ex) {
		String mensagemUsuario = messageSource.getMessage("recurso.identico", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ ConflitoCompeticaoException.class })
	public ResponseEntity<Object> handleConflitoCompeticaoException(ConflitoCompeticaoException ex) {
		String mensagemUsuario = messageSource.getMessage("conflito.competicao", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ TempoCompeticaoException.class })
	public ResponseEntity<Object> handleTempoCompeticaoException(TempoCompeticaoException ex) {
		String mensagemUsuario = messageSource.getMessage("tempo.competicao", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ UltrapassaLimiteCompeticaoLocalException.class })
	public ResponseEntity<Object> handleUltrapassaLimiteCompeticaoException(
			UltrapassaLimiteCompeticaoLocalException ex) {
		String mensagemUsuario = messageSource.getMessage("limite.competicao", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> handleValidationException(ValidationException ex) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ResponseStatus(value = HttpStatus.CONFLICT) // 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<Object> conflict(DataIntegrityViolationException e) {
		String mensagemUsuario = messageSource.getMessage("recurso.constraint", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = e.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);

	}

	private List<Erro> criaListaDeErros(BindingResult bindResults) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindResults.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	/**
	 * Trata excessoes relacionadas quando tentar executar uma operacao em um
	 * recurso que nao existe
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
