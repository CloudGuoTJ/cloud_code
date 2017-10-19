package com.yunma.service.jdcoupon.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunma.dao.jdcoupon.JDCouponDao;
import com.yunma.dao.jdcoupon.JDCouponOrderRecordDao;
import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDCouponOrder;
import com.yunma.entity.jdcoupon.JDCouponOrderRecord;
import com.yunma.entity.jdcoupon.JDVendorInfo;
import com.yunma.service.jdcoupon.JDCouponOrderRecordService;

@Service
public class JDCouponOrderRecordServiceImpl implements JDCouponOrderRecordService{
	
	@Autowired
	private JDCouponOrderRecordDao dao;
	@Autowired
	private JDCouponDao couponDao;
	
	@Override
	public int addJDCouponOrderRecord(JDCouponOrder order) {
		return dao.addJDCouponOrderRecord(order);
	}

	@Override
	public JDVendorInfo getJDVendorInfo(Integer vendorId) {
		return couponDao.getJDVendorInfoByVendorId(vendorId);
	}

	@Override
	public List<JDCouponCreateInfo> getJDCouponInTime(Integer vendorId,
			String startTime, String endTime) {
		return couponDao.getJDCouponInTime(vendorId, startTime, endTime);
	}

	@Override
	public List<JDCouponOrderRecord> getJDCouponOrderRecord(String startTime,
			String endTime, Integer vendorId) {
		return dao.getJDCouponOrderRecord(startTime, endTime, vendorId);
	}
	
	

}
