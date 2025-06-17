package br.com.theo.bankly.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.theo.bankly.dto.ExtratoDTO;
import br.com.theo.bankly.dto.PagamentoDTO;
import br.com.theo.bankly.dto.TransferenciaDTO;
import br.com.theo.bankly.exceptions.InvalidAccountException;
import br.com.theo.bankly.exceptions.InvalidDateIntervalException;
import br.com.theo.bankly.exceptions.NotEnoughBalanceException;
import br.com.theo.bankly.model.Transacao;
import br.com.theo.bankly.service.transacao.ITransacaoService;

@SpringBootTest
public class TransacaoServiceTests {

	//@Mock
	@Autowired
	private ITransacaoService service;
	
	private PagamentoDTO pagamentoValido;
	private PagamentoDTO pgtoSaldoInsuficiente;
	private PagamentoDTO pgtoContaInvalida;
	
	private TransferenciaDTO transferenciaValida;
	private TransferenciaDTO transfOrigemInvalida;
	private TransferenciaDTO transfDestinoInvalida;
	private TransferenciaDTO transfSaldoInsuficiente;
	
	private ExtratoDTO extratoValido;
	private ExtratoDTO extratoContaInvalida;
	private ExtratoDTO extratoDatasInvalidas;
	
	@BeforeEach
	public void setup() {
		pagamentoValido         = new PagamentoDTO(10, LocalDateTime.parse("2025-09-11T21:00:00"), "123456", "Boleto", 100.00);
		pgtoSaldoInsuficiente   = new PagamentoDTO(20, LocalDateTime.parse("2025-09-11T21:00:00"), "123456", "Boleto", 1000000.00);
		pgtoContaInvalida       = new PagamentoDTO(100, LocalDateTime.parse("2025-09-11T21:00:00"), "123456", "Boleto", 100.00);
		
		transferenciaValida     = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfOrigemInvalida    = new TransferenciaDTO(100, 20, LocalDateTime.parse("2025-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfDestinoInvalida   = new TransferenciaDTO(10, 100, LocalDateTime.parse("2025-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfSaldoInsuficiente = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-09-11T21:00:00"), 1000000.00, "Manda o PIX");
		
		extratoValido = new ExtratoDTO(10, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-09-11T23:59:59"));
		extratoContaInvalida = new ExtratoDTO(100, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-09-11T23:59:59"));
		extratoDatasInvalidas = new ExtratoDTO(10, LocalDateTime.parse("2025-09-11T23:59:59"), LocalDateTime.parse("2025-01-01T00:00:00"));
		
		/*
		Mockito.when(service.efetuarPagamento(pagamentoValido)).thenReturn(true);
		Mockito.when(service.efetuarPagamento(pgtoSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
		Mockito.when(service.efetuarPagamento(pgtoContaInvalida)).thenThrow(InvalidAccountException.class);
		
		Mockito.when(service.efetuarTransferencia(transferenciaValida)).thenReturn(true);
		Mockito.when(service.efetuarTransferencia(transfOrigemInvalida)).thenThrow(InvalidAccountException.class);
		Mockito.when(service.efetuarTransferencia(transfDestinoInvalida)).thenThrow(InvalidAccountException.class);
		Mockito.when(service.efetuarTransferencia(transfSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
		
		Mockito.when(service.getExtrato(extratoValido)).thenReturn(new ArrayList<Transacao>());
		Mockito.when(service.getExtrato(extratoContaInvalida)).thenThrow(InvalidAccountException.class);
		Mockito.when(service.getExtrato(extratoDatasInvalidas)).thenThrow(InvalidDateIntervalException.class);
		*/
		
	}
	
	@Test
	public void shouldEffectiveDoPayment() {
		assertTrue(service.efetuarPagamento(pagamentoValido));
	}
	
	@Test
	public void shouldCheckInsufficientBallance() {
		assertThrows(NotEnoughBalanceException.class, ()->{
			service.efetuarPagamento(pgtoSaldoInsuficiente);
		});
	}
	
	@Test
	public void shouldCheckInvalidAccount() {
		assertThrows(InvalidAccountException.class, ()->{
			service.efetuarPagamento(pgtoContaInvalida);
		});
	}
	
	@Test
	public void shouldEffectiveTransfer() {
		assertTrue(service.efetuarTransferencia(transferenciaValida));
	}
	
	@Test
	public void shouldCheckInvalidSourceAccount() {
		assertThrows(InvalidAccountException.class, ()-> {
			service.efetuarTransferencia(transfOrigemInvalida);
		});
	}
	
	@Test
	public void shouldCheckInvalidDestinationAccount() {
		assertThrows(InvalidAccountException.class, ()-> {
			service.efetuarTransferencia(transfDestinoInvalida);
		});
	}
	
	@Test
	public void shouldCheckAccountBallanceInsuficient() {
		assertThrows(NotEnoughBalanceException.class, () ->{
			service.efetuarTransferencia(transfSaldoInsuficiente);
		});
	}
	
	@Test
	public void shouldRetriveExtrato() {
		assertNotNull(service.getExtrato(extratoValido));
	}
	
	@Test
	public void shouldInvalidAccountNoExtrato() {
		assertThrows(InvalidAccountException.class, () ->{
			service.getExtrato(extratoContaInvalida);
		});
	}
	
	@Test public void shouldCheckDateInterval() {
		assertThrows(InvalidDateIntervalException.class, () ->{
			service.getExtrato(extratoDatasInvalidas);
		});
	}
}
