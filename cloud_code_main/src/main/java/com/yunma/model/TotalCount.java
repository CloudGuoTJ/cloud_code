package com.yunma.model;

import java.io.Serializable;

public class TotalCount implements Serializable{
	Integer vendorId;
	
	Integer totalCount;
	
	String vendorName;
	
	Integer antiCount;
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getAntiCount() {
		return antiCount;
	}
	public void setAntiCount(Integer antiCount) {
		this.antiCount = antiCount;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	

}
