package com.yunma.controller.openweixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.openweixin.OpenWeiXinAuthorizerAccount;
import com.yunma.service.openweixin.OpenWeiXinService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;

/**
 * 小程序 接入 第三方平台
 * @author LUO
 */
@Controller
@RequestMapping("/openweixinwxapp")
public class OpenWeiXinWxAppController {
	
	@Resource
	private OpenWeiXinController openWeiXinController;
	
	@Resource
	private OpenWeiXinService openWeiXinService;
	
	private String accessToken = "G4v6j_pkGt3JRrfXYMlhTMlgEYHpcpEAqxTbEJwYOg3EcDD8AvgbzqXt2z8DbTaDEliaGXemKFSA0vf_2bzziKXuPtLhf3C8Ogb6q2iXdir3ioQRaipD_dlv5DuPwytFCRAhAKDWLU";

	@RequestMapping("/test.do")
	@ResponseBody
	public String test() {
		
		String component_access_token = openWeiXinController.getCAT();
		
		String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={COMPONENT_ACCESS_TOKEN}";
		url = url.replace("{COMPONENT_ACCESS_TOKEN}", component_access_token);
		
		String data = "{" +
				"\"component_appid\" : \"wxb41e9be2f174588e\"," +
				"\"authorization_code\" : \"queryauthcode@@@oTxsOKzFY3stuFbrWH381skQyVnr5lR1eiuQ-c-8xxDYKPpCzcrY70xGXdf8BXH8-nbECvwOEg7wi7M-1eevfg\"" +
				"}";
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "POST", data);
		System.out.println(result);
		
