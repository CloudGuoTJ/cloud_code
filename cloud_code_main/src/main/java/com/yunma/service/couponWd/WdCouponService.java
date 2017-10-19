package com.yunma.service.couponWd;

import java.util.List;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.utils.PageBean;

public interface WdCouponService {
	/**
	 * 添加优惠券
	 * @param wdCoupon
	 * @return
	 */
	public int addCoupon(WdCoupon wdCoupon);
	
	/**
	 * 查询该厂商下的所有优惠券
	 * @param vendorId
	 * @return
	 */
	public List<WdCoupon> getWdCouponListByVendor(Integer vendorId);
	
	/**
	 * 分页显示优惠券
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	public PageBean getWdCouponInfo(PageBean pageBean,Integer vendorId);

	/**
	 * 获取自己的全部优惠券
	 * @param vendorId 
	 * @param currTimeStr 
	 * @return
	 */
	public List<Coupon> getWdCouponAll(Integer vendorId, String currTimeStr);

	/**
	 * 获取其他厂商的全部优惠券
	 * @param currTimeStr 
	 * @return
	 */
	public List<Coupon> getWdCouponAllOther(Integer vendorId, String currTimeStr);

	/**
	 * 根据优惠券id查询优惠券信息
	 * @param couponId
	 * @return
	 */
	public Coupon getCouponByCouponId(String couponId);

	/**
	 * 判断该厂商是否有优惠券
	 * @param vendorId
	 * @return
	 */
	public int hasCoupon(Integer vendorId);

	/**
	 * 删除优惠券
	 * @param p
	 */
	public int deleteCoupon(Integer id);

	/**
	 * 获取所有优惠券
	 * @param vendorId
	 * @return
	 */
	public List<Coupon> getCouponAll(String vendorId);
}
