package br.com.theo.bankly.dto;

import java.time.LocalDateTime;

public record TransferenciaDTO(Integer contaOrigem, Integer contaDestino, LocalDateTime dataHora, Double valor, String descricao) {

}
