package com.yunma.vo.user;

import java.io.Serializable;
import java.sql.Date;

public class UserVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;//用户名
	
	private Integer checkStatus;//用户状态
	
	private Integer userType;//用户类型:99 管理员 1-商家
	
	private Integer vendorId;//是否vendorId,管理员为0
	
	private String vendorName;//商家名
	
	private Integer deleteFlag;//是否删除信息
	
	private Integer userId;//是否删除信息
	
	private String  vendorEmaill;//商家预留邮箱
	
	private String customPhone;//商家预留联系电话
	
	private String tradeMarkImgUrl;//企业营业执照url
	
	private String tradeMarkLicense;//企业商标注册证书图片
	
	private String bankAccountOpeningLicense;//银行开户许可
	
	private String foodProductionLicence;//食品生产许可
	
	private String organizationCodeCertificate;//组织机构
	
	private String  industrialProductionLicense;//工业生产许可证
	
	
	

	
	

	public String getIndustrialProductionLicense() {
		return industrialProductionLicense;
	}
	public void setIndustrialProductionLicense(String industrialProductionLicense) {
		this.industrialProductionLicense = industrialProductionLicense;
	}
	public String getBankAccountOpeningLicense() {
		return bankAccountOpeningLicense;
	}
	public void setBankAccountOpeningLicense(String bankAccountOpeningLicense) {
		this.bankAccountOpeningLicense = bankAccountOpeningLicense;
	}
	public String getFoodProductionLicence() {
		return foodProductionLicence;
	}
	public void setFoodProductionLicence(String foodProductionLicence) {
		this.foodProductionLicence = foodProductionLicence;
	}
	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}
	public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate;
	}
	public String getVendorEmaill() {
		return vendorEmaill;
	}
	public void setVendorEmaill(String vendorEmaill) {
		this.vendorEmaill = vendorEmaill;
	}
	public String getCustomPhone() {
		return customPhone;
	}
	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}
	public String getTradeMarkImgUrl() {
		return tradeMarkImgUrl;
	}
	public void setTradeMarkImgUrl(String tradeMarkImgUrl) {
		this.tradeMarkImgUrl = tradeMarkImgUrl;
	}
	public String getTradeMarkLicense() {
		return tradeMarkLicense;
	}
	public void setTradeMarkLicense(String tradeMarkLicense) {
		this.tradeMarkLicense = tradeMarkLicense;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	private Date createTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
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
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	



}
