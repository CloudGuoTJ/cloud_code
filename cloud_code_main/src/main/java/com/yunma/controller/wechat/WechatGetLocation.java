package com.yunma.controller.wechat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import net.sf.json.JSONObject ;
import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.yunma.utils.WeChatConfig;


@Controller
public class WechatGetLocation extends BaseController{
	


	@RequestMapping("/POST/weChat/signature.do")
	@ResponseBody
	public JSONObject getSignature(@RequestParam("url")String url
			) {
		
		String accessToken = getAccessToken();  
	      
	    //2、获取Ticket  
	    String jsapi_ticket = getTicket(accessToken);  
	      
	    //3、时间戳和随机字符串  
	    String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
	    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
	      
//	    System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);  
	      
//	    //4、获取url  
//	    String url="";  
	    /*根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦 
	    String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"}; 
	    Arrays.sort(ArrTmp); 
	    StringBuffer sf = new StringBuffer(); 
	    for(int i=0;i<ArrTmp.length;i++){ 
	        sf.append(ArrTmp[i]); 
	    } 
	    */  
	      
	    //5、将参数排序并拼接字符串  
	    String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;  
	     String appId = WeChatConfig.APP_ID;
	    //6、将字符串进行sha1加密  
	    String signature =SHA1(str);  
//	    System.out.println("参数："+str+"\n签名："+signature); 
	    JSONObject result = new JSONObject();
	    result.put("signature", signature);
	    result.put("noncestr", noncestr);
	    result.put("timestamp", timestamp);
	    result.put("appId", appId);
		return result;
		
}
//	public static void main(String[] args) {
//		 //1、获取AccessToken  
//	    String accessToken = getAccessToken();  
//	      
//	    //2、获取Ticket  
//	    String jsapi_ticket = getTicket(accessToken);  
//	      
//	    //3、时间戳和随机字符串  
//	    String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
//	    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
//	      
//	    System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);  
//	      
//	    //4、获取url  
//	    String url="";  
//	    /*根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦 
//	    String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"}; 
//	    Arrays.sort(ArrTmp); 
//	    StringBuffer sf = new StringBuffer(); 
//	    for(int i=0;i<ArrTmp.length;i++){ 
//	        sf.append(ArrTmp[i]); 
//	    } 
//	    */  
//	      
//	    //5、将参数排序并拼接字符串  
//	    String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;  
//	     
//	    //6、将字符串进行sha1加密  
//	    String signature =SHA1(str);  
//	    System.out.println("参数："+str+"\n签名："+signature);  
//
//	}
	
	public static String getAccessToken() {  
	    String access_token = "";  
	    String grant_type = "client_credential";//获取access_token填写client_credential   
	    String AppId = WeChatConfig.APP_ID;//第三方用户唯一凭证  
	    String secret = WeChatConfig.SECRET;//第三方用户唯一凭证密钥，即appsecret   
	    //这个url链接地址和参数皆不能变  
	    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;  
	       
	    try {  
	        URL urlGet = new URL(url);  
	        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
	        http.setRequestMethod("GET"); // 必须是get方式请求  
	        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
	        http.setDoOutput(true);  
	        http.setDoInput(true);  
	        System.setProperty("sun.net.client.defaultConnectTimeout", "72000");// 连接超时30秒  
	        System.setProperty("sun.net.client.defaultReadTimeout", "72000"); // 读取超时30秒  
	        http.connect();  
	        InputStream is = http.getInputStream();  
	        int size = is.available();  
	        byte[] jsonBytes = new byte[size];  
	        is.read(jsonBytes);  
	        String message = new String(jsonBytes, "UTF-8");  
	        JSONObject demoJson = JSONObject.parseObject(message);  
	        System.out.println("JSON字符串："+demoJson);  
	        access_token = demoJson.getString("access_token");  
	        is.close();  
	    } catch (Exception e) {  
	            e.printStackTrace();  
	    }  
	    return access_token;  
	}  
	
	
	public static String getTicket(String access_token) {  
	    String ticket = null;  
	    String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变  
	    try {  
	        URL urlGet = new URL(url);  
	        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
	        http.setRequestMethod("GET"); // 必须是get方式请求  
	        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
	        http.setDoOutput(true);  
	        http.setDoInput(true);  
	        System.setProperty("sun.net.client.defaultConnectTimeout", "72000");// 连接超时30秒  
	        System.setProperty("sun.net.client.defaultReadTimeout", "72000"); // 读取超时30秒  
	        http.connect();  
	        InputStream is = http.getInputStream();  
	        int size = is.available();  
	        byte[] jsonBytes = new byte[size];  
	        is.read(jsonBytes);  
	        String message = new String(jsonBytes, "UTF-8");  
	        JSONObject demoJson = JSONObject.parseObject(message); 
	        System.out.println("JSON字符串："+demoJson);  
	        ticket = demoJson.getString("ticket");  
	        is.close();  
	    } catch (Exception e) {  
	            e.printStackTrace();  
	    }  
	    return ticket;  
	} 
	
	public static String SHA1(String decript) {  
	    try {  
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");  
	        digest.update(decript.getBytes());  
	        byte messageDigest[] = digest.digest();  
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        // 字节数组转换为 十六进制 数  
	            for (int i = 0; i < messageDigest.length; i++) {  
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
	                if (shaHex.length() < 2) {  
	                    hexString.append(0);  
	                }  
	                hexString.append(shaHex);  
	            }  
	            return hexString.toString();  
	   
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	} 

}
