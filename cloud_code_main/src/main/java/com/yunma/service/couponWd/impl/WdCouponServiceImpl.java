package com.yunma.service.couponWd.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.dao.couponWd.WdCouponDao;
import com.yunma.dao.couponWechat.WeChatCouponDao;
import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.service.couponWd.WdCouponService;
import com.yunma.utils.PageBean;
import com.yunma.vo.wdCoupon.WdCouponVO;

@Service
public class WdCouponServiceImpl implements WdCouponService {

	@Resource
	private WdCouponDao wdCouponDao;
	
	@Resource
	private WeChatCouponDao weChatCouponDao;

	@Override
	public int addCoupon(WdCoupon wdCoupon) {
		
		return wdCouponDao.addCoupon(wdCoupon);
	}
	
	@Override
	public List<WdCoupon> getWdCouponListByVendor(Integer vendorId) {
		return wdCouponDao.getWdCouponListByVendor(vendorId);
	}
	
	@Override
	public List<Coupon> getWdCouponAll(Integer vendorId,String currTimeStr) {
		return wdCouponDao.getWdCouponAll(vendorId,currTimeStr);
	}
	
	@Override
	public List<Coupon> getWdCouponAllOther(Integer vendorId,String currTimeStr) {
		return wdCouponDao.getWdCouponAllOther(vendorId,currTimeStr);
	}

	@Override
	public PageBean getWdCouponInfo(PageBean pageBean, Integer vendorId)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = wdCouponDao.getCouponCount(vendorId);
		temp += wdCouponDao.getCouponWxCount(vendorId);
		
		WdCouponVO vo = new WdCouponVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("WdCouponVO", vo);
		map.put("vendorId", vendorId);
		List<WdCoupon> list = wdCouponDao.findCouponInfo(map);
		if (list != null && list.size() > 0) {
			for (WdCoupon wdCouponVO : list) {
				JSONObject object = new JSONObject();
				object.put("couponId", wdCouponVO.getCouponId());
				object.put("vendorId", wdCouponVO.getVendorId());
				object.put("stock", wdCouponVO.getStock());
				object.put("count", wdCouponVO.getCount());
				if (wdCouponVO.getBeginTime() != null) {
					object.put("beginTime", sdf.format(wdCouponVO.getBeginTime()));
				}
				if (wdCouponVO.getEndTime() != null) {
					object.put("endTime", sdf.format(wdCouponVO.getEndTime()));
				}
				object.put("name", wdCouponVO.getTitle());
				object.put("type", wdCouponVO.getType());
				object.put("reduce", wdCouponVO.getReduce());
				object.put("leastCost", wdCouponVO.getLeastCost());
				object.put("buyerLimit", wdCouponVO.getBuyerLimit());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public Coupon getCouponByCouponId(String couponId) {
		return wdCouponDao.getCouponByCouponId(couponId);
	}

	@Override
	public int hasCoupon(Integer vendorId) {
		return wdCouponDao.hasCoupon(vendorId);
	}

	@Override
	public int deleteCoupon(Integer id) {
		return wdCouponDao.deleteCoupon(id);
	}

	@Override
	public List<Coupon> getCouponAll(String vendorId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Coupon> list = new  ArrayList<Coupon>();
		if (vendorId != null) {
			Integer vendorId1 = Integer.parseInt(vendorId);
			list = wdCouponDao.getWdCouponAll(vendorId1, sdf.format(new Date()));
			list.addAll(weChatCouponDao.getWeChatCouponAll(vendorId1, sdf.format(new Date())));
		} else {
			list = wdCouponDao.getWdCouponAll(null, sdf.format(new Date()));
			list.addAll(weChatCouponDao.getWeChatCouponAll(null, sdf.format(new Date())));
		}
		return list;
	}

		
	
}
