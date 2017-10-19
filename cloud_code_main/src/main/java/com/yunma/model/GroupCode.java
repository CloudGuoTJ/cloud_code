package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;
/**
 * 溯源组码表
 * @author Administrator
 *	    sb.append("`groupCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '组码表 主键',");
		sb.append("`orderId` bigint(32) not null COMMENT '订单ID',");
		sb.append("`productId` bigint(32) not null COMMENT '产品Id',");
		sb.append("`vendorId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`groupCount` bigint(32) not null COMMENT '商家ID',");
		sb.append("`groupCode`varchar(50) not null COMMENT '产品组号',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`groupNum` int(32) not null COMMENT '组号',");
		sb.append("`codePrefix` varchar(64) not null COMMENT '前缀',");
	
		sb.append("`createDate` datetime  NOT NULL COMMENT '创建时间',");
		sb.append("`totalRowNum` int(32) NOT NULL COMMENT '微信openId 对应代理表中的openId',");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '代理用户Id 对应代理表',");
		sb.append("`scanCount` int(20)  DEFAULT 0,");
		sb.append("PRIMARY KEY (groupCodeId)");
 */
public class GroupCode implements Serializable{
	
	private Integer groupCodeId;// 溯源码Id
	
	private Integer orderId;// 订单ID
	
	private Integer productId;// 产品ID
	
	private Integer vendorId;// 厂商ID
	
	private Integer groupCount;//组数量与溯源码表中的rowCount相对应
	
	private String  groupCode;//根据组数量
	
	private String productName;// 产品名
	
	private String codePrefix;// 溯源码的前缀类似防伪码，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成
	
	private Date createDate;// 生成日期
	
	private int totalRowNum;//总共多少组
	
	private int groupNum;//组号
	
	private Integer boxCount;//箱码数
	
	private Integer boxNum;//箱码数
	
	public Integer getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public Integer getGroupCodeId() {
		return groupCodeId;
	}
	public void setGroupCodeId(Integer groupCodeId) {
		this.groupCodeId = groupCodeId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCodePrefix() {
		return codePrefix;
	}
	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getTotalRowNum() {
		return totalRowNum;
	}
	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	public Integer getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}

}
