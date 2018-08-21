package com.libedi.new_java_feature.java7;

public abstract class Resource {
	
	private String oldAndNew;
	
	public Resource(String oldAndNew) {
		this.oldAndNew = oldAndNew;
	}
	
	public void use() {
		System.out.println("Use resouce: " + this.oldAndNew);
	}
	
}
