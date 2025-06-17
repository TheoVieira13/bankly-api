package br.com.theo.bankly.exceptions;

public class NotEnoughBalanceException extends RuntimeException{
	public NotEnoughBalanceException(String message) {
		super(message);
	}
	
}
