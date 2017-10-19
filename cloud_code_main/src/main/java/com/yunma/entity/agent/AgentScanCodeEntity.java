package com.yunma.entity.agent;

import java.io.Serializable;
import java.util.Date;

public class AgentScanCodeEntity implements Serializable{
	
	private Integer id;
	
	private Integer empId; //扫码员工的id
	
	private Integer agentId; //代理商的id
	
	private String scanAddress; //扫码地点
	
	private Date scanTime; //扫码时间
	
	private Integer productId; //产品id
	
	private String scanTime1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getScanAddress() {
		return scanAddress;
	}

	public void setScanAddress(String scanAddress) {
		this.scanAddress = scanAddress;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getScanTime1() {
		return scanTime1;
	}

	public void setScanTime1(String scanTime1) {
		this.scanTime1 = scanTime1;
	}
	
	

}
