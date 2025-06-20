package br.com.theo.bankly.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.theo.bankly.dto.ClienteDTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ClienteAPITests {

	@Autowired
	private MockMvc mvc;

	private ClienteDTO reqValida;
	private ClienteDTO reqInvalida;
	private ClienteDTO reqEmailDupl;
	private ClienteDTO reqCpfDupl;
	private ClienteDTO reqTelDupl;

	@BeforeEach
	public void setup() {
		reqValida    = new ClienteDTO("Cliente Valido API", "clienteapi@email.com", "11122255588", "11199988877", "abc123456");
		reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@email.com", "12345678009", "11123456789", "abc123456");
		reqCpfDupl   = new ClienteDTO("Cliente cpf duplicado", "email@outroemail.com", "11122255588", "987584126", "abc123456");
		reqTelDupl   = new ClienteDTO("Cliente tel duplicado", "email@novoemail.com", "12378945600", "11987654321", "abc123456");

		reqInvalida  = new ClienteDTO("Cliente Invalido", null, null, null, null);
	}

	@Test
	public void shouldCallAPIForValidCliente() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqValida);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				          .contentType("application/json")
				          .content(str)).andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	public void sholdNotCreateClienteWithDuplicateEmail() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqEmailDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				          .contentType("application/json")
				          .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void sholdNotCreateClienteWithDuplicateCPF() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqCpfDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				          .contentType("application/json")
				          .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void sholdNotCreateClienteWithDuplicatePhone() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqTelDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				          .contentType("application/json")
				          .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
}
