package br.com.theo.bankly.dto;

import br.com.theo.bankly.model.Cliente;
import br.com.theo.bankly.model.Conta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDTO(@NotNull Integer numeroBanco, 
					   @NotNull Integer numeroAgencia, 
					   @NotNull @PositiveOrZero Double saldo,
					   @NotNull @PositiveOrZero Double limite, 
					   @NotNull Integer idCliente) {

	public Conta toConta() {
		Conta conta = new Conta();
		conta.setNumeroBanco(numeroBanco);
		conta.setNumeroAgencia(numeroAgencia);
		conta.setSaldo(saldo);
		conta.setLimite(limite);
		conta.setAtiva(1);
		Cliente cliente = new Cliente();
		cliente.setIdCliente(idCliente);
		conta.setCliente(cliente);
		return conta;
		
	}
	
}
