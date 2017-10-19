package com.yunma.service.jdcoupon;

import java.util.List;

import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDCouponOrder;
import com.yunma.entity.jdcoupon.JDCouponOrderRecord;
import com.yunma.entity.jdcoupon.JDVendorInfo;

public interface JDCouponOrderRecordService {
	
	/**
	 * 新增京东订单信息
	 * @param order
	 * @return
	 */
	int addJDCouponOrderRecord(JDCouponOrder order);
	
	/**
	 * 根据vendorId获取京东商家信息
	 * @param vendorId
	 * @return
	 */
	JDVendorInfo getJDVendorInfo(Integer vendorId);
	
	/**
	 * 查询在特定时间内的优惠券
	 * @param vendorId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<JDCouponCreateInfo> getJDCouponInTime(Integer vendorId,String startTime,String endTime);
	
	/**
	 * 获取京东对账单
	 * @param startTime
	 * @param endTime
	 * @param vendorId
	 * @return
	 */
	List<JDCouponOrderRecord> getJDCouponOrderRecord(String startTime,String endTime,Integer vendorId);
}
