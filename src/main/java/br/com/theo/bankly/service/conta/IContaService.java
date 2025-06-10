package br.com.theo.bankly.service.conta;

import java.util.List;

import br.com.theo.bankly.dto.ContaDTO;
import br.com.theo.bankly.model.Cliente;
import br.com.theo.bankly.model.Conta;

public interface IContaService {
	
	public Integer cadastrarNovaConta(ContaDTO nova);
	public List<Conta> recuperarPeloCliente(Cliente cliente);
	public Conta recuperarPeloNumero(Integer numeroConta);
	
}
