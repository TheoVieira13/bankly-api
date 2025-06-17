package br.com.theo.bankly.exceptions;

public class InvalidAccountException extends RuntimeException{
	public InvalidAccountException(String message) {
		super(message);
	}
	
}
