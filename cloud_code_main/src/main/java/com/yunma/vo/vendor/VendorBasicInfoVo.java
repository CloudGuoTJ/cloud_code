package com.yunma.vo.vendor;

import java.io.Serializable;

/**
 * 企业基本信息Vo
 */
public class VendorBasicInfoVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer vendorId;		//id
	private String vendorName;		//账号
	private String userName;		//企业登陆账号
	private String brandName;		//品牌商名称
	private String imgUrl;			//头像地址
	private String industryName;	//所属行业
	private String contactName;		//联系人
	private String customPhone;		//手机号码
	private String vendorWeixin; 	//微信绑定
	private String customTel;		//客服电话
	private String vendorAddress;	//联系地址
	private String link;			//官网地址
	private String vendorEmaill;	//商家邮箱
	private String officialAccounts;//关联公众号
	
	
	
	public String getVendorEmaill() {
		return vendorEmaill;
	}
	public void setVendorEmaill(String vendorEmaill) {
		this.vendorEmaill = vendorEmaill;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public String getCustomPhone() {
		return customPhone;
	}
	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}
	public String getVendorWeixin() {
		return vendorWeixin;
	}
	public void setVendorWeixin(String vendorWeixin) {
		this.vendorWeixin = vendorWeixin;
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
