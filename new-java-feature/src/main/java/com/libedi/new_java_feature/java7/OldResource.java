package com.libedi.new_java_feature.java7;

import java.io.Closeable;
import java.io.IOException;

/**
 * Something Resource for Test
 * @author Sangjun, Park
 *
 */
public class OldResource extends Resource implements Closeable {

	public OldResource(String oldAndNew) {
		super(oldAndNew);
	}

	@Override
	public void close() throws IOException {
		System.out.println("Old Resource Close!");
	}
	
	public static OldResource getResource(boolean isException) throws IOException {
		if(isException) {
			throw new IOException();
		}
		return new OldResource("Old");
	}
	
}
