package com.yunma.controller.vendorIdIntegral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.vendorIntegral.VendorIntegralExchange;
import com.yunma.entity.vendorIntegral.VendorIntegralRule;
import com.yunma.model.User;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.product.ProductService;
import com.yunma.service.user.UserService;
import com.yunma.service.vendorIdIntegral.VendorIntegralService;
import com.yunma.utils.PageBean;

@Controller
public class VendorIntegralController extends BaseController {
	@Autowired
	private VendorIntegralService integralService;
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private ProductService pService;
	@Autowired
	private UserService uservice;

	/**
	 * 在新增规则的同时查询对应的表是否创建如果没有创建, 则创建相关的表.
	 * 
	 * @param vendorIntegralRule
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/CreateVendorIntegralRule.do")
	@ResponseBody
	@Transactional
	public JSONObject createVendorIntegralRule(
			VendorIntegralRule vendorIntegralRule) {
		// 创建相关的表
		Integer vendorId = vendorIntegralRule.getVendorId();
		int i = integralService
				.createVendorIntegralActivPlayerDBTable(vendorId);
		JSONObject result = new JSONObject();
		// 添加数据
		ProductOrder order = new ProductOrder();
		String productName;
		String vendorName;
		order = orderService.findOrderByProductOrderId(vendorIntegralRule
				.getOrderId());
		if (order != null) {

			productName = order.getProductName();
			vendorName = order.getVendorName();
			vendorIntegralRule.setProductName(productName);
			vendorIntegralRule.setVendorName(vendorName);
		}

		result = integralService.addAntiFakeIntegralRule(vendorIntegralRule);
		result.put("errorCode", i);
		// 返回数据
		return result;
	}

	/**
	 * 显示商家已生成的积分规则列表
	 * 
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/vendorIntegralRuleList.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public PageBean getVendorIdIntegralRuleList(PageBean pageBean,
			Integer vendorId) {
		return integralService.getVendorIntegralRuleInfo(pageBean, vendorId);
	}

	/**
	 * 兑换记录
	 * 
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/getExchangeHistoryInfoList.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public PageBean getExchangeHistoryInfoList(PageBean pageBean,
			Integer vendorId) {
		return integralService.getExchangeHistoryInfoList(pageBean, vendorId);
	}

	/**
	 * 分页显示参与者信息
	 * 
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/vendorIntegralPlayerList.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public PageBean getAntiFakeIntegralPlayerInfo(PageBean pageBean,
			Integer vendorId) {
		return integralService.getVendorIntegralPlayerInfo(pageBean, vendorId);
	}

	/***
	 * 分页显示,奖品信息
	 * 
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/vendorIntegralExchangesHistoryList.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public PageBean getExchangesInfoList(PageBean pageBean, Integer vendorId) {
		return integralService.getExchangesInfoList(pageBean, vendorId);
	}

	/**
	 * 提供关于未启用的规则可修改改状态
	 * 
	 * @param vendorIntegralRule
	 * @return
	 */
	@RequestMapping("/GET/vendorIdIntegral/updateVendorIntegralRule.do")
	@ResponseBody
	@Transactional
	public JSONObject updateVendorIdIntegralRule(
			VendorIntegralRule vendorIntegralRule) {
		JSONObject result = new JSONObject();
		// 查询相关的规则
		Integer vendorId = vendorIntegralRule.getVendorId();
		Integer inteRuleId = vendorIntegralRule.getInteRuleId();

		VendorIntegralRule priIntegralRule = integralService
				.getVendorIntegralRuleByInteRuleId(vendorId, inteRuleId);
		// 如果 未查出数据则可能是数据源存在问题须修复
		if (priIntegralRule == null) {

			result.put("msg", "数据异常,请联系管理员!");
			result.put("errorCode", -1);
			// 返回数据
			return result;
		}
		// 规则在使用中也能修改

		Product product = new Product();
		ProductOrder order = new ProductOrder();
		String productName;
		String vendorName;
		order = orderService.findOrderByProductOrderId(vendorIntegralRule
				.getOrderId());
		User user = uservice.findUserByVendorId(vendorId);
		product = pService.findById(vendorIntegralRule.getProductId());

		if (order != null) {

			productName = order.getProductName();
			vendorName = order.getVendorName();

		} else {
			productName = product.getProductName();
			vendorName = user.getVendorName();
		}
		vendorIntegralRule.setProductName(productName);
		vendorIntegralRule.setVendorName(vendorName);
		int i = 0;
		result = integralService.updateVendorIntegralRuleStatus(vendorId, inteRuleId);
		//获取更改后的状态值
		VendorIntegralRule priVendorIntegralRule = integralService
				.getVendorIntegralRuleByInteRuleId(vendorId, inteRuleId);
		result.put("status", priVendorIntegralRule.getIsUseing());
		result.put("errorCode", i);
		result.put("msg", "修改成功!");
		// 返回数据
		return result;
	}

