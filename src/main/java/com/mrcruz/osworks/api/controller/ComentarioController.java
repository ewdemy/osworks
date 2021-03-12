package com.mrcruz.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mrcruz.osworks.api.model.Comentario;
import com.mrcruz.osworks.api.model.ComentarioDTO;
import com.mrcruz.osworks.api.model.ComentarioInput;
import com.mrcruz.osworks.api.model.OrdemServicoDTO;
import com.mrcruz.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.mrcruz.osworks.domain.model.OrdemServico;
import com.mrcruz.osworks.domain.repository.OrdemServicoRepository;
import com.mrcruz.osworks.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordem-servicos/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ComentarioDTO> listar(@PathVariable Long ordemServicoId){
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada."));
	
		System.out.println(ordemServico.getDescricao());
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDTO adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = ordemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao()); 
		return toModel(comentario);
	}
	
	
	
	private ComentarioDTO toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDTO.class );
	}
	
	private List<ComentarioDTO> toCollectionModel(List<Comentario> comentarios){
		
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}
}
