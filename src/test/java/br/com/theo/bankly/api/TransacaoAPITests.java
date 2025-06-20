package br.com.theo.bankly.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.theo.bankly.dto.ExtratoDTO;
import br.com.theo.bankly.dto.PagamentoDTO;
import br.com.theo.bankly.dto.TransferenciaDTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TransacaoAPITests {
	
	@Autowired
	private MockMvc mvc;

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
		pagamentoValido       = new PagamentoDTO(10, LocalDateTime.now(), "123456", "Boleto", 100.00);
		pgtoSaldoInsuficiente = new PagamentoDTO(20, LocalDateTime.parse("2023-09-11T21:00:00"), "123456", "Boleto", 100000.00);
		pgtoContaInvalida     = new PagamentoDTO(100, LocalDateTime.parse("2023-09-11T21:00:00"), "123456", "Boleto", 100.00);

		transferenciaValida     = new TransferenciaDTO(10, 20, LocalDateTime.parse("2023-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfOrigemInvalida    = new TransferenciaDTO(100, 20, LocalDateTime.parse("2023-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfDestinoInvalida   = new TransferenciaDTO(10, 100, LocalDateTime.parse("2023-09-11T21:00:00"), 100.00, "Manda o PIX");
		transfSaldoInsuficiente = new TransferenciaDTO(10, 20, LocalDateTime.parse("2023-09-11T21:00:00"), 1000000.00, "Manda o PIX");

		extratoValido         = new ExtratoDTO(10, LocalDateTime.parse("2023-01-01T00:00:00"), LocalDateTime.parse("2023-09-11T23:59:59"));
		extratoContaInvalida  = new ExtratoDTO(100, LocalDateTime.parse("2023-01-01T00:00:00"), LocalDateTime.parse("2023-09-11T23:59:59"));
		extratoDatasInvalidas = new ExtratoDTO(10, LocalDateTime.parse("2023-09-11T23:59:59"), LocalDateTime.parse("2023-01-01T00:00:00"));
	}

	
	@Test
	public void shouldPerformPayment() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pagamentoValido);

		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
				.contentType("application/json")
				.content(str)).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void shouldInvalidPaymentDueToBalance() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pgtoSaldoInsuficiente);

		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
				.contentType("application/json")
				.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldInvalidPaymentDueToDestinationAccount() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pgtoContaInvalida);

		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
				.contentType("application/json")
				.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
}
