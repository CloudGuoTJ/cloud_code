package com.yunma.dao.couponWd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wd.WdCoupon;

@Repository("wdCouponDao")
public interface WdCouponDao {
	/**
	 * 添加优惠券
	 * @param wdCoupon
	 * @return
	 */
	public int addCoupon (WdCoupon wdCoupon);
	/**
	 * 查询商家优惠券  
	 * @param vendorId
	 * @return
	 */
	public Integer getCouponCount (Integer vendorId);
	
	/**
	 * 查询商家优惠券（微信小店）
	 * @param vendorId
	 * @return
	 */
	public Integer getCouponWxCount(Integer vendorId);
	
	/**
	 * findCouponInfo
	 *查询优惠卷信息
	 * @param map
	 * @return
	 */
	public List<WdCoupon> findCouponInfo(Map<String, Object> map);
	
	/**
	 * 查询该厂商下的所有优惠券
	 * @param vendorId
	 * @return
	 */
	public List<WdCoupon> getWdCouponListByVendor(Integer vendorId);
	
	/**
	 * 获取自己的全部优惠券
	 * @param vendorId 
	 * @param currTimeStr 
	 * @return
	 */
	public List<Coupon> getWdCouponAll(@Param("vendorId")Integer vendorId, @Param("currTimeStr")String currTimeStr);
	
	/**
	 * 获取其他厂商的全部优惠券信息
	 * @param currTimeStr 
	 * @return
	 */
	public List<Coupon> getWdCouponAllOther(@Param("vendorId")Integer vendorId, @Param("currTimeStr")String currTimeStr);
	
	/**
	 * 根据优惠券id查询优惠券信息
	 * @param couponId
	 * @return
	 */
	public Coupon getCouponByCouponId(String couponId);
	
	/**
	 * 判断该厂商是否存在优惠券
	 * @param vendorId
	 * @return
	 */
	public int hasCoupon(Integer vendorId);
	/**
	 * 删除优惠券
	 * @param id
	 * @return
	 */
	public int deleteCoupon(Integer id);
}
