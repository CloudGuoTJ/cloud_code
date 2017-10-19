package com.yunma.service.vendorIdIntegral;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.vendorIntegral.VendorActivPlayer;
import com.yunma.entity.vendorIntegral.VendorIntegralExchange;
import com.yunma.entity.vendorIntegral.VendorIntegralExchangeHistory;
import com.yunma.entity.vendorIntegral.VendorIntegralRule;
import com.yunma.utils.PageBean;

/***
 * 积分系统service
 * @author Cloud
 *
 */

public interface VendorIntegralService {
	/**
	 * 添加规则信息
	 * @param vendorIntegralRule
	 * @return
	 */
	public JSONObject addAntiFakeIntegralRule(VendorIntegralRule vendorIntegralRule);
	
	/**
	 * 添加兑奖信息
	 * 兑奖信息表
	 * 
	 * 流程:
	 *  { 显示player积分--> player积分足够 --> 选择兑换物 --> 填写兑奖信息(实物需增加邮寄地址和电话) -->
	 *  player扣除对应积分 && 兑换记录保存信息 && 奖池减少兑奖物数量  --> 兑奖结束返回验证消息 --> 刷新积分数值和兑换记录}
	 *
	 * @param openId 回调返回
	 * @param vendorId 从扫码返回
	 * @param orderId 随扫码返回
	 * @param inteExchangeId 兑换物的Id
	 * @return
	 */
	public JSONObject playerGetExchangesAddInHistory(String openId,Integer vendorId,Integer orderId,Integer inteExchangeId ,Integer ruleId);
	
	/**
	 * 创建 活动参与者表
	 * @Param orderId
	 */
	public int createVendorIntegralActivPlayerDBTable(Integer vendorId);
	
	/**
	 *删除对应的表
	 *@Parma orderId
	 *
	 */
	public int dropPlayerTableByVendorId(Integer vendorId);
	
	/**
	 * 删除活动
	 *
	 * @param orderId
	 * @return
	 */
	public int dropActivityByInteRuleId(Integer inteRuleId,Integer vendorId);
	

	/**
	 * 分页查询生成规则
	 * @param map
	 * @return
	 */
	PageBean getVendorIntegralRuleInfo(PageBean pageBean,Integer vendorId);
	
	/**
	 * 分页查询活动参与者信息
	 * 
	 * @param pageBean
	 * @param orderId
	 * @return
	 */
	PageBean getVendorIntegralPlayerInfo(PageBean pageBean,Integer vendorId);
	
	/**
	 * 分页查询奖品信息
	 *
	 * @param pageBean
	 * @param orderId
	 * @return
	 */
	PageBean getExchangesInfoList(PageBean pageBean,Integer vendorId);
	
	/**
	 * 分页查询兑奖信息
	 * 
	 */
	PageBean getExchangeHistoryInfoList(PageBean pageBean,Integer vendorId);
	/**
	 * 追加积分
	 * 
	 */
	public int updatePlayerIntegralByOpenId(Integer vendorId,String openId,int integral); 
	
	
	/**.
	 *
	 * 领奖信息记录
	 * 
	 * @param vendorIntegralExchangeHistory
	 * @return
	 */
	public int addExchangeHistoryInfoList(VendorIntegralExchangeHistory vendorIntegralExchangeHistory);
	/**
	 * 修改未启用 状态的规则
	 */
	public int updateVendorIntegeralRule(VendorIntegralRule vendorIntegralRule );
	/**
	 * 通过inteRuleId查询规则信息
	 * @param inteRuleId
	 * @return
	 */
	public VendorIntegralRule getVendorIntegralRuleByInteRuleId(Integer vendorId,
			Integer inteRuleId);
	/**
	 * 启用规则
	 * @param vendorId
	 * @param inteRuleId
	 * @return
	 */
	public JSONObject updateVendorIntegralRuleStatus(Integer vendorId,
			Integer inteRuleId);
	/**
	 * 根据规则进行追加积分
	 * @param vendorId
	 * @param openId
	 * @return
	 */
	public JSONObject vendorIntegralPlayerGainIntegral(Integer vendorId,
			String openId);
	/**
	 * 根据订单Id查询规则
	 * @param vendorId
	 * @param orderId
	 * @return
	 */
	public VendorIntegralRule findRuleByOrderId(int vendorId, int orderId);
	/**
	 * 根据产品Id查询规则
	 * @param vendorId
	 * @param productId
	 * @return
	 */
	public VendorIntegralRule findRuleByProductId(int vendorId,
			Integer productId);
	/**
	 * 获取vendor全局控制的规则
	 * @param vendorId
	 * @param orderId
	 * @return
	 */
	public VendorIntegralRule findRuleByVendorId(int vendorId, int orderId);
	/**
	 * 创建兑换物
	 * @param vendorIntegralExchange
	 * @return
	 */
	public JSONObject addVendorIntegralExchange(
			VendorIntegralExchange vendorIntegralExchange);
	/**
	 * 更新兑换物
	 * @param vendorIntegralExchange
	 * @param inteExchangeId 
	 * @return
	 */
	public JSONObject updateVendorIntegralExchange(
			VendorIntegralExchange vendorIntegralExchange, Integer inteExchangeId);
	/**
	 * 删除指定兑换规则
	 * @param inteExchangeId
	 * @param vendorId 
	 * @return
	 */
	public JSONObject deleteVendorIntegralExchange(Integer inteExchangeId, Integer vendorId);
	/**
	 * 删除指定的积分规则
	 * @param inteRuleId
	 * @param vendorId 
	 * @return
	 */
	public JSONObject deleteVendorIntegralRule(Integer inteRuleId, Integer vendorId);
	/**
	 * 查询单个规则信息
	 * @param inteRuleId
	 * @param vendorId
	 * @return
	 */
	public JSONObject findRuleByInteRuleId(Integer inteRuleId, Integer vendorId);
	/**
	 * 根据openId查询参与者信息
	 * @param vendorId
	 * @param openId
	 * @return
	 */
	public VendorActivPlayer findPlayerByOpenId(int vendorId, String openId);
	/**
	 * 创建积分活动参与者
	 * @param vendorActivPlayer
	 */
	public int addPlayerInfo(VendorActivPlayer vendorActivPlayer);
	/**
	 * 根据参与者id查询参与者信息
	 * @param playerId
	 * @param vendorId
	 * @return
	 */
	public JSONObject findPlayerByPlayerId(Integer playerId, Integer vendorId);
	/**
	 * 根据单个兑换品id查询兑换品信息
	 * @param inteExchangeId
	 * @param vendorId
	 * @return
	 */
	public JSONObject getVendorSingleIntegralRule(Integer inteExchangeId,
			Integer vendorId);
	/**
	 * 更新player信息追加注释
	 * @param hisId
	 * @param vendorId
	 * @param conmment
	 * @return
	 */
	public JSONObject updateVendorIntegralPlayerAddConmment(Integer hisId,
			Integer vendorId, String conmment);

	
	
	 
	

	
}
