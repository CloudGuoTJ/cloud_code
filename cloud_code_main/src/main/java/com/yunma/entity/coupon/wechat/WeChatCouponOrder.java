package com.yunma.entity.coupon.wechat;

import java.util.Date;

public class WeChatCouponOrder {

	private Integer id;
	private Date pay_time;
	private String pay_time_str;
	private String appid;
	private String mchid;
	private String special_mchid;
	private String device_id;
	private String wx_order_id;
	private String order_id;
	private String openid;
	private String pay_type;
	private String pay_status;
	private String pay_bank;
	private String money_type;
	private Double order_money;
	private Double coupon_money;
	private String wx_refund_id;
	private String refund_id;
	private Double refund_money;
	private Double recharge_refund_coupon_money;
	private String refund_type;
	private String refund_status;
	private String goods_name;
	private String data_package;
	private Double handling_fee;
	private String rate;
	private Double order_money_1;
	
	@Override
	public String toString() {
		return "WeChatCouponOrder [id=" + id + ", pay_time=" + pay_time
				+ ", pay_time_str=" + pay_time_str + ", appid=" + appid
				+ ", mchid=" + mchid + ", special_mchid=" + special_mchid
				+ ", device_id=" + device_id + ", wx_order_id=" + wx_order_id
				+ ", order_id=" + order_id + ", openid=" + openid
				+ ", pay_type=" + pay_type + ", pay_status=" + pay_status
				+ ", pay_bank=" + pay_bank + ", money_type=" + money_type
				+ ", order_money=" + order_money + ", coupon_money="
				+ coupon_money + ", wx_refund_id=" + wx_refund_id
				+ ", refund_id=" + refund_id + ", refund_money=" + refund_money
				+ ", recharge_refund_coupon_money="
				+ recharge_refund_coupon_money + ", refund_type=" + refund_type
				+ ", refund_status=" + refund_status + ", goods_name="
				+ goods_name + ", data_package=" + data_package
				+ ", handling_fee=" + handling_fee + ", rate=" + rate + "]";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getPay_time_str() {
		return pay_time_str;
	}
	public void setPay_time_str(String pay_time_str) {
		this.pay_time_str = pay_time_str;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getSpecial_mchid() {
		return special_mchid;
	}
	public void setSpecial_mchid(String special_mchid) {
		this.special_mchid = special_mchid;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getWx_order_id() {
		return wx_order_id;
	}
	public void setWx_order_id(String wx_order_id) {
		this.wx_order_id = wx_order_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getPay_bank() {
		return pay_bank;
	}
	public void setPay_bank(String pay_bank) {
		this.pay_bank = pay_bank;
	}
	public String getMoney_type() {
		return money_type;
	}
	public void setMoney_type(String money_type) {
		this.money_type = money_type;
	}
	public Double getOrder_money() {
		return order_money;
	}
	public void setOrder_money(Double order_money) {
		this.order_money = order_money;
	}
	public Double getCoupon_money() {
		return coupon_money;
	}
	public void setCoupon_money(Double coupon_money) {
		this.coupon_money = coupon_money;
	}
	public String getWx_refund_id() {
		return wx_refund_id;
	}
	public void setWx_refund_id(String wx_refund_id) {
		this.wx_refund_id = wx_refund_id;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public Double getRefund_money() {
		return refund_money;
	}
	public void setRefund_money(Double refund_money) {
		this.refund_money = refund_money;
	}
	public Double getRecharge_refund_coupon_money() {
		return recharge_refund_coupon_money;
	}
	public void setRecharge_refund_coupon_money(Double recharge_refund_coupon_money) {
		this.recharge_refund_coupon_money = recharge_refund_coupon_money;
	}
	public String getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getData_package() {
		return data_package;
	}
	public void setData_package(String data_package) {
		this.data_package = data_package;
	}
	public Double getHandling_fee() {
		return handling_fee;
	}
	public void setHandling_fee(Double handling_fee) {
		this.handling_fee = handling_fee;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public Double getOrder_money_1() {
		return order_money_1;
	}
	public void setOrder_money_1(Double order_money_1) {
		this.order_money_1 = order_money_1;
	}
}
