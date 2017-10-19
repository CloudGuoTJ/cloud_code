package com.yunma.controller.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.DateUtils;
import com.common.util.Radix;
import com.yunma.dao.weChart.VendorHtmlActivInfoDao;
import com.yunma.model.VendorHtmlActivInfo;

/***
 * 新增需求,创建扫码回调模板相关
 * 
 * @author Administrator
 * 
 */

@Controller
public class WxAntiFakeBuildHtmlController extends BaseController {

	@Autowired
	private VendorHtmlActivInfoDao activInfoDao;

	/**
	 * 商家用户动态生成扫码页面
	 * 
	 * @param vendorId
	 * @param html
	 * @param vo
	 * @return
	 */
	@RequestMapping("/POST/antiFake/html.do")
	@ResponseBody
	public JSONObject buildHtml(
			@RequestParam("templateName") String templateName,
			@RequestParam("comment") String comment,
			@RequestParam("vendorId") Integer vendorId,
			@RequestParam("html") String html,
			@RequestParam("getRedEnv") Integer getRedEnv,
			@RequestParam("productInfo") String productInfo,
			@RequestParam("vendorHttp") String vendorHttp,
			@RequestParam("weShop") String weShop,
			@RequestParam("spree") Integer spree,
			@RequestParam("securityAndTraceability") Integer securityAndTraceability,
			@RequestParam("tempType") Integer tempType,// 经典和自定义:1,经典,2:自定义
			@RequestParam(required = false) String storeData// 储存字段,用于动态修改

	) {
		JSONObject jsonObject = new JSONObject();
		System.out.println("模板名称：：" + templateName);

		String urlName = Radix.convert10To62(vendorId, 2)
				+ (UUID.randomUUID().toString().replace("\\-", "")).substring(
						0, 8) + ".html";

		if (html != null) {
			// String HtmlPath =null;//存放网页最终位置
			/**
			 * 获取项目下的webapp位置,并最终定位储存位置/cloud_code/src/main/webapp/wx
			 */
			String pathTest;
			try {
				pathTest = getClass().getResource("/").toURI().getPath();
				String path = pathTest.substring(0,
						pathTest.lastIndexOf("webapps") + 7);
				String content = "/wx/";
				String realPath = path + content;
				File pathFile = new File(realPath);
				logger.debug("生成文件路径为: {}" + realPath);
				if (!pathFile.exists()) {
					pathFile.mkdirs();

				}
				String activeHtml = html;
				String htmlName = templateName;
				try {
					FileOutputStream fos = new FileOutputStream(realPath
							+ urlName);
					OutputStreamWriter osw = new OutputStreamWriter(fos,
							"UTF-8");
					osw.write(activeHtml);
					osw.flush();
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("生成异常,请联系管理员！");
				}
				/**
				 * 解析保存信息
				 */

				logger.debug("保存页面信息开始: {}", DateUtils.getTimeString());
				VendorHtmlActivInfo activInfo = new VendorHtmlActivInfo();
				activInfo = (VendorHtmlActivInfo) activInfoDao
						.findVendorHtmlActivInfoByHtmlName(htmlName);
				if (activInfo != null) {
					jsonObject.put("msg", "该模板已存在,请重新生成");
					jsonObject.put("errorCode", -5);

				} else {
					int a = 0;

					VendorHtmlActivInfo activInfo1 = new VendorHtmlActivInfo();
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					activInfo1.setComment(comment);

					activInfo1.setHtmlUri(realPath);

					activInfo1.setHtmlName(htmlName);

					activInfo1.setGetRedEnv(getRedEnv);

					activInfo1.setProductInfo(productInfo);

					activInfo1.setVendorHttp(vendorHttp);

					activInfo1.setWeShop(weShop);

					activInfo1.setSpree(spree);

					activInfo1.setVendorId(vendorId);

					activInfo1
							.setSecurityAndTraceability(securityAndTraceability);

					activInfo1.setCreateTime1(sdf.format(date));

					activInfo1.setUrlName(urlName);
					// 当temType = 2 时储存 storeData
					if (tempType == 2) {
						activInfo1.setStoreData(storeData);
					}

					activInfo1.setTempType(tempType);

					a += activInfoDao.saveHtmlInfo(activInfo1);

					logger.debug("创建成功! 状态:" + a + "时间:",
							DateUtils.getTimeString());
					jsonObject.put("msg", "新增成功!");
					jsonObject.put("errorCode", 0);

					logger.debug("生成成功:");
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}

		}

		return jsonObject;

	}

	@RequestMapping("/POST/antiFake/updateHtml.do")
	@ResponseBody
	public JSONObject updateHtml(@RequestParam("vendorId") Integer vendorId,
			@RequestParam("html") String html,
			@RequestParam("actionId") Integer actionId,// 模板Id
			@RequestParam("tempType") Integer tempType,// 经典和自定义:1,经典,2:自定义
			@RequestParam("storeData") String storeData// 储存字段,用于动态修改
	) {
		JSONObject jsonObject = new JSONObject();
		/**
		 * 获取已存在的模板信息
		 */
		VendorHtmlActivInfo activInfo = activInfoDao
				.findVendorHtmlActivInfoByActionId(actionId);

		String urlName = activInfo.getUrlName();
		if (activInfo == null || activInfo.getTempType() != tempType
				|| activInfo.getVendorId() != vendorId) {
			jsonObject.put("msg", "非指定模板不能进行更新 操作!");
			jsonObject.put("errorCode", -1);
			return jsonObject;
		}

		if (html != null) {
			// String HtmlPath =null;//存放网页最终位置
			/**
			 * 获取项目下的webapp位置,并最终定位储存位置/cloud_code/src/main/webapp/wx
			 */
			String pathTest;
			try {
				pathTest = getClass().getResource("/").toURI().getPath();
				String path = pathTest.substring(0,
						pathTest.lastIndexOf("webapps") + 7);
				String content = "/wx/";
				String realPath = path + content;

				// 如果存在文件则先删除对应的文件
				boolean flag = false;
				File deleteName = new File(realPath + urlName);
//				System.out.println("deleteName::" + deleteName);
				// 删除对应文件
				deleteName.delete();
				flag = true;
				String activeHtml = html;
				try {
					File pathFile = new File(realPath);
					logger.debug("生成文件路径为:" + realPath);
					if (!pathFile.exists()) {
						pathFile.mkdirs();

					}
					FileOutputStream fos = new FileOutputStream(realPath
							+ urlName);
					OutputStreamWriter osw = new OutputStreamWriter(fos,
							"UTF-8");
					osw.write(activeHtml);
					osw.flush();
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("生成异常,请联系管理员！");
				}
				/**
				 * 更新模板信息
				 */
				int a = activInfoDao.updateVendorHtmlActivInfoById(actionId,
						storeData);

				logger.debug("更新成功! 状态:" + a + "时间:", DateUtils.getTimeString());
				jsonObject.put("msg", "更新成功!");
				jsonObject.put("errorCode", 0);
				jsonObject.put("flag", flag);
				logger.debug("更新成功!");
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}

		}

		return jsonObject;

	}

	/**
	 * 查询对应模板的storeDate
	 */
	@RequestMapping("/POST/antiFake/htmlStoreDate.do")
	@ResponseBody
	public JSONObject getStoreDate(@RequestParam("actionId") Integer actionId) {
		JSONObject jsonObject = new JSONObject();
		VendorHtmlActivInfo activInfo = activInfoDao
				.findVendorHtmlActivInfoByActionId(actionId);
		if (activInfo == null) {
			jsonObject.put("msg", "该模板没有相关信息!");
			jsonObject.put("errorCode", -1);
		} else {
			String storeData = activInfo.getStoreData();
			jsonObject.put("storeData", storeData);
			jsonObject.put("errorCode", 0);
		}
		return jsonObject;
	}

	/**
	 * 判断模板名称是否存在
	 * 
	 * @param templateName
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/POST/antiFake/htmlNameExist.do")
	@ResponseBody
	public JSONObject htmlName(
			@RequestParam("templateName") String templateName,
			@RequestParam("vendorId") Integer vendorId) {
		JSONObject result = new JSONObject();
		String httpName = templateName;
		VendorHtmlActivInfo info = activInfoDao.findExistNameInfo(vendorId,
				httpName);
		// String httpName =
		if (info != null) {
			result.put("errorCode", -1);
			result.put("msg", "名字 已存在,请重新输入模板名!");
			return result;
		} else {
			result.put("errorCode", 0);
			result.put("msg", "模板名可以使用");
			return result;
		}

	}
	
	
	

}
