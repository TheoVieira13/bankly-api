package br.com.theo.bankly.exceptions;

public class InvalidDateIntervalException extends RuntimeException {
	public InvalidDateIntervalException(String message) {
		super(message);
	}
}
