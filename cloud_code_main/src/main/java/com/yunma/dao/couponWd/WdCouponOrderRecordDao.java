package com.yunma.dao.couponWd;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wd.WdOrderCount;
import com.yunma.entity.coupon.wd.WdOrderRecord;
import com.yunma.utils.PageBean;
import com.yunma.vo.wdCoupon.WdOrderCountVo;

@Repository("wdCouponOrderRecordDao")
public interface WdCouponOrderRecordDao {

	/**
	 * 新增订单记录
	 * @param wdOrderRecord
	 * @return
	 */
	public int addOrderRecord(WdOrderRecord wdOrderRecord);

	/**
	 * 获取订单记录
	 * @param orderId
	 * @param order_type 
	 * @return
	 */
	public WdOrderRecord getOrderRecord(@Param("orderId") String orderId, @Param("order_type") String order_type);

	/**
	 * 获取该厂商的所有订单id
	 * @param vendorId
	 * @param statusOri
	 * @return
	 */
	public List<String> getOrderRecordAll(@Param("vendorId") Integer vendorId, @Param("statusOri") String statusOri);

	/**
	 * 判断该订单是否存在
	 * @param orderId
	 * @param statusOri
	 * @return
	 */
	public int hasOrderRecord(@Param("orderId") String orderId, @Param("statusOri") String statusOri);
	
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
	public List<WdCoupon> getValidCouponByVendor(@Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("vendorId") Integer vendorId);

	/**
	 * 添加订单记录
	 * @param wdOrderCount
	 * @return
	 */
	public int addOrderCount(WdOrderCount wdOrderCount);
	
	/**
	 * 判断订单记录中是否存在
	 * @param orderId
	 * @return
	 */
	public int hasOrderCount(@Param("orderId") String orderId);

	/**
	 * 获取订单记录的总条数
	 * @param time 
	 * @param vendorId 
	 * @return
	 */
	public int getOrderCount(@Param("vendorId") Integer vendorId, @Param("time") String time);

	/**
	 * 获取微店订单记录信息
	 * @param pageBean
	 * @param time 
	 * @param vendorId 
	 */
	public List<WdOrderCount> getOrderCountInfo(@Param ("pageBean")PageBean pageBean, @Param ("vendorId")Integer vendorId, @Param ("time") String time);

	/**
	 * 获取所有分页 的微店订单记录信息 中的 价格总和
	 * @param vendorId
	 * @param time
	 * @return
	 */
	public Double getOrderCountInfoSum(@Param ("vendorId")Integer vendorId, @Param ("time") String time);
	
	/**
	 * 根据订单id获取该订单的所有状态的订单记录
	 * @param orderId
	 * @return
	 */
	public List<WdOrderRecord> getOrderAllTypeById(@Param ("orderId") String orderId);

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
	public List<WdOrderCountVo> getVendorOrder(@Param ("pageBean") PageBean pageBean,@Param ("time") String time);
	
	/**
	 * 获取分页中所有 厂商订单总信息中的 订单数总计
	 * @param time
	 * @return
	 */
	public int getVendorOrderTotalCount(@Param ("time") String time);
	
	
	/**
	 * 统计 厂商订单总信息 条数
	 * @param time
	 * @return
	 */
	public int getVendorOrderCount(@Param ("time") String time);
	
	/**
	 * 统计 厂商订单总信息 的 实付合计
	 * @param time
	 * @return
	 */
	public List<WdOrderCountVo> getVendorOrderSum(@Param ("time") String time);
}
