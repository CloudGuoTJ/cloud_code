package com.yunma.controller.couponWd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wd.WdOrderCount;
import com.yunma.entity.coupon.wd.WdOrderRecord;
import com.yunma.service.couponWd.WdConfigService;
import com.yunma.service.couponWd.WdCouponOrderRecordService;
import com.yunma.service.couponWd.WdCouponService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.PageBean;

@Controller
@RequestMapping("/wdcouponorder")
public class WdCouponOrderController {
	
	@Resource
	private WdCouponService wdCouponService;
	
	@Resource
	private WdConfigService wdConfigService;
	
	@Resource
	private WdCouponOrderRecordService wdCouponOrderRecordService;
	
	private org.slf4j.Logger log = LoggerFactory.getLogger(WdCouponOrderController.class);
	
	private Integer pageSize = 50;
	
	public static void main(String[] args) throws ParseException {
		/*JSONObject  _public1 = new JSONObject();
		
		_public1.put("method", "vdian.order.get");
		_public1.put("access_token", "2d369f48bb56ad5892db19315900d415000738337b");
		_public1.put("version", "1.0");
		_public1.put("format", "json");
		
		JSONObject param1 = new JSONObject();
		param1.put("order_id", "800240285924690");
		
		String url1 = "https://api.vdian.com/api?param="+param1.toString()+"&public="+_public1.toString();
		System.out.println(url1);
		
		String resultStr2 = HttpRequestUtil.get(url1,null);
		System.out.println(resultStr2);*/
		
		/*JSONObject  _public1 = new JSONObject();
		
		_public1.put("method", "vdian.order.get");
		_public1.put("access_token", "c12ba24a04339da6e7292e5c5b86cb4900073821b4");
		_public1.put("version", "1.0");
		_public1.put("format", "json");
		
		JSONObject param1 = new JSONObject();
		param1.put("order_id", "800224231374251");
		
		String url1 = "https://api.vdian.com/api?param="+param1.toString()+"&public="+_public1.toString();
		System.out.println(url1);
		
		String resultStr2 = HttpRequestUtil.get(url1,null);
		System.out.println(resultStr2);*/
		
		String param = "SELECT * FROM ims_ewei_shop_coupon WHERE id = 7";
		param = param.replaceAll(" ", "%20");
		
		System.out.println(param);
		
		String url = "http://mp.ym-a.top/api.php?appid=wxf8b4f85f3a794e77&&func=wqSql&sql="+param;
		String result = HttpRequestUtil.sendGet(url,null);
		JSONArray array = JSONArray.parseArray(result);
		
		for (int i=0 ; i<array.size() ; i++) {
			String objectStr = array.getString(i);
			JSONObject object = JSONObject.parseObject(objectStr);
			String couponname = object.getString("couponname");
			String deduct = object.getString("deduct");
			String enough = object.getString("enough");
			String time = object.getString("createtime");
			String timestart = object.getString("timestart");
			String timeend = object.getString("timeend");
			String total = object.getString("total");
			String getmax = object.getString("getmax");
			String limitdiscounttype = object.getString("limitdiscounttype");
			
			
			
			System.out.println(unicodeToString(couponname));
			System.out.println(deduct);
			System.out.println(enough);
			System.out.println(parseTimeStamp(Long.parseLong(time)));
			System.out.println(parseTimeStamp(Long.parseLong(timestart)));
			System.out.println(parseTimeStamp(Long.parseLong(timeend)));
			System.out.println(total);
			System.out.println(getmax);
			System.out.println(limitdiscounttype);
			
		}
		
	}
	
	public static String parseTimeStamp(final long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long s = timeStamp*1000l;
		Long time = new Long(s);
	    String d = format.format(time);  
	    return d;
	}
	
