package com.yunma.controller.wechat;



import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.BaiduMapUtils;
import com.common.util.Radix;
import com.yunma.dao.weChart.VendorHtmlActivInfoDao;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.entity.agent.AgentEntity;

import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.tracing.ProductTracingCode;

import com.yunma.entity.tracing.ProductTracingCodeAgentScan;
import com.yunma.entity.tracing.TracingCodeCustomerScan;
import com.yunma.entity.tracing.VendorTracingStatistics;

import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.model.Location;
import com.yunma.model.User;
import com.yunma.model.VendorHtmlActivBackInfo;
import com.yunma.service.agent.AgentService;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.product.ProductService;
import com.yunma.service.tracing.ProductsTracingService;
import com.yunma.service.user.UserService;

@Controller
public class WeChatTracingAntiFakeHomeController extends BaseController {
	@Autowired
	private ProductsTracingService tracingService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private VendorHtmlActivInfoDao activInfoDao;
	/**
	 * agent代理扫码回调接口
	 * @param openId
	 * @param securityCode
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	
	@RequestMapping("/POST/weChat/productAgentTracingHome.do")
	@ResponseBody
	public JSONObject getAgentTracingScanInfo(@RequestParam("openId") String openId,
			@RequestParam("shortCode") String shortCode,
			@RequestParam("codeType") Integer codeType,
			@RequestParam(required = false) String longitude,
			@RequestParam(required = false) String latitude) {
		logger.info("传递扫码参数:");
		/**
		 * 从二维码中获取厂商相关信息
		 */
		JSONObject result = new JSONObject();
//		Integer productId = Radix.convert62To10(shortCode.substring(6,8));
		Integer orderId = Radix.convert62To10(shortCode.substring(0, 4));// 从二维码中转化orderId
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);//获取订单信息	
		Integer vendorId = order.getVendorId();//vendorId
			
		logger.debug("更新地理位置信息:");
		Location location = new Location();
		int b = 0;
		User user = userService.findUserByVendorId(vendorId);
		String vendorName  = user.getVendorName();
		if (longitude != null && latitude != null) {
			b += tracingService.updateAgenScantLocation(openId, vendorId, longitude, latitude);// 更新地理位置信息
			
		}
		
		logger.debug("更新地理位置信息结束:");
				
		logger.debug("储存商家溯源信息开始");
		
		
		
		/**
		 * 获取代理等级
		 */
		AgentEmployee agentEmployee = tracingService.findAgentEmployeeInfoByOpenId(openId,vendorId);
		Integer agentId = agentEmployee.getAgentId();
		AgentEntity agent = tracingService.findAgentInfoByAgentId(agentId);
		Integer lv = agent.getAgentLevel();
		int count = 0;;
		int rowNum = 0 ; 
		int totalRowNum = 0;
		/**
		 * 直接判别根据type划定code的类型视为箱码还是组码
		 */
		if(codeType == 1){
			BoxCode boxCode = tracingService.getTracingBoxCodeByBoxCode(orderId,shortCode);	
			count = boxCode.getBoxCount();
			rowNum = boxCode.getBoxNum();
			totalRowNum = boxCode.getTotalRowNum(); 
				}
		if(codeType == 2){
			GroupCode groupCode = tracingService.getTracingGroupCodeByCode(orderId,shortCode);
			count = groupCode.getGroupCount();
			rowNum = groupCode.getGroupNum();
			totalRowNum = groupCode.getGroupNum();
		}
		
		ProductTracingCodeAgentScan agentScan = tracingService.getAgentSanInfo(openId,shortCode);

		/**
		 * 保存商家溯源信息数据
		 */
		VendorTracingStatistics vendorTracingStatisticsOld = new VendorTracingStatistics();
		location = BaiduMapUtils.getLocation(latitude, longitude);//当次地理位置
		
		/**
		 * 完善上版本agent扫码信息
		 * 
		 */
		int a = tracingService.updateAgentInfoSetLocation(vendorId,location,openId,shortCode);
		
			
		//保存溯源统计信息 
		VendorTracingStatistics vendorTracingStatistics = new VendorTracingStatistics();	
		vendorTracingStatistics.setAddress(location.getAddress());
		vendorTracingStatistics.setProvince(location.getProvince());
		vendorTracingStatistics.setCity(location.getCity());
		vendorTracingStatistics.setDistrict(location.getDistrict());
		vendorTracingStatistics.setOpenId(openId);
		vendorTracingStatistics.setAgentId(agentId);
		
		//追加溯源码和单批次产品数量
		vendorTracingStatistics.setTracingCode(shortCode);
		vendorTracingStatistics.setCount(count);
		vendorTracingStatistics.setCodeType(codeType);
		
		
		vendorTracingStatistics.setLv(lv);
		vendorTracingStatistics.setProductRowNum(rowNum);
		vendorTracingStatistics.setAgentName(agent.getAgentName());
		vendorTracingStatistics.setOrderId(orderId);
		vendorTracingStatistics.setProductId(order.getProductId());
		vendorTracingStatistics.setProductName(order.getProductName());
		vendorTracingStatistics.setVendorId(vendorId);

		//1.如果已存在同级同订单且同溯源码(同一批次)代理则替换当前等级
		vendorTracingStatisticsOld = tracingService.findAgentTracingStatisticsInfoIfExist(lv,orderId,openId,shortCode);
		if(vendorTracingStatisticsOld == null){
					
			tracingService.addVendorTracingStatisticsInfo(vendorTracingStatistics);						
		}else{
		
			tracingService.updateVendorTracingStatisticsInfo(vendorTracingStatistics,orderId,lv);	
		}
		List<VendorTracingStatistics> agentTracing = tracingService.agentFindAllAgentInfo(orderId,lv ,openId,shortCode);
		if(agentTracing == null){
			result.put("msg", "该商家未设置溯源信息请先建立代理渠道树!");
		}
			
		result.put("AgentMap",agentTracing );
		result.put("lvmsg", "当前层级");
		result.put("lv",agent.getAgentLevel());
		result.put("productName", order.getProductName());
		result.put("vendorName", vendorName);
		result.put("orderId", orderId);
		result.put("rowNum", rowNum);
		
		result.put("totalRowNum", totalRowNum);

		return result;

	}
	
	

	/**
	 * customer代理扫码回调接口
	 * @param openId
	 * @param securityCode
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	
	@RequestMapping("/POST/weChat/productCustomerTracingHome.do")
	@ResponseBody
	public JSONObject getCustomerTracingScanInfo(@RequestParam("openId") String openId,
			@RequestParam("tracingCode") String tracingCode,
			@RequestParam("codeType") Integer codeType,
			@RequestParam(required = false) String longitude,
			@RequestParam(required = false) String latitude) {
		logger.info("传递扫码参数:");
		JSONObject result = new JSONObject();

		Integer orderId = Radix.convert62To10(tracingCode.substring(0, 4));// 从二维码中转化orderId
		ProductTracingCode code = tracingService.findTracingCodeInfoByTracingCode(tracingCode);// 查询二维码是否正确
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);//获取订单信息
		logger.debug("更新地理位置信息:");
		int b = 0;
		Integer vendorId = order.getVendorId();
		User user = userService.findUserByVendorId(vendorId);
		String vendorName  = user.getVendorName();
//		if (longitude != null && latitude != null) {
//			b += tracingService.updateCustomerScanLocation(openId, vendorId, longitude, latitude);// 更新地理位置信息
//			
//		}
		logger.debug("更新地理位置信息结束:");		
		
		/**
		 * 保存消费者扫码信息数据
		 */
		
