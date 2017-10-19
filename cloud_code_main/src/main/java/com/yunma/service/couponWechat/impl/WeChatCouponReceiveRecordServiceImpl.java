package com.yunma.service.couponWechat.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.dao.couponWechat.WeChatCouponReceiveRecordDao;
import com.yunma.entity.coupon.wechat.WeChatCouponOrder;
import com.yunma.entity.coupon.wechat.WeChatCouponOrderCount;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.service.couponWechat.WeChatCouponReceiveRecordService;
import com.yunma.utils.PageBean;
import com.yunma.vo.weChatCoupon.WeChatCouponReceiveRecordVo;

@Service
public class WeChatCouponReceiveRecordServiceImpl implements WeChatCouponReceiveRecordService {

	@Resource
	private WeChatCouponReceiveRecordDao weChatCouponReceiveRecordDao;

	@Override
	public List<WeChatCouponReceiveRecordVo> getRecordByTime(
			WeChatCouponReceiveRecordVo record) {
		return weChatCouponReceiveRecordDao.getRecordByTime(record);
	}

	@Override
	public int hasCouponOrder(WeChatCouponOrder order) {
		// TODO Auto-generated method stub
		return weChatCouponReceiveRecordDao.hasCouponOrder(order);
	}

	@Override
	public int addCouponOrder(WeChatCouponOrder order) {
		// TODO Auto-generated method stub
		return weChatCouponReceiveRecordDao.addCouponOrder(order);
	}
	
	@Override
	public List<WxCoupon> getValidCoupon(Integer vendorId,String startTime,String endTime) {
		return weChatCouponReceiveRecordDao.getValidCoupon(vendorId, startTime, endTime);
	}
	
	@Override
	public int addOrderCount(WeChatCouponOrderCount weChatCouponOrderCount) {
		return weChatCouponReceiveRecordDao.addOrderCount(weChatCouponOrderCount);
	}

	@Override
	public int hasOrderCount(String wxOrderId) {
		return weChatCouponReceiveRecordDao.hasOrderCount(wxOrderId);
	}

	@Override
	public PageBean getOrderCount(PageBean pageBean) {
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		int count = weChatCouponReceiveRecordDao.getOrderCount();
		
		pageBean.setTotalRecords(count);
		
		List<WeChatCouponOrderCount> list = weChatCouponReceiveRecordDao.getOrderCountInfo(pageBean);
		
		for (WeChatCouponOrderCount order : list) {
			JSONObject object = new JSONObject();
			object.put("id", order.getId());
			object.put("vendorId", order.getVendorId());
			object.put("vendorName", order.getVendorName());
			object.put("orderId", order.getOrderId());
			object.put("wxOrderId", order.getWxOrderId());
			object.put("openId", order.getOpenId());
			object.put("goodsName", order.getGoodsName());
			object.put("couponId", order.getCouponId());
			object.put("couponName", order.getCouponName());
			object.put("reduce", order.getReduce());
			object.put("leastCost", order.getLeastCost());
			object.put("order_money", order.getOrder_money_1());
			object.put("payTime", order.getPayTime());
			object.put("updateTime", order.getUpdateTime());
			resultArray.add(object);
		}
		result.put("data", resultArray);
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}
}
