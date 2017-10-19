package com.yunma.entity.tracing;

import java.io.Serializable;
import java.util.Date;
/**
 * 厂商溯源统计表
 * 记录和统计厂商订单溯源信息和agentTracingScan相区别,后期根据厂商独立创建
 * 该表为实现厂商用户溯源而创建适用于最新版本,未使用分库分表设计,后期数据量增加须优化
 * tb_vendor_tracing_statistics
 * @author Administrator
 *
 */
public class VendorTracingStatistics implements Serializable{
	
	private Integer vendorTracingId;//主键
	
	private Integer vendorId;//厂商Id
		
	private Integer orderId;//订单id
	
	private Integer productId;//产品id
	
	private Integer agentId;//代理id
	
	private String agentName;//代理名称
	
	private Integer productRowNum;//产品所属第几组
	
	private Integer lv;//厂商等级
	
	private String tracingCode;//溯源码全码
	
	private Integer count;//溯源码对应的数量
	
	private Integer codeType;//对应的溯源码类型1: 箱码 ,2.条码
		
	private Date scanTime;//记录时间
	
	private String productName;//产品名
	
	private String openId;//对应openId
	
	private String address;//地址
	
    private String province;//省
    
    private String city;//市
    
    private String district;//区
    
    public Integer getProductRowNum() {
    	return productRowNum;
    }
    
    public void setProductRowNum(Integer productRowNum) {
    	this.productRowNum = productRowNum;
    }
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCodeType() {
		return codeType;
	}
	
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}
	public String getTracingCode() {
		return tracingCode;
	}

	public void setTracingCode(String tracingCode) {
		this.tracingCode = tracingCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getVendorTracingId() {
		return vendorTracingId;
	}

	public void setVendorTracingId(Integer vendorTracingId) {
		this.vendorTracingId = vendorTracingId;
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

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
}
