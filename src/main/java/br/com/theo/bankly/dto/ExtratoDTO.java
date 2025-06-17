package br.com.theo.bankly.dto;

import java.time.LocalDateTime;

public record ExtratoDTO(Integer numeroConta, LocalDateTime inicio, LocalDateTime fim) {

}
