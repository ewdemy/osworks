package com.mrcruz.osworks.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mrcruz.osworks.domain.model.Cliente;
import com.mrcruz.osworks.domain.repository.ClienteRepository;
import com.mrcruz.osworks.domain.service.CadastroClienteService;

@RestController
public class ClienteController {
	
	@Autowired
	CadastroClienteService cadastroCliente;
	
	@Autowired
	ClienteRepository repository;
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable(value="id") Long idCliente) {
		Optional<Cliente> cliente = repository.findById(idCliente);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable(value="id") Long idCliente, @RequestBody Cliente cliente) {
		if(!repository.existsById(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(idCliente);
		cliente = repository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity remover(@PathVariable(value="id") Long idCliente){
		if(!repository.existsById(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(idCliente);
		
		return ResponseEntity.noContent().build();
	}

}
