package com.yunma.service.couponWechat;

import java.util.List;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wechat.WeChatCouponReceiveRecord;
import com.yunma.entity.coupon.wechat.WxCoupon;

public interface WeChatCouponService {

	/**
	 * 添加优惠券
	 * @param wxCoupon
	 * @return
	 */
	public int createCoupon(WxCoupon wxCoupon);

	/**
	 * 查询自己所有优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getWeChatCouponAll(Integer vendorId, String currTimeStr);

	/**
	 * 查询其他厂商的优惠券信息
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAllOther(Integer vendorId, String currTimeStr);
	
	/**
	 * 根据id查询优惠券信息
	 * @param string
	 * @return
	 */
	public Coupon getCouponByCouponId(String id);
	
	/**
	 * 添加领取优惠券记录
	 * @param record
	 * @return
	 */
	public int addReceiveRecord(WeChatCouponReceiveRecord record);

}
