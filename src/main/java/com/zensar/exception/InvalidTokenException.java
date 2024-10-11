package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException{
	String message = "";
	
	public InvalidTokenException() {}
	
	public InvalidTokenException(String message) {
		this.message=message;
		
	}
	public String toString() {
		return "Invalid token "  + message;
	}

}
