package com.yunma.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.HttpUtil;
import com.jd.open.api.sdk.request.promotion.SellerCouponWriteCreateRequest;
import com.jd.open.api.sdk.response.promotion.SellerCouponWriteCreateResponse;






public class JOSConfig {
	
	public static final String code="U95XyO";
	
	public static final String appSecret="75a59240e1774426873de39cac520566";
	
	public static final String appKey="10BA26253A1DC058653DB0EC06069639";
	
	public static String URL="https://ym-a.top/cloud_code/GET/JDCoupon/getAccessTokenByCode.do";
	//public static String URL="http://119.23.33.110:8080";
	
	public static final String SERVER_URL="https://api.jd.com/routerjson";
	
	public static void main(String[] args) {
		String url="https://oauth.jd.com/oauth/token";
				
//				+"grant_type=authorization_code&client_id="+appKey
//				+"&client_secret="+ appSecret
//				+"&scope=read&redirect_uri="+ URL
//				+"&code="+ code
//				+"&state=1234";
		
		Map<String, String> params=new HashMap<String, String>();
		
		params.put("grant_type", "authorization_code");
		params.put("client_id", appKey);
		params.put("client_secret", appSecret);
		params.put("scope", "read");
		params.put("redirect_uri", URL);
		params.put("code", code);
		params.put("state", "1234");
		
		try {
			String test=HttpUtil.doPost(url, params, 5000, 5000);
			if (test == null || "".equals(test)) {
				System.out.println("测试：：：："+test);
				
			}else{
				System.out.println("test:::"+test);
				Object obj=JSONUtil.getStringFromJSONObject(test, "access_token");
				String accessToken=obj.toString();
				System.out.println("测试：：：："+obj.toString());
				JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);

				SellerCouponWriteCreateRequest request=new SellerCouponWriteCreateRequest();
				
				request.setName("jingdong");
				request.setIp("120.77.214.200");
				request.setPort(80);
				request.setName( "jingdong" );
				request.setType(1);
				request.setBindType(1);
				request.setGrantType(1);
				request.setNum( 12 );
				request.setDiscount(new BigDecimal(10));
				request.setQuota(new BigDecimal(50));
				request.setValidityType(1);
				request.setDays(1);
				request.setBeginTime(System.currentTimeMillis());
				request.setEndTime(System.currentTimeMillis());
				request.setPassword( "jingdong" );
				request.setBatchKey( "jingdong" );
				request.setMember(50);
				request.setTakeBeginTime(System.currentTimeMillis());
				request.setTakeEndTime(System.currentTimeMillis());
				request.setTakeRule(5);
				request.setTakeNum(2);
				request.setDisplay(1);
				request.setPlatformType(1);
				request.setPlatform( "jingdong" );
				request.setImgUrl( "jingdong" );
				//request.setBoundStatus( 123 );
				//request.setJdNum( 123 );
				//request.setItemId((long) 123.00);
				request.setShareType(1);
				request.setSkuId( "123,234,345" );

				SellerCouponWriteCreateResponse response=client.execute(request);
				
				System.out.println("京东响应地址getUrl："+response.getUrl());
				System.out.println("京东响应数据getCode："+response.getCode());
				System.out.println("京东响应数据getMsg："+response.getMsg());
				System.out.println("京东响应数据getCouponId："+response.getCouponId());
				System.out.println("京东响应数据getZhDesc："+response.getZhDesc());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
