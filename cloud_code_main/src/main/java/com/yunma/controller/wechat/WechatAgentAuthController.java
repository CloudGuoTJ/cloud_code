package com.yunma.controller.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.service.agent.AgentService;
import com.yunma.utils.WeChatConfig;

@Controller
public class WechatAgentAuthController {
	
	@Autowired
	private AgentService service;
	
	private Logger log = LoggerFactory.getLogger(WechatAntiFakeController.class);
	
	/**
	 * 代理商授权码回调接口
	 * @param shortCode
	 * @return
	 */
	@RequestMapping("/w/{shortCode}")
	public ModelAndView antiFakeForBackForLigistic(@PathVariable String shortCode) {		
		if (shortCode == null) {
			return new ModelAndView("redirect:/error1/msg1.do");

		} else {

			LogisticCode securityCode = service.getAgentLogisticCodeByCode(shortCode);
					
			String code = securityCode.getLogisticCode();
			
			if (code.equals(shortCode)) {
				String appId = WeChatConfig.APP_ID;
				String http = WeChatConfig.WX_HTTp;
				String domainName = WeChatConfig.DOMAIN_NAME;
				String projectName = WeChatConfig.PROJECT_NAME;
				
				log.debug("---redirect:"+"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fw%2FwxCallback.do?source="
						+shortCode
						+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
				
				return new ModelAndView(
						"redirect:"+http+"?appid="+appId + "&redirect_uri=https%3A%2F%2F"+domainName+"%2F"+projectName+"%2Fw%2FantiFakeForBackForLigistic.do?source="
				+shortCode
				+"&flag=0&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
			}else{
				
				return new ModelAndView("redirect:/error1/msg2.do");
			}
		}


	} 

	@RequestMapping("/error1/msg1.do")
	@ResponseBody
	public JSONObject msg1() {
		JSONObject result = new JSONObject();
		result.put("错误路径，请确认二维码信息是否正确！", -11);
		return result;
	}

	@RequestMapping("/error1/msg2.do")
	@ResponseBody
	public JSONObject msg2() {
		JSONObject result = new JSONObject();
		result.put("二维码信息错误,", -12);
		return result;
	}

	@RequestMapping("/error1/msg3.do")
	@ResponseBody
	public JSONObject msg3() {
		JSONObject result = new JSONObject();
		result.put("您扫描的二维码错误请谨慎购买！", -13);
		return result;
	}

	@RequestMapping("/error1/msg.do")
	@ResponseBody
	public JSONObject msg() {
		JSONObject result = new JSONObject();
		result.put("错误路径，请确认二维码信息是否正确！", -14);
		return result;
	}

}
