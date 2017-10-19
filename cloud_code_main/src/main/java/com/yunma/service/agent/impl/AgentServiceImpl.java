package com.yunma.service.agent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.Radix;
import com.common.util.TreeNode;
import com.yunma.dao.agent.AgentDao;
import com.yunma.dao.tracing.LogisticTracingDao;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.entity.tracing.LogisticCodeScan;
import com.yunma.service.agent.AgentService;
import com.yunma.utils.PageBean;

@Service
public class AgentServiceImpl implements AgentService{

	@Resource
	private AgentDao dao;
	@Autowired
	private LogisticTracingDao tracingDao;
	
	private List<AgentEntity> list = new ArrayList<AgentEntity>();

	@Override
	public int addAgentInfo(AgentEntity entity) {
		return dao.addAgentInfo(entity);
	}

	@Override
	public int updateAgentInfo(AgentEntity entity) {
		// TODO Auto-generated method stub
		return dao.updateAgentInfo(entity);
	}

	@Override
	public TreeNode<AgentEntity> getAgentTree(Integer vendorId) {
		
		List<AgentEntity> entitys = dao.getAgentTree(vendorId);
		
		List<TreeNode<AgentEntity>> nodes = new ArrayList<TreeNode<AgentEntity>>();
		
		
		
		if (entitys != null && entitys.size()>0) {
			
			for (AgentEntity entity : entitys) {
				TreeNode<AgentEntity> node=new TreeNode<AgentEntity>();
				
				node.setId(entity.getId().toString());
				if (entity.getAgentFid()!=null) {
					
					node.setParentId(entity.getAgentFid().toString());
				}
				node.setText(entity.getAgentName());
				
				node.setNodeData(entity);
				nodes.add(node);
				
			}
		}
		
		return TreeNode.buildTree(nodes);
	}

	@Override
	public int deleteAgentNode(Integer id) {
		
		return dao.deleteAgentNode(id);
	}

	@Override
	public PageBean getAllAgent(PageBean page, Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		int count = dao.getCountAgent(vendorId);
		
		page.setTotalRecords(count);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", page);
		map.put("vendorId", vendorId);
		List<AgentEntity> list = dao.getAllAgent(map);
		
		for (AgentEntity entity : list) {
			JSONObject obj=new JSONObject();
			obj.put("id", entity.getId());
			obj.put("agentName", entity.getAgentName());
			obj.put("vendorId", entity.getVendorId());
			obj.put("agentFid", entity.getAgentFid());
			obj.put("productId", entity.getProductId());
			obj.put("agentTel", entity.getAgentTel());
			obj.put("agentEmaill", entity.getAgentEmaill());
			obj.put("agentAddress", entity.getAgentAddress());
			obj.put("business_licence", entity.getBusinessLicence());
			obj.put("agent_authorize", entity.getAgentAuthorize());
			obj.put("agentLevel", entity.getAgentLevel());
			obj.put("agentFirstChilId", entity.getAgentFirstChilId());
			obj.put("broAgentId", entity.getBroAgentId());
			obj.put("mark", entity.getMark());
			
			array.add(obj);
		}
		result.put("data", array);
		page.setResult(result);
		
		return page;
	}

	@Override
	public AgentEntity getAgentInfoById(Integer id) {
		// TODO Auto-generated method stub
		return dao.getAgentInfoById(id);
	}

	@Override
	public LogisticCode getAgentLogisticCode(Integer agentId) {
		return dao.getLogisticCodeByAgentId(agentId);
	}

	@Override
	public LogisticCode getAgentLogisticCodeByCode(String logisticCode) {
		return dao.getAgentLogisticCodeByCode(logisticCode);
	}

	@Override
	public int addLogisticCodeScan(LogisticCodeScan codeSca) {
		return dao.addLogisticCodeScan(codeSca);
	}

