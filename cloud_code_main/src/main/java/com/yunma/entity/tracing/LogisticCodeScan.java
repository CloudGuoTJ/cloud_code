package com.yunma.entity.tracing;

import java.io.Serializable;
import java.util.Date;
/**
 * 物流扫码记录表,用于提取新增代理用户
 * @author cloud
 *
 */
public class LogisticCodeScan implements Serializable{
	private	Integer logisticCodeScanId;//物流扫码信息表 Id
	private String openId;//扫码成员OpenId
	private String logisticCode;//厂商物流码
	private Date scanTime;//扫码时间
	private Integer logisticCodeId;//厂商物流码id
	private Integer lvCount;//授权代理商级别
	private Integer agentId;//代理id
	public Integer getLogisticCodeScanId() {
		return logisticCodeScanId;
	}
	public void setLogisticCodeScanId(Integer logisticCodeScanId) {
		this.logisticCodeScanId = logisticCodeScanId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLogisticCode() {
		return logisticCode;
	}
	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public Integer getLogisticCodeId() {
		return logisticCodeId;
	}
	public void setLogisticCodeId(Integer logisticCodeId) {
		this.logisticCodeId = logisticCodeId;
	}
	public Integer getLvCount() {
		return lvCount;
	}
	public void setLvCount(Integer lvCount) {
		this.lvCount = lvCount;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
		

}
