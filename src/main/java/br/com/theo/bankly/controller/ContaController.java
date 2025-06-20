package br.com.theo.bankly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.theo.bankly.dto.ContaDTO;
import br.com.theo.bankly.dto.ResponseDTO;
import br.com.theo.bankly.service.conta.IContaService;

@RestController
@CrossOrigin("*")
public class ContaController {

	@Autowired
	private IContaService service;
	
	@PostMapping("/contas")
	public ResponseEntity<ResponseDTO> cadastrarConta(@RequestBody ContaDTO conta) {
		Integer numConta = service.cadastrarNovaConta(conta);
		if (numConta != null) {
			return ResponseEntity.status(201).body(new ResponseDTO("Conta "+numConta+" criada com secesso"));
		}
		return ResponseEntity.badRequest().body(new ResponseDTO("Dados invalidos para conta"));
	}
}