	@Override
	@Transactional
	public JSONObject vendorGenerateLogisticCode(Integer vendorId,
			Integer agentId,Integer agentLevel) {
			JSONObject result = new JSONObject();//设置返回数据
//			VendorTracingInfo tracingInfo = new VendorTracingInfo();//获取代理信息
//			tracingInfo = tracingDao.getVerndorInfo(vendorId, agentId);
//			int agentFid = tracingInfo.getAgentFid();//父级Id暂时保留
			/**
			 * 物流授权码组成
			 * 1.商家的vendorId,
			 * 2.代理父级Id
			 * 3.代理等级lv(等级由厂商自己定)
			 * 4.代理本身agentId
			 */ 
			//String productCode = Radix.convert10To62(productId, 3);
			Integer logisticIdMax = dao.getLogisticIdMax();
			
			String vendorCode  = Radix.convert10To62(vendorId, 3);
			
			String agentCode = Radix.convert10To62(agentId, 3);
			
			String logisticId="";
			if(logisticIdMax == null){
				
				logisticId = Radix.convert10To62(1, 3);
			}else{
				logisticId = Radix.convert10To62(logisticIdMax+1, 3);
			}
			
			String logisticCode = vendorCode + agentCode + logisticId;//授权码次序产品前三位,商家中三位,代理Id后三位
			String logisticCodePri  = tracingDao.findLogisticCodeByAgentId(agentId);
			if(logisticCodePri == null){
				/**
				 * 如果已生成的不用再生成,未生成的,生成并保存生成的授权码
				 * 
				 */
				LogisticCode logisticCodePrivate = new LogisticCode();
				logisticCodePrivate.setAgentId(agentId);
				//logisticCodePrivate.setProductId(productId);
				logisticCodePrivate.setVendorId(vendorId);
				logisticCodePrivate.setLogisticCode(logisticCode);
				logisticCodePrivate.setLvCount(agentLevel);
				logisticCodePrivate.setStatus(0);
				int a = tracingDao.addTracingInfo(logisticCodePrivate);
				if(a > 0){
					
					result.put("errorCode", 1);
					result.put("msg", "生成成功!");
				}
				if(a < 0){
					
					result.put("errorCode", -2);
					result.put("msg", "生成失败请重试!");
				}
				
				
			}else{
				result.put("errorCode", -1);
				result.put("msg", "该代理用户已生成授权码!");
				
			}
			
		return result;
	}

	@Override
	public TreeNode<AgentEntity> getAllParentTreeNodeByChild(Integer agentId) {
		
		List<AgentEntity> entities=getAllParentTreeNodeByChild1(agentId);
		List<TreeNode<AgentEntity>> nodes=new ArrayList<TreeNode<AgentEntity>>();
		
		if (entities !=null && entities.size()>0) {
			
			for (AgentEntity entity : entities) {
				TreeNode<AgentEntity> node=new TreeNode<AgentEntity>();
				
				node.setId(entity.getId().toString());
				if (entity.getAgentFid() != null) {
					
					node.setParentId(entity.getAgentFid().toString());
				}
				node.setText(entity.getAgentName());
				
				node.setNodeData(entity);
				nodes.add(node);
				
			}
			list.clear();
		}
		
		return TreeNode.buildTree(nodes);
	}
	
	@Override
	public List<AgentEntity> getAllParentTreeNodeByChild1(Integer agentId){
		
		AgentEntity entity=dao.getAgentInfoById(agentId);
		
		if (entity !=null) {
			list.add(entity);
			Integer parentId=entity.getAgentFid();
			if (parentId !=null) {
				getAllParentTreeNodeByChild1(parentId);
			}
		}
		
		return list;
	}

	@Override
	public List<AgentEntity> getAllParentTreeNodeByChildForList(Integer agentId) {
		
		list.clear();
		list=getAllParentTreeNodeByChild1(agentId);
		return list;
	}

	@Override
	public Integer getLogisticIdMax() {
		return dao.getLogisticIdMax();
	}

	@Override
	public List<AgentEntity> getAllAgentName(Integer vendorId) {
		return dao.getAllAgentName(vendorId);
	}
	
	
}
