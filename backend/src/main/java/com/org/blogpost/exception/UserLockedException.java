package com.org.blogpost.exception;

public class UserLockedException extends RuntimeException {

	public UserLockedException(String message) {
		super(message);
	}

}
