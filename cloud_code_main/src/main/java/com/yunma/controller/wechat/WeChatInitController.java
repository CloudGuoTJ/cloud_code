package com.yunma.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.yunma.utils.WeChatConfig;


@Controller
@RequestMapping("/init.do")
public class WeChatInitController extends WeixinSupport {

	/**
	 * 匹配微信授权TOKEN
	 */
	@Override
	protected String getToken() {
		return WeChatConfig.TOKEN;
	}

}
