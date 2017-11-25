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
import com.pisoms.olimpico.api.model.Competidor;
import com.pisoms.olimpico.api.service.CompetidorService;

@RestController
@RequestMapping("/competidores")
public class CompetidorResource {
	
	@Autowired
	private CompetidorService competidorService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Competidor> listar(){
		return competidorService.listarTodos(); 
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Competidor> obter(@PathVariable Integer codigo){
		Competidor competidor = competidorService.obter(codigo); 
		return competidor != null ? ResponseEntity.ok(competidor) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer codigo){
		competidorService.delete(codigo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Competidor> criar(@Valid @RequestBody Competidor competidor, HttpServletResponse response) {
		Competidor competidorSalvo = competidorService.salvar(competidor);
	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, competidorSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(competidorSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Competidor> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Competidor competidor){
		Competidor competidorSalvo = competidorService.atualizar(codigo, competidor);
		return ResponseEntity.ok(competidorSalvo);
	}
}
