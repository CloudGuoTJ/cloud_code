package com.yunma.controller.couponWd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.service.couponWd.WdConfigService;
import com.yunma.utils.coupon.WdUtil;

@Controller
public class WdConfigController {
	
	@Resource
	private WdConfigService wdConfigService;

	/**
	 * 新增微店配置
	 * @param appKey
	 * @param secret
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/ADD/wd/config.do")
	@ResponseBody
	public JSONObject addWdConfig(String appKey,String secret,Integer vendorId,String productUrl,String wenUrl) {
		JSONObject result = new JSONObject();
		
		WdConfig wdConfig = new WdConfig();
		wdConfig.setAppKey(appKey);
		wdConfig.setSecret(secret);
		wdConfig.setProductUrl(productUrl);
		wdConfig.setWenUrl(wenUrl);
		
		String accessToken = WdUtil.getAccessToken(wdConfig);
		
		if (accessToken == null) {
			result.put("statuscode", -1);
			result.put("msg", "获取厂商微店accessToken失败,请填写正确的信息");
			return result;
		}
		
		wdConfig.setVendorId(vendorId);
		wdConfig.setAccessToken(accessToken);
		
		//判断数据库中是否存在该厂商的微店信息
		WdConfig dbWdConfig = wdConfigService.getWdConfig(vendorId);
		
		if (dbWdConfig != null) {
			int resultNum = wdConfigService.updateWdConfig(wdConfig);
			if (resultNum > 0) {
				result.put("statuscode", 2);
				result.put("msg", "修改成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "修改厂商微店失败");
			}
			return result;
		} else {
			int resultNum = wdConfigService.addWdConfig(wdConfig);
			if (resultNum > 0) {
				result.put("statuscode", 1);
				result.put("msg", "新增成功");
			} else {
				result.put("statuscode", -3);
				result.put("msg", "新增厂商微店失败");
			}
			return result;
		}
	}
	
	/**
	 * 修改微店配置
	 * @param appKey
	 * @param secret
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/UPDATE/wd/config.do")
	@ResponseBody
	public JSONObject updateWdConfig(String appKey,String secret,Integer vendorId,String productUrl,String wenUrl) {
		JSONObject result = new JSONObject();
		
		WdConfig wdConfig = new WdConfig();
		wdConfig.setAppKey(appKey);
		wdConfig.setSecret(secret);
		wdConfig.setVendorId(vendorId);
		wdConfig.setProductUrl(productUrl);
		wdConfig.setWenUrl(wenUrl);
		
		String accessToken = WdUtil.getAccessToken(wdConfig);
		if (accessToken == null || accessToken.length() == 0) {
			result.put("statuscode", -1);
			result.put("msg", "获取厂商微店accessToken失败,请填写正确的信息");
		}
		
		wdConfig.setAccessToken(accessToken);
		
		int resultNum = wdConfigService.updateWdConfig(wdConfig);
		if (resultNum > 0) {
			result.put("statuscode", 1);
			result.put("msg", "修改成功");
		} else {
			result.put("statuscode", -2);
			result.put("msg", "修改失败");
		}
		
		return result;
	}
	
	/**
	 * 查询厂商的微店配置信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/wd/config.do")
	@ResponseBody
	public JSONObject getWdConfig(Integer vendorId) {
		JSONObject result = new JSONObject();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig != null) {
			result.put("statuscode", 1);
			result.put("id", wdConfig.getId());
			result.put("appKey", wdConfig.getAppKey());
			result.put("secret", wdConfig.getSecret());
			result.put("accessToken", wdConfig.getAccessToken());
			result.put("vendorId", wdConfig.getVendorId());
			result.put("createTime", sdf.format(wdConfig.getCreateTime()));
			result.put("productUrl", wdConfig.getProductUrl());
			result.put("wenUrl", wdConfig.getWenUrl());
		} else {
			result.put("statuscode", -1);
			result.put("msg", "未查询到对应的微店配置信息");
		}
		
		return result;
	}
	
	@RequestMapping("/testwd")
	@ResponseBody
	public String test() {
		
		/*WdConfig wdConfig1 = wdConfigService.getWdConfig(vendorId);
		
		WdConfig wdConfig = new WdConfig();
		wdConfig.setAppKey(wdConfig1.getAppKey());
		wdConfig.setSecret(wdConfig1.getSecret());
		wdConfig.setProductUrl(wdConfig1.getProductUrl());
		
		String accessToken = WdUtil.getAccessToken(wdConfig);
		return accessToken;*/
		
		List<WdConfig> list = wdConfigService.getWdConfigAll();
		if (list != null && list.size() > 0) {
			for (WdConfig wdConfig : list) {
				wdConfig.setAccessToken(WdUtil.getAccessToken(wdConfig));
				wdConfigService.updateWdConfig(wdConfig);
			}
		}
		return "更新成功";
	}
	
	public static void main(String[] args) {
		List list = new ArrayList();
		Collections.shuffle(list);
	}
}
