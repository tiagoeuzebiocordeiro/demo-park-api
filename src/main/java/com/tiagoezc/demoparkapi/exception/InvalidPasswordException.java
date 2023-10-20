package com.tiagoezc.demoparkapi.exception;

public class InvalidPasswordException extends RuntimeException {

	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
