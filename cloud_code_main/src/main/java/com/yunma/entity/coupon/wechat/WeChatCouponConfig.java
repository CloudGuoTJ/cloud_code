package com.yunma.entity.coupon.wechat;

public class WeChatCouponConfig {
	
	private Integer id;
	private Integer vendorId;
	private String appId;
	private String secret;
	private String apiKey;
	private String mchId;
	private String productUrl;
	private String credentialsLocation;
	
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
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getCredentialsLocation() {
		return credentialsLocation;
	}
	public void setCredentialsLocation(String credentialsLocation) {
		this.credentialsLocation = credentialsLocation;
	}
	
}
