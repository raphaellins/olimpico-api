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
import com.pisoms.olimpico.api.model.Modalidade;
import com.pisoms.olimpico.api.service.ModalidadeService;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeResource {
	
	@Autowired
	private ModalidadeService modalidadeService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Modalidade> listar(){
		return modalidadeService.listarTodos();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Modalidade> buscarPeloCodigo(@PathVariable Integer codigo){
		Modalidade modalidade = modalidadeService.obter(codigo);
		return modalidade != null ? ResponseEntity.ok(modalidade) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer codigo){
		modalidadeService.delete(codigo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Modalidade> criar(@RequestBody Modalidade modalidade, HttpServletResponse response) {
		Modalidade modalidadeSalvo = modalidadeService.salvar(modalidade);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, modalidadeSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(modalidadeSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Modalidade> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Modalidade modalidade){
		Modalidade modalidadeSalvo = modalidadeService.atualizar(codigo, modalidade);
		return ResponseEntity.ok(modalidadeSalvo);
	}
}
