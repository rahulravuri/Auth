package com.example.BookMyShow.Auth.Exceptions;

public class UserExceptions extends Exception {

	/**
	 * 
	 */
	public UserExceptions() {
		super();
	}
	public UserExceptions(String message) {
		super(message);
		
	}
	public UserExceptions(String message, Throwable cause) {
		super(message,cause);
	}

}
