package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**  获取微信公众号的相关信息表
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class WxConfig implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
    private Integer vendorId;
    private String appid; //微信公众号的appid
    private String appsecret; //微信公众号的appsecret
    private String redirect_uri; //微信公众号的授权回调域名下的接口(二维码转URL)
    private String shortUrl;//短域名--仅我们自己的公众号需要填写，当客户没有配置微信公众号时，通过短域名来查找对应的公众号
    private String suCaiUrl; //素材，用来引导关注
    private String mchId;//微信商务号
    private String wxKey;//微信支付安全证书密钥
    private String certName;//证书的保存路径
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String wxQrCode;//微信关注公众号二维码识别后的值
    private String sendName;//发送者名称
    private String wishing;//红包祝福语
    private String wxQrCodePath;//微信关注公众号二维码的存储路径
    private boolean cssLink;//是否使用系统默认的公众号
    private String token;//微信调取接口唯一令牌
    private String deleteFlag;
    private String oauthAddress;//授权页面的保存地址
       
      
    public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isCssLink() {
        return cssLink;
    }

    public void setCssLink(boolean cssLink) {
        this.cssLink = cssLink;
    }

    public String getWxQrCodePath() {
        return wxQrCodePath;
    }

    public void setWxQrCodePath(String wxQrCodePath) {
        this.wxQrCodePath = wxQrCodePath;
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

    public String getWxQrCode() {
        return wxQrCode;
    }

    public void setWxQrCode(String wxQrCode) {
        this.wxQrCode = wxQrCode;
    }

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

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getSuCaiUrl() {
        return suCaiUrl;
    }

    public void setSuCaiUrl(String suCaiUrl) {
        this.suCaiUrl = suCaiUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
