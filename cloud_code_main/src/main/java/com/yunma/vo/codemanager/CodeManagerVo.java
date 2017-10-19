package com.yunma.vo.codemanager;

import java.io.Serializable;

public class CodeManagerVo implements Serializable{
	
	private Integer id;//id
	
	private Integer vendor_id;
	
	private String anti_fake_name;//活动名称
	
	private String start_time;//开始时间
	
	private String end_time;//结束时间
	
	private Integer orderId;//订单id
	
	private String mark;//备注
	
	private Integer activity_status;//活动状态
	
	private Integer mode_id;//模板id
	
	private String httpName;//模板名
	
	private String urlName;
	
	private Integer deleteFlag;
	
	private Integer getRedEnv;// 是否显示领取红包
	
	private String productInfo;// 是否显示产品信息
	
	private String vendorHttp;// 是否显示商家主页
	
	private String weShop;// 是否显示微商城
	
	private Integer spree;// 是否显示大礼包
	
	private Integer securityAndTraceability;// 是否显示防伪溯源

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getAnti_fake_name() {
		return anti_fake_name;
	}

	public void setAnti_fake_name(String anti_fake_name) {
		this.anti_fake_name = anti_fake_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getActivity_status() {
		return activity_status;
	}

	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
	}

	public Integer getMode_id() {
		return mode_id;
	}

	public void setMode_id(Integer mode_id) {
		this.mode_id = mode_id;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Integer getGetRedEnv() {
		return getRedEnv;
	}

	public void setGetRedEnv(Integer getRedEnv) {
		this.getRedEnv = getRedEnv;
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

	public String getHttpName() {
		return httpName;
	}

	public void setHttpName(String httpName) {
		this.httpName = httpName;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	
	
}
