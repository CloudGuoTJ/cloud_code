package com.yunma.controller.wechat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.util.Radix;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.entity.tracing.LogisticCodeScan;
import com.yunma.entity.weChat.WeChatUser;
import com.yunma.service.agent.AgentEmployeeService;
import com.yunma.service.agent.AgentService;
import com.yunma.service.weChatUser.WeChatService;
import com.yunma.utils.WeChatConfig;

@Controller
@RequestMapping(value = "/w")
public class WechatAgentCallbackController {

	@Resource
	private AgentService agentService;
	@Resource
	private AgentEmployeeService empService;
	@Resource
	private ApiConfig apiConfig;
	@Resource
	private WeChatService weChatService;
	
	private Logger log = LoggerFactory.getLogger(WechatController.class);
	
	/**
	 * 代理商授权码微信回调接口
	 * 
	 */
	@RequestMapping(value="/antiFakeForBackForLigistic.do")
	public ModelAndView antiFakeForBackForLigistic(String code, String source ,Integer flag) {
		log.info("进入微信回调");
		OauthAPI oauth = new OauthAPI(apiConfig);
		OauthGetTokenResponse token = oauth.getToken(code);
		GetUserInfoResponse userInfo = oauth.getUserInfo(token.getAccessToken(), token.getOpenid());
		
		//将第一次进入公众号的人的信息存入 微信用户表中
		int hasUser = weChatService.hasWeChatUserInfo(userInfo.getOpenid());
		if (hasUser <= 0) {
			WeChatUser user = new WeChatUser();
			user.setOpenId(userInfo.getOpenid());
			user.setNickName(userInfo.getNickname());
			user.setSex(userInfo.getSex());
			user.setCity(userInfo.getCity());
			user.setProvince(userInfo.getProvince());
			user.setHeadImgurl(userInfo.getHeadimgurl());
			user.setCreateDate(new Date());
			weChatService.addWeChatUserInfo(user);
		}
		
		//将用户的扫码信息存入扫码表中
		String logisticCode  = source;
		LogisticCode logcode = agentService.getAgentLogisticCodeByCode(logisticCode);
		Integer vendorId = Radix.convert62To10(logisticCode.substring(0,3));
		Integer agentId= Radix.convert62To10(logisticCode.substring(3,6));
		if(logcode != null){					
			LogisticCodeScan codeSca=new LogisticCodeScan();
			codeSca.setOpenId(userInfo.getOpenid());
			codeSca.setLogisticCode(logisticCode);
			codeSca.setScanTime(new Date());
			codeSca.setLogisticCodeId(logcode.getLogisticId());
			codeSca.setLvCount(logcode.getLvCount());
			codeSca.setAgentId(agentId);
			agentService.addLogisticCodeScan(codeSca);
				
			}else{
				log.debug("储存扫码用户信息失败!");
				
			}	
		
		String urlName = "";
		
		//当前员工是否已经在当前厂商下绑定过代理商
		List<Integer> vendors=empService.getOpenIdFromEmp(userInfo.getOpenid());
		log.debug("agentId:::::"+agentId);
		//log.debug("vendor1:::::::"+vendor1);
		
		if (vendors == null || vendors.size() == 0) {
			return new ModelAndView("redirect:https://"
					+ WeChatConfig.DOMAIN_NAME
					+ "/wx/yuangongInfo.html?openId=" + userInfo.getOpenid()
					+ "&agentId=" + agentId + "&vendorId=" + vendorId);
		} else {

			for (Integer vendor1 : vendors) {
				if (vendor1.equals(vendorId)) {
					return new ModelAndView("redirect:https://"
							+ WeChatConfig.DOMAIN_NAME + "/"
							+ WeChatConfig.PROJECT_NAME
							+ "/wx/test/SuccessJsp.jsp");
				}
			}

			return new ModelAndView("redirect:https://"
					+ WeChatConfig.DOMAIN_NAME
					+ "/wx/yuangongInfo.html?openId=" + userInfo.getOpenid()
					+ "&agentId=" + agentId + "&vendorId=" + vendorId);

		}

		//return	new ModelAndView("redirect:https://project.ym-b.top/wx/"+urlName+"?openId="+token.getOpenid()+"&securityCode="+source+"&productId="+productId+"&securityCodeId="+logcode.getLogisticId());				
//		return "redirect:/wx/1/red.html?openId="+token.getOpenid()+"&securityCode="+source+"&orderId="+privateCode.getOrderId()+"&securityCodeId="+privateCode.getSecurityCodeId();
		//return "redirect:/wx/1/red.html?openId="+token.getOpenid()+"&securityCode="+source;
		//return "callBack";
	}
}
