package com.yunma.controller.openweixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.message.aes.AesException;
import com.github.sd4324530.fastweixin.message.aes.WXBizMsgCrypt;
import com.yunma.entity.openweixin.OpenWeiXinAuthVendor;
import com.yunma.entity.openweixin.OpenWeiXinAuthorizerAccount;
import com.yunma.entity.openweixin.OpenWeiXinConfig;
import com.yunma.service.openweixin.OpenWeiXinService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;

/**
 * 微信开放平台（open.weixin.qq.com）
 * 不是公众平台（https://mp.weixin.qq.com/）
 * @author LUO
 *
 */
@Controller
@RequestMapping("/openweixin")
public class OpenWeiXinController {
	
	private Logger log = LoggerFactory.getLogger(OpenWeiXinController.class);

	@Resource
	private OpenWeiXinService openWeiXinService;
	
	/**
	 * 授权事件接收URL
	 * @param request
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param msg_signature 消息体签名，用于验证消息体的正确性
	 * @param signature 签名
	 * @param encrypt_type 加密类型，为aes
	 */
	@RequestMapping("/getAuthorize.do")
	@ResponseBody
	public String getAuthorize(HttpServletRequest request,String timestamp,String nonce,String msg_signature,String signature,String encrypt_type){
		log.debug("进入 /openweixin/getAuthorize.do");
		log.debug("timestamp："+timestamp);
		log.debug("nonce："+nonce);
		log.debug("msg_signature："+msg_signature);
		log.debug("signature："+signature);
		log.debug("encrypt_type："+encrypt_type);
		 try {
			BufferedReader   bufferedReader =   new BufferedReader(
			            new InputStreamReader( (ServletInputStream)request.getInputStream(),"utf-8")); 
			    StringBuffer stringBuffer =new StringBuffer("");
			    String temp;
			    while((temp=bufferedReader.readLine())!=null){
			        stringBuffer.append(temp);
			    }
			    bufferedReader.close();
			    String   xml  =  stringBuffer.toString();
			    log.debug("原始请求串:"+xml);
			    
			    WXBizMsgCrypt pc = new WXBizMsgCrypt(WeChatConfig.TOKEN, WeChatConfig.OPEN_ENCODINGAESKEY, WeChatConfig.OPEN_APPID);
			    String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, xml);
				log.debug("解密后明文: " + result2);
				String cvt = xmlParase(result2);
				if (cvt == null) {
					log.debug("ComponentVerifyTicket解密失败,ComponentVerifyTicket为:" + cvt);
					return "success";
				}
				log.debug("ComponentVerifyTicket:" + cvt);
				OpenWeiXinConfig config = new OpenWeiXinConfig();
				config.setOpenWeixinAppid(WeChatConfig.OPEN_APPID);
				config.setComponentVerifyTicket(cvt);
				int temp1 = openWeiXinService.updateComponent_verify_ticketByAppid(config);
				if (temp1 > 0) {
					log.debug("更新开放平台 ComponentVerifyTicket 字段 成功");
				}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		 log.debug("结束 /openweixin/getAuthorize.do");
		 return "success";
	}
	
	/**
	 * 解析xml 获取ComponentVerifyTicket
	 * @param xml
	 * @return
	 * @throws AesException
	 */
	private static String xmlParase(String xml) throws AesException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("ComponentVerifyTicket");
			
