package com.yunma.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.wxpay.MoneyUtils;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wd.WdOrderCount;
import com.yunma.entity.coupon.wd.WdOrderRecord;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.entity.coupon.wechat.WeChatCouponOrder;
import com.yunma.entity.coupon.wechat.WeChatCouponOrderCount;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.service.couponWd.WdConfigService;
import com.yunma.service.couponWd.WdCouponOrderRecordService;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.service.couponWechat.WeChatCouponReceiveRecordService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.coupon.WdUtil;

public class WdConfigTimer {
	
	private Logger log = LoggerFactory.getLogger(WdConfigTimer.class);
	
	@Resource
	private WdConfigService wdConfigService;
	
	@Resource
	private WdCouponOrderRecordService wdCouponOrderRecordService;
	
	@Resource
	private WeChatCouponConfigService weChatCouponConfigService;
	
	@Resource
	private WeChatCouponReceiveRecordService weChatCouponReceiveRecordService;
	
	private Integer pageSize = 50;

	/**
	 * 每天凌晨1点定时更新厂商微店配置信息
	 */
	public void updateTimer() {
		List<WdConfig> list = wdConfigService.getWdConfigAll();
		if (list != null && list.size() > 0) {
			for (WdConfig wdConfig : list) {
				wdConfig.setAccessToken(WdUtil.getAccessToken(wdConfig));
				wdConfigService.updateWdConfig(wdConfig);
			}
			log.debug("更新厂商微店配置信息完毕");
		}
		
		List<WeChatCouponConfig>  wxList = weChatCouponConfigService.getWeChatCouponConfigAll();
		
		updateOrder(list,wxList);
		
	}
	
	public void updateOrder(List<WdConfig> wdList,List<WeChatCouponConfig> wxList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currTime = sdf.format(new Date());
		
		String startTime = getYesterdayTime(currTime,-2);
		String endTime = getYesterdayTime(currTime,-1);
		
		String time = getYesterdayTime(currTime,-1);
		String time15 = getYesterdayTime(currTime,-15);
		
		for (WdConfig wdConfig : wdList) {
			updateWdOrder(wdConfig.getVendorId(),time,time);
		}
		for (WdConfig wdConfig : wdList) {
			updateWdOrder(wdConfig.getVendorId(),time15,time15);
		}
		for (WeChatCouponConfig weChatCouponConfig : wxList) {
			if (!"".equals(weChatCouponConfig.getAppId()) && !"".equals(weChatCouponConfig.getSecret()) && !"".equals(weChatCouponConfig.getMchId()) && !"".equals(weChatCouponConfig.getApiKey()) && weChatCouponConfig.getVendorId() != null) {
				updateWxOrder(weChatCouponConfig.getVendorId(),startTime,endTime);
			}
		}
	}
	
