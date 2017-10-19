package com.yunma.entity.coupon.wd;

import java.io.Serializable;

public class WdOrderRecord implements Serializable{

	private Integer id;
	private Integer vendorId;
	private String orderId;
	private String discountType;
	private String discountInfo;
	private String discountPrice;
	private Double price;
	private Double total;
	private Double expressFee;
	private String statusOri;
	private String isRefund;
	private String itemId;
	private String itemName;
	private String itemPrice;
	private String itemQuantity;
	private String itemTotalPrice;
	private String payTime;
	
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountInfo() {
		return discountInfo;
	}
	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(Double expressFee) {
		this.expressFee = expressFee;
	}
	public String getStatusOri() {
		return statusOri;
	}
	public void setStatusOri(String statusOri) {
		this.statusOri = statusOri;
	}
	public String getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(String itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
