package com.yunma.vo.wechatHtml;

import java.io.Serializable;
/**
 * 用于接收参数信息
 * @author Administrator
 *
 */
public class VendorHtmlActivInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actionId; //模板表id
	private Integer vendorId;// 商家ID
	private Integer getRedEnv;// 是否显示领取红包
	private String productInfo;// 是否显示产品信息
	private String vendorHttp;// 是否显示商家主页
	private String weShop;// 是否显示微商城
	private Integer spree;// 是否显示大礼包
	private Integer securityAndTraceability;// 是否显示防伪溯源
	private String comment;// 商家回调页面名称
	private String httpName;// 商家回调页面名称
	private String htmlUri;// 回调页面位置
	private String createTime;// 创建时间
	private Integer status;//模板使用状态   1被使用  0未使用
	private String urlName; //
	public String getHtmlUri() {
		return htmlUri;
	}
	public void setHtmlUri(String htmlUri) {
		this.htmlUri = htmlUri;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getGetRedEnv() {
		return getRedEnv;
	}
	public void setGetRedEnv(Integer getRedEnv) {
		this.getRedEnv = getRedEnv;
	}
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	public String getVendorHttp() {
		return vendorHttp;
	}
	public void setVendorHttp(String vendorHttp) {
		this.vendorHttp = vendorHttp;
	}
	public String getWeShop() {
		return weShop;
	}
	public void setWeShop(String weShop) {
		this.weShop = weShop;
	}
	public Integer getSpree() {
		return spree;
	}
	public void setSpree(Integer spree) {
		this.spree = spree;
	}
	public Integer getSecurityAndTraceability() {
		return securityAndTraceability;
	}
	public void setSecurityAndTraceability(Integer securityAndTraceability) {
		this.securityAndTraceability = securityAndTraceability;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getHttpName() {
		return httpName;
	}
	public void setHttpName(String httpName) {
		this.httpName = httpName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
	
}
