package com.yunma.entity.vendor;

import java.io.Serializable;
import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;
/**
 * 	厂商钱包余额表
 * @author Administrator
 *
 */
public class VendorAccount implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Integer id;		//厂商账户ID
		private Integer vendorId;		//厂商ID
		private Double balance;		//当前余额
		private Integer Integer;	//账户状态  0:正常 、1:注销
		
		private Date createTime;	//账户创建时间
		private Date updateTime;	//账户失效时间
		private String remark;		//备注
		private String text;	//商户报表
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
		public Double getBalance() {
			return balance;
		}
		public void setBalance(Double balance) {
			this.balance = balance;
		}
		public Integer getInteger() {
			return Integer;
		}
		public void setInteger(Integer integer) {
			Integer = integer;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
			
}
