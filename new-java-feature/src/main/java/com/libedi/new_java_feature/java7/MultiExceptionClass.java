package com.libedi.new_java_feature.java7;

import java.io.IOException;

public class MultiExceptionClass {

	public void throwIOException() throws IOException {
		throw new IOException();
	}
	
	public void throwTestException() throws TestException {
		throw new TestException();
	}
}