	public void updateWdOrder(Integer vendorId,String startTime,String endTime) {

		JSONObject result = new JSONObject();
		
		String type = "5";
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			
		} else {
			JSONObject  _public = new JSONObject();
			_public.put("method", "vdian.order.list.get");
			_public.put("access_token", wdConfig.getAccessToken());
			_public.put("version", "1.2");
			
			JSONObject param = new JSONObject();
			if ("1".equals(type))
				param.put("order_type", "unship");
			else if ("2".equals(type))
				param.put("order_type", "unpay");
			else if ("3".equals(type))
				param.put("order_type", "shiped");
			else if ("4".equals(type))
				param.put("order_type", "refunding");
			else if ("5".equals(type))
				param.put("order_type", "finish");
			else if ("6".equals(type))
				param.put("order_type", "close");
			else if ("7".equals(type))
				param.put("order_type", "all");
			
			String startTimeStr = startTime + "%2000:00:00";
			String endTimeStr = endTime + "%2023:59:59";
			
			
			param.put("update_start", startTimeStr);
			param.put("update_end", endTimeStr);
			param.put("is_wei_order", "0");//0表示全部订单，1表示作为供货商的微订单，2表示普通订单
			param.put("page_size", pageSize);
			param.put("group_type", "0");//1不包含微团购未成团订单，0包含，默认是1
			
			
			List<WdOrderRecord> orderRecordList = new ArrayList<WdOrderRecord>();
			
			for (int i=0 ; i<5 ;i++) {
				
				int total_num = 0;//订单总条数
				int curr_page = 0;//当前页
				
				if (i == 0) {
					param.put("order_type", "unship");
				} else if (i == 1) {
					param.put("order_type", "shiped");
				} else if (i == 2) {
					param.put("order_type", "refunding");
				} else if (i == 3) {
					param.put("order_type", "finish");
				} else if (i == 4) {
					param.put("order_type", "close");
				}
				
				do {
					curr_page ++;
					param.put("page_num", curr_page);
					String url = "https://api.vdian.com/api?param="+param.toString()+"&public="+_public.toString();
					System.out.println(url);
					
					HttpRequestUtil util = new HttpRequestUtil();
					String resultStr = util.get(url, null);
					JSONObject resultJSON = JSONObject.parseObject(resultStr);
					
					String statusStr = resultJSON.getString("status");
					String resultStr1 = resultJSON.getString("result");
					JSONObject statusJSON = JSONObject.parseObject(statusStr);
					JSONObject result1SON1 = JSONObject.parseObject(resultStr1);
					
					String status_code = statusJSON.getString("status_code");
					String status_reason = statusJSON.getString("status_reason");
					
					if (!"0".equals(status_code) && !"".equals(status_reason)) {
						result.put("statuscode", status_code);
						result.put("msg", status_reason);
						break;
					}
					
					total_num = Integer.parseInt(result1SON1.getString("total_num"));//订单总条数
					
					String ordersStr = result1SON1.getString("orders");
					JSONArray ordersJSON = JSONArray.parseArray(ordersStr);
					
					JSONObject  _public1 = new JSONObject();
					_public1.put("method", "vdian.order.get");
					_public1.put("access_token", wdConfig.getAccessToken());
					_public1.put("version", "1.0");
					_public1.put("format", "json");
					
					JSONObject param1 = new JSONObject();
					
					for (Object object1 : ordersJSON) {
						JSONObject object = (JSONObject) object1;
						String order_id = object.getString("order_id");
						String order_status = object.getString("status");
						JSONObject object3 = new JSONObject();
						WdOrderRecord orderRecord = null;
						
						/*int temp = wdCouponOrderRecordService.hasOrderRecord(order_id, order_status);
						
						if (temp > 0) {
							orderRecord = wdCouponOrderRecordService.getOrderRecord(order_id, order_status);
							
							object3.put("orderId", orderRecord.getOrderId());
							object3.put("discountType", orderRecord.getDiscountType());
							object3.put("discountInfo", orderRecord.getDiscountInfo());
							object3.put("discountInfo", orderRecord.getDiscountPrice());
							object3.put("price", orderRecord.getPrice());
							object3.put("expressFee", orderRecord.getExpressFee());
							array.add(object3);
							
							orderRecordList.add(orderRecord);
							
						} else {*/
							param1.put("order_id", order_id);
							
							String url1 = "https://api.vdian.com/api?param="+param1.toString()+"&public="+_public1.toString();
							System.out.println(url1);
							
							String resultStr2 = util.get(url1,null);
							
							JSONObject statusJSON1 = JSONObject.parseObject(resultStr2);
							
							String statusStr1 = statusJSON1.getString("status");
							String resultStr3 = statusJSON1.getString("result");
							JSONObject statusJSON2 = JSONObject.parseObject(statusStr1);
							JSONObject resultJSON3 = JSONObject.parseObject(resultStr3);
							
							String status_codeStr1 = statusJSON2.getString("status_code");
							String status_reasonStr1 = statusJSON2.getString("status_reason");
							
							//失败
							if (!"0".equals(status_codeStr1) && !"".equals(status_reasonStr1)) {
								result.put("statuscode", status_code);
								result.put("msg", status_reason);
								break;
							}
							
							orderRecord = new WdOrderRecord();
							
							String price = resultJSON3.getString("price");
							String total = resultJSON3.getString("total");
							String expressFee = resultJSON3.getString("express_fee");
							String payTime = resultJSON3.getString("pay_time");
							String status = resultJSON3.getString("status");
							String statusOri = resultJSON3.getString("status_ori");
							String refund_status_ori = resultJSON3.getString("refund_status_ori");
							
							orderRecord.setOrderId(order_id);
							orderRecord.setVendorId(vendorId);
							orderRecord.setPrice(Double.valueOf(price).doubleValue());
							orderRecord.setTotal(Double.valueOf(total).doubleValue());
							orderRecord.setExpressFee(Double.valueOf(expressFee).doubleValue());
							orderRecord.setPayTime(payTime);
							orderRecord.setStatusOri(statusOri);
							
							
							
							String itemId = "";
							String itemName = "";
							String itemPrice = "";
							String itemQuantity = "";
							String itemTotalPrice = "";
							
							String items = resultJSON3.getString("items");//商品信息
							JSONArray items_list = JSONArray.parseArray(items);
							
							for (Object object2 : items_list) {
								JSONObject obj = (JSONObject) object2;
								if ("".equals(itemId)) {
									itemId = obj.getString("item_id");
									itemName = obj.getString("item_name");
									itemPrice = obj.getString("price");
									itemQuantity = obj.getString("quantity");
									itemTotalPrice = obj.getString("total_price");
								} else {
									itemId += ","+obj.getString("item_id");
									itemName += ","+obj.getString("item_name");
									itemPrice += ","+obj.getString("price");
									itemQuantity += ","+obj.getString("quantity");
									itemTotalPrice += ","+obj.getString("total_price");
								}
							}
							
							orderRecord.setItemId(itemId);
							orderRecord.setItemName(itemName);
							orderRecord.setItemPrice(itemPrice);
							orderRecord.setItemQuantity(itemQuantity);
							orderRecord.setItemTotalPrice(itemTotalPrice);
							
							String discount_listStr = resultJSON3.getString("discount_list");
							JSONArray discount_listArray = JSONArray.parseArray(discount_listStr);
							
							for (Object object2 : discount_listArray) {
								JSONObject obj = (JSONObject) object2;
								orderRecord.setDiscountType(obj.getString("discount_type"));
								orderRecord.setDiscountInfo(obj.getString("discount_info"));
								orderRecord.setDiscountPrice(obj.getString("discount_price"));
							}
							
							orderRecordList.add(orderRecord);
							
							int temp = wdCouponOrderRecordService.hasOrderRecord(order_id, order_status);
							
							
							if (temp == 0) {
								
								if ("2".equals(refund_status_ori)) {//判断是否退款
									orderRecord.setIsRefund("2");
								}
								
								wdCouponOrderRecordService.addOrderRecord(orderRecord);
								
							} else {
								
								if ("2".equals(refund_status_ori)) {//判断是否退款
									
									WdOrderRecord record = wdCouponOrderRecordService.getOrderRecordByType(orderRecord);
									orderRecord.setId(record.getId());
									orderRecord.setIsRefund(refund_status_ori);
									wdCouponOrderRecordService.updateOrderRecord(orderRecord);
								}
							}
							
							orderRecordList.add(orderRecord);
					}
						
					log.debug("resultJSON:"+resultJSON);
				
				} while (curr_page * pageSize < total_num); 
			}
			
			
			List<WdCoupon> dbList = wdCouponOrderRecordService.getValidCouponByVendor(startTime,endTime,vendorId);
			
			Double sum = 0.0;
			
			for (WdOrderRecord record : orderRecordList) {
				for (WdCoupon wdCoupon : dbList) {
					if (record != null) {
						if (record.getDiscountPrice() != null && !"".equals(record.getDiscountPrice())) {
							String price = record.getDiscountPrice().replace("-","");
							price = price.replace("￥", "");
							try {
								price = price.substring(0, price.indexOf("."));
							} catch (StringIndexOutOfBoundsException e) {
								//没有小数点
							}
							Integer price1 = Integer.parseInt(price);
							if (wdCoupon.getReduce() == price1) {
								JSONObject object = new JSONObject();
								object.put("orderId", record.getOrderId());
								object.put("name", wdCoupon.getTitle());
								object.put("reduce", wdCoupon.getReduce());
								object.put("leastCost", wdCoupon.getLeastCost());
								object.put("price", record.getPrice());
								object.put("payTime", record.getPayTime().replace(".0", ""));
	
								WdOrderCount order = new WdOrderCount();
								order.setVendorId(vendorId);
								order.setOrderId(record.getOrderId());
								order.setCouponId(wdCoupon.getCouponId());
								order.setCouponName(wdCoupon.getTitle());
								order.setReduce(wdCoupon.getReduce() + "");
								order.setLeastCost(wdCoupon.getLeastCost() + "");
								order.setPrice(record.getPrice());
								order.setTotal(record.getTotal());
								order.setItemId(record.getItemId());
								order.setItemName(record.getItemName());
								order.setItemPrice(record.getItemPrice());
								order.setItemQuantity(record.getItemQuantity());
								order.setItemTotalPrice(record.getItemTotalPrice());
								order.setPayTime(record.getPayTime());
								order.setStatusOri(record.getStatusOri());
								if ("0".equals(record.getIsRefund())) {
									order.setIsRefund("0");
								} else if ("2".equals(record.getIsRefund())){
									order.setIsRefund(record.getIsRefund());
								}
								
	
								int temp = wdCouponOrderRecordService.hasOrderCount(record.getOrderId());
								if (temp == 0) {
									wdCouponOrderRecordService.addOrderCount(order);
								} else {
									wdCouponOrderRecordService.updateOrderCount(order);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void updateWxOrder(Integer vendorId,String startTime,String endTime) {

		WeChatCouponConfig  wxConfig = weChatCouponConfigService.getWeChatCouponConfig(vendorId);
		if (wxConfig == null) {
			log.debug("statuscode:-1");
			log.debug("msg:请填写微信配置信息");
		} else if ("".equals(wxConfig.getAppId()) || "".equals(wxConfig.getMchId())) {
			log.debug("statuscode:-2");
			log.debug("msg:微信配置信息填写不完整");
		} else {
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
						/*JSONObject object = new JSONObject();
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
						sum = sum+order.getOrder_money_1();*/
						
						WeChatCouponOrderCount count = new WeChatCouponOrderCount();
						count.setVendorId(vendorId);
						count.setOrderId(order.getOrder_id());
						count.setWxOrderId(order.getWx_order_id());
						count.setOpenId(order.getOpenid());
						count.setGoodsName(order.getGoods_name());
						count.setCouponId(wxCoupon.getId()+"");
						count.setCouponName(wxCoupon.getCoupon_name());
						count.setReduce(wxCoupon.getCoupon_value());
						count.setLeastCost(wxCoupon.getCoupon_mininumn());
						count.setOrder_money_1(order.getOrder_money_1());
						String time = order.getPay_time_str().replace("\r", "");
						time = time.replace("\n", "");
						count.setPayTimeStr(time);
						
						int temp = weChatCouponReceiveRecordService.hasOrderCount(order.getWx_order_id());
						if (temp == 0) {
							weChatCouponReceiveRecordService.addOrderCount(count);
						}
						
					}
				}
			}
			log.debug("msg:定时器，更新微信小店订单记录完毕");
		}
		
	}
	
	
	public static String getYesterdayTime(String specifiedDay,Integer limit) {
		Calendar c = Calendar.getInstance(); 
		Date date=null;
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+limit); 
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayBefore;
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
