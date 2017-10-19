package com.yunma.vo.collectWord;

import java.util.ArrayList;
import java.util.List;

public class CollectWordForJson {
	
	private Integer id;//规则id
	
	private String name; //规则名称
	
	private Integer vendorId; //厂商id
	
	private int status; //规则状态
	
	private int number; //字数
	
	private List<CollectWordItemJson> items=new ArrayList<CollectWordItemJson>();
	
	private List<CollectWordRateJson> rates=new ArrayList<CollectWordRateJson>();

	
	
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

	public List<CollectWordItemJson> getItems() {
		return items;
	}

	public void setItems(List<CollectWordItemJson> items) {
		this.items = items;
	}

	public List<CollectWordRateJson> getRates() {
		return rates;
	}

	public void setRates(List<CollectWordRateJson> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "CollectWordForJson [id=" + id + ", name=" + name
				+ ", vendorId=" + vendorId + ", status=" + status + ", number="
				+ number + ", items=" + items + ", rates=" + rates + "]";
	}
	
	
}
