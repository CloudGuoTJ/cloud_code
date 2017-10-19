package com.yunma.model;

import java.io.Serializable;

public class VendorHtmlActivBackInfo implements Serializable{
	private Integer getRedEnv;// 是否显示领取红包
	private String productInfo;// 是否显示产品信息
	private String vendorHttp;// 是否显示商家主页
	private String weShop;// 是否显示微商城
	private Integer spree;// 是否显示大礼包
	private Integer securityAndTraceability;// 是否显示防伪溯源
	public Integer getGetRedEnv() {
		return getRedEnv;
	}
	public void setGetRedEnv(Integer getRedEnv) {
		this.getRedEnv = getRedEnv;
	}
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	public String getVendorHttp() {
		return vendorHttp;
	}
	public void setVendorHttp(String vendorHttp) {
		this.vendorHttp = vendorHttp;
	}
	public String getWeShop() {
		return weShop;
	}
	public void setWeShop(String weShop) {
		this.weShop = weShop;
	}
	public Integer getSpree() {
		return spree;
	}
	public void setSpree(Integer spree) {
		this.spree = spree;
	}
	public Integer getSecurityAndTraceability() {
		return securityAndTraceability;
	}
	public void setSecurityAndTraceability(Integer securityAndTraceability) {
		this.securityAndTraceability = securityAndTraceability;
	}

}