//		Location location1 = BaiduMapUtils.getLocation(latitude, longitude);
				
//		//获取最新的一条代理信息
//		VendorTracingStatistics vendorTracingStatisticsOld = new VendorTracingStatistics();
//		
//		TracingCodeCustomerScan  tracingCodeCustomerScan = new TracingCodeCustomerScan();
//		tracingCodeCustomerScan.setLatitude(latitude);
//		tracingCodeCustomerScan.setLongitude(longitude);
//		tracingCodeCustomerScan.setOpenId(openId);
//		tracingCodeCustomerScan.setProductName(order.getProductName());
//		tracingCodeCustomerScan.setProductId(order.getProductId());
//		tracingCodeCustomerScan.setProductTracingCode(tracingCode);
//		tracingCodeCustomerScan.setProductTracingCodeId(code.getProductTracingCodeId());
//		tracingCodeCustomerScan.setProvince(location1.getProvince());
//		tracingCodeCustomerScan.setScanAddress(location1.getAddress());
//		tracingCodeCustomerScan.setVendorId(vendorId);
//		tracingCodeCustomerScan.setDistrict(location1.getDistrict());
//		tracingCodeCustomerScan.setCity(location1.getCity());
//		tracingService.addCustomerScanInfo(vendorId, tracingCodeCustomerScan);
		
		/**
		 * 返回扫码验证信息
		 */	
		
		Integer boxNum = Radix.convert62To10(code.getBoxShortCode());
		Integer groupNum = code.getCreateRowNum();
		
		List<VendorHtmlActivBackInfo> activInfo = activInfoDao
				.findVendorHtmlActivInfoByOrderId(orderId);

		String msg = "该产品为"+order.getProductName()+",是 "+vendorName + " 厂商生产,订单号为:"+orderId;
		List<VendorTracingStatistics> agentTracing = tracingService.findAllAgentInfo(orderId,boxNum,groupNum);
		if(agentTracing != null){
			result.put("agentTracing", agentTracing);	
			result.put("message", msg);
		}else{
			result.put("warn", "该厂家还未添加 溯源信息服务!");
			result.put("message", msg);
		}
		result.put("vendorId", vendorId);
		result.put("activInfo", activInfo);
		return result;

	}

	

}
