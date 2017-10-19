package com.yunma.vo.product;

import java.io.Serializable;

public class GroupVo implements Serializable {

	private Integer rowId;		//id
	private Integer vendorId;	//商家id
	private String rowName;		//分组名
	private Integer rowPid;		//父节点id
	private Integer productNum;	//产品数
	private String type;		//1 代表一级 2代表二级 分类
	
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public Integer getRowPid() {
		return rowPid;
	}
	public void setRowPid(Integer rowPid) {
		this.rowPid = rowPid;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
