package com.yunma.dao.redEnvelope;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.redEnvelope.RedEnv;
import com.yunma.entity.redEnvelope.RedEnvRule;
import com.yunma.entity.redEnvelope.RedEnvRuleArea;
import com.yunma.entity.redEnvelope.RedEnvelope;
import com.yunma.entity.securityCode.SecurityCode;

@Repository("redEnvDao")
public interface RedEnvDao {

	/**
	 * 新增红包规则
	 * @param rule
	 * @return
	 */
	public int addRedEnvRule(RedEnvRule rule);
	
	/**
	 * 查询该厂商id下的所有红包规则
	 * @param vendorId
	 * @return
	 */
	public List<RedEnvRule> getRedEnvRuleByVendorId(Integer vendorId);
	
	/**
	 * 判断是否有重复的规则名
	 * @param vendorId
	 * @param ruleName
	 * @return
	 */
	public int hasRedEnvRuleName(Integer vendorId, String ruleName);
	
	/**
	 * 根据id查询 对应的规则信息
	 * @param id
	 * @return
	 */
	public RedEnvRule getRedEnvRuleById(Integer id);

	/**
	 * 创建红包表
	 * @param string
	 * @return
	 */
	public void createRedEvnTable(@Param("sql") String sql);
	
	/**
	 * 给红包住表插入数据
	 * @param rule
	 * @param redEnv
	 * @return
	 */
	public int addRedEnvInfo(Map<String,Object> map);
	
	/**
	 * 批量插入数据
	 */
	public int batchInsert(@Param("sql") String sql);
	
	/**
	 * 查询规则记录总数
	 * @param map
	 * @return
	 */
	public int getRedEnvRuleCount(Map<String, Object> map);
	
	/**
	 * 分页查询规则记录
	 * @param map
	 * @return
	 */
	public List<RedEnvRule> getRedEnvRuleList(Map<String, Object> map);
	
	/**
	 * 查询红包信息 记录数
	 * @param map
	 * @return
	 */
	public int getRedEnvInfoCount(Map<String,Object> map);

	/**
	 * 查询红包信息记录数
	 * @param map
	 * @return
	 */
	public List<RedEnvelope> getRedEnvInfo(Map<String,Object> map);
	
	/**
	 * 根据红包id查询 红包信息
	 * @param redEnv
	 * @return
	 */
	public RedEnv getRedEnvById(RedEnv redEnv);
	
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
	 * 修改对应orderId的红包
	 * @param redEnvelope
	 * @return
	 */
	public int updateEnvelope(RedEnvelope redEnvelope);

	public List<SecurityCode> getSecurityCodeByOrderId(Integer orderId);
	
	/**
	 * cloud
	 * 单个商家统计总的活动数量
	 */
	public int getActivTotalCount(Integer orderId);
	/**
	 * cloud
	 * 单个商家统计活动参与量
	 * 
	 */
	public int getJoinActivCount(Integer orderId);
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public int getDaylyJoinActivCount(Integer orderId);

	/**
	 * 添加红包省市区信息
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