	//Unicode转中文  
	public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch+"" );

        }

        return str;

    }
	
	/**
	 * 获取账单
	 * @param vendorId 厂商id
	 * @param type 订单类型 (1) unship：待发货；(2) unpay：待付款；(3) shiped：已发货；(4) refunding：退款中；(5) finish：已完成；(6) close：已关闭；(7) all：全部类型
	 * @param startTime 订单更新开始时间 如：2014-11-12 16:36:08精确到秒
	 * @param endTime 订单更新结束时间 如：2014-11-12 16:36:08精确到秒
	 * @return
	 */
	@RequestMapping("/getBill.do")
	@ResponseBody
	public JSONObject getBill(Integer vendorId,String type,String startTime,String endTime) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		//com.yunma.utils.HttpRequestUtil
		
		Level level = null;
		Logger loggerPri = null;
		
		level = Level.toLevel(Priority.INFO_INT, Level.INFO);
		loggerPri = LogManager
				.getLogger("java.net.URLConnection");
		loggerPri.setLevel(level);
		
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			return result;
		}
		
		JSONObject  _public = new JSONObject();
		_public.put("method", "vdian.order.list.get");
		_public.put("access_token", wdConfig.getAccessToken());
		_public.put("version", "1.2");
		
		JSONObject param = new JSONObject();
		/*if ("1".equals(type))
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
			param.put("order_type", "all");*/
		
		String startTimeStr = startTime + "%2000:00:00";
		String endTimeStr = endTime + "%2023:59:59";
		
		
		param.put("update_start", startTimeStr);
		param.put("update_end", endTimeStr);
		param.put("is_wei_order", "0");//0表示全部订单，1表示作为供货商的微订单，2表示普通订单
		param.put("page_size", pageSize);
		param.put("group_type", "0");//1不包含微团购未成团订单，0包含，默认是1
		
		List<WdOrderRecord> orderRecordList = new ArrayList<WdOrderRecord>();
		
		//List<String> dbOrderList = wdCouponOrderRecordService.getOrderRecordAll(vendorId, param.getString("order_type"));
		
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
					return result;
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
					
					int temp = wdCouponOrderRecordService.hasOrderRecord(order_id, order_status);
					
					if (temp > 0) {
						orderRecord = wdCouponOrderRecordService.getOrderRecord(order_id, order_status);
						
						/*object3.put("orderId", orderRecord.getOrderId());
						object3.put("discountType", orderRecord.getDiscountType());
						object3.put("discountInfo", orderRecord.getDiscountInfo());
						object3.put("discountInfo", orderRecord.getDiscountPrice());
						object3.put("price", orderRecord.getPrice());
						object3.put("expressFee", orderRecord.getExpressFee());
						array.add(object3);*/
						
						orderRecordList.add(orderRecord);
						
					} else {
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
							return result;
						}
						
						orderRecord = new WdOrderRecord();
						
						String price = resultJSON3.getString("price");
						String total = resultJSON3.getString("total");
						String expressFee = resultJSON3.getString("express_fee");
						String payTime = resultJSON3.getString("pay_time");
						String status = resultJSON3.getString("status");
						String statusOri = resultJSON3.getString("status_ori");
						String refund_status_ori = resultJSON3.getString("refund_status_ori");
						
						if ("2".equals(refund_status_ori)) {//判断是否退款
							orderRecord.setIsRefund("2");
						} else {
							orderRecord.setIsRefund("0");
						}
						
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
						
						wdCouponOrderRecordService.addOrderRecord(orderRecord);
						
					}
					
				}
					
				log.debug("resultJSON:"+resultJSON);
			
			} while (curr_page * pageSize < total_num); 
	}
		
		
		
		
		
		/*result = resultJSON;
		
		pageBean.setResult(result);*/
		
		level = Level.toLevel(Priority.DEBUG_INT, Level.DEBUG);
		loggerPri = LogManager
				.getLogger("java.net.URLConnection");
		loggerPri.setLevel(level);
		
		/*List<WdOrderRecord> list = wdCouponOrderRecordService.getCouponOrder(vendorId);*/
		
		List<WdCoupon> dbList = wdCouponOrderRecordService.getValidCouponByVendor(startTime,endTime,vendorId);
		
		/*List<WdCoupon> dbList = wdCouponService.getWdCouponListByVendor(vendorId);*/
		
		Double sum = 0.0;
		
		for (WdOrderRecord record : orderRecordList) {
			if (record != null) {
					for (WdCoupon wdCoupon : dbList) {
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
								object.put("couponId", wdCoupon.getCouponId());
								object.put("price", record.getPrice());
								object.put("payTime",
										record.getPayTime().replace(".0", ""));
								
								object.put("status", record.getStatusOri());
								
								JSONArray array1 = new JSONArray();
								String[] itemId = record.getItemId().split(",");
								String[] itemName = record.getItemName().split(",");
								String[] itemPrice = record.getItemPrice().split(
										",");
								String[] itemQuantity = record.getItemQuantity()
										.split(",");
								String[] itemTotalPrice = record
										.getItemTotalPrice().split(",");
								for (int i = 0; i < itemId.length; i++) {
									JSONObject obj = new JSONObject();
									obj.put("item_id", itemId[i]);
									obj.put("item_name", itemName[i]);
									obj.put("item_price", itemPrice[i]);
									obj.put("item_quantity", itemQuantity[i]);
									obj.put("item_total_price", itemTotalPrice[i]);
									array1.add(obj);
								}
	
								object.put("items", array1);
	
								array.add(object);
								
								if ("0".equals(record.getIsRefund())) {
									if ("10".equals(record.getStatusOri()))
										object.put("status_ori", "待付款");
									else if ("20".equals(record.getStatusOri()))
										object.put("status_ori", "已付款，待发货");
									else if ("21".equals(record.getStatusOri()))
										object.put("status_ori", "部分付款");
									else if ("30".equals(record.getStatusOri()))
										object.put("status_ori", "已发货");
									else if ("31".equals(record.getStatusOri()))
										object.put("status_ori", "部分发货");
									else if ("40".equals(record.getStatusOri()))
										object.put("status_ori", "已确认收货");
									else if ("50".equals(record.getStatusOri()))
										object.put("status_ori", "已完成");
									else if ("60".equals(record.getStatusOri()))
										object.put("status_ori", "已关闭");
									
									sum += record.getPrice();
									
								} else if ("2".equals(record.getIsRefund())){
									object.put("status_ori", "退款成功");
								}
							}
						}
					}
			}
		}
		
		result.put("sum", sum);
		result.put("data", array);
		
		return result;
	}
	
	/**
	 * 获取厂商账单记录
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/getVendorOrder.do")
	@ResponseBody
	public PageBean getVendorOrder(PageBean pageBean,String time) {
		
		return wdCouponOrderRecordService.getVendorOrder(pageBean,time);
		
	}
	
	/**
	 * 分页获取订单记录
	 * @param page
	 * @return
	 */
	@RequestMapping("/getOrderCount.do")
	@ResponseBody
	public PageBean getOrderCount(PageBean page,Integer vendorId,String time) {
		return wdCouponOrderRecordService.getOrderCount(page,vendorId,time);
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
	
	@RequestMapping("/test.do")
	@ResponseBody
	public String test() {
		List<WdConfig> list = wdConfigService.getWdConfigAll();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currTime = sdf.format(new Date());
		
		/*String startTime = getYesterdayTime(currTime,-2);
		String endTime = getYesterdayTime(currTime,-1);*/
		String startTime = "2017-10-11";
		String endTime = "2017-10-12";
		/*for (WdConfig wdConfig : list) {
			updateWdOrder(wdConfig.getVendorId(),startTime,endTime);
		}*/
		
		updateWdOrder1(8,startTime,endTime);
		
		/*for (WdConfig wdConfig : list) {
			updateWdOrder1(8,startTime,endTime);
		}*/
		return "success";
	}
	
	public void updateWdOrder1(Integer vendorId,String startTime,String endTime) {
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
			JSONObject  _public = new JSONObject();
			_public.put("method", "vdian.order.list.get");
			_public.put("access_token", wdConfig.getAccessToken());
			_public.put("version", "1.2");
			
			JSONObject param = new JSONObject();
			
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
							String price = record.getDiscountPrice().replace("-", "");
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
								object.put("payTime", record.getPayTime()
										.replace(".0", ""));

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
								order.setItemTotalPrice(record
										.getItemTotalPrice());
								order.setPayTime(record.getPayTime());
								order.setStatusOri(record.getStatusOri());
								order.setIsRefund(record.getIsRefund());

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
			
			int total_num = 0;//订单总条数
			int curr_page = 0;//当前页
			
			List<WdOrderRecord> orderRecordList = new ArrayList<WdOrderRecord>();
			
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
				} else {
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
						JSONObject object3 = new JSONObject();
						WdOrderRecord orderRecord = null;
						
						int temp = wdCouponOrderRecordService.hasOrderRecord(order_id, param.getString("order_type"));
						
						if (temp > 0) {
							orderRecord = wdCouponOrderRecordService.getOrderRecord(order_id, param.getString("order_type"));
							
							orderRecordList.add(orderRecord);
							
						} else {
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
							} else {
								orderRecord = new WdOrderRecord();
								
								String price = resultJSON3.getString("price");
								String total = resultJSON3.getString("total");
								String expressFee = resultJSON3.getString("express_fee");
								String payTime = resultJSON3.getString("pay_time");
								String status_ori = resultJSON3.getString("status_ori");
								
								orderRecord.setOrderId(order_id);
								orderRecord.setVendorId(vendorId);
								orderRecord.setPrice(Double.valueOf(price).doubleValue());
								orderRecord.setTotal(Double.valueOf(total).doubleValue());
								orderRecord.setExpressFee(Double.valueOf(expressFee).doubleValue());
								orderRecord.setStatusOri(status_ori);
								orderRecord.setPayTime(payTime);
								
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
								
								wdCouponOrderRecordService.addOrderRecord(orderRecord);
							}
						}
					}
				}
					
				log.debug("resultJSON:"+resultJSON);
			
			} while (curr_page * pageSize < total_num); 
			
			List<WdCoupon> dbList = wdCouponOrderRecordService.getValidCouponByVendor(startTime,endTime,vendorId);
			
			Double sum = 0.0;
			
			for (WdOrderRecord record : orderRecordList) {
				for (WdCoupon wdCoupon : dbList) {
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
							object.put("payTime",
									record.getPayTime().replace(".0", ""));

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

							/*int temp = wdCouponOrderRecordService
									.hasOrderCount(record.getOrderId());
							if (temp == 0) {
								wdCouponOrderRecordService.addOrderCount(order);
							}*/
						}
					}
				}
			}
		}
	}
	
}
