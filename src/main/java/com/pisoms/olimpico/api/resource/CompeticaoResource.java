package com.pisoms.olimpico.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisoms.olimpico.api.event.RecursoCriadoEvent;
import com.pisoms.olimpico.api.model.Competicao;
import com.pisoms.olimpico.api.service.CompeticaoService;

@RestController
@RequestMapping("/competicoes")
public class CompeticaoResource {

	@Autowired
	CompeticaoService competicaoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Competicao> listar() {
		return competicaoService.listarTodos();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Competicao> obter(@PathVariable Integer codigo) {
		Competicao competicao = competicaoService.obter(codigo);
		return competicao != null ? ResponseEntity.ok(competicao) : ResponseEntity.notFound().build();
	}

	@GetMapping("/modalidade/{codigo}")
	public List<Competicao> listarPorModalidade(@PathVariable Integer codigo) {
		return competicaoService.listarPorModalidade(codigo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Competicao> criar(@Valid @RequestBody Competicao competicao, HttpServletResponse response) {
		Competicao competicaoSalva = competicaoService.salvar(competicao);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, competicaoSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer codigo) {
		competicaoService.delete(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Competicao> atualizar(@PathVariable Integer codigo,
			@Valid @RequestBody Competicao competicao) {
		Competicao competicaoAtualizada = competicaoService.atualizar(codigo, competicao);
		return ResponseEntity.ok(competicaoAtualizada);
	}
}
