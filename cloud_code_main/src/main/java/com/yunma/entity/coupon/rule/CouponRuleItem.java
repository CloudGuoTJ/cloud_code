package com.yunma.entity.coupon.rule;

/**
 * 优惠券规则详情
 */
public class CouponRuleItem {

	private Integer id;
	private Integer ruleId;
	private String type; //1黑名单厂商id，2白名单厂商id，3全部，4产品id，5订单id 6默认规则
	private Integer itemId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
}
