package com.mrcruz.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrcruz.osworks.domain.exception.NegocioException;
import com.mrcruz.osworks.domain.model.Cliente;
import com.mrcruz.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = repository.findByEmail(cliente.getEmail());
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe un cliente cadastrado com este email");
		}
		
		return repository.save(cliente);
		
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}

}
