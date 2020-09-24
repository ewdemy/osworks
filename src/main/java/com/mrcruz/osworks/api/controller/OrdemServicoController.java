package com.mrcruz.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mrcruz.osworks.domain.model.OrdemServico;
import com.mrcruz.osworks.domain.repository.OrdemServicoRepository;
import com.mrcruz.osworks.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordem-servicos")
public class OrdemServicoController {
	
	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	OrdemServicoService ordemServicoService;
	
	@GetMapping
	public List<OrdemServico> listarOrdens(){
		return ordemServicoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServico> buscarOrdem(@PathVariable("id") Long id){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
		if(ordemServico.isPresent()) {
			return ResponseEntity.ok(ordemServico.get());
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServico adicionar(@Valid @RequestBody OrdemServico ordemServico) {
		return ordemServicoService.criar(ordemServico);
	}
	

}
