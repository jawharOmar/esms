package com.joh.esms.exception;

@SuppressWarnings("serial")
public class InputErrorException extends RuntimeException {
	public InputErrorException(String message) {
		super(message);
	}

	public InputErrorException() {
		super();
	}

}
