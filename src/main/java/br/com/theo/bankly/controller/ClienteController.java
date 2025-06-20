package br.com.theo.bankly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.theo.bankly.dto.ClienteDTO;
import br.com.theo.bankly.dto.ResponseDTO;
import br.com.theo.bankly.service.cliente.IClienteService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class ClienteController {
	
	@Autowired
	private IClienteService service;
	
	@PostMapping("/clientes")
	public ResponseEntity<ResponseDTO> cadastrarNovoCliente(@Valid @RequestBody ClienteDTO cliente) {
		try {
			Integer resultado = service.cadastrarCliente(cliente);
			if (resultado != null) {
				return ResponseEntity.status(201).body(new ResponseDTO("Cliente cadastrado com sucesso "+resultado));
			}
			return ResponseEntity.badRequest().body(new ResponseDTO("Impossivel cadastrar cliente com dados existentes"));
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(new ResponseDTO("Dados do cliente Incompletos"));
		}
	}
}
