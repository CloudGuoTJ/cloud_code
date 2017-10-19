package com.yunma.service.couponWd;

import java.util.List;

import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wd.WdOrderCount;
import com.yunma.entity.coupon.wd.WdOrderRecord;
import com.yunma.utils.PageBean;

public interface WdCouponOrderRecordService {
	
	/**
	 * 新增订单记录
	 * @param wdOrderRecord
	 * @return
	 */
	public int addOrderRecord(WdOrderRecord wdOrderRecord);

	/**
	 * 获取订单记录
	 * @param order_id
	 * @param string 
	 * @return
	 */
	public WdOrderRecord getOrderRecord(String order_id, String order_type);
	
	/**
	 * 获取该厂商的所有订单
	 * @param vendorId
	 * @param statusOri
	 * @return
	 */
	public List<String> getOrderRecordAll(Integer vendorId,String statusOri);

	/**
	 * 判断该订单id是否存在
	 * @param order_id
	 * @param statusOri
	 * @return
	 */
	public int hasOrderRecord(String order_id, String statusOri);
	
	/**
	 * 获取该厂商的使用了优惠券的订单
	 * @param vendorId
	 * @return
	 */
	public List<WdOrderRecord> getCouponOrder(Integer vendorId);

	/**
	 * 获取该厂商有效的优惠券
	 * @param startTime
	 * @param endTime
	 * @param vendorId
	 * @return
	 */
	public List<WdCoupon> getValidCouponByVendor(String startTime, String endTime,
			Integer vendorId);
	
	/**
	 * 添加订单统计
	 * @param wdOrderCount
	 * @return
	 */
	public int addOrderCount(WdOrderCount wdOrderCount);

	/**
	 * 判断订单记录中是否存在
	 * @param orderId
	 * @return
	 */
	public int hasOrderCount(String orderId);

	/**
	 * 获取订单记录
	 * @param page
	 * @param time 
	 * @param vendorId 
	 * @param vendorId 
	 * @return
	 */
	public PageBean getOrderCount(PageBean page, Integer vendorId, String time);

	/**
	 * 根据订单id获取该订单的所有状态的订单记录
	 * @param orderId
	 * @return
	 */
	public List<WdOrderRecord> getOrderAllTypeById(String orderId);

	/**
	 * 更新订单状态
	 * @param orderRecord
	 * @return
	 */
	public int updateOrderRecord(WdOrderRecord orderRecord);

	/**
	 * 获取订单记录
	 * @param orderRecord
	 * @return
	 */
	public WdOrderRecord getOrderRecordByType(WdOrderRecord orderRecord);

	/**
	 * 更新订单统计表状态信息
	 * @param order
	 * @return
	 */
	public int updateOrderCount(WdOrderCount order);

	/**
	 * 获取厂商订单总信息
	 * @param pageBean
	 * @param time
	 * @return
	 */
	public PageBean getVendorOrder(PageBean pageBean, String time);
}
