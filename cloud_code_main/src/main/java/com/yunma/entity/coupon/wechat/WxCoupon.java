package com.yunma.entity.coupon.wechat;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信商户代金券
 * Created by Administrator on 2017/5/10.
 */
public class WxCoupon implements Serializable{
    private Integer id;
    private String appid;//公众号ID
    private String mch_id;//商户ID
    private String coupon_id;
    private String coupon_state;
    private String coupon_stock_id;//代金券批次id
    private String coupon_name;//代金券名称
    private Integer vendor_id;//厂商ID
    private Integer product_id;//指定可使用优惠券的商品ID（默认-1：全场可用）
    private Double coupon_value;//代金券面值
    private Double coupon_mininumn;//代金券使用最低限额
    private Integer coupon_type;//代金券类型：1-代金券无门槛，2-代金券有门槛互斥，3-代金券有门槛叠加
    private Integer coupon_stock_status; ;//批次状态： 1-未激活；2-审批中；4-已激活；8-已作废；16-中止发放
    private Integer coupon_total;//代金券数量
    private Integer max_quota;//代金券每个人最多能领取的数量, 如果为0，则表示没有限制
    private Integer used_num;//代金券已使用数量
    private Integer is_send_num;//代金券已经发送的数量
    private String err="";//错误信息
    private Date begin_time;//生效时间
    private Date end_time;//结束时间
    private Date createTime;//创建时间
    
	@Override
	public String toString() {
		return "WxCoupon [id=" + id + ", appid=" + appid + ", mch_id=" + mch_id
				+ ", coupon_id=" + coupon_id + ", coupon_state=" + coupon_state
				+ ", coupon_stock_id=" + coupon_stock_id + ", coupon_name="
				+ coupon_name + ", vendor_id=" + vendor_id + ", product_id="
				+ product_id + ", coupon_value=" + coupon_value
				+ ", coupon_mininumn=" + coupon_mininumn + ", coupon_type="
				+ coupon_type + ", coupon_stock_status=" + coupon_stock_status
				+ ", coupon_total=" + coupon_total + ", max_quota=" + max_quota
				+ ", used_num=" + used_num + ", is_send_num=" + is_send_num
				+ ", err=" + err + ", begin_time=" + begin_time + ", end_time="
				+ end_time + ", createTime=" + createTime + "]";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_state() {
		return coupon_state;
	}
	public void setCoupon_state(String coupon_state) {
		this.coupon_state = coupon_state;
	}
	public String getCoupon_stock_id() {
		return coupon_stock_id;
	}
	public void setCoupon_stock_id(String coupon_stock_id) {
		this.coupon_stock_id = coupon_stock_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public Integer getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Double getCoupon_value() {
		return coupon_value;
	}
	public void setCoupon_value(Double coupon_value) {
		this.coupon_value = coupon_value;
	}
	public Double getCoupon_mininumn() {
		return coupon_mininumn;
	}
	public void setCoupon_mininumn(Double coupon_mininumn) {
		this.coupon_mininumn = coupon_mininumn;
	}
	public Integer getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(Integer coupon_type) {
		this.coupon_type = coupon_type;
	}
	public Integer getCoupon_stock_status() {
		return coupon_stock_status;
	}
	public void setCoupon_stock_status(Integer coupon_stock_status) {
		this.coupon_stock_status = coupon_stock_status;
	}
	public Integer getCoupon_total() {
		return coupon_total;
	}
	public void setCoupon_total(Integer coupon_total) {
		this.coupon_total = coupon_total;
	}
	public Integer getMax_quota() {
		return max_quota;
	}
	public void setMax_quota(Integer max_quota) {
		this.max_quota = max_quota;
	}
	public Integer getUsed_num() {
		return used_num;
	}
	public void setUsed_num(Integer used_num) {
		this.used_num = used_num;
	}
	public Integer getIs_send_num() {
		return is_send_num;
	}
	public void setIs_send_num(Integer is_send_num) {
		this.is_send_num = is_send_num;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    

}
