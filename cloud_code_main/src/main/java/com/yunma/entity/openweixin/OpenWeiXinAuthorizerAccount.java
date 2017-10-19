package com.yunma.entity.openweixin;

/**
 * 第三方平台授权的公众账号信息
 * @author LUO
 *
 */
public class OpenWeiXinAuthorizerAccount {

	private Integer id;						//
	private String appid;					//appid 
	private String nick_name;				//公众账号昵称
	private String head_img;				//公众账号头像地址
	private String user_name;				//原始id
	private String authorizer_refresh_token;//获取（刷新）授权公众号或小程序的接口调用凭据
	private String principal_name;			//公众账号的主体名称
	private String signature;				//帐号介绍
	private String wxapp_audit_status;		//小程序代码审核状态 0审核中 1 成功 2失败
	private String wxapp_audit_msg;			//小程序审核（成功/失败）消息
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getAuthorizer_refresh_token() {
		return authorizer_refresh_token;
	}
	public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
		this.authorizer_refresh_token = authorizer_refresh_token;
	}
	public String getPrincipal_name() {
		return principal_name;
	}
	public void setPrincipal_name(String principal_name) {
		this.principal_name = principal_name;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getWxapp_audit_status() {
		return wxapp_audit_status;
	}
	public void setWxapp_audit_status(String wxapp_audit_status) {
		this.wxapp_audit_status = wxapp_audit_status;
	}
	public String getWxapp_audit_msg() {
		return wxapp_audit_msg;
	}
	public void setWxapp_audit_msg(String wxapp_audit_msg) {
		this.wxapp_audit_msg = wxapp_audit_msg;
	}
}
