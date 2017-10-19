package com.yunma.controller.couponWechat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.utils.WeChatConfig;

/**
 * 微信小店配置接口
 */
@Controller
@RequestMapping("/wechatCouponConfig")
public class WeChatCouponConfigController {
	
	@Resource
	private WeChatCouponConfigService weChatCouponConfigService;

	/**
	 * 添加微信配置文件
	 * @param vendorId
	 * @param appId
	 * @param secret
	 * @param apiKey
	 * @param mchId
	 * @param credentials
	 * @return
	 */
	@RequestMapping("/addConfig.do")
	@ResponseBody
	public JSONObject addWechatConfig(
			String vendorId,
			String appId,
			String secret,
			String apiKey,
			String mchId,
			String productUrl,
			@RequestParam(value="credentials", required=false) CommonsMultipartFile[] credentials) {
		JSONObject result = new JSONObject();
		
		WeChatCouponConfig  config1 = weChatCouponConfigService.getWeChatCouponConfig(Integer.parseInt(vendorId));
		//为空 新增 不为空修改
		if (config1 == null) {
			if (credentials != null && credentials.length > 0) {
				//上传证书
				String location = uploadCredentials(Integer.parseInt(vendorId),credentials);
				
				WeChatCouponConfig  config = new WeChatCouponConfig();
				
				config.setVendorId(Integer.parseInt(vendorId));
				config.setAppId(appId);
				config.setSecret(secret);
				config.setApiKey(apiKey);
				config.setMchId(mchId);
				config.setProductUrl(productUrl);
				config.setCredentialsLocation(location);
				int temp = weChatCouponConfigService.addWechatConfig(config);
				if (temp > 0) {
		        	result.put("statuscode", 1);
					result.put("msg", "上传成功");
				} else {
		        	result.put("statuscode", -1);
					result.put("msg", "上传失败");
				}
				
			} else {
				result.put("statuscode", -2);
				result.put("msg", "请添加文件");
			}
		} else {
			return updateConfig(Integer.parseInt(vendorId),appId,secret,apiKey,mchId,productUrl,credentials);
		}
		
		return result;
	}
	
	/**
	 * 查询微信配置文件
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getConfig.do")
	@ResponseBody
	public JSONObject getConfig(Integer vendorId) {
		JSONObject result = new JSONObject();
		
		WeChatCouponConfig  config = weChatCouponConfigService.getWeChatCouponConfig(vendorId);
		if (config != null) {
			result.put("vendorId", config.getVendorId());
			result.put("appId", config.getAppId());
			result.put("secret", config.getSecret());
			result.put("apiKey", config.getApiKey());
			result.put("mchId", config.getMchId());
			result.put("productUrl", config.getProductUrl());
			result.put("credentialsLocation", config.getCredentialsLocation());
		}
		
		return result;
	}
	
	/**
	 * 修改微信配置文件
	 * @param vendorId
	 * @param appId
	 * @param secret
	 * @param apiKey
	 * @param mchId
	 * @param credentials
	 * @return
	 */
	@RequestMapping("/updateConfig.do")
	@ResponseBody
	public JSONObject updateConfig(
			Integer vendorId,
			String appId,
			String secret,
			String apiKey,
			String mchId,
			String productUrl,
			@RequestParam(value="credentials", required=false) CommonsMultipartFile[] credentials) {
		
		JSONObject result = new JSONObject();
		
		WeChatCouponConfig config = new WeChatCouponConfig();
		config.setVendorId(vendorId);
		config.setAppId(appId);
		config.setSecret(secret);
		config.setApiKey(apiKey);
		config.setMchId(mchId);
		config.setProductUrl(productUrl);
		
		if (credentials != null && credentials.length > 0) {
			String location = uploadCredentials(vendorId,credentials);
			config.setCredentialsLocation(location);
		}
		
		int temp = weChatCouponConfigService.updateWeChatCouponConfig(config);
		if (temp > 0) {
        	result.put("statuscode", 1);
			result.put("msg", "修改成功");
		} else {
        	result.put("statuscode", -1);
			result.put("msg", "修改失败");
		}
		
		return result;
	}
	
	/**
	 * 上传证书 
	 * @param vendorId
	 * @param credentials
	 */
	private String uploadCredentials(Integer vendorId,CommonsMultipartFile[] credentials) {
		String classesPath;
		try {
			classesPath = getClass().getResource("/").toURI().getPath();//获取到webapps/classes路径
			String path = classesPath.substring(0, classesPath.lastIndexOf("webapps") + 7);
			String realPath = path + WeChatConfig.USER_CREDENTIALS_LOCATION+"/"+vendorId;
			
			File pathFile = new File(realPath);
			// 如果文件夹不存在，则创建文件夹
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			
			for (CommonsMultipartFile commonsMultipartFile : credentials) {
				File file = new File(realPath+"/"+commonsMultipartFile.getOriginalFilename());
				commonsMultipartFile.transferTo(file);
			}
			
			return realPath+"/apiclient_cert.p12";
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
