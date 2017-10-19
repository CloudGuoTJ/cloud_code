package com.yunma.controller.wxConfig;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.DateUtils;
import com.yunma.cache.WxConfigCache;
import com.yunma.model.WxConfig;
import com.yunma.service.wxConfig.WxConfigService;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.utils.PageBean;
import com.yunma.vo.wxConfig.WxConfigGzhVo;
import com.yunma.vo.wxConfig.WxConfigVo;

@Controller
public class WxConfigController extends BaseController{
	
	@Autowired
	private WxConfigService wxConfigService;
	
	@Autowired
	private WxConfigCache wxConfigCache;
	
	/**
	 * 新增微信公众号信息
	 * @param request
	 * @param vendorId
	 * @param appid
	 * @param appsecret
	 * @param wxQrCode
	 * @param redirect_uri
	 * @param suCaiUrl
	 * @param mchId
	 * @param certName
	 * @param wxKey
	 * @param sendName
	 * @param wishing
	 * @return
	 */
	@RequestMapping("/ADD/wxconfig/info.do")
	@ResponseBody
	public JSONObject addWxConfigInfo(HttpServletRequest request,
			Integer vendorId,
			String appid,
			String appsecret,
			MultipartFile img_wxQrCode,
			String redirect_uri,
			String suCaiUrl,
			String mchId,
			MultipartFile img_certName,
			String wxKey,
			String sendName,
			String wishing
			){
		JSONObject result = new JSONObject();
		WxConfigVo vo = new WxConfigVo();
		ImageUploladUtil util = new ImageUploladUtil ();
		int vendoeId = wxConfigService.countWxConfig();
		
			try {
				if (request instanceof MultipartHttpServletRequest){

				request.setCharacterEncoding("UTF-8");
				
				vo.setVendorId(vendorId);
				vo.setAppid(appid);
				vo.setAppsecret(appsecret);
				vo.setRedirect_uri(redirect_uri);
				vo.setSuCaiUrl(suCaiUrl);
				vo.setMchId(mchId);
				vo.setWxKey(wxKey);
				vo.setSendName(sendName);
				vo.setWishing(wishing);
				
				if (img_wxQrCode !=null && img_wxQrCode.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(img_wxQrCode, result);
					if ( (Integer)imageResult.get("uploadState") > 0) {
						vo.setImg_wxQrCode(imageResult.getString("imgUrl").toString());
					}
				}
				if (img_certName !=null && img_certName.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(img_certName, result);
					if ( (Integer)imageResult.get("uploadState") > 0) {
						vo.setImg_certName(imageResult.getString("imgUrl").toString());
					}
				}
			}
			int temp = wxConfigService.addWxConfig(vo);
			
			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "新增失败");	
				}
			}catch (Exception e) {
				
				e.printStackTrace();
			}	
		return result;
	}
	/**
	 * 修改公众号信息
	 * @param request
	 * @param id
	 * @param vendorId
	 * @param appid
	 * @param appsecret
	 * @param img_wxQrCode
	 * @param redirect_uri
	 * @param suCaiUrl
	 * @param mchId
	 * @param img_certName
	 * @param wxKey
	 * @param sendName
	 * @param wishing
	 * @return
	 */
	@RequestMapping("/UPDATE/wxconfig/info.do")
	@ResponseBody
	public JSONObject updateWxConfig(HttpServletRequest request,
			Integer id,
			Integer vendorId,
			String appid,
			String appsecret,
			MultipartFile img_wxQrCode,
			String redirect_uri,
			String suCaiUrl,
			String mchId,
			MultipartFile img_certName,
			String wxKey,
			String sendName,
			String wishing
			){
		JSONObject result = new JSONObject();
		WxConfigVo vo = new WxConfigVo();
		ImageUploladUtil util = new ImageUploladUtil ();
		
			try {
				if (request instanceof MultipartHttpServletRequest){

				request.setCharacterEncoding("UTF-8");
				vo.setId(id);
				vo.setVendorId(vendorId);
				vo.setAppid(appid);
				vo.setAppsecret(appsecret);
				vo.setRedirect_uri(redirect_uri);
				vo.setSuCaiUrl(suCaiUrl);
				vo.setMchId(mchId);
				vo.setWxKey(wxKey);
				vo.setSendName(sendName);
				vo.setWishing(wishing);
				
				if (img_wxQrCode !=null && img_wxQrCode.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(img_wxQrCode, result);
					if ( (Integer)imageResult.get("uploadState") > 0) {
						vo.setImg_wxQrCode(imageResult.getString("imgUrl").toString());
					}
				}
				if (img_certName !=null && img_certName.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(img_certName, result);
					if ( (Integer)imageResult.get("uploadState") > 0) {
						vo.setImg_certName(imageResult.getString("imgUrl").toString());
					}
				}
			}
			int temp = wxConfigService.updateWxConfig(vo);
			
			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "修改成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "修改失败");	
				}
			}catch (Exception e) {
				
				e.printStackTrace();
			}	
		return result;
	}
	/**
	 * 根据vendorId查询企业微信公众号信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/wxconfig/info.do")
	@ResponseBody
	public JSONObject getBasicInfo(Integer vendorId) {
		logger.info("VendorController.basicInfo中获取企业信息开始时间：{}"
				+ DateUtils.getTimeString());
		JSONObject result = wxConfigCache.getWxConfigInfo(vendorId);
		logger.info("VendorController.basicInfo中获取企业信息结束时间：{}"
				+ DateUtils.getTimeString());
		return result;
	}
	/**
	 * 分页查询公众号信息
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/wxconfig/allinfo.do")
	@ResponseBody
	public PageBean getWxConfigInfo(PageBean pageBean, WxConfig vo) {

		return wxConfigService.getWxConfigInfo(pageBean, vo);
	}
	
	
	/**
	 * 新增从微擎上获取的公众号信息
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("/ADD/wxConfig/addWxGzh.do")
	@ResponseBody
	public JSONObject addWxGzh(
			Integer vendorId,
			String cname,
			String account,
			String original,
			String description,
			String level,
			String key,
			String secret,
			String qrcode,
			String headimg){
		JSONObject result=new JSONObject();
		WxConfigGzhVo vo=new WxConfigGzhVo();
		WxConfigGzhVo wVo=wxConfigService.getWxGzhInfo(vendorId);
		
		if (wVo==null) {
			vo.setVendorId(vendorId);
			vo.setGzhName(cname);
			vo.setGzhAccount(account);
			vo.setGzhId(original);
			vo.setGzhMark(description);
			vo.setGzhType(level);
			vo.setAppid(key);
			vo.setAppsecret(secret);
			vo.setWxQrCodePath(qrcode);
			vo.setGzhHeadImg(headimg);
			
			int temp=wxConfigService.addWxGzh(vo);
			if (temp > 0) {
				result.put("id", vo.getId());
				result.put("statuscode", 1);
				result.put("msg", "新增成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "新增失败");	
			}
		}else{
			vo.setVendorId(vendorId);
			vo.setGzhName(cname);
			vo.setGzhAccount(account);
			vo.setGzhId(original);
			vo.setGzhMark(description);
			vo.setGzhType(level);
			vo.setAppid(key);
			vo.setAppsecret(secret);
			vo.setWxQrCodePath(qrcode);
			vo.setGzhHeadImg(headimg);
			logger.debug("公众号名称："+cname);
			int temp=wxConfigService.addWxGzhInfo(vo);
			if (temp > 0) {
				result.put("id", wVo.getId());
				result.put("statuscode", 1);
				result.put("msg", "新增修改成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "新增修改失败");	
			}
			
		}
		
		
		return result;
		
	}
	
	
	/**
	 * 点击下一步提交的从微擎上获取的公众号信息
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("/UPDATE/wxConfig/updateWxGzhInfo.do")
	@ResponseBody
	public JSONObject updateWxGzhInfo(Integer id,
			String url,
			String token,
			String encodingaeskey){
		JSONObject result=new JSONObject();
		WxConfigGzhVo vo=new WxConfigGzhVo();
		
		vo.setId(id);
		vo.setToken(token);
		vo.setShortUrl(url);
		vo.setWxKey(encodingaeskey);
		
		int temp=wxConfigService.updateWxGzhInfo(vo);
		if (temp > 0) {
			result.put("statuscode", 1);
			result.put("msg", "修改成功");
		} else {
			result.put("statuscode", -2);
			result.put("msg", "修改失败");	
		}
		
		return result;
	}
	
	/**
	 * 获取公众号信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/wxConfig/getWxGzhInfo.do")
	@ResponseBody
	public JSONObject getWxGzhInfo(Integer vendorId){
		JSONObject result=new JSONObject();
		WxConfigGzhVo vo=wxConfigService.getWxGzhInfo(vendorId);
		if (vo !=null) {
			result.put("id", vo.getId());
			result.put("vendorId", vo.getVendorId());
			result.put("gzhName", vo.getGzhName());
			result.put("gzhAccount", vo.getGzhAccount());
			result.put("gzhId", vo.getGzhId());
			result.put("gzhMark", vo.getGzhMark());
			result.put("gzhType", vo.getGzhType());
			result.put("appid", vo.getAppid());
			result.put("appsecret", vo.getAppsecret());
			result.put("wxQrCodePath", vo.getWxQrCodePath());
			result.put("gzhHeadImg", vo.getGzhHeadImg());
			result.put("url", vo.getShortUrl());
			result.put("encodingAESKey", vo.getWxKey());
			result.put("createTime", vo.getUpdateTime());
			result.put("token", vo.getToken());
		}else{
			result.put("status", -1);
			result.put("msg", "当前账户未绑定公众号");
		}
		
		return result;
	}
	
	
	/**
	 * 分页查询公众号信息
	 * @param page
	 * @return
	 */
	@RequestMapping("/GET/wxConfig/getAllWxGzhInfo.do")
	@ResponseBody
	public PageBean getAllWxGzhInfo(PageBean page){
		
		return wxConfigService.getAllWxGzhInfo(page);
	}
}
