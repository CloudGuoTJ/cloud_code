package com.common.util;
//
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
//import com.securitycode.cache.AccessTokenCache;
//import com.yunma.model.OAuthInfo;
//import com.securitycode.model.SysUser;
//import com.securitycode.model.WxConfig;
//import com.securitycode.service.WxConfigService;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
///**
// * Created by Administrator on 2016/4/24.
// */
public class WeixinUtil {
//    @Autowired
//    private WxConfigService wxConfigService;
//
	private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
//
    public static OauthGetTokenResponse getOAuthOpenId(String appid, String secret, String code) {
    	OauthGetTokenResponse oAuthInfo;
        CloseableHttpResponse response = null;

        try {
            /*oAuthInfo = (OAuthInfo) XMemcachedHelper.findCache("OAuthInfo_Appid_Secret_" + appid + "_" + secret);
            if(oAuthInfo != null){
                logger.info("从Memcached中获取到OAuthInfo信息");
                return oAuthInfo;
            }*/

            String o_auth_openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            String requestUrl = o_auth_openid_url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(requestUrl);

            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                logger.info("微信公众号，appid：" + appid + "，secret：" + secret + "未获取到OAuthInfo信息");
                return null;
            }

            String result = EntityUtils.toString(entity, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject == null || jsonObject.get("errcode") != null) {
                logger.info("errcode：" + jsonObject.get("errcode") + "，errmsg：" + jsonObject.get("errmsg"));
                logger.info("微信公众号，appid：" + appid + "，secret：" + secret + "，code：" + code + "未获取到OAuthInfo信息");
                return null;
            }

            oAuthInfo = new OauthGetTokenResponse();
            oAuthInfo.setAccessToken(jsonObject.getString("access_token"));
            oAuthInfo.setExpiresIn(jsonObject.getInteger("expires_in"));
            oAuthInfo.setRefreshToken(jsonObject.getString("refresh_token"));
            oAuthInfo.setOpenid(jsonObject.getString("openid"));
            oAuthInfo.setScope(jsonObject.getString("scope"));

            //XMemcachedHelper.set("OAuthInfo_Appid_Secret_" + appid + "_" + secret, oAuthInfo, 30 * 60);
        } catch (IOException e) {
            logger.error("根据厂商appid、secret和code查找OAuthInfo时：", e);
            return null;
        } catch (Exception e) {
            logger.error("根据厂商appid、secret和code查找OAuthInfo时：", e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("根据厂商appid、secret和code查找OAuthInfo时：", e);
                    return null;
                }
            }
        }

        //logger.info("获取OAuthInfo信息缓存在Memcached中");
        logger.info("获取OAuthInfo信息");
        return oAuthInfo;
    }
//
////    public   String  getToken(String appid,String secret){
////        String accessToken= wxConfigService.findByAccessToken(appid);
////        try {
////            if(StringUtils.isNotBlank(accessToken)){
////                return accessToken;
////            }else {
////                ApiConfig apiConfig=new ApiConfig(appid,secret);
////                accessToken= apiConfig.getAccessToken();
////                //更新数据库表中access_token
////                wxConfigService.updateOAuthAccessToken(appid, accessToken);
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////            logger.info("--------获取access_token异常--------");
////        }
////        return  accessToken;
////    }
//
//    @Test
//    public void testMothod(){
////        String text=getToken("wxb3ef53937fbe78db","404e736a07036b32e19a6ca3fe3873ef");
////        System.out.print(text);
//    }
}
