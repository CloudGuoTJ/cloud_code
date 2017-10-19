package com.yunma.dao.jdcoupon;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDVendorInfo;

public interface JDCouponDao {
	
	/**
	 * 商户授权之后保存商户信息
	 * @param info
	 * @return
	 */
	int addJDUserInfo(JDVendorInfo info);
	
	/**
	 * 根据vendorId查询京东商户信息
	 * @param vendorId
	 * @return
	 */
	JDVendorInfo getJDVendorInfoByVendorId(Integer vendorId);
	
	/**
	 * 根据vendorId修改京东商户信息
	 * @param vendorId
	 * @return
	 */
	int updateJDVendorInfoByVendorId(JDVendorInfo info);
	
	/**
	 * 新建京东优惠券
	 * @param info
	 * @return
	 */
	int addJDCouponinfo(JDCouponCreateInfo info);
	
	/**
	 * 分页获取优惠券
	 * @param vendorId
	 * @return
	 */
	List<JDCouponCreateInfo> getJDCouponList(Integer vendorId);
	
	/**
	 * 根据vendorId查询京东商家信息
	 * @param vendorId
	 * @return
	 */
	JDVendorInfo getJDVendorInfo(Integer vendorId);
	
	/**
	 * 根据优惠券id查询优惠券信息
	 * @param couponId
	 * @return
	 */
	JDCouponCreateInfo getCouponInfoByCouponId(String couponId);
	
	/**
	 * 获取当前厂商下处于有效期的优惠券
	 * @param vendorId
	 * @return
	 */
	List<JDCouponCreateInfo> getJDCouponByVendorId(Integer vendorId);
	
	/**
	 * 获取其他厂商处于有效期的优惠券
	 * @param vendorId
	 * @return
	 */
	List<JDCouponCreateInfo> getJDCouponByVendorIdOther(Integer vendorId);
	
	/**
	 * 
	 * @return
	 */
	int updateCouponInfoStatus(String couponId);
	
	/**
	 * 查询在特定时间内的优惠券
	 * @return
	 */
	List<JDCouponCreateInfo> getJDCouponInTime(@Param("vendorId")Integer vendorId,@Param("startTime")String startTime,@Param("endTime")String endTime);
}
