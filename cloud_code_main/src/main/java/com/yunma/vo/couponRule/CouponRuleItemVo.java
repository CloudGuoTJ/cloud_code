package com.yunma.vo.couponRule;

import java.io.Serializable;

public class CouponRuleItemVo implements Serializable{

	private Integer id;
	private String itemName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