	/**
	 * 启用规则 用于创建 后默认未启用的规则改变状态为启用
	 * 
	 * @param vendorId
	 * @param inteRuleId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/updateRuleStatus.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public JSONObject updateVendorIntegralRule(Integer vendorId,
			Integer inteRuleId) {
		return integralService.updateVendorIntegralRuleStatus(vendorId,
				inteRuleId);
	}

	/**
	 * 
	 * 参与积分兑奖用户显示积分信息
	 * 
	 * @param vendorId
	 * @param inteRuleId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/vendorIntegralPlayerGainIntegral.do")
	@ResponseBody
	@Transactional(readOnly = true)
	public JSONObject vendorIntegralPlayerGainIntegral(Integer vendorId,
			String openId) {
		return integralService.vendorIntegralPlayerGainIntegral(vendorId,
				openId);
	}

	/**
	 * 创建积分兑奖列表
	 * 
	 */
	@RequestMapping("/GET/vendorIntegral/addExchangesInfo.do")
	@ResponseBody
	public JSONObject addExchangesInfo(
			VendorIntegralExchange vendorIntegralExchange) {
		return integralService
				.addVendorIntegralExchange(vendorIntegralExchange);
	}

	/**
	 * 更改兑换物信息
	 * 
	 * @param vendorIntegralExchange
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/updateExchangesInfo.do")
	@ResponseBody
	public JSONObject updateExchangesInfo(
			VendorIntegralExchange vendorIntegralExchange,
			Integer inteExchangeId) {
		return integralService.updateVendorIntegralExchange(
				vendorIntegralExchange, inteExchangeId);
	}

	/**
	 * 删除兑换物规则
	 * 
	 * @param inteExchangeId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/deleteExchangesInfo.do")
	@ResponseBody
	public JSONObject deleteExchangesInfo(Integer inteExchangeId,
			Integer vendorId) {
		return integralService.deleteVendorIntegralExchange(inteExchangeId,
				vendorId);
	}

	/**
	 * 删除积分规则
	 * 
	 * @param inteRuleId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/deleteVendorIntegral.do")
	@ResponseBody
	public JSONObject deleteVendorIntegralRule(Integer inteRuleId,
			Integer vendorId) {

		return integralService.deleteVendorIntegralRule(inteRuleId, vendorId);
	}

	/**
	 * 查询单个规则信息用于修改
	 * 
	 * @param inteRuleId
	 * @param vendorId
	 * @return
	 */

	@RequestMapping("/GET/vendorIntegral/findRuleByInteRuleId.do")
	@ResponseBody
	public JSONObject findRuleByInteRuleId(Integer inteRuleId, Integer vendorId) {

		return integralService.findRuleByInteRuleId(inteRuleId, vendorId);
	}

	@RequestMapping("/GET/vendorIntegral/findPlayerByPlayerId.do")
	@ResponseBody
	public JSONObject findPlayerByPlayerId(Integer playerId, Integer vendorId) {

		return integralService.findPlayerByPlayerId(playerId, vendorId);
	}

	/**
	 * 根据单个兑换品id查询兑换物信息
	 * 
	 * @param inteExchangeId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendorIntegral/getVendorSingleIntegralExchange.do")
	@ResponseBody
	public JSONObject getVendorSingleIntegralRule(Integer inteExchangeId,
			Integer vendorId) {

		return integralService.getVendorSingleIntegralRule(inteExchangeId,
				vendorId);
	}

	/**
	 * 更新player信息追加备注
	 * 
	 */
	@RequestMapping("/POST/vendorIntegral/updateVendorIntegralPlayer.do")
	@ResponseBody
	public JSONObject updateVendorIntegralPlayerAddConmment(Integer hisId,
			Integer vendorId, String conmment) {

		return integralService.updateVendorIntegralPlayerAddConmment(hisId,
				vendorId, conmment);
	}
	
	

}
