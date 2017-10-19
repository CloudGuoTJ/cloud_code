package com.yunma.controller.wqapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.WqApp.WqAppEntity;
import com.yunma.service.WqApp.WqAppService;

@Controller
public class WqAppController {

	@Resource
	private WqAppService service;

	/**
	 * 新增应用
	 * 
	 * @param weChatUrl
	 * @param weqUrl
	 * @param gzhId
	 * @param gzhUnionId
	 * @param appImg
	 * @param qrCodeUrl
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/ADD/wqapp/addWqApp.do")
	@ResponseBody
	public JSONObject addWqApp(String appName, String weChatUrl, String weqUrl,
			Integer gzhId, Integer gzhUnionId, String appImg, String qrCodeUrl,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		WqAppEntity entity = new WqAppEntity();
		entity.setAppName(appName);
		entity.setWeChatUrl(weChatUrl);
		entity.setWeqUrl(weqUrl);
		entity.setGzhId(gzhId);
		entity.setGzhUnionId(gzhUnionId);
		entity.setAppImg(appImg);
		entity.setQrCodeUrl(qrCodeUrl);
		entity.setVendorId(vendorId);

		// 查询当前app是否已经添加
		WqAppEntity entity1 = service.getAppInfoByName(appName);

		if (entity1 != null) {
			int temp=service.updateAppinfo(entity);
			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "修改成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}
		} else {

			int temp = service.addWqApp(entity);

			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}
		}
		return result;
	}

	/**
	 * 获取某个应用的信息
	 * 
	 * @param appId
	 * @return
	 */
	@RequestMapping("/GET/wqapp/getAppInfo.do")
	@ResponseBody
	public JSONObject getAppInfo(Integer appId) {
		JSONObject result = new JSONObject();
		WqAppEntity entity = service.getAppInfo(appId);

		if (entity != null) {
			result.put("id", entity.getId());
			result.put("appName", entity.getAppName());
			result.put("weChatUrl", entity.getWeChatUrl());
			result.put("weqUrl", entity.getWeqUrl());
			result.put("gzhId", entity.getGzhId());
			result.put("gzhUnionId", entity.getGzhUnionId());
			result.put("appImg", entity.getAppImg());
			result.put("qrCodeUrl", entity.getQrCodeUrl());
			result.put("vendorId", entity.getVendorId());

		}

		return result;
	}

	/**
	 * 获取所有的应用信息
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/wqapp/getAllAppInfo.do")
	@ResponseBody
	public JSONObject getAllAppInfo(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		List<WqAppEntity> entitys = service.getAllAppInfo(vendorId);
		if (entitys != null && entitys.size() > 0) {
			for (WqAppEntity entity : entitys) {
				JSONObject obj = new JSONObject();
				obj.put("id", entity.getId());
				obj.put("appName", entity.getAppName());
				obj.put("weChatUrl", entity.getWeChatUrl());
				obj.put("weqUrl", entity.getWeqUrl());
				obj.put("gzhId", entity.getGzhId());
				obj.put("gzhUnionId", entity.getGzhUnionId());
				obj.put("appImg", entity.getAppImg());
				obj.put("qrCodeUrl", entity.getQrCodeUrl());
				obj.put("vendorId", entity.getVendorId());
				if(entity.getWeChatUrl() !=null){
					array.add(obj);
				}
			}
			result.put("data", array);
		}

		return result;
	}

	/**
	 * 删除应用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/wqapp/deleteApp.do")
	@ResponseBody
	public JSONObject deleteApp(Integer id) {
		JSONObject result = new JSONObject();
		int temp = service.deleteApp(id);
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "成功");
		} else {
			result.put("status", -2);
			result.put("msg", "删除失敗");
		}
		return result;
	}

}
