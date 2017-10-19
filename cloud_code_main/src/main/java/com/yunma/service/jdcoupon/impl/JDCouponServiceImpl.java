package com.yunma.service.jdcoupon.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.DateUtils;
import com.google.gson.Gson;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.promotion.SellerCouponReadGetCouponByIdRequest;
import com.jd.open.api.sdk.request.promotion.SellerCouponWriteCloseRequest;
import com.jd.open.api.sdk.request.promotion.SellerCouponWriteCreateRequest;
import com.jd.open.api.sdk.response.promotion.SellerCouponReadGetCouponByIdResponse;
import com.jd.open.api.sdk.response.promotion.SellerCouponWriteCloseResponse;
import com.jd.open.api.sdk.response.promotion.SellerCouponWriteCreateResponse;
import com.yunma.dao.jdcoupon.JDCouponDao;
import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDVendorInfo;
import com.yunma.service.jdcoupon.JDCouponService;
import com.yunma.utils.JOSConfig;

@Service
public class JDCouponServiceImpl implements JDCouponService{
	
	@Autowired
	private JDCouponDao dao;
	
	private Logger log = LoggerFactory.getLogger(JDCouponServiceImpl.class);
	
	@Override
	public int addJDUserInfo(JDVendorInfo info) {
		
		return dao.addJDUserInfo(info);
	}

	@Override
	public JSONObject getJDVendorInfoByVendorId(Integer vendorId) {
		JSONObject result=new JSONObject();
		JDVendorInfo info = dao.getJDVendorInfoByVendorId(vendorId);
		
		if (info != null) {
			
			long currentTime=System.currentTimeMillis();
			long expiresTime=Long.parseLong(info.getTime())+(info.getExpires_in()*1000);
			
			if (currentTime > expiresTime) {
				result.put("status", -1);
				result.put("msg", "授权时间超过有效期");
			}else{
				result.put("status", 1);
				result.put("jdUid", info.getUid());
				result.put("msg", "授权有效");
			}
		}else{
			result.put("status", -2);
			result.put("msg", "当前用户未进行授权");
		}
		
		return result;
	}

	@Override
	public int updateJDVendorInfoByVendorId(JDVendorInfo info) {
		
		return dao.updateJDVendorInfoByVendorId(info);
	}

