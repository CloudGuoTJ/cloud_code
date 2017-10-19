package com.yunma.model;
/**
 * 管理元首页显示内容
 * @author Administrator
 *
 */
public class SysMapCountMainPage {
	
	private  Integer addDayOrder;//新增订单数
	
	private  Integer excepScanCount;//异常扫码数
	
	private  Integer sysScanCount;//总扫码数
	
	private  Integer orderCount;//订单数

	public Integer getAddDayOrder() {
		return addDayOrder;
	}

	public void setAddDayOrder(Integer addDayOrder) {
		this.addDayOrder = addDayOrder;
	}

	public Integer getExcepScanCount() {
		return excepScanCount;
	}

	public void setExcepScanCount(Integer excepScanCount) {
		this.excepScanCount = excepScanCount;
	}

	public Integer getSysScanCount() {
		return sysScanCount;
	}

	public void setSysScanCount(Integer sysScanCount) {
		this.sysScanCount = sysScanCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	
	

}
