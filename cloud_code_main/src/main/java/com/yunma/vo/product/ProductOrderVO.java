package com.yunma.vo.product;

import java.io.Serializable;
import java.sql.Date;

/**
 * productOrder信息 首先从缓存中获取信息,未获取直接读取数据库信息存入缓存 可以取代ProductOrder的model
 * 
 * @author Cloud_Goo
 * 
 */

/*
 * orderId, vendorId,vendorName,productId, productName,productCount,createDate,
 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where deleteFlag=0
 */

public class ProductOrderVO implements Serializable {
	private Integer orderId;// 订单ID
	private Integer vendorId;// 商家ID
	private String vendorName;// 商家名
	private Integer productId;// 产品ID
	private String productName;// 产品名字
	private Integer productCount;// 产品数量
	private Date createDate;// 创建日期
	private Integer status;// 订单状态 1、待审批，2、审批成功
	private Date lastUpdateTime;
	private int deleteFlog;// 删除标志
	private int isException;// 订单异常状态 0、正常，1、非正常
	private String perfixOrderNo;// 订单前缀
	private double referencePrice;// 参考价格默认为0
	private Date expiryDate; // 有效期默认为空
	private Integer tracingFlag;//生成溯源码状态
	private Integer exprotTracingCodeCount;//溯源码导出次数
	

	public Integer getTracingFlag() {
		return tracingFlag;
	}

	public void setTracingFlag(Integer tracingFlag) {
		this.tracingFlag = tracingFlag;
	}

	public Integer getExprotTracingCodeCount() {
		return exprotTracingCodeCount;
	}

	public void setExprotTracingCodeCount(Integer exprotTracingCodeCount) {
		this.exprotTracingCodeCount = exprotTracingCodeCount;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getDeleteFlog() {
		return deleteFlog;
	}

	public void setDeleteFlog(int deleteFlog) {
		this.deleteFlog = deleteFlog;
	}

	public int getIsException() {
		return isException;
	}

	public void setIsException(int isException) {
		this.isException = isException;
	}

	public String getPerfixOrderNo() {
		return perfixOrderNo;
	}

	public void setPerfixOrderNo(String perfixOrderNo) {
		this.perfixOrderNo = perfixOrderNo;
	}

	public double getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(double referencePrice) {
		this.referencePrice = referencePrice;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
