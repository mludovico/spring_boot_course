package com.hexploretech.library_api.exceptions;

public class OperationNotPermittedException extends RuntimeException {
	public OperationNotPermittedException(String message) {
		super(message);
	}
}
