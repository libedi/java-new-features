package com.libedi.new_java_feature.java7;

import java.io.Closeable;
import java.io.IOException;

public class NewResource extends Resource implements AutoCloseable, Closeable {

	public NewResource(String oldAndNew) {
		super(oldAndNew);
	}

	@Override
	public void close() throws IOException {
		System.out.println("New Resource Close!");
	}
	
	public static NewResource getResource(boolean isException) throws IOException {
		if(isException) {
			throw new IOException();
		}
		return new NewResource("New");
	}

}
