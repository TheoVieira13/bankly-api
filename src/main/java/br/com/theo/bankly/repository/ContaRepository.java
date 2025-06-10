package br.com.theo.bankly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.theo.bankly.model.Cliente;
import br.com.theo.bankly.model.Conta;

public interface ContaRepository extends CrudRepository<Conta, Integer>{

	public List<Conta> findByCliente(Cliente cliente); 
	
}
