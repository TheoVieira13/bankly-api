package br.com.theo.bankly.service.transacao;

import java.util.List;

import br.com.theo.bankly.dto.ExtratoDTO;
import br.com.theo.bankly.dto.PagamentoDTO;
import br.com.theo.bankly.dto.TransferenciaDTO;
import br.com.theo.bankly.model.Transacao;

public interface ITransacaoService {

	public boolean efetuarPagamento(PagamentoDTO pagamento);
	public boolean efetuarTransferencia(TransferenciaDTO transferencia);
	public List<Transacao> getExtrato(ExtratoDTO extrato);
}
