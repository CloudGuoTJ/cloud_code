package com.yunma.service.couponWq;

import java.util.List;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wq.WqCouponVendor;

public interface WqCouponService {

	/**
	 * 判断该优惠券是否存在
	 * @param wqId
	 * @return
	 */
	public int hasCoupon(Integer wqId);
	
	/**
	 * 添加优惠券
	 * @param coupon
	 * @return
	 */
	public int addCoupon(WqCouponVendor coupon);

	/**
	 * 获取该厂商的优惠券
	 * @param vendorId
	 * @return
	 */
	public List<WqCouponVendor> getCouponByVendorId(Integer vendorId);

	/**
	 * 更新优惠券信息
	 * @param coupon
	 * @return
	 */
	public int updateCoupon(WqCouponVendor coupon);

	/**
	 * 获取自己所有的有效优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAll(Integer vendorId, String currTimeStr);

	/**
	 * 获取其他厂商的所有有效优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAllOther(Integer vendorId, String currTimeStr);

	/**
	 * 获取最新产品地址
	 * @param vendorId
	 * @return
	 */
	public String getCouponPorudctUrl(Integer vendorId);


}
