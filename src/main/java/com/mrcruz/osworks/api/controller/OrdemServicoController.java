package com.mrcruz.osworks.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.mrcruz.osworks.api.model.OrdemServicoDTO;
import com.mrcruz.osworks.domain.model.OrdemServico;
import com.mrcruz.osworks.domain.repository.OrdemServicoRepository;
import com.mrcruz.osworks.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordem-servicos")
public class OrdemServicoController {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<OrdemServicoDTO> listarOrdens(){
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> buscarOrdem(@PathVariable("id") Long id){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
		if(ordemServico.isPresent()) {
			OrdemServicoDTO model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDTO adicionar(@Valid @RequestBody OrdemServico ordemServico) {
		return toModel(ordemServicoService.criar(ordemServico));
	}
	
	private OrdemServicoDTO toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDTO.class );
	}
	
	private List<OrdemServicoDTO> toCollectionModel(List<OrdemServico> ordensServico){
		
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	 

}
