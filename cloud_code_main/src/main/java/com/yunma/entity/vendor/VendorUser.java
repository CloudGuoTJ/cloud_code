package com.yunma.entity.vendor;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 企业用户表
 */
public class VendorUser implements Serializable {
	
	private Integer vendorId;		//
	private String vendorName;		//用户名
	private String vendorPwd;		//密码
	private Integer vendorTel;		//用户电话
	private String vendorNickName;	//昵称
	private Integer rowId;			//用户组id
	private String vendorEmail;	//商家的邮箱
	private String accessToken;	//电子凭证
	private String findPwd;			//查询密码
	private String mainHtmlComment;	//评论消息
	private String payPassword;	//设立支付密码
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime = null;		//上次登录的时间,随登录刷新

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorPwd() {
		return vendorPwd;
	}

	public void setVendorPwd(String vendorPwd) {
		this.vendorPwd = vendorPwd;
	}

	public Integer getVendorTel() {
		return vendorTel;
	}

	public void setVendorTel(Integer vendorTel) {
		this.vendorTel = vendorTel;
	}

	public String getVendorNickName() {
		return vendorNickName;
	}

	public void setVendorNickName(String vendorNickName) {
		this.vendorNickName = vendorNickName;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFindPwd() {
		return findPwd;
	}

	public void setFindPwd(String findPwd) {
		this.findPwd = findPwd;
	}

	public String getMainHtmlComment() {
		return mainHtmlComment;
	}

	public void setMainHtmlComment(String mainHtmlComment) {
		this.mainHtmlComment = mainHtmlComment;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
