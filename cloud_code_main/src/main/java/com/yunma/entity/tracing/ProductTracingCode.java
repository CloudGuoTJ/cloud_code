package com.yunma.entity.tracing;

import java.io.Serializable;
import java.sql.Date;

/**
 * agent溯源码表
 * 
 * @author cloud
 * 
 *         CREATE TABLE `tb_product_tracing_code` ( 
 *         `productTracingCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '溯源码Id', 
 *         `orderId` bigint(20) NOT NULL COMMENT '订单ID', 
 *         `productId` bigint(32) NOT NULL COMMENT '产品ID',
 *          `vendorId` bigint(32) NOT NULL COMMENT '厂商ID',
 *         `productName` varchar(64) NOT NULL COMMENT '产品名', 
 *         `codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源 ||已废弃
 *         本版本中防伪码和溯源码彻底分开', 
 *          `codePrefix` varchar(64) NOT NULL COMMENT'溯源码的前缀类似防伪码，前4位：根据orderId转换，
 *          中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成', 
 *          `productTracingCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT'溯源码全码', 
 *         `codeTailTag` varchar(32) NOT NULL COMMENT'溯源码尾号：使用UUID生成变长码，码长和产品数量有关', 
 *         `rowCount` varchar(32) NOT NULL COMMENT '单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount', 
 *         `createRowNum` varchar(32) NOT NULL COMMENT '分组生成的组号，单次生成不超过rowCount的，组号为1', 
 *         `createDate` datetime NOT NULL COMMENT '生成日期', 
 *         PRIMARY KEY (`productTracingCodeId`) ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='溯源码表';
 */
public class ProductTracingCode implements Serializable {

	private Integer productTracingCodeId;// 溯源码Id
	private Integer orderId;// 订单ID
	private Integer productId;// 产品ID
	private Integer vendorId;// 厂商ID
	private Integer rowCount;// 单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount
	private String productName;// 产品名
	private Integer codeFlag;// codeFlag:本版本中代表：1.防伪 2.溯源 ||已废弃 本版本中防伪码和溯源码彻底分开
	private String codePrefix;// 溯源码的前缀类似防伪码，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成
	private String productTracingCode;// 溯源码全码
	private Date createDate;// 生成日期
	private String codeTailTag;// 二维码尾号：使用UUID生成变长码，码长和产品数量有关
	private int createRowNum;// 分组生成的组号，单次生成不超过rowCount的，组号为1
	private int totalRowNum;//总共多少组
	private Integer boxCount;//箱码数
	private String boxShortCode;//箱码转换值
	public Integer getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}
	public String getBoxShortCode() {
		return boxShortCode;
	}
	public void setBoxShortCode(String boxShortCode) {
		this.boxShortCode = boxShortCode;
	}
	public int getTotalRowNum() {
		return totalRowNum;
	}
	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	public Integer getProductTracingCodeId() {
		return productTracingCodeId;
	}
	public void setProductTracingCodeId(Integer productTracingCodeId) {
		this.productTracingCodeId = productTracingCodeId;
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
	public Integer getCodeFlag() {
		return codeFlag;
	}
	public void setCodeFlag(Integer codeFlag) {
		this.codeFlag = codeFlag;
	}
	public String getCodePrefix() {
		return codePrefix;
	}
	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}
	public String getProductTracingCode() {
		return productTracingCode;
	}
	public void setProductTracingCode(String productTracingCode) {
		this.productTracingCode = productTracingCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCodeTailTag() {
		return codeTailTag;
	}
	public void setCodeTailTag(String codeTailTag) {
		this.codeTailTag = codeTailTag;
	}
	public int getCreateRowNum() {
		return createRowNum;
	}
	public void setCreateRowNum(int createRowNum) {
		this.createRowNum = createRowNum;
	}

}
