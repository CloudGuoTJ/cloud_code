package com.yunma.vo.wxConfig;

import java.sql.Date;

public class WxConfigVo {
	
	private Integer id;
	private Integer vendorId; 
    private String appid; //微信公众号的appid
    private String appsecret; //微信公众号的appsecret
    private String img_wxQrCode;//公众号二维码图片
    private String redirect_uri; //微信公众号的授权回调域名下的接口
    private String suCaiUrl; //素材，用来引导关注
    private String mchId;//微信商务号
    private String img_certName;	//API证书图片
    private String wxKey;	//微信支付API证书密钥
    private String sendName;//红包名字
    private String wishing;	//红包祝福语
    private String cssLink;
    private Date createTime;//创建时间
    
    
    
    
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCssLink() {
		return cssLink;
	}
	public void setCssLink(String cssLink) {
		this.cssLink = cssLink;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	
	public String getImg_wxQrCode() {
		return img_wxQrCode;
	}
	public void setImg_wxQrCode(String img_wxQrCode) {
		this.img_wxQrCode = img_wxQrCode;
	}
	public String getImg_certName() {
		return img_certName;
	}
	public void setImg_certName(String img_certName) {
		this.img_certName = img_certName;
	}
	public String getRedirect_uri() {
		return redirect_uri;
	}
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getSuCaiUrl() {
		return suCaiUrl;
	}
	public void setSuCaiUrl(String suCaiUrl) {
		this.suCaiUrl = suCaiUrl;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	
	
	public String getWxKey() {
		return wxKey;
	}
	public void setWxKey(String wxKey) {
		this.wxKey = wxKey;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
    
}
