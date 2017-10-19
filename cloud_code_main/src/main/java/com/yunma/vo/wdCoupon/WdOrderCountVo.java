package com.yunma.vo.wdCoupon;

public class WdOrderCountVo {
	
	private Integer id;
	private Integer vendorId;
	private String vendorName;
	private String payCount;
	private String paySum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getPayCount() {
		return payCount;
	}
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}
	public String getPaySum() {
		return paySum;
	}
	public void setPaySum(String paySum) {
		this.paySum = paySum;
	}
	
}
