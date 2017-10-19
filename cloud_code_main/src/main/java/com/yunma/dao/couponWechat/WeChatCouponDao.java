package com.yunma.dao.couponWechat;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wechat.WeChatCouponReceiveRecord;
import com.yunma.entity.coupon.wechat.WxCoupon;

@Repository("weChatCouponDao")
public interface WeChatCouponDao {
	
	/**
	 * 添加优惠券
	 * @param wxCoupon
	 * @return
	 */
	public int createCoupon(WxCoupon wxCoupon);

	/**
	 * 查询自己的所有优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getWeChatCouponAll(@Param("vendorId") Integer vendorId, @Param("currTimeStr") String currTimeStr);

	/**
	 * 查询其他厂商的所有优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAllOther(@Param("vendorId") Integer vendorId, @Param("currTimeStr") String currTimeStr);

	/**
	 * 根据id查询优惠券信息
	 * @param id
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
