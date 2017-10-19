package com.yunma.controller.openweixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.message.aes.AesException;
import com.github.sd4324530.fastweixin.message.aes.WXBizMsgCrypt;
import com.yunma.entity.openweixin.OpenWeiXinConfig;
import com.yunma.service.openweixin.OpenWeiXinService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;

/**
 * 公众号消息与事件接收URL
 * @author LUO
 *
 */
@Controller
public class OpenWeiXinCallbackController {
	
	@Resource
	private OpenWeiXinController openWeiXinController;
	
	@Resource
	private OpenWeiXinService openWeiXinService;
	
	@Resource
	private OpenWeiXinWxAppController openWeiXinWxAppController;
	
	private Logger log = LoggerFactory.getLogger(OpenWeiXinController.class);
	
	/**
	 * 通过该URL接收公众号消息和事件推送，该参数按规则填写
	 * （需包含/$APPID$，如www.abc.com/$APPID$/callback），
	 * 实际接收消息时$APPID$将被替换为公众号AppId。
	 */
	@RequestMapping("/openweixincallback/{APPID}")
	public void callback(@PathVariable String APPID,String timestamp,String nonce,String msg_signature,String signature,String encrypt_type,HttpServletRequest request,HttpServletResponse response) {
		
		log.debug("进入 开放平台 接收公众号消息和事件推送 方法");
		log.debug("APPID:"+APPID);
		log.debug("timestamp:"+timestamp);
		log.debug("nonce:"+nonce);
		log.debug("msg_signature："+msg_signature);
		log.debug("signature："+signature);
		log.debug("encrypt_type："+encrypt_type);
		
		String token = "xiyanggzh";
		String encodingAesKey = "rwSplPWn3hLlcKb1mRupCudQDJnsSeyxNujGDHQzrsw";
		String appId = "wxb41e9be2f174588e";
		
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
				log.debug("微信推送加密字符串:"+xml);
				
				WXBizMsgCrypt pc = new WXBizMsgCrypt(WeChatConfig.TOKEN, WeChatConfig.OPEN_ENCODINGAESKEY, WeChatConfig.OPEN_APPID);
				String decXml = pc.decryptMsg(msg_signature, timestamp, nonce, xml);
				log.debug("解密后明文: " + decXml);
				
				String toUserName = xmlParase("ToUserName",decXml);
				String msgType = xmlParase("MsgType",decXml);
				String content = "";
				try {
					content = xmlParase("Content",decXml);
				} catch (Exception e) {
					content = xmlParase("Event",decXml);
				}
				
				
				if ("gh_3c884a361561".equals(toUserName)) {// 微信全网测试账号
					QWFBCheck(msgType,content,timestamp,nonce,pc,response);
				} else {
					if ("event".equals(msgType)) {
						//判断是否为小程序的回调消息
						if ("weapp_audit_success".equals(content)) {//审核成功
							openWeiXinWxAppController.wxappAuditResult(toUserName,content,"success",null);
						} else if ("weapp_audit_fail".equals(content)) {//审核失败
							String reason = xmlParase("Reason",decXml);
							openWeiXinWxAppController.wxappAuditResult(toUserName,content,"fail",reason);
						}
					}
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
			
	}
	
	/**
	 * 全网发布检测
	 * @param msgType
	 * @param content
	 * @param timestamp
	 * @param nonce
	 * @param pc
	 * @param response
	 * @throws AesException
	 * @throws IOException
	 */
	public void QWFBCheck(String msgType,String content,String timestamp,String nonce,WXBizMsgCrypt pc,HttpServletResponse response) throws AesException, IOException {
		String result = "";
		Long createTime = System.currentTimeMillis() / 1000;  
		//全网发布：1、模拟粉丝触发专用测试公众号的事件
		if ("event".equals(msgType) && "LOCATION".equals(content)) {
			result = "<xml><ToUserName><![CDATA[ozy4qt5QUADNXORxCVipKMV9dss0]]></ToUserName>"+
					"<FromUserName><![CDATA[gh_3c884a361561]]></FromUserName>"+
					"<CreateTime>"+createTime+"</CreateTime>"+
					"<MsgType><![CDATA[text]]></MsgType>"+
					"<Content><![CDATA[LOCATIONfrom_callback]]></Content>"+
					"</xml>";
			result = pc.encryptMsg(result, timestamp, nonce);
			log.debug("全网发布1，加密后:"+result);
			//return result;
			response.getWriter().write(result);
			/*output(response,result);*/
			
		//全网发布：2、模拟粉丝发送文本消息给专用测试公众号
		} else if ("text".equals(msgType) && "TESTCOMPONENT_MSG_TYPE_TEXT".equals(content)) {
			result = "<xml>" +
					"<ToUserName><![CDATA[ozy4qt5QUADNXORxCVipKMV9dss0]]></ToUserName>"+
					"<FromUserName><![CDATA[gh_3c884a361561]]></FromUserName>"+
					"<CreateTime>"+System.currentTimeMillis()+"</CreateTime>"+
					"<MsgType><![CDATA[text]]></MsgType>"+
					"<Content><![CDATA[TESTCOMPONENT_MSG_TYPE_TEXT_callback]]></Content>"+
					"</xml>";
			result = pc.encryptMsg(result, timestamp, nonce);
			log.debug("全网发布2:"+result);
			response.getWriter().write(result);
			
		//全网发布：3、模拟粉丝发送文本消息给专用测试公众号【返回api文本消息】
		} else if ("text".equals(msgType) && content.indexOf("QUERY_AUTH_CODE") > -1) {
			
			//需在5秒内返回空串表明暂时不回复，然后再立即使用客服消息接口发送消息回复粉丝  
			response.getWriter().print("");
			
			String query_auth_code = content.substring(16,content.length());
			log.debug("content:"+query_auth_code);
			
			String component_access_token = openWeiXinController.getCAT();
			if (component_access_token != null) {
				HttpRequestUtil util = new HttpRequestUtil();
				OpenWeiXinConfig  config = openWeiXinService.getComponent_verify_ticketByAppid(WeChatConfig.OPEN_APPID);
				log.debug("auth_code:"+config.getAuthCode());
				
				String url = WeChatConfig.OPEN_AUTHORIZER_ACCESS_TOKEN;
				url = url.replace("{component_access_token}", component_access_token);
				String data = 
						"{"+
						"\"component_appid\":\""+WeChatConfig.OPEN_APPID+"\" ,"+
						"\"authorization_code\": \""+query_auth_code+"\""+
						"}";
				String result1 = util.sendPost(url, data);
				log.debug("全网发布3 返回值："+result1);
				
				JSONObject result1JSON = JSONObject.parseObject(result1);
				String authorization_info = result1JSON.get("authorization_info").toString();
				JSONObject authorization_infoJSON = JSONObject.parseObject(authorization_info);
				String accessToken = (String) authorization_infoJSON.get("authorizer_access_token");
				log.debug("全网发布3 accessToken："+accessToken);
				
				url = WeChatConfig.CUSTOM_SEND_MESSAGE;
				url = url.replace("{ACCESS_TOKEN}", accessToken);
				
				String data1 = 
						"{"+
							"\"touser\":\"ozy4qt5QUADNXORxCVipKMV9dss0\","+
							"\"msgtype\":\"text\","+
							"\"text\":{"+
								"\"content\":\""+query_auth_code+"_from_api\""+
							"}"+
						"}";
				String result2 = util.sendPost(url, data1);
				log.debug("全网发布3 发送客服消息返回值：" + result2);
			}
		}
		log.debug("结束 开放平台 接收公众号消息和事件推送 方法");
	}
	
    /** 
     * 工具类：回复微信服务器"文本消息" 
     * @param response 
     * @param returnvaleue 
     */  
    public void output(HttpServletResponse response,String returnvaleue){  
        try {  
            PrintWriter pw = response.getWriter();  
            pw.write(returnvaleue);  
//          System.out.println("****************returnvaleue***************="+returnvaleue);  
            pw.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
    /**
     * 解析xml
     * @param type 要提取的key
     * @param xml xml 输入值
     * @return
     */
	private static String xmlParase(String type,String xml) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName(type);
			return nodelist1.item(0).getTextContent();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
