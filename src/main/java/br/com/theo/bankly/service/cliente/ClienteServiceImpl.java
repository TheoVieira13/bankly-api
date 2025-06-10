package br.com.theo.bankly.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.theo.bankly.dto.ClienteDTO;
import br.com.theo.bankly.model.Cliente;
import br.com.theo.bankly.repository.ClienteRepository;
import jakarta.validation.Valid;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public Integer cadastrarCliente(@Valid ClienteDTO novo) {
		// TODO Auto-generated method stub
		Cliente teste = repository.findByEmailOrCpfOrTelefone(novo.email(), novo.cpf(), novo.telefone());
		if (teste != null)
			return null;
		return repository.save(novo.toCliente()).getIdCliente();
	}

	@Override
	public Integer alterarDados(ClienteDTO cliente) {
		// TODO Auto-generated method stub
		return repository.save(cliente.toCliente()).getIdCliente();
	}

	
	
}
