package com.victor.campanha.exception;

public class InaccessibleQueueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InaccessibleQueueException(Throwable cause) {
		super(cause);
	}
}
