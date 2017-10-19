package com.yunma.controller.wechat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.service.antiFakeService.AntiFakeService;
import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.service.tracing.ProductsTracingService;
import com.yunma.utils.WeChatConfig;



@Controller
public class WechatAntiFakeController {


	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private AntiFakeService antiFakeService;
	@Autowired
	private ProductsTracingService tracingService;

	private Logger log = LoggerFactory.getLogger(WechatAntiFakeController.class);	  
	@Resource
	private ApiConfig apiConfig;
	/**
	 * 微信扫码
	 * 
	 * @param shortCode
	 * @return
	 */
	@RequestMapping("/s/{shortCode}")
	public ModelAndView antiFakeForBack(@PathVariable String shortCode) {	
		/**
		 * 追加已扫码,默认授权判断
		 * 
		 */	
		
		if (shortCode == null) {
			return new ModelAndView(WeChatConfig.ERROR_HTML);
		} else if (shortCode.length() < 12){
			return new ModelAndView(WeChatConfig.ERROR_HTML);

		} else {

			SecurityCode securityCode = securityCodeService
					.getSecurityCode(shortCode);
			String code = securityCode.getSecurityCode();
			if (code.equals(shortCode)) {
				String appId = WeChatConfig.APP_ID;
				String http = WeChatConfig.WX_HTTp;
				String domainName = WeChatConfig.DOMAIN_NAME;
				String projectName = WeChatConfig.PROJECT_NAME;
				
				log.debug("---redirect:"+"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fwx%2FwxCallback.do?source="
						+shortCode
						+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
				/***
				 * 初始化都用静默授权scope=snsapi_base,如果获取的用户信息不全再使用弹窗授权scope=snsapi_userinfo
				 */
				return new ModelAndView(
						"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fwx%2FwxCallback.do?source="
				+shortCode
				+"&flag=1&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			}else{
				
				return new ModelAndView(WeChatConfig.ERROR_HTML);
			}
		}


	} 
	
 
	/**
	 * TracingCode
	 * 溯源扫码回调
	 * @param shortCode
	 * @return
	 */
	@RequestMapping("/t/{shortCode}")
	public ModelAndView antiFakeForBackForTracingCode(@PathVariable String shortCode) {		
		if (shortCode == null) {
			return new ModelAndView(WeChatConfig.ERROR_HTML);
		} else if (shortCode.length() < 12){
			return new ModelAndView(WeChatConfig.ERROR_HTML);

		} else {			
				String appId = WeChatConfig.APP_ID;
				String http = WeChatConfig.WX_HTTp;
				String domainName = WeChatConfig.DOMAIN_NAME;
				String projectName = WeChatConfig.PROJECT_NAME;
				
				log.debug("---redirect:"+"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fwx%2FwxAntiFakeForTracingCode.do?source="
						+shortCode
						+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
				
				return new ModelAndView(
						"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fwx%2FwxAntiFakeForTracingCode.do?source="
				+shortCode
				+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		
			
		}


	} 

	
	
	
	
	
	@RequestMapping("/error/msg1.do")
	@ResponseBody
	public JSONObject msg1() {
		JSONObject result = new JSONObject();
		result.put("错误路径，请确认二维码信息是否正确！", -11);
		return result;
	}

	@RequestMapping("/error/msg2.do")
	@ResponseBody
	public JSONObject msg2() {
		JSONObject result = new JSONObject();
		result.put("二维码信息错误,", -12);
		return result;
	}

	@RequestMapping("/error/msg3.do")
	@ResponseBody
	public JSONObject msg3() {
		JSONObject result = new JSONObject();
		result.put("您扫描的二维码错误请谨慎购买！", -13);
		return result;
	}

	@RequestMapping("/error/msg.do")
	@ResponseBody
	public JSONObject msg() {
		JSONObject result = new JSONObject();
		result.put("错误路径，请确认二维码信息是否正确！", -14);
		return result;
	}

	

}
