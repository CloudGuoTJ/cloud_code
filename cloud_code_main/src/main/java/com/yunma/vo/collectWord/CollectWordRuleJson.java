package com.yunma.vo.collectWord;

public class CollectWordRuleJson {
	
	private String name; //规则名称
	
	private Integer vendorId; //厂商id
	
	private int status; //规则状态
	
	private int number; //字数

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
