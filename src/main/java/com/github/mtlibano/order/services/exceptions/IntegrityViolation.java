package com.github.mtlibano.order.services.exceptions;

public class IntegrityViolation extends RuntimeException {
	
	public IntegrityViolation(String message) {
		super(message);
	}

}