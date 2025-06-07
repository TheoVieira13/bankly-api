package br.com.theo.bankly.dto;

import br.com.theo.bankly.model.Cliente;

public record ClienteDTO (String nome,String email, String cpf, String telefone, String senha) {
	public Cliente toCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpf(cpf);
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setTelefone(telefone);
		cliente.setSenha(senha);
		return cliente;
	}
}
