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
import com.pisoms.olimpico.api.model.Local;
import com.pisoms.olimpico.api.service.LocalService;

@RestController
@RequestMapping("/locais")
public class LocalResource {
	
	@Autowired
	private LocalService localService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Local> listar(){
		return localService.listarTodos(); 
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Local> obter(@PathVariable Integer codigo){
		Local local = localService.obter(codigo); 
		return local != null ? ResponseEntity.ok(local) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer codigo){
		localService.delete(codigo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Local> criar(@Valid @RequestBody Local local, HttpServletResponse response) {
		Local localSalvo = localService.salvar(local);
	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, localSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(localSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Local> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Local local){
		Local localSalvo = localService.atualizar(codigo, local);
		return ResponseEntity.ok(localSalvo);
	}
}
