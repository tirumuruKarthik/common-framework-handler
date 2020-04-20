package com.fatface.mule.exceptions;

public class InvalidPayloadException extends Exception{

	private static final long serialVersionUID = -8267333729706048889L;
	
	
	public InvalidPayloadException() {
	
	}
	
	public InvalidPayloadException(Throwable cause) {
		super(cause);
	}
	
	public InvalidPayloadException(String newMsg) {
		super(newMsg);
	}

	public InvalidPayloadException(String newMsg, Throwable cause) {
		super(newMsg,cause);
		
	}

}
