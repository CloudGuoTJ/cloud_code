package com.yunma.controller.couponJD;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.order.OrderQueryJsfService.CouponDetail;
import com.jd.open.api.sdk.domain.order.OrderQueryJsfService.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderQueryJsfService.OrderSearchInfo;
import com.jd.open.api.sdk.request.order.PopOrderSearchRequest;
import com.jd.open.api.sdk.response.order.PopOrderSearchResponse;
import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDCouponOrder;
import com.yunma.entity.jdcoupon.JDCouponOrderRecord;
import com.yunma.entity.jdcoupon.JDVendorInfo;
import com.yunma.service.jdcoupon.JDCouponOrderRecordService;
import com.yunma.utils.JOSConfig;
@Controller
public class JDCouponOrderController {

	@Autowired
	private JDCouponOrderRecordService service;
	
	@RequestMapping("/GET/JDCouponOrder/getJDOrderInfo.do")
	@ResponseBody
	public JSONObject getJDOrderInfo(Integer vendorId,String startDate,String endDate){
		
		String startTime=startDate+" 00:00:00";
		String endTime=endDate+" 23:59:59";
		
		JDVendorInfo vendorInfo=service.getJDVendorInfo(vendorId);
		
		JdClient client=new DefaultJdClient(JOSConfig.SERVER_URL,vendorInfo.getAccess_token(),JOSConfig.appKey,JOSConfig.appSecret); 
		PopOrderSearchRequest request=new PopOrderSearchRequest();
		request.setStartDate(startTime);
		request.setEndDate(endTime);
		request.setOrderState("FINISHED_L");
		request.setOptionalFields("orderId,venderId," +
				"payType,orderTotal,orderTotalPrice," +
				"itemInfoList,orderSellerPrice," +
				"couponDetailList,orderinfo");
		request.setPage( "jingdong" );
		request.setPageSize("50");
		request.setSortType(1);
		request.setDateType(1);
		
		int orderTotal = 0;//订单总条数
		int curr_page = 0;//当前页
		
		List<JDCouponOrder> orders=new ArrayList<JDCouponOrder>();
		List<JDCouponCreateInfo> couponInfos=service.getJDCouponInTime(vendorId, startTime, endTime);
		try {
			
			do{
				curr_page++;
				
				PopOrderSearchResponse response=client.execute(request);
				
				orderTotal=response.getSearchorderinfoResult().getOrderTotal();
				
				List<OrderSearchInfo> results=response.getSearchorderinfoResult().getOrderInfoList();
				
				for (OrderSearchInfo orderInfo : results) {
					
					List<CouponDetail> coupons=orderInfo.getCouponDetailList();
					List<ItemInfo> items=orderInfo.getItemInfoList();
					
					JDCouponOrder order=new JDCouponOrder();
					order.setOrderId(orderInfo.getOrderId());
					order.setVendorId(vendorId);
					order.setOrderTotalPrice(orderInfo.getOrderTotalPrice());
					order.setOrderSellerPrice(orderInfo.getOrderSellerPrice());
					order.setOrderSource(orderInfo.getOrderSource());
					order.setOrderStartTime(orderInfo.getOrderStartTime());
					
					String skuName="";
					String jdPrice="";
					
					for (ItemInfo item : items) {
						if ("".equals(skuName)) {
							skuName=item.getSkuName();
							jdPrice=item.getJdPrice();
						}else{
							skuName+=","+item.getSkuName();
							jdPrice+=","+item.getJdPrice();
						}
					}
					
					order.setSkuName(skuName);
					order.setJdPrice(jdPrice);
					
					
					for (JDCouponCreateInfo info : couponInfos) {
						
						for (int i=0;i< coupons.size();i++) {
							
							Long couponPrice=info.getDiscount().longValue();
							System.out.println("优惠券面额：：：：："+couponPrice.toString());
							if (coupons.get(i).getCouponPrice().equals(couponPrice.toString())) {
								order.setCouponPrice(couponPrice.toString());
								order.setCouponType(coupons.get(i).getCouponType());
								
								service.addJDCouponOrderRecord(order);
								
							}
						}
						
						
					}
					
				}
				
				
			}while(curr_page*50 < orderTotal);
			
			
			
			List<JDCouponOrderRecord> res=service.getJDCouponOrderRecord(startTime, endTime, vendorId);
			
			JSONObject result=new JSONObject();
			JSONArray array=new JSONArray();
			
			for (JDCouponOrderRecord order : res) {
				JSONObject obj=new JSONObject();
				obj.put("orderId", order.getOrderId());
				obj.put("orderTotalPrice", order.getOrderTotalPrice());
				obj.put("orderSellerPrice", order.getOrderSellerPrice());
				obj.put("orderSource", order.getOrderSource());
				obj.put("couponPrice", order.getCouponPrice());
				obj.put("couponType", order.getCouponType());
				obj.put("orderStartTime", order.getOrderStartTime());
				obj.put("skuName", order.getSkuName());
				obj.put("jdPrice", order.getJdPrice());
				obj.put("couponId", order.getCouponId());
				obj.put("name", order.getName());
				
				array.add(obj);
			}
			
			result.put("data", array);
			return result;
			
			
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
