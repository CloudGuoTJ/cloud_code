package com.yunma.entity.openweixin;

import java.util.Date;

/**
 * 微信开放平台 基础配置类
 * @author LUO
 *
 */
public class OpenWeiXinConfig {

	private Integer id;
	private String openWeixinAppid;			//此appid为开放平台（open.weixin.qq.com）appid   不是公众平台appid    
	private String componentVerifyTicket;	//Ticket内容  详情请看https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=989b84a2b99e9f43723cd1ef836eca8f59c96e0e&lang=zh_CN 
	private String componentAccessToken;	//第三方平台compoment_access_token是第三方平台的下文中接口的调用凭据，也叫做令牌（component_access_token）。每个令牌是存在有效期（2小时）的，且令牌的调用不是无限制的，请第三方平台做好令牌的管理，在令牌快过期时（比如1小时50分）再进行刷新。
	private Date componentAccessTokenTime;	//令牌更新时间
	private String preAuthCode;				//预授权码
	private Date preAuthCodeTime;			//预授权码更新时间
	private String authCode;				//授权码
	private Date authCodeTime;				//授权码更新时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenWeixinAppid() {
		return openWeixinAppid;
	}
	public void setOpenWeixinAppid(String openWeixinAppid) {
		this.openWeixinAppid = openWeixinAppid;
	}
	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}
	public String getComponentAccessToken() {
		return componentAccessToken;
	}
	public void setComponentAccessToken(String componentAccessToken) {
		this.componentAccessToken = componentAccessToken;
	}
	public Date getComponentAccessTokenTime() {
		return componentAccessTokenTime;
	}
	public void setComponentAccessTokenTime(Date componentAccessTokenTime) {
		this.componentAccessTokenTime = componentAccessTokenTime;
	}
	public String getPreAuthCode() {
		return preAuthCode;
	}
	public void setPreAuthCode(String preAuthCode) {
		this.preAuthCode = preAuthCode;
	}
	public Date getPreAuthCodeTime() {
		return preAuthCodeTime;
	}
	public void setPreAuthCodeTime(Date preAuthCodeTime) {
		this.preAuthCodeTime = preAuthCodeTime;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Date getAuthCodeTime() {
		return authCodeTime;
	}
	public void setAuthCodeTime(Date authCodeTime) {
		this.authCodeTime = authCodeTime;
	}
	
}
