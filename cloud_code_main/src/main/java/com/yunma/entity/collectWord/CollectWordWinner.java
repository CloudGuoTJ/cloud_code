package com.yunma.entity.collectWord;


public class CollectWordWinner {
	
	private Integer id;
	
	private Integer itemId;//奖项id
	
	private Integer orderId;//订单id
	
	private Integer productId;//产品id
	
	private String openId;
	
	private String winner;//收货人
	
	private String winnerTel;//收货人电话
	
	private String area;//省市
	
	private String street;//街道
	
	private String detailAddress;//详细地址
	
	private String address;//收货完整地址

	private int isDefault;//是否为默认地址
	
	private String createTime;//创建时间
	
	private int isDeliver;//是否发货
	
	private Integer vendorId;//厂商id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getWinnerTel() {
		return winnerTel;
	}

	public void setWinnerTel(String winnerTel) {
		this.winnerTel = winnerTel;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIsDeliver() {
		return isDeliver;
	}

	public void setIsDeliver(int isDeliver) {
		this.isDeliver = isDeliver;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
