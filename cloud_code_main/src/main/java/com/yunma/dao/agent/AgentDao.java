package com.yunma.dao.agent;

import java.util.List;
import java.util.Map;

import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.entity.tracing.LogisticCodeScan;

public interface AgentDao {
	
	/**
	 * 新增代理商
	 * @param entity
	 * @return
	 */
	int addAgentInfo(AgentEntity entity);
	
	/**
	 * 更新代理商信息
	 * @param entity
	 * @return
	 */
	int updateAgentInfo(AgentEntity entity);
	
	/**
	 * 查询代理商数据
	 * @param vendorId
	 * @return
	 */
	List<AgentEntity> getAgentTree(Integer vendorId);
	
	/**
	 * 删除代理商
	 * @param id
	 * @return
	 */
	int deleteAgentNode(Integer id);
	
	/**
	 * 获取代理商总数
	 * @param vendorId
	 * @return
	 */
	int getCountAgent(Integer vendorId);
	
	/**
	 * 分页查询所以的代理商
	 * @param page
	 * @param vendorId
	 * @return
	 */
	List<AgentEntity> getAllAgent(Map<String,Object> map);
	
	/**
	 * 根据代理商id查询某个代理商的信息
	 * @param id
	 * @return
	 */
	AgentEntity getAgentInfoById(Integer id);
	
	/**
	 * 查询代理商授权码
	 * @param agentId
	 * @return
	 */
	LogisticCode getLogisticCodeByAgentId(Integer agentId);
	
	/**
	 * 根据溯源码查询
	 * @return
	 */
	LogisticCode getAgentLogisticCodeByCode(String logisticCode);
	
	/**
	 * 添加溯源扫码表
	 * @param codeSca
	 * @return
	 */
	int addLogisticCodeScan(LogisticCodeScan codeSca);
	
	/**
	 * 根据厂商id和代理商id查询
	 * @param vendorId
	 * @param AgentId
	 * @return
	 */
	AgentEntity getAllParentTreeNodeByChild(Integer vendorId,Integer AgentId);
	
	/**
	 * 获取授权码表中id的最大值
	 * @return
	 */
	Integer getLogisticIdMax();
	
	/**
	 * 根据厂商id查询所有的代理商名称
	 * @param vendorId
	 * @return
	 */
	List<AgentEntity> getAllAgentName(Integer vendorId);
}
