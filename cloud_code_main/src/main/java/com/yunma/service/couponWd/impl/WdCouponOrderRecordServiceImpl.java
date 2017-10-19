package com.yunma.service.couponWd.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.dao.couponWd.WdCouponOrderRecordDao;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wd.WdOrderCount;
import com.yunma.entity.coupon.wd.WdOrderRecord;
import com.yunma.service.couponWd.WdCouponOrderRecordService;
import com.yunma.utils.PageBean;
import com.yunma.vo.wdCoupon.WdOrderCountVo;

@Service
public class WdCouponOrderRecordServiceImpl implements WdCouponOrderRecordService{
	
	@Resource
	private WdCouponOrderRecordDao wdCouponOrderRecordDao;

	@Override
	public int addOrderRecord(WdOrderRecord wdOrderRecord) {
		return wdCouponOrderRecordDao.addOrderRecord(wdOrderRecord);
	}

	@Override
	public WdOrderRecord getOrderRecord(String orderId, String order_type) {
		return wdCouponOrderRecordDao.getOrderRecord(orderId,order_type);
	}

	@Override
	public List<String> getOrderRecordAll(Integer vendorId, String statusOri) {
		return wdCouponOrderRecordDao.getOrderRecordAll(vendorId,statusOri);
	}
	
	@Override
	public int hasOrderRecord(String orderId,String statusOri) {
		return wdCouponOrderRecordDao.hasOrderRecord(orderId, statusOri);
	}

	@Override
	public List<WdOrderRecord> getCouponOrder(Integer vendorId) {
		return wdCouponOrderRecordDao.getCouponOrder(vendorId);
	}

	@Override
	public List<WdCoupon> getValidCouponByVendor(String startTime,
			String endTime, Integer vendorId) {
		return wdCouponOrderRecordDao.getValidCouponByVendor(startTime,
				endTime, vendorId);
	}

	@Override
	public int addOrderCount(WdOrderCount wdOrderCount) {
		return wdCouponOrderRecordDao.addOrderCount(wdOrderCount);
	}

	@Override
	public int hasOrderCount(String orderId) {
		return wdCouponOrderRecordDao.hasOrderCount(orderId);
	}

	@Override
	public PageBean getOrderCount(PageBean pageBean,Integer vendorId, String time) {
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		int count = wdCouponOrderRecordDao.getOrderCount(vendorId,time);
		pageBean.setTotalRecords(count);
		
		Double sum = 0.0;
		
		List<WdOrderCount> list = wdCouponOrderRecordDao.getOrderCountInfo(pageBean,vendorId,time);
		sum = wdCouponOrderRecordDao.getOrderCountInfoSum(vendorId,time);
		for (WdOrderCount order : list) {
			JSONObject object = new JSONObject();
			object.put("id", order.getId());
			object.put("vendorId", order.getVendorId());
			object.put("vendorName", order.getVendorName());
			object.put("orderId", order.getOrderId());
			object.put("couponId", order.getCouponId());
			object.put("couponName", order.getCouponName());
			object.put("reduce", order.getReduce());
			object.put("leastCost", order.getLeastCost());
			object.put("price", order.getPrice());
			object.put("total", order.getTotal());
			object.put("itemId", order.getItemId());
			object.put("itemName", order.getItemName());
			object.put("itemPrice", order.getItemPrice());
			object.put("itemQuantity", order.getItemQuantity());
			object.put("itemTotalPrice", order.getItemTotalPrice());
			object.put("payTime", order.getPayTime());
			object.put("updateTime", order.getUpdateTime());
			
			if ("0".equals(order.getIsRefund())) {
				if ("10".equals(order.getStatusOri()))
					object.put("status_ori", "待付款");
				else if ("20".equals(order.getStatusOri()))
					object.put("status_ori", "已付款，待发货");
				else if ("21".equals(order.getStatusOri()))
					object.put("status_ori", "部分付款");
				else if ("30".equals(order.getStatusOri()))
					object.put("status_ori", "已发货");
				else if ("31".equals(order.getStatusOri()))
					object.put("status_ori", "部分发货");
				else if ("40".equals(order.getStatusOri()))
					object.put("status_ori", "已确认收货");
				else if ("50".equals(order.getStatusOri()))
					object.put("status_ori", "已完成");
				else if ("60".equals(order.getStatusOri()))
					object.put("status_ori", "已关闭");
				
			} else if ("2".equals(order.getIsRefund())){
				object.put("status_ori", "退款成功");
			}
			
			resultArray.add(object);
		}
		result.put("data", resultArray);
		result.put("sum", sum);
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}

	@Override
	public List<WdOrderRecord> getOrderAllTypeById(String orderId) {
		return wdCouponOrderRecordDao.getOrderAllTypeById(orderId);
	}

	@Override
	public int updateOrderRecord(WdOrderRecord orderRecord) {
		return wdCouponOrderRecordDao.updateOrderRecord(orderRecord);
	}

	@Override
	public WdOrderRecord getOrderRecordByType(WdOrderRecord orderRecord) {
		return wdCouponOrderRecordDao.getOrderRecordByType(orderRecord);
	}

	@Override
	public int updateOrderCount(WdOrderCount order) {
		return wdCouponOrderRecordDao.updateOrderCount(order);
	}

	@Override
	public PageBean getVendorOrder(PageBean pageBean, String time) {
		
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		int count = wdCouponOrderRecordDao.getVendorOrderCount(time);
		pageBean.setTotalRecords(count);
		
		List<WdOrderCountVo> list = wdCouponOrderRecordDao.getVendorOrder(pageBean,time);
		
		List<WdOrderCountVo> listSum = wdCouponOrderRecordDao.getVendorOrderSum(time);
		
		int totalCount = wdCouponOrderRecordDao.getVendorOrderTotalCount(time);
		
		Double sum1 = 0.0;
		
		for (WdOrderCountVo sum : listSum) {
			sum1 += Double.valueOf(sum.getPaySum());
		}
		
		for (WdOrderCountVo order : list) {
			for (WdOrderCountVo sum : listSum) {
				if (order.getVendorId().equals(sum.getVendorId())) {
					JSONObject object = new JSONObject();
					object.put("vendorId", order.getVendorId());
					object.put("vendorName", order.getVendorName());
					object.put("payCount", order.getPayCount());
					object.put("paySum", sum.getPaySum());
					resultArray.add(object);
				}
			}
		}
		
		result.put("data", resultArray);
		result.put("count", totalCount);
		result.put("sum", sum1);
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}
	

}
