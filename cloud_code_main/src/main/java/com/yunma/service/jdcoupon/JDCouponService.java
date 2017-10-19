package com.yunma.service.jdcoupon;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDVendorInfo;

public interface JDCouponService {
	
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
	JSONObject getJDVendorInfoByVendorId(Integer vendorId);
	
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
	JSONObject addJDCouponinfo(JDCouponCreateInfo info);
	
	/**
	 * 根据卡券id查询优惠券信息
	 * @param couponId
	 * @param vendorId
	 * @return
	 */
	String getJDCouponInfo(String couponId);
	
	/**
	 * 分页获取优惠券
	 * @param vendorId
	 * @return
	 */
	JSONObject getJDCouponList(Integer vendorId);
	
	/**
	 * 根据vendorId获取京东商家信息
	 * @param vendorId
	 * @return
	 */
	JDVendorInfo getJDVendorInfo(Integer vendorId);
	
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
	 * 关闭优惠券
	 * @param couponId
	 * @return
	 */
	JSONObject deleteJDCoupon(Integer vendorId,Long couponId);
}
