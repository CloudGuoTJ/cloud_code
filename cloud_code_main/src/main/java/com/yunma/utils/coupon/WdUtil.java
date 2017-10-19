package com.yunma.utils.coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yunma.controller.product.ProductController;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;

public class WdUtil {
	
	private static Logger log = LoggerFactory.getLogger(WdUtil.class);

	/**
	 * 获取微店accessToken
	 * @param wdConfig
	 * @return
	 */
	public static String getAccessToken(WdConfig wdConfig) {
		String accessTokenUrl = WeChatConfig.WD_ACCESS_TOKEN;
		accessTokenUrl = accessTokenUrl.replace("{appkey}", wdConfig.getAppKey());
		accessTokenUrl = accessTokenUrl.replace("{secret}", wdConfig.getSecret());
		
		HttpRequestUtil util = new HttpRequestUtil();
		
		//发送请求
		JSONObject resultJSON = JSONObject.parseObject(util.sendGet(accessTokenUrl, null));
		
		Object result1Str = resultJSON.get("result");
		if (result1Str == null) {
			return null;
		}
		
		//获取accessToken
		JSONObject accessTokenJSON = JSONObject.parseObject(resultJSON.get("result").toString());
		
		return accessTokenJSON.getString("access_token").toString();
	}
	
	public static WdCoupon getWdCoupon(WdConfig wdConfig,String couponId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JSONObject param = new JSONObject();
		param.put("couponId", couponId);
		
		JSONObject _public = new JSONObject();
		_public.put("method", "get.shop.coupon.detail");
		_public.put("access_token", wdConfig.getAccessToken());
		_public.put("format", "json");
        
		HttpRequestUtil util = new HttpRequestUtil();
		String resultStr = util.sendPost(WeChatConfig.WD_API, "param="+param.toString()+"&public="+_public.toString());
		
		log.debug("resultStr:"+resultStr);
		
		JSONObject resultJSON = JSONObject.parseObject(resultStr);
		
		//获取接口返回的result信息
		String result1 = resultJSON.getString("result").toString();
		JSONObject coupon= JSONObject.parseObject(result1);
		
		WdCoupon wdCoupon = new WdCoupon();
		wdCoupon.setCouponId(Integer.parseInt(coupon.get("id")+""));
		wdCoupon.setBeginTimeStr(coupon.get("startTime")+"");
		wdCoupon.setEndTimeStr(coupon.get("endTime")+"");
		wdCoupon.setStock(Integer.parseInt(coupon.get("stock")+""));
		wdCoupon.setBuyerLimit(Integer.parseInt(coupon.get("buyerLimit")+""));
		wdCoupon.setTitle(coupon.get("title")+"");
		wdCoupon.setGmtCreatedStr(coupon.get("gmtCreated")+"");
		wdCoupon.setGmtModifiedStr(coupon.get("gmtModified")+"");
		wdCoupon.setReduce(Integer.parseInt(coupon.get("reduce")+""));
		wdCoupon.setLeastCost(Integer.parseInt(coupon.get("leastCost")+""));
		wdCoupon.setShowFlag(Integer.parseInt(coupon.get("showFlag")+""));
		wdCoupon.setWeidianFetchUrl(coupon.get("weidianFetchUrl")+"");
		wdCoupon.setStatus(Integer.parseInt(coupon.get("status")+""));
		wdCoupon.setOpenGet(coupon.get("openGet") == "true" || coupon.get("openGet").toString().contains("true") ? 1 : 0);
		wdCoupon.setShowFinish(coupon.get("showFinish") == "true" || coupon.get("showFinish").toString().contains("true") ? 1 : 0);
		wdCoupon.setDeleted(coupon.get("deleted") == "true" || coupon.get("deleted").toString().contains("true") ? 1 : 0);
		wdCoupon.setVendorId(wdConfig.getVendorId());
		return wdCoupon;
	}
	
	public static void main(String[] args) {
		WdUtil wd = new WdUtil();
		
		/*WdConfig wdConfig = new WdConfig();
		wdConfig.setAppKey(WeChatConfig.WD_APPKEY);
		wdConfig.setSecret(WeChatConfig.WD_SECRET);
		wdConfig.setVendorId(2);
		wdConfig.setAccessToken(WdUtil.getAccessToken(wdConfig));
		
		WdCoupon wdCoupin = wd.getWdCoupon(wdConfig,"3963788");
		System.out.println(wdCoupin);*/
		WdConfig wdConfig = new WdConfig();
		wdConfig.setAppKey("683046");
		wdConfig.setSecret("246be7dd3d7afb111754c922037775ae");
		System.out.println(wd.getAccessToken(wdConfig));
	}
	
}
