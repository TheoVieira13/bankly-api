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

import br.com.theo.bankly.dto.ContaDTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ContaAPITests {

	@Autowired
	private MockMvc mvc;
	
	private ContaDTO contaValida;
	private ContaDTO contaInvalida;
	
	@BeforeEach
	public void setup() {
		
		contaValida   = new ContaDTO(1, 1, 100.0, 0.0, 10);
		contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 150);
		
	}
	
	@Test
	public void shouldCallAPIToCreateConta() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(contaValida);
		mvc.perform(MockMvcRequestBuilders.post("/contas")
				.contentType("application/json")
				.content(str)).andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	public void shouldNotCallAPIToCreateConta() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(contaInvalida);
		mvc.perform(MockMvcRequestBuilders.post("/contas")
				.contentType("application/json")
				.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
}
