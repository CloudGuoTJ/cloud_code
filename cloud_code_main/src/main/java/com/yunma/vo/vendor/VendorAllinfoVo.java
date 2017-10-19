package com.yunma.vo.vendor;

import java.io.Serializable;
import java.util.Date;

public class VendorAllinfoVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer detail_id;  //厂商详情id
	private Integer vendorId;		//厂商id
	private Integer vendorName;		//厂商名称
	private String brand_name; 		//商家名称
	private String industryName;	//所属行业
	private String functionType;	//企业需求功能 1:互联推广2：防伪3：溯源4：红包5：游戏6：大数据(可多选)'
	private String vendorQualification;		//企业资质（图片上传）
	private String trademark_certificate;	//企业商标注册证书（图片上传）
	private String industrialProductionLicense;		//工业产品生产许可证
	private String food_production_license;		//食品生产许可证
	private String bank_account_license;		//银行开户许可证
	private String imgUrl;				//企业LOGO头像地址
	private String product_brand;		//产品品牌
	private String brand_image;			//品牌代表图片
	private String contactName;		//业务联系人
	private	String customPhone;			//联系方式
	private String vendorAddress;	//联系地址	
	private String vendorEmaill;	//邮箱
	private String vendor_introduce;	//企业介绍
	private String link;			//域名——企业官网地址
	private String vendor_mall;		//企业商城/微商
	private String officialAccounts;//关联公众号	
	private Date createTime;		//创建时间
	private String comment;		//备注
	
	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public void setVendorEmaill(String vendorEmaill) {
		this.vendorEmaill = vendorEmaill;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getVendorName() {
		return vendorName;
	}
	public void setVendorName(Integer vendorName) {
		this.vendorName = vendorName;
	}
	public String getBrandName() {
		return brand_name;
	}
	public void setBrandName(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getFunctionType() {
		return functionType;
	}
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	public String getVendorQualification() {
		return vendorQualification;
	}
	public void setVendorQualification(String vendorQualification) {
		this.vendorQualification = vendorQualification;
	}
	public String getTrademark_certificate() {
		return trademark_certificate;
	}
	public void setTrademark_certificate(String trademark_certificate) {
		this.trademark_certificate = trademark_certificate;
	}
	public String getIndustrialProductionLicense() {
		return industrialProductionLicense;
	}
	public void setIndustrialProductionLicense(String industrialProductionLicense) {
		this.industrialProductionLicense = industrialProductionLicense;
	}
	public String getFood_production_license() {
		return food_production_license;
	}
	public void setFood_production_license(String food_production_license) {
		this.food_production_license = food_production_license;
	}
	public String getBank_account_license() {
		return bank_account_license;
	}
	public void setBank_account_license(String bank_account_license) {
		this.bank_account_license = bank_account_license;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public String getBrand_image() {
		return brand_image;
	}
	public void setBrand_image(String brand_image) {
		this.brand_image = brand_image;
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
	public void setVendorEmail(String vendorEmaill) {
		this.vendorEmaill = vendorEmaill;
	}
	public String getVendor_introduce() {
		return vendor_introduce;
	}
	public void setVendor_introduce(String vendor_introduce) {
		this.vendor_introduce = vendor_introduce;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getVendor_mall() {
		return vendor_mall;
	}
	public void setVendor_mall(String vendor_mall) {
		this.vendor_mall = vendor_mall;
	}
	public String getOfficialAccounts() {
		return officialAccounts;
	}
	public void setOfficialAccounts(String officialAccounts) {
		this.officialAccounts = officialAccounts;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
		
}
