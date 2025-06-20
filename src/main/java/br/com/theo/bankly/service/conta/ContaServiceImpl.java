package br.com.theo.bankly.service.conta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.theo.bankly.dto.ContaDTO;
import br.com.theo.bankly.model.Cliente;
import br.com.theo.bankly.model.Conta;
import br.com.theo.bankly.repository.ClienteRepository;
import br.com.theo.bankly.repository.ContaRepository;

@Service
public class ContaServiceImpl implements IContaService{

	@Value("${bankly.banknumber}")
	private Integer numeroBanco = 12345;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Integer cadastrarNovaConta(ContaDTO nova) {
		// TODO Auto-generated method stub

		Cliente cliente = clienteRepository.findById(nova.idCliente()).orElse(null);
		if (cliente == null) {
			return null;
		}
		Conta conta = nova.toConta();
		conta.setNumeroBanco(numeroBanco);
		//System.out.println(conta);
		
		return contaRepository.save(nova.toConta()).getNumeroConta();
	}

	@Override
	public List<Conta> recuperarPeloCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return contaRepository.findByCliente(cliente);
	}

	@Override
	public Conta recuperarPeloNumero(Integer numeroConta) {
		// TODO Auto-generated method stub
		return contaRepository.findById(numeroConta).orElse(null);
	}
	
	
	
	
}
