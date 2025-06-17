package br.com.theo.bankly.service.transacao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.theo.bankly.dto.ExtratoDTO;
import br.com.theo.bankly.dto.PagamentoDTO;
import br.com.theo.bankly.dto.TransferenciaDTO;
import br.com.theo.bankly.exceptions.InvalidAccountException;
import br.com.theo.bankly.exceptions.InvalidDateIntervalException;
import br.com.theo.bankly.exceptions.NotEnoughBalanceException;
import br.com.theo.bankly.model.Conta;
import br.com.theo.bankly.model.Transacao;
import br.com.theo.bankly.repository.ContaRepository;
import br.com.theo.bankly.repository.TransacaoRepository;

@Service
public class TransacaoServiceImpl implements ITransacaoService{
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	
	@Override
	public boolean efetuarPagamento(PagamentoDTO pagamento) {
		Conta conta = contaRepository.findById(pagamento.numeroConta()).orElse(null);
		if (conta == null) {
			throw new InvalidAccountException("Account "+pagamento.numeroConta()+" does not exist");
		}
		if (conta.getSaldo()+ conta.getLimite() < pagamento.valor()) {
			throw new NotEnoughBalanceException("Not enough balance available for Account #"+pagamento.numeroConta());
		}
		Transacao transacao = new Transacao();
		Double saldoIni, saldoFim;
		saldoIni = conta.getSaldo();
		saldoFim = conta.getSaldo() - pagamento.valor();
		transacao.setConta(conta);
		transacao.setValor(pagamento.valor());
		transacao.setSaldoInicial(saldoIni);
		transacao.setDataHora(pagamento.dataHora());
		transacao.setDescricao(pagamento.descricao());
		transacao.setNumeroDocumento(pagamento.numDoc());
		transacao.setTipo(-1); // sempre debito (por enquanto)
		transacao.setSaldoFinal(saldoFim);
		transacaoRepository.save(transacao);
		
		conta.setSaldo(saldoFim);
		contaRepository.save(conta);
		return true;
	}

	@Override
	public boolean efetuarTransferencia(TransferenciaDTO transferencia) {
		// TODO Auto-generated method stub
		Conta contaOrigem;
		Conta contaDestino;
		
		contaOrigem = contaRepository.findById(transferencia.contaOrigem()).orElse(null);
		contaDestino = contaRepository.findById(transferencia.contaDestino()).orElse(null);
		
		System.out.println("Conta Origem: " + contaOrigem);
		System.out.println("Conta Destino: " + contaDestino);
		
		if (contaOrigem == null || contaDestino == null) {
			throw new InvalidAccountException("Invalid Source or Destination Account");
		}
		if (contaOrigem.getSaldo() + contaOrigem.getLimite() < transferencia.valor()) {
			throw new NotEnoughBalanceException("Not Enough Balance for Account #"+contaOrigem.getNumeroConta());
		}
		
		Transacao trDebito, trCredito;
		Double saldoIniOrig, saldoIniDest, saldoFimOrig, saldoFimDest;
		saldoIniOrig = contaOrigem.getSaldo();
		saldoFimOrig = contaOrigem.getSaldo() - transferencia.valor();
		
		saldoIniDest = contaDestino.getSaldo();
		saldoFimDest = contaDestino.getSaldo() + transferencia.valor();
		
		trDebito = new Transacao();
		trDebito.setConta(contaOrigem);
		trDebito.setDataHora(transferencia.dataHora());
		trDebito.setValor(transferencia.valor());
		trDebito.setSaldoInicial(saldoIniOrig);
		trDebito.setSaldoFinal(saldoFimOrig);
		trDebito.setTipo(-1);
		trDebito.setDescricao(transferencia.descricao());
		trDebito.setNumeroDocumento(UUID.randomUUID().toString());
		
		trCredito = new Transacao();
		trCredito.setConta(contaDestino);
		trCredito.setDataHora(transferencia.dataHora());
		trCredito.setValor(transferencia.valor());
		trCredito.setSaldoInicial(saldoIniDest);
		trCredito.setSaldoFinal(saldoFimDest);
		trCredito.setTipo(1);
		trCredito.setDescricao(transferencia.descricao());
		trCredito.setNumeroDocumento(UUID.randomUUID().toString());
		
		transacaoRepository.save(trDebito);
		transacaoRepository.save(trCredito);
		
		contaOrigem.setSaldo(saldoFimOrig);
		contaDestino.setSaldo(saldoFimDest);
		
		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);
		return true;
	}

	@Override
	public List<Transacao> getExtrato(ExtratoDTO extrato) {
		Conta conta = contaRepository.findById(extrato.numeroConta()).orElse(null);
		if (conta == null) {
			throw new InvalidAccountException("Invalid Account number #"+extrato.numeroConta());
		}
		if (extrato.inicio().isAfter(extrato.fim())) {
			throw new InvalidDateIntervalException("Invalid Date Interval");
		}
		return transacaoRepository.findByContaAndDataHoraBetween(conta, extrato.inicio(), extrato.fim());
	}
	
}