	@Override
	public JSONObject addJDCouponinfo(JDCouponCreateInfo info) {
		JSONObject result=new JSONObject();
		JDVendorInfo vendorInfo = dao.getJDVendorInfoByVendorId(info.getVendorId());
		
		if (vendorInfo != null) {
			
			JdClient client=new DefaultJdClient(JOSConfig.SERVER_URL,vendorInfo.getAccess_token(),JOSConfig.appKey,JOSConfig.appSecret);
			
			SellerCouponWriteCreateRequest request=new SellerCouponWriteCreateRequest();
			
			request.setName(info.getName());
			request.setIp("120.77.214.200");
			request.setPort(80);
			request.setType(info.getType());
			request.setBindType(info.getBindType());
			request.setGrantType(info.getGrantType());
			request.setNum(info.getNum());
			request.setDiscount(new BigDecimal(info.getDiscount()));
			request.setQuota(new BigDecimal(info.getQuota()));
			request.setValidityType(5);
			request.setDays(info.getDays());
			request.setBeginTime(Long.parseLong(DateUtils.dateToStamp(info.getBeginTime())));
			request.setEndTime(Long.parseLong(DateUtils.dateToStamp(info.getEndTime())));
			request.setPassword(info.getPassword());
			request.setBatchKey(info.getBatchKey());
			request.setMember(info.getMember());
			request.setTakeBeginTime(Long.parseLong(DateUtils.dateToStamp(info.getTakeBeginTime())));
			request.setTakeEndTime(Long.parseLong(DateUtils.dateToStamp(info.getTakeEndTime())));
			request.setTakeRule(info.getTakeRule());
			request.setTakeNum(info.getTakeNum());
			request.setDisplay(info.getDisplay());
			request.setPlatformType(1);
			request.setPlatform(info.getPlatform());
			request.setImgUrl(info.getImgUrl());
			//request.setBoundStatus( 123 );
			//request.setJdNum( 123 );
			//request.setItemId((long) 123.00);
			request.setShareType(info.getShareType());
			request.setSkuId(info.getSkuId());
			
			
			
			SellerCouponWriteCreateResponse response;
			try {
				response = client.execute(request);
				
				if (response.getCouponId() != null) {
					info.setCouponId(response.getCouponId().toString());
					
					SellerCouponReadGetCouponByIdRequest requestInfo=new SellerCouponReadGetCouponByIdRequest();
					requestInfo.setIp( "120.77.214.200" );
					requestInfo.setPort(80);
					requestInfo.setCouponId(response.getCouponId());
					SellerCouponReadGetCouponByIdResponse responseInfo=client.execute(requestInfo);
					
					if (responseInfo.getJosCoupon().getLink() != null) {
						log.debug("优惠券领取链接：：：：："+responseInfo.getJosCoupon().getLink());
						info.setLink(responseInfo.getJosCoupon().getLink());
						info.setActivityLink(responseInfo.getJosCoupon().getActivityLink());
					}
					
					int temp=dao.addJDCouponinfo(info);
					if (temp > 0) {
						result.put("dao", "新增京东优惠券成功");
					}else{
						result.put("dao", "数据库新增京东优惠券失败");
					}
					result.put("status", 1);
					result.put("msg", "添加优惠券成功");
					
				}else{
					result.put("status", -1);
					result.put("msg", response.getMsg());
				}
				
				System.out.println("京东响应地址getUrl："+response.getUrl());
				System.out.println("京东响应数据getCode："+response.getCode());
				System.out.println("京东响应数据getMsg："+response.getMsg());
				System.out.println("京东响应数据getCouponId："+response.getCouponId());
				System.out.println("京东响应数据getZhDesc："+response.getZhDesc());
				return result;
			} catch (JdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public String getJDCouponInfo(String couponId) {
		String result="";
		JDCouponCreateInfo couponInfo = dao.getCouponInfoByCouponId(couponId);
		result=new Gson().toJson(couponInfo);
		return result;
	}

	@Override
	public JSONObject getJDCouponList(Integer vendorId) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		List<JDCouponCreateInfo> infos=dao.getJDCouponList(vendorId);
		
		if (infos != null && infos.size() > 0) {
			for (JDCouponCreateInfo info : infos) {
				JSONObject obj=new JSONObject();
				obj.put("id", info.getId());
				obj.put("vendorId", info.getVendorId());
				obj.put("jdUid", info.getJdUid());
				obj.put("couponId", info.getCouponId());
				obj.put("name", info.getName());
				obj.put("type", info.getType());
				obj.put("bindType", info.getBindType());
				obj.put("num", info.getNum());
				obj.put("discount", info.getDiscount());
				obj.put("quota", info.getQuota());
				obj.put("beginTime", info.getBeginTime());
				obj.put("endTime", info.getEndTime());
				obj.put("takeBeginTime", info.getTakeBeginTime());
				obj.put("takeEndTime", info.getTakeEndTime());
				obj.put("takeRule", info.getTakeRule());
				obj.put("takeNum", info.getTakeNum());
				obj.put("shareType", info.getShareType());
				obj.put("skuId", info.getSkuId());
				
				array.add(obj);
			}
			result.put("data", array);
		}
		
		return result;
	}
	
	public JDVendorInfo getJDVendorInfo(Integer vendorId){
		
		return dao.getJDVendorInfo(vendorId);
	}

	@Override
	public List<JDCouponCreateInfo> getJDCouponByVendorId(Integer vendorId) {
		return dao.getJDCouponByVendorId(vendorId);
	}

	@Override
	public List<JDCouponCreateInfo> getJDCouponByVendorIdOther(Integer vendorId) {
		return dao.getJDCouponByVendorIdOther(vendorId);
	}

	@Override
	public JSONObject deleteJDCoupon(Integer vendorId,Long couponId) {
		JSONObject result=new JSONObject();
		JDVendorInfo vendorInfo = dao.getJDVendorInfoByVendorId(vendorId);
		
		if (vendorInfo.getAccess_token() != null) {
			JdClient client=new DefaultJdClient(JOSConfig.SERVER_URL,vendorInfo.getAccess_token(),JOSConfig.appKey,JOSConfig.appSecret); 
			SellerCouponWriteCloseRequest request=new SellerCouponWriteCloseRequest();
			request.setIp("120.77.214.200");
			request.setPort(80);
			request.setCouponId(couponId);
			try {
				SellerCouponWriteCloseResponse response=client.execute(request);
				if (response.getCloseResult()) {
					int temp = dao.updateCouponInfoStatus(couponId.toString());
					if (temp > 0) {
						result.put("status", 1);
						result.put("msg", "关闭数据库优惠券成功");
					}else{
						result.put("status", -1);
						result.put("msg", "关闭数据库优惠券失败");
					}
				}else{
					result.put("status", -2);
					result.put("msg", "关闭京东优惠券失败");
				}
				
			} catch (JdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	
	
	
}
