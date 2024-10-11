package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidStockException extends RuntimeException{
	String message = "";
	
	public InvalidStockException() {}
	
	public InvalidStockException(String message) {
		this.message=message;
		
	}
	public String toString() {
		return "Incorrect stock Id "  + message;
	}

}
