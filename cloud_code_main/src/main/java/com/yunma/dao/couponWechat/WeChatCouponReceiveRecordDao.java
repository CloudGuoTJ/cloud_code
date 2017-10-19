package com.yunma.dao.couponWechat;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.wechat.WeChatCouponOrder;
import com.yunma.entity.coupon.wechat.WeChatCouponOrderCount;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.utils.PageBean;
import com.yunma.vo.weChatCoupon.WeChatCouponReceiveRecordVo;

@Repository("weChatCouponReceiveRecordDao")
public interface WeChatCouponReceiveRecordDao {

	/**
	 * 根据时间获取领取记录
	 * @param record
	 * @return
	 */
	List<WeChatCouponReceiveRecordVo> getRecordByTime(
			WeChatCouponReceiveRecordVo record);

	/**
	 * 判断该订单是否存在
	 * @param order
	 * @return
	 */
	public int hasCouponOrder(WeChatCouponOrder order);

	/**
	 * 添加优惠券订单记录
	 * @param order
	 * @return
	 */
	public int addCouponOrder(WeChatCouponOrder order);
	
	/**
	 * 获取有效的微信小店优惠券
	 * @param vendorId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WxCoupon> getValidCoupon(@Param("vendorId")Integer vendorId,@Param("startTime")String startTime,@Param("endTime")String endTime);

	/**
	 * 添加微信小店订单记录
	 * @param weChatCouponOrderCount
	 * @return
	 */
	public int addOrderCount(WeChatCouponOrderCount weChatCouponOrderCount);

	/**
	 * 判断微信订单记录中是否存在
	 * @param wxOrderId
	 * @return
	 */
	public int hasOrderCount(@Param("wxOrderId") String wxOrderId);

	/**
	 * 获取微信小店订单记录总条数
	 * @return
	 */
	public int getOrderCount();

	/**
	 * 获取微信小店订单记录信息
	 * @param pageBean
	 * @return
	 */
	public List<WeChatCouponOrderCount> getOrderCountInfo(@Param("pageBean") PageBean pageBean);
}
