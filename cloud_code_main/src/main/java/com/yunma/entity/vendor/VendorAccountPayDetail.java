package com.yunma.entity.vendor;

import java.util.Date;

public class VendorAccountPayDetail {

	private Integer id;						//
	private Integer vendorId;				//厂商ID
	private Integer vendorAccountId;		//厂商账户表ID   
	private String payChannelName;		//充值渠道名称  
	private Double payChannelRateMoney;	//充值渠道手续费      
	private String payUserName;			//充值用户名
	private String payUserPwd;			//设立独立的支付密码 
	private String payVendorAccount;		//充值卡账号
	private String unionOrderNo;			//银联订单号
	private String serialNumber;			//充值流水号
	private Integer status;					//充值状态
	private Date createTime;				//充值时间
	private Double payMoney;				//充值金额
	private Double bizMoney;				//消费金额
	private Date bizTime;					//消费时间
	private String remark;					//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getVendorAccountId() {
		return vendorAccountId;
	}
	public void setVendorAccountId(Integer vendorAccountId) {
		this.vendorAccountId = vendorAccountId;
	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	public Double getPayChannelRateMoney() {
		return payChannelRateMoney;
	}
	public void setPayChannelRateMoney(Double payChannelRateMoney) {
		this.payChannelRateMoney = payChannelRateMoney;
	}
	public String getPayUserName() {
		return payUserName;
	}
	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}
	public String getPayUserPwd() {
		return payUserPwd;
	}
	public void setPayUserPwd(String payUserPwd) {
		this.payUserPwd = payUserPwd;
	}
	public String getPayVendorAccount() {
		return payVendorAccount;
	}
	public void setPayVendorAccount(String payVendorAccount) {
		this.payVendorAccount = payVendorAccount;
	}
	public String getUnionOrderNo() {
		return unionOrderNo;
	}
	public void setUnionOrderNo(String unionOrderNo) {
		this.unionOrderNo = unionOrderNo;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getBizMoney() {
		return bizMoney;
	}
	public void setBizMoney(Double bizMoney) {
		this.bizMoney = bizMoney;
	}
	public Date getBizTime() {
		return bizTime;
	}
	public void setBizTime(Date bizTime) {
		this.bizTime = bizTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
