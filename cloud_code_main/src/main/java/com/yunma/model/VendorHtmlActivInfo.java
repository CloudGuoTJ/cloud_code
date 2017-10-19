package com.yunma.model;

/**
 * 微信回调页面呈现控制表
 */
import java.io.Serializable;
import java.util.Date;

public class VendorHtmlActivInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
  `actionId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '微信回调模板表id',
  `httpName` varchar(64) NOT NULL COMMENT '模板名称',
  `htmlUri` varchar(64) NOT NULL COMMENT '存放位置',
  `vendorId` bigint(32) NOT NULL COMMENT '商家Id',
  `getRedEnv` tinyint NOT NULL COMMENT '是否显示领取红包,否为0,是则存入对应信息',
  `productInfo` varchar(64) NOT NULL COMMENT '是否显示产品信息',
  `vendorHttp` varchar(64) NOT NULL COMMENT '是否显示商家主页',
  `weShop` varchar(64) NOT NULL COMMENT '是否显示微商城',
  `securityAndTraceability` tinyint NOT NULL COMMENT '是否显示防伪溯源',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `comment` varchar(64) NOT NULL COMMENT '模板说明',
  `spree` tinyint NOT NULL COMMENT '大礼包',
	 */
	private Integer vendorId;// 商家ID
	private Integer getRedEnv;// 是否显示领取红包
	private String productInfo;// 是否显示产品信息
	private String vendorHttp;// 是否显示商家主页
	private String weShop;// 是否显示微商城
	private Integer spree;// 是否显示大礼包
	private Integer securityAndTraceability;// 是否显示防伪溯源
	private Date createTime;// 创建时间
	private String createTime1;// 创建时间
	private String actionId;// 微信回调显示表ID'
	private String comment;// 商家回调页面名称
	private String htmlName;// 商家回调页面名称
	private String htmlUri;// 回调页面位置
	
	private Integer id;//
	private String urlName;
	
	private Integer tempType;//追加字段用于区分自定义和经典模板
	
	private String storeData;//用于自定义模板字段变更
 
	
	public Integer getTempType() {
		return tempType;
	}
	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}

	public String getStoreData() {
		return storeData;
	}
	public void setStoreData(String storeData) {
		this.storeData = storeData;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getHtmlName() {
		return htmlName;
	}
	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}
	public String getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
