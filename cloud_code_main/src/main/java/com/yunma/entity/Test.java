package com.yunma.entity;

import java.io.Serializable;

public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + "]";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
