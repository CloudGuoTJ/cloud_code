package com.yunma.controller.couponJD;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.google.gson.Gson;
import com.jd.open.api.sdk.internal.util.HttpUtil;
import com.yunma.entity.jdcoupon.JDCouponCreateInfo;
import com.yunma.entity.jdcoupon.JDVendorInfo;
import com.yunma.service.jdcoupon.JDCouponService;
import com.yunma.utils.JOSConfig;

@Controller
public class JDCouponController {

	private Logger log = LoggerFactory.getLogger(JDCouponController.class);

	@Autowired
	private JDCouponService service;
	
	/**
	 * 京东登录授权
	 * @return
	 */
	@RequestMapping("/GET/JDCoupon/jdAuthLoginUrl.do")
	@ResponseBody
	public JSONObject jdAuthLoginUrl(){
		JSONObject result=new JSONObject();
		
		result.put("response_type", "code");
		result.put("client_id", JOSConfig.appKey);
		result.put("redirect_uri", JOSConfig.URL);
		
		return result;
	}
	
	/**
	 * 获取AccessToken
	 * @param code
	 * @param vendorId
	 * @param flag
	 * @return
	 */
	@RequestMapping("/GET/JDCoupon/getAccessTokenByCode.do")
	@ResponseBody
	public JSONObject getAccessTokenByCode(Integer state,String code) {
		JSONObject result=new JSONObject();
		String url = "https://oauth.jd.com/oauth/token";

		Map<String, String> params = new HashMap<String, String>();

		params.put("grant_type", "authorization_code");
		params.put("client_id", JOSConfig.appKey);
		params.put("client_secret", JOSConfig.appSecret);
		params.put("scope", "read");
		params.put("redirect_uri", JOSConfig.URL);
		params.put("code", code);
		params.put("state", "1234");
		
		try {
			
			String json=HttpUtil.doPost(url, params, 5000, 5000);
			Gson gson=new Gson();
			System.out.println("gson"+json);
			JDVendorInfo info=gson.fromJson(json, JDVendorInfo.class);
			info.setVendorId(state);
			if (info.getAccess_token() != null) {
				
				JDVendorInfo vInfo=service.getJDVendorInfo(state);
				
				if (vInfo != null) {
					int temp = service.updateJDVendorInfoByVendorId(info);
					if (temp > 0) {
						result.put("status", 1);
						result.put("msg", "授权成功！");
					}else{
						result.put("status", -1);
						result.put("msg", "授权失败！");
					}
				}else{
					int temp = service.addJDUserInfo(info);
					
					if (temp > 0) {
						result.put("status", 1);
						result.put("msg", "授权成功！");
					}else{
						result.put("status", -1);
						result.put("msg", "授权失败！");
					}
					
				}
				
			}else{
				Object obj=JSONUtil.getStringFromJSONObject(json, "error_description");
				result.put("status", -2);
				result.put("msg", obj.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	/**
	 * 判断access_token是否在有效时间内
	 * @return
	 */
	@RequestMapping("/GET/JDCoupon/isAccessTokenExpiresIn.do")
	@ResponseBody
	public JSONObject isAccessTokenExpiresIn(Integer vendorId){
		
		return service.getJDVendorInfoByVendorId(vendorId);
	}
	
	/**
	 * 新增优惠券
	 * @param info
	 * @return
	 */
	@RequestMapping("/ADD/JDCoupon/addJDCouponinfo.do")
	@ResponseBody
	public JSONObject addJDCouponinfo(JDCouponCreateInfo info){
		
		return service.addJDCouponinfo(info);
	}
	
	/**
	 * 根据优惠券id查询优惠券信息
	 * @param response
	 * @param couponId
	 * @param vendorId
	 */
	@RequestMapping("/GET/JDCoupon/getJDCouponInfo.do")
	@ResponseBody
	public void getJDCouponInfo(HttpServletResponse response,String couponId){
		String result=service.getJDCouponInfo(couponId);

		// 输出JSON数据
		// 这里直接通过response输出JSON字符串
		// Spring MVC也提供了输出JSON数据的方法
		// 设置编码格式
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取京东优惠券列表
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/JDCoupon/getJDCouponList.do")
	@ResponseBody
	public JSONObject getJDCouponList(Integer vendorId){
		
		return service.getJDCouponList(vendorId);
	}
	
	
}
