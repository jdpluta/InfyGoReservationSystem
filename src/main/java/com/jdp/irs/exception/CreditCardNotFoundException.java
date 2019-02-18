package com.jdp.irs.exception;

@SuppressWarnings("serial")
public class CreditCardNotFoundException extends Exception {
	public CreditCardNotFoundException(String message) {
		super(message);
	}
}