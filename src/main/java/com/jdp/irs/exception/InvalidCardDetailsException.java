package com.jdp.irs.exception;

@SuppressWarnings("serial")
public class InvalidCardDetailsException extends Exception {
	public InvalidCardDetailsException(String message) {
		super(message);
	}
}
