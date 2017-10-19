package com.yunma.service.wxConfig.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.yunma.dao.wxConfig.WxConfigDao;
import com.yunma.model.WxConfig;
import com.yunma.service.wxConfig.WxConfigService;
import com.yunma.utils.PageBean;
import com.yunma.vo.wxConfig.WxConfigGzhVo;
import com.yunma.vo.wxConfig.WxConfigVo;

@Service
public class WxConfigServiceImpl extends BaseService implements WxConfigService {
	
	@Autowired
	private WxConfigDao wxConfigDao;
	
	
	
	@Override
	public int countWxConfig() {
		return wxConfigDao.countWxConfig();
	}



	@Override
	public int addWxConfig(WxConfigVo vo) {
		
		return wxConfigDao.createWxConfig(vo);
	}



	@Override
	public int updateWxConfig(WxConfigVo vo) {
		
		return wxConfigDao.updateWxConfig(vo);
	}



	@Override
	public PageBean getWxConfigInfo(PageBean pageBean, WxConfig vo) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		//总数
		pageBean.setTotalRecords(wxConfigDao.countWxConfig());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("WxConfigVo", vo);
		List<WxConfig> list = wxConfigDao.getWxConfigInfo(map);
		if (list != null && list.size() > 0) {
			for (WxConfig wx : list) {
				JSONObject object = new JSONObject();
				object.put("id", wx.getId());
				object.put("vendorId", wx.getVendorId());
				object.put("appid", wx.getAppid());
				object.put("appsecret", wx.getAppsecret());
				object.put("redirect_uri", wx.getRedirect_uri());
				object.put("suCaiUrl", wx.getSuCaiUrl());
				object.put("mchId", wx.getMchId());
				object.put("wxQrCode", wx.getWxQrCode());
				object.put("sendName", wx.getSendName());
				object.put("wishing", wx.getWishing());
				object.put("createTime", wx.getCreateTime());
				
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);
		
		return pageBean;
	}



	@Override
	public int addWxGzh(WxConfigGzhVo vo) {
		return wxConfigDao.addWxGzh(vo);
	}



	@Override
	public int updateWxGzhInfo(WxConfigGzhVo vo) {
		
		return wxConfigDao.updateWxGzhInfo(vo);
	}



	@Override
	public WxConfigGzhVo getWxGzhInfo(Integer vendorId) {
		// TODO Auto-generated method stub
		return wxConfigDao.getWxGzhInfo(vendorId);
	}



	@Override
	public int addWxGzhInfo(WxConfigGzhVo vo) {
		// TODO Auto-generated method stub
		return wxConfigDao.addWxGzhInfo(vo);
	}



	@Override
	public PageBean getAllWxGzhInfo(PageBean page) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		//总数
		page.setTotalRecords(wxConfigDao.countWxConfig());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", page);
		
		List<WxConfigGzhVo> vos=wxConfigDao.getAllWxGzhInfo(map);
		
		
		if (vos !=null && vos.size()>0) {
			
			for (WxConfigGzhVo vo : vos) {
				JSONObject obj=new JSONObject();
				obj.put("id", vo.getId());
				obj.put("vendorId", vo.getVendorId());
				obj.put("gzhName", vo.getGzhName());
				obj.put("gzhAccount", vo.getGzhAccount());
				obj.put("gzhId", vo.getGzhId());
				obj.put("gzhMark", vo.getGzhMark());
				obj.put("gzhType", vo.getGzhType());
				obj.put("appid", vo.getAppid());
				obj.put("appsecret", vo.getAppsecret());
				obj.put("wxQrCodePath", vo.getWxQrCodePath());
				obj.put("gzhHeadImg", vo.getGzhHeadImg());
				obj.put("url", vo.getShortUrl());
				obj.put("encodingAESKey", vo.getWxKey());
				obj.put("createTime", vo.getUpdateTime());
				obj.put("token", vo.getToken());
				
				array.add(obj);
			}
			result.put("data", array);
		}
		page.setResult(result);
		return page;
	}
}	
