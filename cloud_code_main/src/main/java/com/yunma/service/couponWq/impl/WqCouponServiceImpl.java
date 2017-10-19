package com.yunma.service.couponWq.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.couponWq.WqCouponDao;
import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wq.WqCouponVendor;
import com.yunma.service.couponWq.WqCouponService;

@Service
public class WqCouponServiceImpl implements WqCouponService {
	
	@Resource
	private WqCouponDao wqCouponDao;
	
	@Override
	public int hasCoupon(Integer wqId) {
		return wqCouponDao.hasCoupon(wqId);
	}

	@Override
	public int addCoupon(WqCouponVendor coupon) {
		return wqCouponDao.addCoupon(coupon);
	}

	@Override
	public List<WqCouponVendor> getCouponByVendorId(Integer vendorId) {
		return wqCouponDao.getCouponByVendorId(vendorId);
	}
	
	@Override
	public int updateCoupon(WqCouponVendor coupon) {
		return wqCouponDao.updateCoupon(coupon);
	}

	@Override
	public List<Coupon> getCouponAll(Integer vendorId, String currTimeStr) {
		return wqCouponDao.getCouponAll(vendorId,currTimeStr);
	}
	
	@Override
	public List<Coupon> getCouponAllOther(Integer vendorId, String currTimeStr) {
		return wqCouponDao.getCouponAllOther(vendorId,currTimeStr);
	}

	@Override
	public String getCouponPorudctUrl(Integer vendorId) {
		return wqCouponDao.getCouponPorudctUrl(vendorId);
	}

}
