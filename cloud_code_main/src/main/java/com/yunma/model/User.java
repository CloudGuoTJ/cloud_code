package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ${CloudGoo} on 2017/5/8 0008.
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;//用户id
	
	private String userName;//用户名
	
	private String passwd;//密码
	
	private Integer status;//状态
	/**
	 * 用户类型userType 沿用瞧一瞧对用户的定义: 1：厂商用户 2：代理用户 3:厂商助理用户 4：厂商财务用户 5：厂商市场用户
	 * 6：厂商生产用户 99：超级管理员 98：次级管理员
	 */
	private Integer userType;
	
	private Integer vendorId;
	
	private String vendorName;
	
	private Integer deleteFlag;
	
	private String findpwd;
	
	private String mainHtmlComment;
	
	private String roleName;
	
	private String mainHtmlCommentEN;
	
	private Date createTime;
	
	private Date lastUpdateTime;
	
	private Integer testSecurityCodeCount;
	
	private Integer testTracingCodeCount;
	//private String globalization;//切换中英文testTracingCodeCount

	public Integer getTestSecurityCodeCount() {
		return testSecurityCodeCount;
	}

	public void setTestSecurityCodeCount(Integer testSecurityCodeCount) {
		this.testSecurityCodeCount = testSecurityCodeCount;
	}

	public Integer getTestTracingCodeCount() {
		return testTracingCodeCount;
	}

	public void setTestTracingCodeCount(Integer testTracingCodeCount) {
		this.testTracingCodeCount = testTracingCodeCount;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public String getFindpwd() {
		return findpwd;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setFindpwd(String findpwd) {
		this.findpwd = findpwd;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMainHtmlComment() {
		return mainHtmlComment;
	}

	public String getMainHtmlCommentEN() {
		return mainHtmlCommentEN;
	}

	public void setMainHtmlCommentEN(String mainHtmlCommentEN) {
		this.mainHtmlCommentEN = mainHtmlCommentEN;
	}

	public void setMainHtmlComment(String mainHtmlComment) {
		this.mainHtmlComment = mainHtmlComment;
	}
}
