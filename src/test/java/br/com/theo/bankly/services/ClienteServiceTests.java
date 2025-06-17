package br.com.theo.bankly.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.theo.bankly.dto.ClienteDTO;
import br.com.theo.bankly.service.cliente.IClienteService;

@SpringBootTest
public class ClienteServiceTests {
	
	private ClienteDTO reqValida;
	private ClienteDTO reqInvalida;
	private ClienteDTO reqEmailDupl;
	private ClienteDTO reqCpfDupl;
	private ClienteDTO reqTelDupl;
	private Integer idValido;
	
	//@Mock
	@Autowired
	private IClienteService service;
	
	@BeforeEach
	public void setup() {
		reqValida = new ClienteDTO("Cliente Valido", "cliente@email.com", "01234567890", "11852741963", "abc12345");
		reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@email.com", "12345678009", "11123456789", "abc12345");
		reqCpfDupl = new ClienteDTO("Cliente cpf duplicado", "email@outroemail.com", "12345678900", "987584126", "abc12345");
		reqTelDupl = new ClienteDTO("Cliente tel duplicado", "email@novoemail.com", "12378945600", "11987654321", "abc12345");
		
		reqInvalida = new ClienteDTO("Cliente Invalido", null, null, null, null);
		idValido = 1;
		
		/*
		Mockito.when(service.cadastrarCliente(reqValida)).thenReturn(idValido);
		Mockito.when(service.cadastrarCliente(reqInvalida)).thenThrow(new ConstraintViolationException(null));
		Mockito.when(service.cadastrarCliente(reqEmailDupl)).thenReturn(null);
		Mockito.when(service.cadastrarCliente(reqCpfDupl)).thenReturn(null);
		Mockito.when(service.alterarDados(reqTelDupl)).thenReturn(null);
		*/
	}
	
	@Test
	public void deveCadastrarClienteValido() {
		assertNotNull(service.cadastrarCliente(reqValida));
	}
	
	@Test
	public void deveRejeitarClienteInvalido() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			service.cadastrarCliente(reqInvalida);
		});
	}
	
	@Test
	public void deveRejeitarClienteEmailDuplicado() {
		assertEquals(service.cadastrarCliente(reqEmailDupl), null);
	}
	
	@Test
	public void deveRejeitarClienteCpfDuplicado() {
		assertEquals(service.cadastrarCliente(reqCpfDupl), null);
	}
	
	@Test
	public void deveRejeitarClienteTelefoneDuplicado() {
		assertEquals(service.cadastrarCliente(reqTelDupl), null);
	}
	
}
