package com.yunma.service.couponWechat;

import java.util.List;

import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wechat.WeChatCouponOrder;
import com.yunma.entity.coupon.wechat.WeChatCouponOrderCount;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.utils.PageBean;
import com.yunma.vo.weChatCoupon.WeChatCouponReceiveRecordVo;

public interface WeChatCouponReceiveRecordService {

	/**
	 * 获取指定时间段的领取记录
	 * @param record
	 * @return
	 */
	public List<WeChatCouponReceiveRecordVo> getRecordByTime(WeChatCouponReceiveRecordVo record);
	
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
	public List<WxCoupon> getValidCoupon(Integer vendorId, String startTime,String endTime);

	/**
	 * 添加订单记录
	 * @param weChatCouponOrderCount
	 * @return
	 */
	public int addOrderCount(WeChatCouponOrderCount weChatCouponOrderCount);
	
	/**
	 * 判断微信订单记录是否重复
	 * @param wxOrderId
	 * @return
	 */
	public int hasOrderCount(String wxOrderId);

	/**
	 * 分页获取微信小店订单记录
	 * @param pageBean
	 * @return
	 */
	public PageBean getOrderCount(PageBean pageBean);

}
