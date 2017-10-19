package com.yunma.dao.jdcoupon;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunma.entity.jdcoupon.JDCouponOrder;
import com.yunma.entity.jdcoupon.JDCouponOrderRecord;

public interface JDCouponOrderRecordDao {
	
	/**
	 * 新增京东订单信息
	 * @param order
	 * @return
	 */
	int addJDCouponOrderRecord(JDCouponOrder order);
	
	/**
	 * 获取京东对账单
	 * @param startTime
	 * @param endTime
	 * @param vendorId
	 * @return
	 */
	List<JDCouponOrderRecord> getJDCouponOrderRecord(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("vendorId")Integer vendorId);

}
