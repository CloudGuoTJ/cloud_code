package com.yunma.utils.weChat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yunma.controller.wechat.WechatController;
import com.yunma.utils.WeChatConfig;



public class SendRedPacketsUtil {
	
	
	public static Map<Object, Object> postCertificate(String url,String requestXml){
		Logger log = LoggerFactory.getLogger(WechatController.class);

		Map<Object, Object> restultMap=null;
		BufferedReader bufferedReader1=null;
		try {
			//定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12"); 
			//读取本机存放的PKCS12证书文件 
			FileInputStream instream = new FileInputStream(new File("/root/credentials/apiclient_cert.p12"));
			//FileInputStream instream = new FileInputStream(new File("E:\\work\\credentials\\ymjs\\apiclient_cert.p12"));//用来本地测试
			//指定PKCS12的密码(商户ID) 
			keyStore.load(instream, WeChatConfig.MCH_ID.toCharArray());
			SSLContext sslContext =SSLContexts.custom().loadKeyMaterial(keyStore, WeChatConfig.MCH_ID.toCharArray()).build();
			//指定TLS版本 
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,new String[] {"TLSv1"},null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			//设置httpclient的SSLSocketFactory 
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost=new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity=new StringEntity(requestXml,"UTF-8");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);
			// 判断网络连接是否成功
            if (response.getStatusLine().getStatusCode() != 200) {
            	//netExp=true;
            	System.out.println("网络错误异常");
            	log.info("------发送前网络错误异常,"+response.getStatusLine().getStatusCode());
            }
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream urlStream1 = entity.getContent();
				bufferedReader1 = new BufferedReader(  
						new InputStreamReader(urlStream1,"UTF-8"));  
				String ss1 = ""; 
				String total1="";
				while ((ss1 = bufferedReader1.readLine()) != null) {  
					total1 += ss1;  
				}
				restultMap = XMLUtil.doXMLParse(total1);
				log.info("====请求返回结果"+total1);
				bufferedReader1.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			return restultMap;
	}
	
	public static Map<Object, Object> postCertificateV2(String url,String mchId,String requestXml,String file){
		Logger log = LoggerFactory.getLogger(WechatController.class);

		Map<Object, Object> restultMap=null;
		BufferedReader bufferedReader1=null;
		try {
			//定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12"); 
			//读取本机存放的PKCS12证书文件 
			FileInputStream instream = new FileInputStream(new File(file));
			//FileInputStream instream = new FileInputStream(new File("E:\\work\\credentials\\ymdm\\apiclient_cert.p12"));//用来本地测试
			//指定PKCS12的密码(商户ID) 
			keyStore.load(instream, mchId.toCharArray());
			SSLContext sslContext =SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			//指定TLS版本 
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,new String[] {"TLSv1"},null,
			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			//设置httpclient的SSLSocketFactory 
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost=new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity=new StringEntity(requestXml,"UTF-8");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);
			// 判断网络连接是否成功
            if (response.getStatusLine().getStatusCode() != 200) {
            	//netExp=true;
            	System.out.println("网络错误异常");
            	log.info("------发送前网络错误异常,"+response.getStatusLine().getStatusCode());
            }
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream urlStream1 = entity.getContent();
				bufferedReader1 = new BufferedReader(  
						new InputStreamReader(urlStream1,"UTF-8"));  
				String ss1 = ""; 
				String total1="";
				while ((ss1 = bufferedReader1.readLine()) != null) {  
					total1 += ss1;  
				}
				restultMap = XMLUtil.doXMLParse(total1);
				log.info("====请求返回结果"+total1);
				bufferedReader1.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return restultMap;
	}
	
}
