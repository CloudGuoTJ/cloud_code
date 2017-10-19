package com.yunma.controller.couponWechat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.wxpay.AnalysisXML;
import com.common.wxpay.MoneyUtils;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.entity.coupon.wechat.WeChatCouponOrder;
import com.yunma.entity.coupon.wechat.WeChatCouponOrderCount;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.service.couponWechat.WeChatCouponReceiveRecordService;
import com.yunma.timer.WdConfigTimer;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.PageBean;
import com.yunma.utils.weChat.PayCommonUtil;
import com.yunma.vo.weChatCoupon.WeChatCouponReceiveRecordVo;

@Controller
@RequestMapping("/wechatcouponorder")
public class WeChatCouponOrderController {
	
	private Logger log = LoggerFactory.getLogger(WdConfigTimer.class);

	@Resource
	private WeChatCouponReceiveRecordService weChatCouponReceiveRecordService;
	
	@Resource
	private WeChatCouponConfigService weChatCouponConfigService;
	
	
	
	/**
	 * 微信小店对账
	 * @param startTime
	 * @param endTime
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getBillV2.do")
	@ResponseBody
	public JSONObject aaa(String startTime,String endTime,Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		WeChatCouponConfig  wxConfig = weChatCouponConfigService.getWeChatCouponConfig(vendorId);
		if (wxConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "请填写微信配置信息");
			return result;
		} else if ("".equals(wxConfig.getAppId()) || "".equals(wxConfig.getMchId())) {
			result.put("statuscode", -2);
			result.put("msg", "微信配置信息填写不完整");
			return result;
		}
		
		Calendar cal = Calendar.getInstance();  
		String startTimeStr = startTime.replace("-", "");
		String endTimeStr = endTime.replace("-", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = sdf.parse(startTimeStr);
			dEnd = sdf.parse(endTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WeChatCouponOrder> orderList = new ArrayList<WeChatCouponOrder>();
		
		List<Date> lDate = findDates(dBegin, dEnd);
		for (Date date : lDate) {
			
			String url = "https://api.mch.weixin.qq.com/pay/downloadbill";
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("appid",wxConfig.getAppId());
	        map.put("mch_id",wxConfig.getMchId());
	        map.put("bill_type","SUCCESS");
	        map.put("bill_date",sdf.format(date));//账单日期，只能查昨天以前的
	        map.put("nonce_str",MoneyUtils.buildRandom());
	        map.put("sign", MoneyUtils.createSign(wxConfig.getApiKey(),map));
	        String xml = MoneyUtils.createXML(map);
	        
	        HttpRequestUtil util = new HttpRequestUtil();
	        String data = util.sendPostWx(url, xml);
	        
	        if (!data.contains("FAIL")) {
	        	data = data.substring(data.indexOf("费率备注")+4,data.length());
				System.out.println(data);
				data = data.substring(0,data.indexOf(",`\r\n总交易单数"));
				
				String [] datas = data.split(" `");
				for (String string : datas) {
					string = string.replace("`", "");
					String [] data1 = string.split(",");
					
					int num = 19;
					
					int flag = data1.length/num;
					
					for (int i=0 ; i<flag ; i++) {
						WeChatCouponOrder order = new WeChatCouponOrder();
						if (i==0) {
							order.setPay_time_str(data1[0]);
							order.setAppid(data1[1]);
							order.setMchid(data1[2]);
							order.setSpecial_mchid(data1[3]);
							order.setDevice_id(data1[4]);
							order.setWx_order_id(data1[5]);
							order.setOrder_id(data1[6]);
							order.setOpenid(data1[7]);
							order.setPay_type(data1[8]);
							order.setPay_status(data1[9]);
							order.setPay_bank(data1[10]);
							order.setMoney_type(data1[11]);
							if (data1[12] != null && !"".equals(data1[12])) {
								order.setOrder_money(Double.valueOf(data1[12]).doubleValue());
							}
							if (data1[13] != null && !"".equals(data1[13])) 
								order.setCoupon_money(Double.valueOf(data1[13]).doubleValue());
							order.setGoods_name(data1[14]);
							order.setData_package(data1[15]);
							if (data1[16] != null && !"".equals(data1[16])) 
								order.setHandling_fee(Double.valueOf(data1[16]).doubleValue());
							order.setRate(data1[17]);
							order.setOrder_money_1(Double.valueOf(data1[18]).doubleValue());
						} else {
							order.setPay_time_str(data1[num*i+0]);
							order.setAppid(data1[num*i+1]);
							order.setMchid(data1[num*i+2]);
							order.setSpecial_mchid(data1[num*i+3]);
							order.setDevice_id(data1[num*i+4]);
							order.setWx_order_id(data1[num*i+5]);
							order.setOrder_id(data1[num*i+6]);
							order.setOpenid(data1[num*i+7]);
							order.setPay_type(data1[num*i+8]);
							order.setPay_status(data1[num*i+9]);
							order.setPay_bank(data1[num*i+10]);
							order.setMoney_type(data1[num*i+11]);
							if (data1[num*i+12] != null && !"".equals(data1[num*i+12])) {
								order.setOrder_money(Double.valueOf(data1[num*i+12]).doubleValue());
							}
							if (data1[num*i+13] != null && !"".equals(data1[num*i+13])) 
								order.setCoupon_money(Double.valueOf(data1[num*i+13]).doubleValue());
							order.setGoods_name(data1[num*i+14]);
							order.setData_package(data1[num*i+15]);
							if (data1[num*i+16] != null && !"".equals(data1[num*i+16])) 
								order.setHandling_fee(Double.valueOf(data1[num*i+16]).doubleValue());
							order.setRate(data1[num*i+17]);
							order.setOrder_money_1(Double.valueOf(data1[num*i+18]).doubleValue());
						}
						
						orderList.add(order);
						
						int count = weChatCouponReceiveRecordService.hasCouponOrder(order);
						if (count <= 0) {
							weChatCouponReceiveRecordService.addCouponOrder(order);
						}
					}
				}
	        }
		}
		
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		
		List<WxCoupon>  couponList = weChatCouponReceiveRecordService.getValidCoupon(vendorId, startTime, endTime);
		
		double sum = 0.00;
		
		for (WxCoupon wxCoupon : couponList) {
			for (WeChatCouponOrder order : orderList) {
				System.out.println("1:"+wxCoupon.getCoupon_value());
				System.out.println("2:"+order.getCoupon_money());
				String a = wxCoupon.getCoupon_value()+"";
				String b = order.getCoupon_money()+"";
				
				if (a.equals(b)) {
					JSONObject object = new JSONObject();
					object.put("orderId", order.getOrder_id());
					object.put("name", order.getGoods_name());
					object.put("couponId", wxCoupon.getId());
					object.put("couponName", wxCoupon.getCoupon_name());
					object.put("reduce", wxCoupon.getCoupon_value());
					object.put("leastCost", wxCoupon.getCoupon_mininumn());
					object.put("realMoney", order.getOrder_money_1());
					String time = order.getPay_time_str().replace("\r", "");
					time = time.replace("\n", "");
					object.put("payTime",time );
					object.put("price", order.getOrder_money_1());
					sum = sum+order.getOrder_money_1();
					array.add(object);
				}
			}
		}
		
		result.put("data", array);
		result.put("sum", sum);
		
		return result;
	}
	
	
	@RequestMapping("/update.do")
	@ResponseBody
	public String updateOrder() {
		
		String startTime = "2017-09-25";
		String endTime = "2017-09-26";
		
		List<WeChatCouponConfig>  wxList = weChatCouponConfigService.getWeChatCouponConfigAll();
		
		for (WeChatCouponConfig weChatCouponConfig : wxList) {
			if (!"".equals(weChatCouponConfig.getAppId()) && !"".equals(weChatCouponConfig.getSecret()) && !"".equals(weChatCouponConfig.getMchId()) && !"".equals(weChatCouponConfig.getApiKey()) && weChatCouponConfig.getVendorId() != null) {
				updateWxOrder(weChatCouponConfig.getVendorId(),startTime,endTime);
			}
		}
		return "success";
	}
	
	public void updateWxOrder(Integer vendorId,String startTime,String endTime) {

		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		WeChatCouponConfig  wxConfig = weChatCouponConfigService.getWeChatCouponConfig(vendorId);
		if (wxConfig == null) {
			log.debug("statuscode:-1");
			log.debug("msg:请填写微信配置信息");
		} else if ("".equals(wxConfig.getAppId()) || "".equals(wxConfig.getMchId())) {
			log.debug("statuscode:-2");
			log.debug("msg:微信配置信息填写不完整");
		}
		
		Calendar cal = Calendar.getInstance();  
		String startTimeStr = startTime.replace("-", "");
		String endTimeStr = endTime.replace("-", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = sdf.parse(startTimeStr);
			dEnd = sdf.parse(endTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WeChatCouponOrder> orderList = new ArrayList<WeChatCouponOrder>();
		
		List<Date> lDate = findDates(dBegin, dEnd);
		for (Date date : lDate) {
			
			String url = "https://api.mch.weixin.qq.com/pay/downloadbill";
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("appid",wxConfig.getAppId());
	        map.put("mch_id",wxConfig.getMchId());
	        map.put("bill_type","SUCCESS");
	        map.put("bill_date",sdf.format(date));//账单日期，只能查昨天以前的
	        map.put("nonce_str",MoneyUtils.buildRandom());
	        map.put("sign", MoneyUtils.createSign(wxConfig.getApiKey(),map));
	        String xml = MoneyUtils.createXML(map);
	        
	        HttpRequestUtil util = new HttpRequestUtil();
	        String data = util.sendPostWx(url, xml);
	        
	        if (!data.contains("FAIL")) {
	        	data = data.substring(data.indexOf("费率备注")+4,data.length());
				System.out.println(data);
				data = data.substring(0,data.indexOf(",`\r\n总交易单数"));
				
				String [] datas = data.split(" `");
				for (String string : datas) {
					string = string.replace("`", "");
					String [] data1 = string.split(",");
					
					int num = 19;
					
					int flag = data1.length/num;
					
					for (int i=0 ; i<flag ; i++) {
						WeChatCouponOrder order = new WeChatCouponOrder();
						if (i==0) {
							order.setPay_time_str(data1[0]);
							order.setAppid(data1[1]);
							order.setMchid(data1[2]);
							order.setSpecial_mchid(data1[3]);
							order.setDevice_id(data1[4]);
							order.setWx_order_id(data1[5]);
							order.setOrder_id(data1[6]);
							order.setOpenid(data1[7]);
							order.setPay_type(data1[8]);
							order.setPay_status(data1[9]);
							order.setPay_bank(data1[10]);
							order.setMoney_type(data1[11]);
							if (data1[12] != null && !"".equals(data1[12])) {
								order.setOrder_money(Double.valueOf(data1[12]).doubleValue());
							}
							if (data1[13] != null && !"".equals(data1[13])) 
								order.setCoupon_money(Double.valueOf(data1[13]).doubleValue());
							order.setGoods_name(data1[14]);
							order.setData_package(data1[15]);
							if (data1[16] != null && !"".equals(data1[16])) 
								order.setHandling_fee(Double.valueOf(data1[16]).doubleValue());
							order.setRate(data1[17]);
							order.setOrder_money_1(Double.valueOf(data1[18]).doubleValue());
						} else {
							order.setPay_time_str(data1[num*i+0]);
							order.setAppid(data1[num*i+1]);
							order.setMchid(data1[num*i+2]);
							order.setSpecial_mchid(data1[num*i+3]);
							order.setDevice_id(data1[num*i+4]);
							order.setWx_order_id(data1[num*i+5]);
							order.setOrder_id(data1[num*i+6]);
							order.setOpenid(data1[num*i+7]);
							order.setPay_type(data1[num*i+8]);
							order.setPay_status(data1[num*i+9]);
							order.setPay_bank(data1[num*i+10]);
							order.setMoney_type(data1[num*i+11]);
							if (data1[num*i+12] != null && !"".equals(data1[num*i+12])) {
								order.setOrder_money(Double.valueOf(data1[num*i+12]).doubleValue());
							}
							if (data1[num*i+13] != null && !"".equals(data1[num*i+13])) 
								order.setCoupon_money(Double.valueOf(data1[num*i+13]).doubleValue());
							order.setGoods_name(data1[num*i+14]);
							order.setData_package(data1[num*i+15]);
							if (data1[num*i+16] != null && !"".equals(data1[num*i+16])) 
								order.setHandling_fee(Double.valueOf(data1[num*i+16]).doubleValue());
							order.setRate(data1[num*i+17]);
							order.setOrder_money_1(Double.valueOf(data1[num*i+18]).doubleValue());
						}
						
						orderList.add(order);
						
						int count = weChatCouponReceiveRecordService.hasCouponOrder(order);
						if (count <= 0) {
							weChatCouponReceiveRecordService.addCouponOrder(order);
						}
					}
				}
	        }
		}
		
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		
		List<WxCoupon>  couponList = weChatCouponReceiveRecordService.getValidCoupon(vendorId, startTime, endTime);
		
		double sum = 0.00;
		
		for (WxCoupon wxCoupon : couponList) {
			for (WeChatCouponOrder order : orderList) {
				System.out.println("1:"+wxCoupon.getCoupon_value());
				System.out.println("2:"+order.getCoupon_money());
				String a = wxCoupon.getCoupon_value()+"";
				String b = order.getCoupon_money()+"";
				
				if (a.equals(b)) {
					JSONObject object = new JSONObject();
					object.put("orderId", order.getOrder_id());
					object.put("name", order.getGoods_name());
					object.put("couponId", wxCoupon.getId());
					object.put("couponName", wxCoupon.getCoupon_name());
					object.put("reduce", wxCoupon.getCoupon_value());
					object.put("leastCost", wxCoupon.getCoupon_mininumn());
					object.put("realMoney", order.getOrder_money_1());
					String time = order.getPay_time_str().replace("\r", "");
					time = time.replace("\n", "");
					object.put("payTime",time );
					object.put("price", order.getOrder_money_1());
					sum = sum+order.getOrder_money_1();
					array.add(object);
					
					WeChatCouponOrderCount count = new WeChatCouponOrderCount();
					count.setOrderId(order.getOrder_id());
					count.setVendorId(vendorId);
					count.setWxOrderId(order.getWx_order_id());
					count.setOpenId(order.getOpenid());
					count.setGoodsName(order.getGoods_name());
					count.setCouponId(wxCoupon.getId()+"");
					count.setCouponName(wxCoupon.getCoupon_name());
					count.setReduce(wxCoupon.getCoupon_value());
					count.setLeastCost(wxCoupon.getCoupon_mininumn());
					count.setOrder_money_1(order.getOrder_money_1());
					count.setPayTimeStr(time);
					
					int temp = weChatCouponReceiveRecordService.hasOrderCount(order.getWx_order_id());
					if (temp == 0) {
						weChatCouponReceiveRecordService.addOrderCount(count);
					}
					
				}
			}
		}
	}
	
	/**
	 * 分页获取微信订单记录
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getOrderCount.do")
	@ResponseBody
	public PageBean getOrderCount(PageBean pageBean) {
		return weChatCouponReceiveRecordService.getOrderCount(pageBean);
	}
	
	public static List<Date> findDates(Date dBegin, Date dEnd) {  
		  List lDate = new ArrayList();  
		  lDate.add(dBegin);  
		  Calendar calBegin = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calBegin.setTime(dBegin);  
		  Calendar calEnd = Calendar.getInstance();  
		  // 使用给定的 Date 设置此 Calendar 的时间  
		  calEnd.setTime(dEnd);  
		  // 测试此日期是否在指定日期之后  
		  while (dEnd.after(calBegin.getTime()))  
		  {  
		   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
		   calBegin.add(Calendar.DAY_OF_MONTH, 1);  
		   lDate.add(calBegin.getTime());  
		  }  
		  return lDate;  
		 } 

	
}
