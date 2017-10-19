package com.yunma.dao.couponWq;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wq.WqCouponVendor;

@Repository("wqCouponDao")
public interface WqCouponDao {

	/**
	 * 判断该优惠券是否存在
	 * @param wqId
	 * @return
	 */
	public int hasCoupon(@Param("wqId") Integer wqId);
	
	/**
	 * 添加微擎优惠券
	 * @param coupon
	 * @return
	 */
	public int addCoupon(WqCouponVendor coupon);

	/**
	 * 获取该厂商的优惠券
	 * @param vendorId
	 * @return
	 */
	public List<WqCouponVendor> getCouponByVendorId(@Param("vendorId") Integer vendorId);
	
	/**
	 * 更新优惠券信息
	 * @return
	 */
	public int updateCoupon(WqCouponVendor wqCouponVendor);

	/**
	 * 获取自己所有有效优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAll(@Param("vendorId") Integer vendorId, @Param("currTimeStr") String currTimeStr);

	/**
	 * 获取其他厂商的所有有效优惠券
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getCouponAllOther(@Param("vendorId") Integer vendorId, @Param("currTimeStr") String currTimeStr);

	/**
	 * 获取最新产品地址
	 * @param vendorId
	 * @return
	 */
	public String getCouponPorudctUrl(Integer vendorId);
}
