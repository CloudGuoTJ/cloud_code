package com.yunma.entity.tracing;

import java.io.Serializable;
import java.sql.Date;
/**
 * 
 * 溯源码表
 * @author Cloud
 *
 */
public class LogisticCode implements Serializable {
	private Integer logisticId;//物流码id
	private Integer vendorId;//vendorId转化的code
	private Integer productId;//ProductId转化的code
	private Integer agentId;//agent转化的code
	private String logisticCode;//完整的logisticCode
	private Date createTime;//创建时间
	private Integer status;//状态
	private Integer lvCount;//等级
	

	public Integer getLvCount() {
		return lvCount;
	}
	public void setLvCount(Integer lvCount) {
		this.lvCount = lvCount;
	}
	public Integer getLogisticId() {
		return logisticId;
	}
	public void setLogisticId(Integer logisticId) {
		this.logisticId = logisticId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public void setAgentId(Integer agentId2) {
		this.agentId = agentId2;
	}
	public String getLogisticCode() {
		return logisticCode;
	}
	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


}
