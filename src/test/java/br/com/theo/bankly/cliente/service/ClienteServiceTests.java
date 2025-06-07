package br.com.theo.bankly.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.theo.bankly.dto.ClienteDTO;
import br.com.theo.bankly.service.cliente.IClienteService;

@SpringBootTest
public class ClienteServiceTests {
	
	private ClienteDTO reqValida;
	private ClienteDTO reqInvalida;
	private Integer idValido;
	
	@Mock
	private IClienteService service;
	
	@BeforeEach
	public void setup() {
		reqValida = new ClienteDTO("Cliente Valido", "email@email.com", "12345678900", "11987654321", "abc12345");
		reqInvalida = new ClienteDTO("Cliente Invalido", null, null, null, null);
		idValido = 1;
		
		Mockito.when(service.cadastrarCliente(reqValida)).thenReturn(idValido);
		Mockito.when(service.cadastrarCliente(reqInvalida)).thenReturn(null);
	}
	
	@Test
	public void deveCadastrarClienteValido() {
		assertEquals(service.cadastrarCliente(reqValida), idValido);
	}
	@Test
	public void deveRejeitarClienteInvalido() {
		assertEquals(service.cadastrarCliente(reqInvalida), null);
	}
	
}