		/*OpenWeiXinConfig  config = openWeiXinService.getComponent_verify_ticketByAppid(WeChatConfig.OPEN_APPID);
		
		
		String ext_json = "{"+
                        "\"extEnable\": true,"+
                        "\"extAppid\": \"wxf9c4501a76931b33\","+
                        "\"ext\": {"+
                        "  \"name\": \"wechat\","+
                        "},"+
                        "\"window\":{"+
                        "  \"backgroundTextStyle\":\"light\","+
                        "  \"navigationBarBackgroundColor\": \"#fff\","+
                        "  \"navigationBarTitleText\": \"Demo\","+
                        "  \"navigationBarTextStyle\":\"black\""+
                        "},"+
                        "\"tabBar\": {"+
                        "  \"list\": [{"+
                        "    \"pagePath\": \"pages/index/index\","+
                        "    \"text\": \"首页\""+
                        "  }, {"+
                        "    \"pagePath\": \"pages/logs/logs\","+
                        "    \"text\": \"日志\""+
                        "  }]"+
                        "},"+
                        "\"networkTimeout\": {"+
                        "  \"request\": 10000,"+
                        "  \"downloadFile\": 10000"+
                        "}"+
                      "}";
		
		String url = "https://api.weixin.qq.com/wxa/commit?access_token={ACCESS_TOKEN}";
		url = url.replace("{ACCESS_TOKEN}", "");
		String data = "{" +
				"\"template_id\" : 0" +
				"\"ext_json\" : "+ext_json +
				"\"user_version\" : \"V1.0\"" +
				"\"user_desc\" : \"test\""+
			"}";
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendPost(url, data);
		System.out.println(result);*/
		return "success";
	}
	
	@RequestMapping("/commit1.do")
	@ResponseBody
	public String commit1() {
		
		/*String ext_json = "{"+
                        "\"extEnable\": true,"+
                        "\"extAppid\": \"wxf9c4501a76931b33\","+
                        "\"ext\": {"+
                        "  \"name\": \"wechat\","+
                        "},"+
                        "\"window\":{"+
                        "  \"backgroundTextStyle\":\"light\","+
                        "  \"navigationBarBackgroundColor\": \"#fff\","+
                        "  \"navigationBarTitleText\": \"Demo\","+
                        "  \"navigationBarTextStyle\":\"black\""+
                        "},"+
                        "\"tabBar\": {"+
                        "  \"list\": [{"+
                        "    \"pagePath\": \"pages/index/index\","+
                        "    \"text\": \"首页\""+
                        "  }, {"+
                        "    \"pagePath\": \"pages/logs/logs\","+
                        "    \"text\": \"日志\""+
                        "  }]"+
                        "},"+
                        "\"networkTimeout\": {"+
                        "  \"request\": 10000,"+
                        "  \"downloadFile\": 10000"+
                        "}"+
                      "}";*/
		
		 //ext_json = "{\"extEnable\": true,\"extAppid\": \"wxf9c4501a76931b33\",\"ext\": {\"name\": \"wechat\"},\"window\":{\"backgroundTextStyle\":\"light\",\"navigationBarBackgroundColor\": \"#fff\",\"navigationBarTitleText\": \"Demo\",\"navigationBarTextStyle\":\"black\"},\"tabBar\": {\"list\": [{\"pagePath\": \"pages/index/index\",\"text\": \"首页\"}, {\"pagePath\": \"pages/logs/logs\",\"text\": \"日志\"}]},\"networkTimeout\": {\"request\": 10000,\"downloadFile\": 10000}}";
		
		 String ext_json = "" +
		 		" {"+
				"  \"extEnable\": true,"+
				"  \"extAppid\": \"wxf9c4501a76931b33\","+
				"  \"ext\": {"+
				"    \"name\": \"wechat\","+
				"    \"attr\": {"+
					"    \"host\": \"open.weixin.qq.com\","+
					"    \"users\": ["+
					"        \"user_1\","+
					"        \"user_2\""+
					"      ]"+
				"    }"+
				"  },"+
				" \"extPages\": {"+
				"   \"pages/logs/logs\": {"+
				"      \"navigationBarTitleText\": \"logs\""+
				"    }"+
				"  },"+
				" \"window\": {"+
				"    \"backgroundTextStyle\": \"light\","+
				"    \"navigationBarBackgroundColor\": \"#fff\","+
				"    \"navigationBarTitleText\": \"Demo\","+
				"    \"navigationBarTextStyle\": \"black\""+
				"  },"+
				"  \"tabBar\": {"+
				"    \"list\": ["+
				"      {"+
				"        \"pagePath\": \"pages/index/index\","+
				"        \"text\": \"首页\""+
				"      },"+
				"      {"+
				"        \"pagePath\": \"pages/logs/logs\","+
				"        \"text\": \"日志\""+
				"      }"+
				"    ]"+
				"  },"+
				"  \"networkTimeout\": {"+
				"    \"request\": 10000,"+
				"    \"downloadFile\": 10000"+
				"  }"+
				"}";
		 
		 /*JSONObject extJSON = new JSONObject();
		 extJSON.put("extEnable", true);
		 extJSON.put("extAppid", "wxf9c4501a76931b33");
			 JSONArray extArray = new JSONArray();
			 JSONObject ext = new JSONObject();
			 ext.put("name", "wechat");
			 JSONA
			 
			 ext.put("", value)*/
			 
		ext_json = ext_json.replace(" ", "");
		 
		String url = "https://api.weixin.qq.com/wxa/commit?access_token={ACCESS_TOKEN}";
		
		url = url.replace("{ACCESS_TOKEN}", accessToken);
		
		try {
			ext_json = URLEncoder.encode(ext_json,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String data = "{" +
				"\"template_id\" : 1," +
				"\"ext_json\" : \""+ext_json+"\","+
				"\"user_version\" : \"V1.0\"," +
				"\"user_desc\" : \"test\""+
			"}";
		
		data = data.replace(" ", "");
		
		HttpRequestUtil util = new HttpRequestUtil();
		System.out.println("ext_json"+ext_json);
		System.out.println("data:"+data);
		System.out.println("url:"+url);
		String result = util.sendHttpsRequest(url, "POST", data);
		System.out.println(result);
		
		return "success";
	}
	
	/**
	 * 上传代码
	 * @param ext_json
	 * @param accessToken
	 * @return
	 */
	@RequestMapping("/commit.do")
	@ResponseBody
	public JSONObject commit(String ext_json,String accessToken) {
		JSONObject result = new JSONObject();
		HttpRequestUtil util = new HttpRequestUtil();
		
		String url = WeChatConfig.OPEN_WXA_COMMIT;
		url = url.replace("{access_token}", accessToken);
		
		String data = "{" +
				"\"template_id\":0,"+
				"\"ext_json\":"+ext_json+","+
				"\"user_version\":\"V1.0\","+
				"\"user_desc\":\"test\""+
			"}";
		
		data = data.replace(" ", "");
		
		System.out.println("ext_json"+ext_json);
		System.out.println("data:"+data);
		System.out.println("url:"+url);
		String resultStr = util.sendHttpsRequest(url, "POST", data);
		
		getQrCode(accessToken,result);
		String category =  getCategory(accessToken);
		String page = getPage(accessToken);
		
		
		return result;
	}
	
	/**
	 * 获取体验小程序的体验二维码
	 * @param accessToken
	 * @param result
	 */
	public void getQrCode(String accessToken,JSONObject result) {
		String url = WeChatConfig.OPEN_WXA_GET_QRCODE;
		url = url.replace("{access_token}", accessToken);
		
		result.put("qrCodeLink", url);//体验小程序连接
	}
	
	/**
	 * 获取授权小程序帐号的可选类目
	 * @param accessToken
	 * @return
	 */
	public String getCategory(String accessToken) {
		String url = WeChatConfig.OPEN_WXA_GET_CATEGORY;
		url = url.replace("{access_token}", accessToken);
		
		String result = HttpRequestUtil.sendHttpsRequest(url, "GET", null);
		
		return result;
	}
	
	/**
	 * 获取小程序的第三方提交代码的页面配置（仅供第三方开发者代小程序调用）
	 * @param accessToken
	 * @return
	 */
	public String getPage(String accessToken) {
		
		String url = WeChatConfig.OPEN_WXA_GET_PAGE;
		url = url.replace("{access_token}", accessToken);
		
		String result = HttpRequestUtil.sendHttpsRequest(url, "GET", null);
		
		return result;
	}
	
	/**
	 * 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
	 * @param category
	 * @param page
	 */
	public void submitAudit1(String category,String page,String accessToken,JSONObject result) {
		JSONObject categoryJSON = JSONObject.parseObject(category);
		JSONObject pageJSON = JSONObject.parseObject(page);
		
		//未解析 上面两个json
		
		String url = WeChatConfig.OPEN_WXA_SUBMIT_AUDIT;
		url = url.replace("{access_token}", accessToken);
		
		String data = "";
		String resultStr = HttpRequestUtil.sendHttpsRequest(url, "POST", data);
	}
	
	/**
	 * 微信审核结果
	 * @param toUserName 小程序原始id
	 * @param event
	 * @param status
	 * @param reason
	 */
	public void wxappAuditResult(String toUserName,String event,String status,String reason) {
		//openWeiXinService
		OpenWeiXinAuthorizerAccount account = new OpenWeiXinAuthorizerAccount();
		account.setUser_name(toUserName);
		if ("success".equals(status)) {
			account.setWxapp_audit_status("1");
			account.setWxapp_audit_msg("审核通过");
		} else if ("fail".equals(status)) {
			account.setWxapp_audit_status("2");
			account.setWxapp_audit_msg(reason);
		}
		int temp = openWeiXinService.updateAccount(account);
	}
	
	/**
	 * 审核成功
	 */
	public void wxappAuditSuccess() {
		
	}
	
	/**
	 * 审核失败
	 */
	public void wxappAuditFail() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取授权小程序帐号的可选类目
	 * @return
	 */
	@RequestMapping("/get_category.do")
	@ResponseBody
	public String getCategory1() {
		
		String url = "https://api.weixin.qq.com/wxa/get_category?access_token={ACCESS_TOKEN}";
		url = url.replace("{ACCESS_TOKEN}", accessToken);
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "GET", null);
		System.out.println(result);
		
		return "success";
	}
	
	/**
	 * 获取小程序的第三方提交代码的页面配置
	 * @return
	 */
	@RequestMapping("/getConfig.do")
	@ResponseBody
	public String getConfig1() {
		
		String url = "https://api.weixin.qq.com/wxa/get_page?access_token={ACCESS_TOKEN}";
		
		url = url.replace("{ACCESS_TOKEN}", accessToken);
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "GET", null);
		
		return "success";
	}
	
	/**
	 * 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
	 * @return
	 */
	@RequestMapping("/submitAudit.do")
	@ResponseBody
	public String submitAudit1() {
		
		String url = "https://api.weixin.qq.com/wxa/submit_audit?access_token={ACCESS_TOKEN}";
		url = url.replace("{ACCESS_TOKEN}", accessToken);
		
		String data = 
			"{"+
			"	\"item_list\": ["+
			"		{"+
			"			\"address\":\"pages/index/index\","+
			"			\"tag\":\"测试\","+
			"			\"first_class\": \"商业服务\","+
			"			\"second_class\": \"广告/设计\","+
			"                        \"first_id\":402,"+
			"                        \"second_id\":774,"+
			"			\"title\": \"首页\""+
			"		}"+
			"	]"+
			"}";
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "POST", data);
		System.out.println(result);
		
		return "success";
	}
	
	
	
	public static void main(String[] args) {
		/*String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={COMPONENT_ACCESS_TOKEN}";
		url = url.replace("{COMPONENT_ACCESS_TOKEN}", "");
		
		String data = "{" +
				"\"component_appid\" : \"wxb41e9be2f174588e\"" +
				"\"authorization_code\" : \"\"" +
				"}";
		
		System.out.println(data);*/
		
		/*HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "POST", data);*/
		
		
		
		/*String url1 = "https://api.weixin.qq.com/wxa/get_page?access_token={ACCESS_TOKEN}";
		
		url1 = url1.replace("{ACCESS_TOKEN}", "G4v6j_pkGt3JRrfXYMlhTMlgEYHpcpEAqxTbEJwYOg3EcDD8AvgbzqXt2z8DbTaDEliaGXemKFSA0vf_2bzziKXuPtLhf3C8Ogb6q2iXdir3ioQRaipD_dlv5DuPwytFCRAhAKDWLU");
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result1 = util.sendHttpsRequest(url1, "GET", null);
		System.out.println(result1);
		
		
		
		String url = "https://api.weixin.qq.com/wxa/get_category?access_token={ACCESS_TOKEN}";
		url = url.replace("{ACCESS_TOKEN}", "G4v6j_pkGt3JRrfXYMlhTMlgEYHpcpEAqxTbEJwYOg3EcDD8AvgbzqXt2z8DbTaDEliaGXemKFSA0vf_2bzziKXuPtLhf3C8Ogb6q2iXdir3ioQRaipD_dlv5DuPwytFCRAhAKDWLU");
		
		String result = util.sendHttpsRequest(url, "GET", null);
		System.out.println(result);*/
		
		
		String result = " {\"authorization_info\":{\"authorizer_appid\":\"wxf4da9da792c29053\",\"authorizer_access_token\":\"auQL9bqExqudpgBYf12yARfadRhRnh0H2WCYIcfCTRcJxp1t_H7Jlj64ln50WIWwQ7VRoGgCHkiX_ZCwjSLO1chntPvn0aqPEjKGgEWTkI_H4lrr_fuR2hgt3dAJX4pMWMFdAEDFVO\",\"expires_in\":7200,\"authorizer_refresh_token\":\"refreshtoken@@@aKxy1s6PWTKHhyiz8DRQ8oOhsJjLmNQvPM0RoDHI1fY\",\"func_info\":[{\"funcscope_category\":{\"id\":17}},{\"funcscope_category\":{\"id\":18}},{\"funcscope_category\":{\"id\":19}},{\"funcscope_category\":{\"id\":25},\"confirm_info\":{\"need_confirm\":0,\"already_confirm\":0,\"can_confirm\":0}},{\"funcscope_category\":{\"id\":30},\"confirm_info\":{\"need_confirm\":0,\"already_confirm\":0,\"can_confirm\":0}},{\"funcscope_category\":{\"id\":31},\"confirm_info\":{\"need_confirm\":0,\"already_confirm\":0,\"can_confirm\":0}}]}}";
		JSONObject resultJSON = JSONObject.parseObject(result);
		
		String authorizationStr = resultJSON.getString("authorization_info");
		JSONObject authorizationJSON = JSONObject.parseObject(authorizationStr);
		
		String appid = authorizationJSON.getString("authorizer_appid");
		String authorizer_access_token = authorizationJSON.getString("authorizer_access_token");
		System.out.println("appid:"+appid);
		System.out.println("token:"+authorizer_access_token);
	}
	
	@RequestMapping("/test1.do")
	@ResponseBody
	public String test1(HttpServletRequest request) {
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
		}
		return "success";
	}
}
