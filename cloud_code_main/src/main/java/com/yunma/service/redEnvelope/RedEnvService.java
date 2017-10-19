package com.yunma.service.redEnvelope;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.redEnvelope.RedEnv;
import com.yunma.entity.redEnvelope.RedEnvRule;
import com.yunma.entity.redEnvelope.RedEnvRuleArea;
import com.yunma.entity.redEnvelope.RedEnvelope;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.utils.PageBean;

public interface RedEnvService {

	/**
	 * 新增红包规则
	 * @param rule
	 * @return
	 */
	public int addRedEnvRule(RedEnvRule rule);
	
	/**
	 * 判断是否有重复的规则名
	 * @param vendorId
	 * @param ruleName
	 * @return
	 */
	public int hasRedEnvRuleName(Integer vendorId,String ruleName);
	
	/**
	 * 查询该厂商id下的所有红包规则
	 * @param vendorId
	 * @return
	 */
	public List<RedEnvRule> getRedEnvRuleByVendorId(Integer vendorId);

	/**
	 * 根据id查询 对应的规则信息
	 * @param id
	 * @return
	 */
	public RedEnvRule getRedEnvRuleById(Integer id);

	/**
	 * 新建红包
	 * @param order
	 * @param rule
	 * @param redEnv
	 * @param money 
	 * @return
	 */
	public int addRedEnv(ProductOrder order, RedEnvRule rule, RedEnv redEnv,JSONObject result, Double money);
	
	/**
	 * 分页查询红包规则信息
	 * @param pageBean
	 * @param vendorId 
	 * @param endTime 
	 * @param startTime 
	 * @param keyword 
	 * @return
	 */
	public PageBean getRedEnvRuleList(PageBean pageBean, Integer vendorId, String keyword, String startTime, String endTime);

	/**
	 * 分页查询红包信息
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	public PageBean getgetRedEnvList(PageBean pageBean, Integer vendorId);

	/**
	 * 计算红包金额范围
	 * @param ruleId
	 * @param orderId
	 * @param result
	 * @return
	 */
	public JSONObject getMoneyScope(Integer ruleId, Integer orderId,JSONObject result);

	/**
	 * 获取红包信息
	 * @param redEnvId
	 * @param orderId
	 * @return 
	 */
	public RedEnv openEnv(Integer securityCodeId, Integer orderId);

	/**
	 * 修改红包信息
	 * @param redEnv
	 * @return
	 */
	public int updateRedEnvById(RedEnv redEnv);
	
	/**
	 * 根据二维码id查询二维码的所属人
	 * @param redEnv
	 * @return
	 */
	public String getSecurityCodeByIdRedEnvId(RedEnv redEnv);
	
	/**
	 * 添加红包领取记录
	 * @param redEnv
	 * @return
	 */
	public int addDrawRecord(RedEnv redEnv);

	/**
	 * 查询对应orderid的红包总表信息
	 * @param orderId
	 * @return
	 */
	public RedEnvelope getEnvelopeByOrderId(Integer orderId);

	/**
	 * 修改对应orderId的红包总表信息
	 * @param redEnvelope
	 * @return
	 */
	public int updateEnvelope(RedEnvelope redEnvelope);

	/**
	 * 添加红包地区信息
	 * @param area
	 * @return
	 */
	public int addRedEnvRuleArea(RedEnvRuleArea area);
	
	/**
	 * 获取红包省市区信息
	 * @param orderId
	 * @return
	 */
	public List<RedEnvRuleArea> getRedEnvRuleArea(Integer orderId);

}
