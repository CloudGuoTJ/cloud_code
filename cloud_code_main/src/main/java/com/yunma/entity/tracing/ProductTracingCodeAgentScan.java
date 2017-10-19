package com.yunma.entity.tracing;

import java.io.Serializable;
import java.util.Date;

/**
 * 开放储存使用溯源扫码服务信息的用户
 * 
 * 代理用户表tb_product_tracing_agent_scan_vendorId
 * @author Cloud
 *
 */
public class ProductTracingCodeAgentScan implements Serializable{
	
	private Integer tracingScanId;//扫码Id
	
	private Integer codeType;//二维码类型1.箱码,2.组码
	
	private Integer countPro;//单位数量
	
	private Integer vendorId;//商家id
	
	private String productTracingCode;//溯源码
	
	private Integer productTracingCodeId;//溯源码ID
	
	private Integer productId;//产品ID
	
	private Integer createRowNum;//产品组号
	
	private String productName;//产品名称
	
	private Date scanTime;//扫描日期
	
	private String scanAddress;//扫描地点
	
	private String longitude;//经度
	
	private String latitude;//纬度
	
	private String openId;//微信openId 对应代理表中的openId
	
	private Integer agentId;//代理用户Id 对应代理表
	
	private String agentName;//代理厂商名字
	
	private Integer agentLv;//扫码代理等级
	
	private String province;//代理所属省份
	
	private String city;//代理所属城市
	
	private String district;//代理所属地区
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getCreateRowNum() {
		return createRowNum;
	}
	public void setCreateRowNum(Integer createRowNum) {
		this.createRowNum = createRowNum;
	}
	
	public Integer getCodeType() {
		return codeType;
	}
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}


	

	public Integer getCountPro() {
		return countPro;
	}
	public void setCountPro(Integer countPro) {
		this.countPro = countPro;
	}
	public Integer getTracingScanId() {
		return tracingScanId;
	}
	public void setTracingScanId(Integer tracingScanId) {
		this.tracingScanId = tracingScanId;
	}
	public String getProductTracingCode() {
		return productTracingCode;
	}
	public void setProductTracingCode(String productTracingCode) {
		this.productTracingCode = productTracingCode;
	}
	public Integer getProductTracingCodeId() {
		return productTracingCodeId;
	}
	public void setProductTracingCodeId(Integer productTracingCodeId) {
		this.productTracingCodeId = productTracingCodeId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanAddress() {
		return scanAddress;
	}
	public void setScanAddress(String scanAddress) {
		this.scanAddress = scanAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Integer getAgentLv() {
		return agentLv;
	}
	public void setAgentLv(Integer agentLv) {
		this.agentLv = agentLv;
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	

}
