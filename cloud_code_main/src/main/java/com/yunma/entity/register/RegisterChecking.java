package com.yunma.entity.register;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 注册审核表
 * 
 * @author Administrator
 *
 */
public class RegisterChecking implements Serializable{

	/**
	 * 审核信息
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer checkId;//主键id
	
	private String  vendorName;//厂商名
	
	private String industryName;//行业
	
	private String tradeMarkImgUrl;//企业营业执照url
	
	private String tradeMarkLicense;//企业商标注册证书图片
	
	private String  bankAccountOpeningLicense;//企业商标注册证书图片
	
	private String  foodProductionLicence;//食品生产许可证
	
	private String  organizationCodeCertificate;//组织机构代码证
	
	private String industrialProductionLicense;//工业产品生成许可证
	  
	private String contactName;//联系人
	
	private String  customPhone;//联系人手机
	
	private String vendorAddress;//企业联系地址,由用户添加
	
	private String vendorEmaill;//企业邮箱
	
	private String checkComment;//审核未通过备注
	
	private String createTime;//申请时间
	
	private Integer checkStatus;//审核状态:1,未通过,2,通过

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getVendorEmaill() {
		return vendorEmaill;
	}

	public void setVendorEmaill(String vendorEmaill) {
		this.vendorEmaill = vendorEmaill;
	}

	public String getCheckComment() {
		return checkComment;
	}

	public void setCheckComment(String checkComment) {
		this.checkComment = checkComment;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
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

	public String getIndustrialProductionLicense() {
		return industrialProductionLicense;
	}

	public void setIndustrialProductionLicense(String industrialProductionLicense) {
		this.industrialProductionLicense = industrialProductionLicense;
	}

	

	
}
