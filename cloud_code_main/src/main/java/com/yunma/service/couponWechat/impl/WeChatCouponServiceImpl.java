package com.yunma.service.couponWechat.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.couponWechat.WeChatCouponDao;
import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wechat.WeChatCouponReceiveRecord;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.service.couponWechat.WeChatCouponService;

@Service
public class WeChatCouponServiceImpl implements WeChatCouponService {

	@Resource
	private WeChatCouponDao weChatCouponDao;
	
	@Override
	public int createCoupon(WxCoupon wxCoupon) {
		return weChatCouponDao.createCoupon(wxCoupon);
	}

	@Override
	public List<Coupon> getWeChatCouponAll(Integer vendorId, String currTimeStr) {
		return weChatCouponDao.getWeChatCouponAll(vendorId,currTimeStr);
	}

	@Override
	public List<Coupon> getCouponAllOther(Integer vendorId, String currTimeStr) {
		return weChatCouponDao.getCouponAllOther(vendorId,currTimeStr);
	}

	@Override
	public Coupon getCouponByCouponId(String id) {
		return weChatCouponDao.getCouponByCouponId(id);
	}

	@Override
	public int addReceiveRecord(WeChatCouponReceiveRecord record) {
		return weChatCouponDao.addReceiveRecord(record);
	}
}
