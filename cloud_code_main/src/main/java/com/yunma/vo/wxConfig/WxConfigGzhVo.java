package com.yunma.vo.wxConfig;

import java.io.Serializable;
import java.sql.Date;


public class WxConfigGzhVo implements Serializable{
	
	private Integer id;
	
	private Integer vendorId;
	
	private String gzhName;
	
	private String gzhAccount;
	
	private String gzhId;
	
	private String gzhMark;
	
	private String gzhType;
	
	private String appid;
	
	private String appsecret;
	
	private String wxQrCodePath;
	
	private String gzhHeadImg;
	
	private String shortUrl;
	
	private String wxKey;
	
	private Date updateTime;
	
	private String token;
	
	private String mchId;//商户平台id
	
	private String certName;
	
	private String oauthAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getGzhName() {
		return gzhName;
	}

	public void setGzhName(String gzhName) {
		this.gzhName = gzhName;
	}

	public String getGzhAccount() {
		return gzhAccount;
	}

	public void setGzhAccount(String gzhAccount) {
		this.gzhAccount = gzhAccount;
	}

	public String getGzhId() {
		return gzhId;
	}

	public void setGzhId(String gzhId) {
		this.gzhId = gzhId;
	}

	public String getGzhMark() {
		return gzhMark;
	}

	public void setGzhMark(String gzhMark) {
		this.gzhMark = gzhMark;
	}

	public String getGzhType() {
		return gzhType;
	}

	public void setGzhType(String gzhType) {
		this.gzhType = gzhType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getWxQrCodePath() {
		return wxQrCodePath;
	}

	public void setWxQrCodePath(String wxQrCodePath) {
		this.wxQrCodePath = wxQrCodePath;
	}

	public String getGzhHeadImg() {
		return gzhHeadImg;
	}

	public void setGzhHeadImg(String gzhHeadImg) {
		this.gzhHeadImg = gzhHeadImg;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getWxKey() {
		return wxKey;
	}

	public void setWxKey(String wxKey) {
		this.wxKey = wxKey;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getOauthAddress() {
		return oauthAddress;
	}

	public void setOauthAddress(String oauthAddress) {
		this.oauthAddress = oauthAddress;
	}
	

}
