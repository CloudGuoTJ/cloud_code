package com.yunma.service.agent;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.common.util.TreeNode;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.entity.tracing.LogisticCodeScan;
import com.yunma.utils.PageBean;

public interface AgentService {
	
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
	 * 将代理商数据整理为树
	 * @param vendorId
	 * @return
	 */
	TreeNode<AgentEntity> getAgentTree(Integer vendorId);
	
	/**
	 * 删除代理商
	 * @param id
	 * @return
	 */
	int deleteAgentNode(Integer id);
	
	/**
	 * 分页查询代理商数据
	 * @param page
	 * @param vendorId
	 * @return
	 */
	PageBean getAllAgent(PageBean page,Integer vendorId);
	
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
	LogisticCode getAgentLogisticCode(Integer agentId);
	
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
	 * 创建商家物流溯源授权码
	 * 
	 */
	public JSONObject vendorGenerateLogisticCode(Integer vendorId,Integer agentId,Integer agentLevel);
	
	/**
	 * 根据某个子节点查询所有的父节点，以树状结构呈现
	 * @param AgentId
	 * @return
	 */
	TreeNode<AgentEntity> getAllParentTreeNodeByChild(Integer agentId);
	
	/**
	 * 根据某个子节点查询所有的父节点，以集合形式呈现
	 * @param AgentId
	 * @return
	 */
	List<AgentEntity> getAllParentTreeNodeByChild1(Integer agentId);
	
	/**
	 * 根据某个子节点查询所有的父节点，以集合形式呈现
	 * @param AgentId
	 * @return
	 */
	List<AgentEntity> getAllParentTreeNodeByChildForList(Integer agentId);
	
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
