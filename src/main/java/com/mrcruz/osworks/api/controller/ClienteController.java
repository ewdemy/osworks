package com.mrcruz.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrcruz.osworks.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		var c1 = new Cliente(1L,"Pedro","pp@mail.com","9999-9999");
		var c2 = new Cliente(2L,"Anna","anna@mail.com","9999-9998");
		return Arrays.asList(c1,c2);
	}

}
