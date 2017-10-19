package com.yunma.entity.agent;

import java.io.Serializable;
import java.util.Date;

public class AgentEmployee implements Serializable{
	
	private Integer id; //id
	
	private Integer vendorId; //厂商id
	
	private Integer agentId; //代理商id
	
	private Integer empFid; //员工父id;
	
	private String empName; //员工姓名
	
	private Integer productId; //产品id
	
	private Date createTime; //扫码时间
	
	private Integer workNum; //工号
	
	private String empTel; //员工电话
	
	private String empIdcard; //员工身份号码
	
	private String createTime1;
	
	private Integer empLevel;//员工等级 
	
	private Date expireTime; //失效时间
	
	private String openId; //微信openId
	
	private String longitude; //经度
	
	private String latitude; //纬度
	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getEmpFid() {
		return empFid;
	}

	public void setEmpFid(Integer empFid) {
		this.empFid = empFid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getWorkNum() {
		return workNum;
	}

	public void setWorkNum(Integer workNum) {
		this.workNum = workNum;
	}

	public String getEmpTel() {
		return empTel;
	}

	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}

	public String getEmpIdcard() {
		return empIdcard;
	}

	public void setEmpIdcard(String empIdcard) {
		this.empIdcard = empIdcard;
	}

	public String getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}

	public Integer getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(Integer empLevel) {
		this.empLevel = empLevel;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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


}
