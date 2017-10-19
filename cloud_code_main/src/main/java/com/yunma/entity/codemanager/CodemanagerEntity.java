package com.yunma.entity.codemanager;

import java.io.Serializable;
import java.util.Date;

public class CodemanagerEntity implements Serializable{
	
	private Integer id;//ID
	
	private Integer orderId;//订单id
	
	private Integer vendor_id;//商户id
	
	private String anti_fake_name;//活动名称
	
	private Date start_time;//开始时间
	
	private Date end_time;//结束时间
	
	private String mark;//备注
	
	private Integer modeId;//模板id
	
	private String startTime;//
	
	private String endTime;
	
	private Integer activity_status;//活动状态
	
	private Integer deleteFlag;//删除 
	
	private Integer getRedEnv;// 是否显示领取红包
	
	private String productInfo;// 是否显示产品信息
	
	private String vendorHttp;// 是否显示商家主页
	
	private String weShop;// 是否显示微商城
	
	private Integer spree;// 是否显示大礼包
	
	private Integer securityAndTraceability;// 是否显示防伪溯源
	
	private Integer funcFlag;//1.防伪  2.溯源

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getModeId() {
		return modeId;
	}

	public void setModeId(Integer modeId) {
		this.modeId = modeId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getAnti_fake_name() {
		return anti_fake_name;
	}

	public void setAnti_fake_name(String anti_fake_name) {
		this.anti_fake_name = anti_fake_name;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Integer getActivity_status() {
		return activity_status;
	}

	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
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

	public Integer getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(Integer funcFlag) {
		this.funcFlag = funcFlag;
	}

	
}
