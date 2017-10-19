package com.yunma.entity.vendor;

import java.io.Serializable;

public class VendorUserDetail implements Serializable{
	
	private Integer detailId;		//
	private Integer vendorId;		//用户id(对tb_vendor_user表)
	private String brandName;		//商家工商部注册名称
	private String industryName;	//行业
	private String contactName;		//联系人
	private String vendorLogo;		//商家提供的logo
	private String vendorWeixin;	//商家的代表使用的微信号
	private String customPhone;		//联系人手机
	private String customTel;		//商家固定客服电话
	private String vendorAddress;	//商家地址,由用户添加
	private String link;			//域名--商家提供的网址最好为主页
	private String officialAccounts;//关联公众号
	
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
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getVendorLogo() {
		return vendorLogo;
	}
	public void setVendorLogo(String vendorLogo) {
		this.vendorLogo = vendorLogo;
	}
	public String getVendorWeixin() {
		return vendorWeixin;
	}
	public void setVendorWeixin(String vendorWeixin) {
		this.vendorWeixin = vendorWeixin;
	}
	public String getCustomPhone() {
		return customPhone;
	}
	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}
	public String getCustomTel() {
		return customTel;
	}
	public void setCustomTel(String customTel) {
		this.customTel = customTel;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getOfficialAccounts() {
		return officialAccounts;
	}
	public void setOfficialAccounts(String officialAccounts) {
		this.officialAccounts = officialAccounts;
	}
	
	

}
