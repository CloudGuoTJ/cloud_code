package com.yunma.entity.jdcoupon;

public class JDCouponOrder {
	
	private Integer id;
	
	private Integer vendorId;
	
	private String orderId;//京东的订单Id
	
	private String orderTotalPrice;//订单总费用(含运费)
	
	private String orderSellerPrice;//订单价格(不含运费)
	
	private String orderSource;//下单平台(微信、京东移动端或web端)
	
	private String couponPrice;//优惠金额
	
	private String couponType;//优惠券类型
	
	private String orderStartTime;//订单开始时间
	
	private String skuName;//商品名称(多个商品以,分割)
	
	private String jdPrice;//商品价格

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

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getOrderSellerPrice() {
		return orderSellerPrice;
	}

	public void setOrderSellerPrice(String orderSellerPrice) {
		this.orderSellerPrice = orderSellerPrice;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(String jdPrice) {
		this.jdPrice = jdPrice;
	}
	

}
