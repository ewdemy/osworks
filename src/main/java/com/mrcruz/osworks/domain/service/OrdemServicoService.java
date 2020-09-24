package com.mrcruz.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrcruz.osworks.domain.exception.NegocioException;
import com.mrcruz.osworks.domain.model.Cliente;
import com.mrcruz.osworks.domain.model.OrdemServico;
import com.mrcruz.osworks.domain.model.StatusOrdemServico;
import com.mrcruz.osworks.domain.repository.ClienteRepository;
import com.mrcruz.osworks.domain.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente ciente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()-> new NegocioException("Cliente não encontrado!"));
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return repository.save(ordemServico);
	}
	
	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
