package com.yunma.entity.vendor;

import java.io.Serializable;
import java.util.Date;

public class VendorInfo implements Serializable{
	
	
	private Integer detailId; //企业信息表ID
	
	private Integer vendorId; //厂商ID
	
	private String brandName; //商家名称
	
	private String industryName; //行业
	
	private String customPhone; //联系方式
	
	private String functionType; //功能需求
	
	private String link; //官网
	
	private String vendorMall; //微商城
	
	private String wxConfig; //公众号
	
	private String Emaill; //E_mell
	
	private Date createTime;		//创建时间
	
	private String img_vendor_qualification; //客户证书
	
	private String img_trademark_certificate; //商标证书
	
	private String img_industrial_production_license; //工业生产许可
	
	private String img_food_production_license; //食品生产许可
	
	private String img_bank_account_license;//银行开户许可证书
	

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}


	

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVendorMall() {
		return vendorMall;
	}

	public void setVendorMall(String vendorMall) {
		this.vendorMall = vendorMall;
	}

	public String getWxConfig() {
		return wxConfig;
	}

	public void setWxConfig(String wxConfig) {
		this.wxConfig = wxConfig;
	}

	public String getEmaill() {
		return Emaill;
	}

	public void setEmaill(String emaill) {
		Emaill = emaill;
	}

	public String getImg_vendor_qualification() {
		return img_vendor_qualification;
	}

	public void setImg_vendor_qualification(String img_vendor_qualification) {
		this.img_vendor_qualification = img_vendor_qualification;
	}

	public String getImg_trademark_certificate() {
		return img_trademark_certificate;
	}

	public void setImg_trademark_certificate(String img_trademark_certificate) {
		this.img_trademark_certificate = img_trademark_certificate;
	}

	public String getImg_industrial_production_license() {
		return img_industrial_production_license;
	}

	public void setImg_industrial_production_license(
			String img_industrial_production_license) {
		this.img_industrial_production_license = img_industrial_production_license;
	}

	public String getImg_food_production_license() {
		return img_food_production_license;
	}

	public void setImg_food_production_license(String img_food_production_license) {
		this.img_food_production_license = img_food_production_license;
	}

	public String getImg_bank_account_license() {
		return img_bank_account_license;
	}

	public void setImg_bank_account_license(String img_bank_account_license) {
		this.img_bank_account_license = img_bank_account_license;
	}
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "VendorInfo [detailId=" + detailId + ", vendorId=" + vendorId
				+ ", brandName=" + brandName + ", industryName=" + industryName
				+ ", customPhone=" + customPhone + ", functionType="
				+ functionType + ", link=" + link + ", vendorMall="
				+ vendorMall + ", wxConfig=" + wxConfig + ", Emaill=" + Emaill
				+ ", createTime=" + createTime + ", img_vendor_qualification="
				+ img_vendor_qualification + ", img_trademark_certificate="
				+ img_trademark_certificate
				+ ", img_industrial_production_license="
				+ img_industrial_production_license
				+ ", img_food_production_license="
				+ img_food_production_license + ", img_bank_account_license="
				+ img_bank_account_license + "]";
	}


	


}
