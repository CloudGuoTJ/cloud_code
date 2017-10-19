package com.yunma.vo.user;

import java.io.Serializable;
import java.sql.Date;

public class UserVendorVO implements Serializable{
	/*
	    @Select("select userId,userName,vendorName,vendorId,createTime, from tb_user where userId=#{userId}")
	    User findUserByUserId(Integer userId);*/
	/**
	 * 商家用户信息显示
	 */
	private Integer userId;
	private Integer vendorId;
	private String userName;
	private String vendorName;
	private Date createTime;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
