package br.com.theo.bankly.service.cliente;

import br.com.theo.bankly.dto.ClienteDTO;

public interface IClienteService {
	
	public Integer cadastrarCliente(ClienteDTO novo);
	public Integer alterarDados(ClienteDTO cliente);

}
