package br.com.theo.bankly.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.theo.bankly.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	
	public Cliente findByEmailOrCpfOrTelefone(String email, String cpf, String telefone);

}