			return nodelist1.item(0).getTextContent();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws AesException {
		String encodingAesKey = "rwSplPWn3hLlcKb1mRupCudQDJnsSeyxNujGDHQzrsw";
		String token = "xiyanggzh";
		String timestamp = "1502184876";
		String nonce = "1040792040";
		String appId = "wxb41e9be2f174588e";
		String replyMsg = "<xml>    <ToUserName><![CDATA[gh_3c884a361561]]></ToUserName>    <Encrypt><![CDATA[73JqeDjxvb9M8OAoVbHDa7HYmHTfxxj7QMFUWjA3oWIomNTkN+UZJ8inpwsMLDIUX0GrvjWEOvGThQE9SOJZEtc5DPNe6fxZnE/j3/d1e1Tjm+rjj3IXOFLf0KSv4VS2qZkz07jUhydzf7h3qm+CcFxJjTtg2gLqn/U+B2nvlfl6zNOslPiuqP5nejT5WkG4VLP+NtQ+vPbNMFW29b+QoiwiKGmlV28ZKGh+SHEGf8OfMdtbM1w04MEMJVG3XD4r053dyJs0B/cgTLWBqnevlp3pHxlWB13XiWZ77ggp7o25maxvoDVkkDFTaIYYV2e5aKFAZWBNzKwBYBCgQWnH6KMp0JnLT+BoapdGv1KOxBwMDIY2J97IkoFmUAUo/XfP21NNYqg9VI3w9sF+wUvKmRhhtERI4jBSMyANgRTMU+jsaORyIx3WSx0GtZkO/Zmn6OVMYd1jAxVvUukrbH+y+A==]]></Encrypt></xml>";
		String msgSignature = "905170306472f85b335d1102bc74865cf2a3b0b0";
		
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
	    String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, replyMsg);
		System.out.println("解密后明文: " + result2);
		
