package com.yunma.entity.weChat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

public class WeChatUser {

	private Integer userId;
	private Integer wxConfigVendorId;
	private String openId;
	private String longitude;
	private String latitude;
	private String address;
	private String province;
	private String city;
	private String district;
	private Date createDate;
	private Integer deleteFlag;
	private Integer scanUser;
	private String nickName;
	private Integer integral;
	private String wxAddress;
	private String scanSource;
	private String headImgurl;
	private Integer sex;
	private String remark;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWxConfigVendorId() {
		return wxConfigVendorId;
	}
	public void setWxConfigVendorId(Integer wxConfigVendorId) {
		this.wxConfigVendorId = wxConfigVendorId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getScanUser() {
		return scanUser;
	}
	public void setScanUser(Integer scanUser) {
		this.scanUser = scanUser;
	}
	public String getNickName() throws UnsupportedEncodingException {
		
//		nickName = URLDecoder.decode(nickName, "utf-8");
		
		return nickName;
	}
	public void setNickName(String nickName) {
		
		
		this.nickName = nickName;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getWxAddress() {
		return wxAddress;
	}
	public void setWxAddress(String wxAddress) {
		this.wxAddress = wxAddress;
	}
	public String getScanSource() {
		return scanSource;
	}
	public void setScanSource(String scanSource) {
		this.scanSource = scanSource;
	}
	public String getHeadImgurl() {
		return headImgurl;
	}
	public void setHeadImgurl(String headImgurl) {
		this.headImgurl = headImgurl;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
