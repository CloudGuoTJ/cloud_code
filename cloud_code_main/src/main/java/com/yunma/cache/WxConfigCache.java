package com.yunma.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.wxConfig.WxConfigDao;
import com.yunma.model.WxConfig;

@Service
public class WxConfigCache extends BaseService{
	
	@Resource
	private WxConfigDao wxConfigDao;
	
	public JSONObject getWxConfigInfo(Integer vendorId){
		JSONObject result = new JSONObject();
		
		WxConfig vo;
	try {
		vo = (WxConfig) XMemcachedHelper.findCache("WxConfigInfo_VendorId_"+vendorId);
		
		if ( vo != null) {
			
			logger.info("从Memcached中获取到了WxConfigVo信息");
			result.put("statuscode", "1");
			result.put("data", vo);
			return result;
			}else{
				//根据vendorId查询公众号信息
				vo =  wxConfigDao.getWxConfiginfoByVendorId(vendorId);
				if (vo != null) {
					//将查询的数据添加到缓存中
					XMemcachedHelper.set("WxConfig_VendorId_"+vendorId, vo, CommonConstants.TIME_MINUTE_30);
					}else{
						logger.info("数据库未获取到企业公众号基本信息");
						result.put("statuscode", "-1");
						result.put("msg", "未获取到对应id的信息");
						return result;
					}
				}
			}catch (Exception e) {
				logger.error("获取企业基本信息时：", e);
				result.put("statuscode", "服务器异常");
				return result;
			}
				
			logger.info("读取数据库VendorBasicInfo信息缓存在Memcached中");
			result.put("statuscode", "1");
			result.put("data", vo);
			return result;				
			}
			  
		
	}
	

