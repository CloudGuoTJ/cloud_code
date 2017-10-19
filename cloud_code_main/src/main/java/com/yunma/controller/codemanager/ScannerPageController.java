package com.yunma.controller.codemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.controller.product.ProductController;
import com.yunma.entity.codemanager.CodemanagerEntity;
import com.yunma.model.ModeFunctionFile;
import com.yunma.service.codemanager.ScannerPageService;
import com.yunma.service.weChatVendorHtmlActivInfo.VendorHtmlActivInfoService;
import com.yunma.utils.PageBean;
import com.yunma.vo.codemanager.CodeManagerVo;
import com.yunma.vo.wechatHtml.VendorHtmlActivInfoVO;

@Controller
public class ScannerPageController {

	@Resource
	private ScannerPageService service;
	@Resource
	private VendorHtmlActivInfoService Infoservice;
	

	private Logger log = LoggerFactory.getLogger(ProductController.class);

	/**
	 * 新增扫码页
	 * 
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ADD/codeManager/addCodeManager.do")
	@ResponseBody
	public JSONObject addCodeManager(HttpServletRequest request,
			Integer orderId, Integer vendor_id, String anti_fake_name,
			String startTime, String endTime, String mark, Integer mode_id,
			Integer getRedEnv, String productInfo, String vendorHttp,
			String weShop, Integer spree, Integer securityAndTraceability,Integer funcFlag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		JSONObject result = new JSONObject();
		CodemanagerEntity entity = new CodemanagerEntity();
		entity.setVendor_id(vendor_id);
		entity.setOrderId(orderId);
		entity.setAnti_fake_name(anti_fake_name);
		if (startTime == null) {
			entity.setStartTime(sdf.format(date));
			System.out.println("当前时间：：：：：：" + sdf.format(date));

		} else {
			entity.setStartTime(startTime);
		}
		if (endTime == null) {
			entity.setEndTime("");
		} else {
			entity.setEndTime(endTime);
		}
		entity.setMark(mark);
		entity.setModeId(mode_id);
		entity.setActivity_status(1);
		entity.setGetRedEnv(getRedEnv);
		entity.setProductInfo(productInfo);
		entity.setVendorHttp(vendorHttp);
		entity.setWeShop(weShop);
		entity.setSpree(spree);
		entity.setSecurityAndTraceability(securityAndTraceability);
		entity.setFuncFlag(funcFlag);
		
		
		if (funcFlag==1) {
			int temp = service.addCodeManager(entity);
			//修改模板状态
			int temp1=service.updateModeStatus(mode_id);
			//修改订单的防伪扫码活动状态
			int temp2=service.updateOrderInfoForActiv(orderId);
			if (temp > 0 && temp1>0 && temp2>0) {
				result.put("status", 1);
				result.put("msg", "新增防伪成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}

		}else if(funcFlag==2){
			int temp = service.addCodeManager(entity);
			//修改模板状态
			int temp1=service.updateModeStatus(mode_id);
			//修改订单的溯源扫码活动状态
			int temp3=service.updateOrderInfoForTracingActiv(orderId);
			if (temp > 0 && temp1>0 && temp3>0) {
				result.put("status", 1);
				result.put("msg", "新增溯源成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}
		}
		
		return result;
	}

	/**
	 * 获取模板的信息
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getScaPageModel.do")
	@ResponseBody
	public JSONObject getScaPageModel(Integer vendorId) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		List<VendorHtmlActivInfoVO> infos = Infoservice
				.findVendorHtmlActivInfoByVendorId(vendorId);

		for (VendorHtmlActivInfoVO info : infos) {
			JSONObject result = new JSONObject();
			if (info != null) {
				result.put("actionId", info.getActionId());
				result.put("htmlName", info.getHttpName());
				result.put("htmlUri", info.getHtmlUri());
				result.put("vendorId", info.getVendorId());
				result.put("getRedEnv", info.getGetRedEnv());
				result.put("productInfo", info.getProductInfo());
				result.put("vendorHttp", info.getVendorHttp());
				result.put("weShop", info.getWeShop());
				result.put("securityAndTraceability",
						info.getSecurityAndTraceability());
				result.put("createTime", info.getCreateTime());
				result.put("comment", info.getComment());
				result.put("spree", info.getSpree());
				result.put("status",info.getStatus());
				result.put("urlName", info.getUrlName());
				array.add(result);
			}
		}
		obj.put("data", array);
		return obj;
	}

	/**
	 * 修改扫码页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/UPDATE/codeManager/updateCodeManager.do")
	@ResponseBody
	public JSONObject updateCodeManager(HttpServletRequest request, Integer id,
			Integer orderId, Integer vendor_id, String anti_fake_name,
			String startTime, String endTime, String function_type,
			String mark, Integer mode_id, Integer initModeId,
			Integer getRedEnv, String productInfo, String vendorHttp,
			String weShop, Integer spree, Integer securityAndTraceability,
			Integer funcFlag) {

		JSONObject result = new JSONObject();
		CodemanagerEntity entity = new CodemanagerEntity();
		entity.setId(id);
		entity.setVendor_id(vendor_id);
		entity.setOrderId(orderId);
		entity.setAnti_fake_name(anti_fake_name);
		entity.setStartTime(startTime);
		entity.setEndTime(endTime);
		entity.setMark(mark);
		entity.setModeId(mode_id);
		entity.setActivity_status(1);
		entity.setGetRedEnv(getRedEnv);
		entity.setProductInfo(productInfo);
		entity.setVendorHttp(vendorHttp);
		entity.setWeShop(weShop);
		entity.setSpree(spree);
		entity.setSecurityAndTraceability(securityAndTraceability);
		entity.setFuncFlag(funcFlag);

		if (funcFlag == 1) {
			if (mode_id == initModeId) {
				int temp = service.updateCodeManager(entity);

				int temp2 = service.updateOrderInfoForActiv(orderId);
				if (temp > 0 && temp2 > 0) {
					result.put("status", 1);
					result.put("msg", "成功");
				} else {
					result.put("status", -2);
					result.put("msg", "修改失敗");
				}
			} else {
				int temp = service.updateCodeManager(entity);
				int temp1 = service.updateModeStatus1(initModeId);
				int temp2 = service.updateOrderInfoForActiv(orderId);
				if (temp > 0 && temp1 > 0 && temp2 > 0) {
					result.put("status", 1);
					result.put("msg", "成功");
				} else {
					result.put("status", -2);
					result.put("msg", "修改失敗");
				}
			}

		}else{
			if (mode_id == initModeId) {
				int temp = service.updateCodeManager(entity);

				int temp2 = service.updateOrderInfoForTracingActiv(orderId);
				if (temp > 0 && temp2 > 0) {
					result.put("status", 1);
					result.put("msg", "成功");
				} else {
					result.put("status", -2);
					result.put("msg", "修改失敗");
				}
			} else {
				int temp = service.updateCodeManager(entity);
				int temp1 = service.updateModeStatus1(initModeId);
				int temp2 = service.updateOrderInfoForTracingActiv(orderId);
				if (temp > 0 && temp1 > 0 && temp2 > 0) {
					result.put("status", 1);
					result.put("msg", "成功");
				} else {
					result.put("status", -2);
					result.put("msg", "修改失敗");
				}
			}
		}

		return result;
	}

	/**
	 * 删除扫码页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/codeManager/deleteCodeManager.do")
	@ResponseBody
	public JSONObject deleteCodeManager(Integer id) {
		JSONObject result = new JSONObject();

		int temp = service.deleteCodeManager(id);

		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "成功");
		} else {
			result.put("status", -2);
			result.put("msg", "删除失敗");
		}
		return result;
	}

	/**
	 * 查询防伪扫码管理分页
	 * 
	 * @param page
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getCodeManagerAll.do")
	@ResponseBody
	public PageBean getCodeManagerAll(PageBean page,String keyword,Integer vendorId) {

		return service.getCodeManagerAll(page,keyword, vendorId);
	}
	
	/**
	 * 查询溯源扫码管理分页
	 * 
	 * @param page
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getCodeManagerAllForTracy.do")
	@ResponseBody
	public PageBean getCodeManagerAllForTracy(PageBean page,String keyword,Integer vendorId) {

		return service.getCodeManagerAllForTracy(page,keyword, vendorId);
	}

	/**
	 * 查询单个扫码管理
	 * 
	 * @param page
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getCodeManagerById.do")
	@ResponseBody
	public JSONObject getCodeManagerById(Integer id) {
		JSONObject result = new JSONObject();
		CodeManagerVo vo = new CodeManagerVo();
		vo = service.getCodeManagerById(id);
		if (vo != null) {
			result.put("id", vo.getId());
			result.put("vendor_id", vo.getVendor_id());
			result.put("anti_fake_name", vo.getAnti_fake_name());
			result.put("start_time", vo.getStart_time());
			result.put("end_time", vo.getEnd_time());
			result.put("orderId", vo.getOrderId());
			result.put("mark", vo.getMark());
			result.put("activity_status", vo.getActivity_status());
			result.put("mode_id", vo.getMode_id());
			result.put("httpName", vo.getHttpName());
			result.put("getRedEnv", vo.getGetRedEnv());
			result.put("productInfo", vo.getProductInfo());
			result.put("vendorHttp", vo.getVendorHttp());
			result.put("weShop", vo.getWeShop());
			result.put("spree", vo.getSpree());
			result.put("securityAndTraceability",
					vo.getSecurityAndTraceability());
		}
		return result;
	}

	/**
	 * 改变活动状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/UPDATE/codeManager/updateScaPageStatus.do")
	@ResponseBody
	public JSONObject updateScaPageStatus(Integer id, Integer currentStatus) {
		JSONObject result = new JSONObject();
		if (currentStatus == 0) {
			int temp = service.updateScaPageStatus(id);

			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "成功");
			} else {
				result.put("status", -2);
				result.put("msg", "修改失敗");
			}
		}
		if (currentStatus==1) {
			int temp = service.updateScaPageStatus1(id);

			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "成功");
			} else {
				result.put("status", -2);
				result.put("msg", "修改失敗");
			}
		}
		

		return result;
	}

	/**
	 * 保存模板功能文件
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/SAVE/codeManager/saveFileFunType.do")
	@ResponseBody
	public JSONObject saveFileFunType(String html) {
		JSONObject result = new JSONObject();

		if (html != null) {
			/**
			 * 获取项目下的webapp位置,并最终定位储存位置/cloud_code/src/main/webapp/wx
			 */
			String pathTest;
			try {
				pathTest = getClass().getResource("/").toURI().getPath();
				String path = pathTest.substring(0,
						pathTest.lastIndexOf("webapps") + 7);
				String content = "/wx/mode_fun/";
				String realPath = path + content;
				File pathFile = new File(realPath);
				String fileName = String.valueOf(System.currentTimeMillis());

				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				String activeHtml = html;
				String htmlName = fileName + ".html";
				try {
					FileOutputStream fos = new FileOutputStream(realPath
							+ htmlName);
					OutputStreamWriter osw = new OutputStreamWriter(fos,
							"UTF-8");
					osw.write(activeHtml);
					osw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ModeFunctionFile mode = new ModeFunctionFile();
				mode.setFile_url(realPath);
				mode.setFunction_name(htmlName);
				mode.setMark("0");
				int temp = service.saveModeFunFile(mode);
				if (temp > 0) {
					result.put("id", mode.getId());
					result.put("fileUrl", htmlName);
					result.put("statuscode", 1);
					result.put("msg", "成功");
				} else {
					result.put("statuscode", -2);
					result.put("msg", "上传失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}
	
	/**
	 * 分页显示防伪扫码活动订单信息
	 * @param vendorId
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/codeManager/productInfoList.do")
	@ResponseBody
	public PageBean getProductOrderInfo(@RequestParam("vendorId") Integer vendorId,PageBean pageBean) {

		return service.getOrderInfoForActiv(pageBean, vendorId);
	}
	
	
	/**
	 * 分页显示溯源扫码活动订单信息
	 * @param vendorId
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getProductOrderTracyInfo.do")
	@ResponseBody
	public PageBean getProductOrderTracyInfo(@RequestParam("vendorId") Integer vendorId,PageBean pageBean) {

		return service.getOrderInfoForTracyActiv(pageBean, vendorId);
	}
	
	/**
	 * 获取某个模板信息
	 * @param actionId
	 * @return
	 */
	@RequestMapping("/GET/codeManager/getVendorHtmlActivById.do")
	@ResponseBody
	public JSONObject getVendorHtmlActivById(Integer actionId){
		JSONObject result=new JSONObject();
		VendorHtmlActivInfoVO vo=Infoservice.getVendorHtmlActivById(actionId);
		if (vo != null) {
			result.put("actionId", vo.getActionId());
			result.put("htmlName", vo.getHttpName());
			result.put("htmlUri", vo.getHtmlUri());
			result.put("vendorId", vo.getVendorId());
			result.put("getRedEnv", vo.getGetRedEnv());
			result.put("productInfo", vo.getProductInfo());
			result.put("vendorHttp", vo.getVendorHttp());
			result.put("weShop", vo.getWeShop());
			result.put("securityAndTraceability",
					vo.getSecurityAndTraceability());
			result.put("createTime", vo.getCreateTime());
			result.put("comment", vo.getComment());
			result.put("spree", vo.getSpree());
			result.put("status",vo.getStatus());
			result.put("urlName", vo.getUrlName());
		}else{
			log.debug("获取模板信息出错");
		}
		
		return result;
	}

}
