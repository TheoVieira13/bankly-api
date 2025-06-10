package br.com.theo.bankly.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.theo.bankly.dto.ContaDTO;
import br.com.theo.bankly.service.conta.IContaService;

@SpringBootTest
public class ContaServiceTests {
	
	//@Mock
	@Autowired
	private IContaService contaService;
	
	private ContaDTO contaValida;
	private ContaDTO contaInvalida;
	
	@BeforeEach
	public void setup() {
		
		contaValida = new ContaDTO(1, 1, 100.0, 0.0, 10);
		contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 2);
		
		/*
		Mockito.when(contaService.cadastrarNovaConta(contaValida)).thenReturn(1);
		Mockito.when(contaService.cadastrarNovaConta(contaInvalida)).thenReturn(null);
		*/
	}
	
	@Test
	public void deveriaAceitaContaComClienteExistente() {
		assertNotNull(contaService.cadastrarNovaConta(contaValida));
	}
	
	@Test
	public void deveriaRejeitarContaComClienteInvalido() {
		assertEquals(contaService.cadastrarNovaConta(contaInvalida), null);
	}

}
