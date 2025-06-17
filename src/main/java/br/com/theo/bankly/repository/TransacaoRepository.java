package br.com.theo.bankly.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.theo.bankly.model.Conta;
import br.com.theo.bankly.model.Transacao;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
	public List<Transacao> findByContaAndDataHoraBetween(Conta conta, LocalDateTime inicio, LocalDateTime fim);
}
