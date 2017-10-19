package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;
/**
 * 溯源箱码表
 * @author Administrator
 *	sb.append("`boxCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '箱码表主键',");
		sb.append("`orderId` bigint(32) not null COMMENT '溯源码ID',");
		sb.append("`productId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`vendorId` bigint(32) not null COMMENT '商家ID',");
		sb.append("`groupCount` bigint(32) not null COMMENT '每组数量',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`codePrefix` varchar(50) not null COMMENT '产品名称',");
		sb.append("`boxCode` varchar(50) not null COMMENT '产品名称',");
		sb.append("`createTime` datetime  not null,");
		sb.append("`totalRowNum` int(32)   DEFAULT null,");
		sb.append("`boxNum` varchar(32)   DEFAULT null,");
		sb.append("`boxShortCode` varchar(32)  DEFAULT null,");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '箱码号',");
 */
public class BoxCode implements Serializable{
	
	private Integer boxCodeId;// 溯源码Id
	private Integer orderId;// 订单ID
	private Integer productId;// 产品ID
	private Integer vendorId;// 厂商ID
	private Integer rowCount;// 单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount
	private String productName;// 产品名
	private String codePrefix;// 溯源码的前缀类似防伪码，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成
	private String boxCode;// 箱码全码
	private Date createDate;// 生成日期
	private int totalRowNum;//总共多少组
	private Integer boxNum;//箱码数
	private String boxShortCode;//箱码转换值
	private Integer boxCount;//每一箱的数量
	
	public Integer getBoxCodeId() {
		return boxCodeId;
	}
	public void setBoxCodeId(Integer boxCodeId) {
		this.boxCodeId = boxCodeId;
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
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
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
	public String getBoxCode() {
		return boxCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
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
	public Integer getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}
	public String getBoxShortCode() {
		return boxShortCode;
	}
	public void setBoxShortCode(String boxShortCode) {
		this.boxShortCode = boxShortCode;
	}
	public Integer getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}
	
	
	
	
}
