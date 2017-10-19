package com.yunma.entity.agent;

import java.io.Serializable;

public class AgentEntity implements Serializable{
	
	private Integer id; //id
	
	private String agentName;//代理商名称
	
	private Integer vendorId; 
	
	private Integer agentFid; //代理商父级id
	
	private Integer productId; //产品id
	
	private String agentTel; //代理商电话
	
	private String agentEmaill; //代理商邮箱
	
	private String agentAddress; //代理商地址
	
	private String businessLicence; //代理商营业执照
	
	private String agentAuthorize; //代理授权证书
	
	private Integer agentLevel; //代理等级
	
	private Integer agentFirstChilId; //第一子节点代理id
	
	private Integer broAgentId; //下一兄弟节点代理id
	
	private String mark;//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getAgentFid() {
		return agentFid;
	}

	public void setAgentFid(Integer agentFid) {
		this.agentFid = agentFid;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getAgentTel() {
		return agentTel;
	}

	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}

	public String getAgentEmaill() {
		return agentEmaill;
	}

	public void setAgentEmaill(String agentEmaill) {
		this.agentEmaill = agentEmaill;
	}

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getAgentAuthorize() {
		return agentAuthorize;
	}

	public void setAgentAuthorize(String agentAuthorize) {
		this.agentAuthorize = agentAuthorize;
	}

	public Integer getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(Integer agentLevel) {
		this.agentLevel = agentLevel;
	}

	public Integer getAgentFirstChilId() {
		return agentFirstChilId;
	}

	public void setAgentFirstChilId(Integer agentFirstChilId) {
		this.agentFirstChilId = agentFirstChilId;
	}

	public Integer getBroAgentId() {
		return broAgentId;
	}

	public void setBroAgentId(Integer broAgentId) {
		this.broAgentId = broAgentId;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	

}