		/*HttpRequestUtil util = new HttpRequestUtil();
		String data = "{\"component_appid\":\""+WeChatConfig.OPEN_APPID+"\",\"component_appsecret\":\""+WeChatConfig.OPEN_APPSECRET+"\",\"component_verify_ticket\":\"ticket@@@u5kWqZWY6QgR4vEYnp-yb20HenG-d9Y9ZsxQDFLI_udMVVoLeuGQDwyfgxN_ykZSdBfCtY9G2fluJc3NVeaA9A\"}";
		//Map<Object, Object> map = util.sendHttps(WeChatConfig.OPEN_COMPONENT_ACCESS_TOKEN, data, "E:\\work\\credentials\\ymjs\\apiclient_cert.p12");
		String a = util.sendHttpsRequest(WeChatConfig.OPEN_COMPONENT_ACCESS_TOKEN, "POST", data);
		System.out.println(a);*/
		replyMsg = "<xml><ToUserName><![CDATA[gh_3c884a361561]]></ToUserName>"+
					"<FromUserName><![CDATA[ozy4qt5QUADNXORxCVipKMV9dss0]]></FromUserName>"+
					"<CreateTime>1502184876</CreateTime>"+
					"<MsgType><![CDATA[text]]></MsgType>"+
					"<Content><![CDATA[TESTCOMPONENT_MSG_TYPE_TEXT_callback]]></Content>"+
					"<MsgId>6451834915372576941</MsgId>"+
					"</xml>";
		String result3 = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("result3:"+result3);
		
	}
	
	/**
	 * 获取第三方平台component_access_token
	 * @return
	 */
	@RequestMapping("/getComponentAccessToken.do")
	@ResponseBody
	public String getComponentAccessToken() {
		return getCAT();
	}
	
	/**
	 * 获取预授权码pre_auth_code
	 * @return
	 */
	@RequestMapping("/getPreAuthCode.do")
	@ResponseBody
	public String getPreAuthCode() {
		return getPAC();
	}
	
	/**
	 * 获取预授权码
	 * @return
	 */
	private String getPAC() {
		log.debug("进入OpenWeiXinController.getPAC(),获取预授权码 方法。。。");
		OpenWeiXinConfig config = openWeiXinService.getComponent_verify_ticketByAppid(WeChatConfig.OPEN_APPID);
		Date dbTime = config.getPreAuthCodeTime();
		Date currTime = new Date();
		long time = 19*60*1000;//官方给出的时间为20分钟   这里 使用19分钟即可
		if ((dbTime.getTime()+time)<currTime.getTime()) {
			String result = null;
			String token = getCAT();
			if (token != null) {
				HttpRequestUtil util = new HttpRequestUtil();
				String url = WeChatConfig.OPEN_PRE_AUTH_CODE;
				url = url.replace("{component_access_token}", token);
				String data = "{\"component_appid\":\""+WeChatConfig.OPEN_APPID+"\"}";
				result = util.sendHttpsRequest(url, "POST", data);
				log.debug("预授权码result:"+result);
				JSONObject resultJSON = JSONObject.parseObject(result);
				String pre_auth_code = (String) resultJSON.get("pre_auth_code");
				log.debug("预授权码:"+pre_auth_code);
				
				if (pre_auth_code == null) {
					log.debug("获取预授权码pre_auth_code失败,pre_auth_code为:"+pre_auth_code);
					return null;
				}
				
				config.setPreAuthCode(pre_auth_code);
				
				int temp = openWeiXinService.updatePreAuthCode(config);
				if (temp > 0) {
					log.debug("更新预授权码成功");
					return pre_auth_code;
				} else {
					log.debug("更新预授权码失败");
					return null;
				}
			} else {
				return null;
			}
		} else {
			log.debug("未超过19分钟，获取数据库中pre_auth_code，结束OpenWeiXinController.getPAC(),获取预授权方法");
			return config.getPreAuthCode();
		}
	}
	
	
	/**
	 * 用户授权后的回调地址
	 * @param auth_code
	 */
	@RequestMapping("/authcallback.do")
	@ResponseBody
	public String authCallBack(String auth_code,HttpServletRequest request) {
		log.debug("auth_code:"+auth_code);
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
			System.out.println("授权回调返回参数："+paraName+": "+request.getParameter(paraName));  
		}
		apiQueryAuth(auth_code);
		return auth_code;
	}
	
	/**
	 * 获取授权公众号或小程序的接口调用凭据（令牌）
	 * @param auth_code
	 */
	public void apiQueryAuth(String auth_code) {
		
		String url = WeChatConfig.OPEN_API_QUERY_AUTH;
		url = url.replace("{component_access_token}", getCAT());
		
		String data = "{" +
				"\"component_appid\" : \""+WeChatConfig.OPEN_APPID+"\"," +
				"\"authorization_code\" : \""+auth_code+"\"" +
				"}";
		
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendHttpsRequest(url, "POST", data);
		JSONObject resultJSON = JSONObject.parseObject(result);
		
		String authorizationStr = resultJSON.getString("authorization_info");
		JSONObject authorizationJSON = JSONObject.parseObject(authorizationStr);
		
		String appid = authorizationJSON.getString("authorizer_appid");
		String authorizer_access_token = authorizationJSON.getString("authorizer_access_token");
		
		getAuthorizerInfo(appid,authorizer_access_token);
	}
	
	/**
	 * 获取授权方的帐号基本信息
	 * @param appid
	 * @param token
	 */
	public void getAuthorizerInfo(String appid,String token) {
		String url = WeChatConfig.OPEN_GET_AUTHORIZER_INFO;
		url = url.replace("{component_access_token}", getCAT());
		
		String data = "{" +
				"\"component_appid\" : \""+WeChatConfig.OPEN_APPID+"\"," +
				"\"authorizer_appid\" : \""+appid+"\"" +
				"}";
		
		HttpRequestUtil util = new HttpRequestUtil();
		
		String result = util.sendHttpsRequest(url, "POST", data);
		JSONObject resultJSON = JSONObject.parseObject(result);
		
		String authorization_info = resultJSON.getString("authorization_info");
		JSONObject authorization_infoJSON = JSONObject.parseObject(authorization_info);
		String authorizer_refresh_token = authorization_infoJSON.getString("authorizer_refresh_token");
		
		String authorizerStr = resultJSON.getString("authorizer_info");
		JSONObject authorizerJSON = JSONObject.parseObject(authorizerStr);
		
		String nick_name = authorizerJSON.getString("nick_name");
		String head_img = authorizerJSON.getString("head_img");
		String user_name = authorizerJSON.getString("user_name");
		String principal_name = authorizerJSON.getString("principal_name");
		String signature = authorizerJSON.getString("signature");
		
		
		
		
		
		
		
		OpenWeiXinAuthorizerAccount account = new OpenWeiXinAuthorizerAccount();
		account.setAppid(appid);
		account.setNick_name(nick_name);
		account.setHead_img(head_img);
		account.setUser_name(user_name);
		account.setPrincipal_name(principal_name);
		account.setSignature(signature);
		account.setAuthorizer_refresh_token(authorizer_refresh_token);
		
		int temp = openWeiXinService.hasAccount(appid);
		if (temp == 0) {
			int temp1 = openWeiXinService.addAccount(account);
		}
		
		
		log.debug("获取授权方的帐号基本信息:"+result);
		
	}
	
	
	/**
	 * 获取厂商授权信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getAuthByVendorId.do")
	@ResponseBody
	public JSONObject getAuthByVendorId(Integer vendorId) {
		JSONObject result = new JSONObject();
		OpenWeiXinAuthVendor auth = openWeiXinService.getAuthByVendorId(vendorId);
		if (auth != null) {
			result.put("vendorId", auth.getVendorId());
			result.put("is_auth", auth.getIsAuth());
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取第三方平台component_access_token
	 * @return
	 */
	public String getCAT() {
		log.debug("进入OpenWeiXinController.getCAT(),获取component_access_token方法");
		log.debug("appid:"+WeChatConfig.OPEN_APPID);
		OpenWeiXinConfig config = openWeiXinService.getComponent_verify_ticketByAppid(WeChatConfig.OPEN_APPID);
		Date dbTime = config.getComponentAccessTokenTime();
		Date currTime = new Date();
		long time = 7100*1000;//微信官方给出的是7200秒  这里 使用7100秒 即可
		
		if ((dbTime.getTime()+time)<currTime.getTime()) {
			HttpRequestUtil util = new HttpRequestUtil();
			String data = "{\"component_appid\":\""+WeChatConfig.OPEN_APPID+"\",\"component_appsecret\":\""+WeChatConfig.OPEN_APPSECRET+"\",\"component_verify_ticket\":\""+config.getComponentVerifyTicket()+"\"}";
			String result = util.sendHttpsRequest(WeChatConfig.OPEN_COMPONENT_ACCESS_TOKEN, "POST", data);
			
			JSONObject resultJSON = (JSONObject) JSONObject.parse(result);
			String component_access_token = (String) resultJSON.get("component_access_token");
			log.debug("component_access_token:"+component_access_token);
			
			if (component_access_token == null) {
				log.debug("获取component_access_token失败,component_access_token为:"+component_access_token);
				return null;
			}
			
			config.setComponentAccessToken(component_access_token);
			
			int temp = openWeiXinService.updateComponentAccessToken(config);
			if (temp > 0) {
				log.debug("更新component_access_token成功");
				return component_access_token;
			} else {
				log.debug("更新component_access_token失败");
				return null;
			}
		} else {
			log.debug("为超过7100秒，获取数据库中component_access_token，结束OpenWeiXinController.getCAT(),获取component_access_token方法");
			return config.getComponentAccessToken();
		}
	}
	
	/**
	 * 开放平台回调地址
	 * @param code
	 * @param state
	 * @param appid
	 */
	@RequestMapping("/callback.do")
	@ResponseBody
	public String callback(String code,String state,String appid) {
		log.debug("code:"+code);
		String url = WeChatConfig.OPEN_ACCESS_TOKEN;
		url = url.replace("{APPID}", "wx620ee71a7e75ffe9");//云码demo appid
		url = url.replace("{CODE}", code);
		url = url.replace("{COMPONENT_APPID}", WeChatConfig.OPEN_APPID);
		url = url.replace("{COMPONENT_ACCESS_TOKEN}", getCAT());
		log.debug("url:"+url);
		HttpRequestUtil util = new HttpRequestUtil();
		String result = util.sendGet(url, null);
		log.debug("result:"+result);
		return result;
	}
	
	/**
	 * 获取授权链接地址
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getauthlink.do")
	@ResponseBody
	public String getauthlink() {
		String url = WeChatConfig.OPEN_AUTH;
		url = url.replace("{component_appid}", WeChatConfig.OPEN_APPID);
		url = url.replace("{pre_auth_code}", getPAC());
		url = url.replace("{redirect_uri}", "https%3a%2f%2fym-a.top%2fcloud_code%2fopenweixin%2fauthcallback.do");
		return url;
	}
	
}
