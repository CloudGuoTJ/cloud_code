package com.yunma.service.agent.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.agent.AgentEmployeeDao;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.service.agent.AgentEmployeeService;
import com.yunma.vo.agentEmployeeVo.AgentEmployeeVo;

@Service
public class AgentEmployeeServiceImpl implements AgentEmployeeService{
	
	@Resource
	private AgentEmployeeDao dao;

	@Override
	public int addEmpLoyee(AgentEmployee emp) {
		return dao.addEmpLoyee(emp);
	}

	@Override
	public int updateEmpInfo(AgentEmployee emp) {
		return dao.updateEmpInfo(emp);
	}

	@Override
	public int hasEmpInfo(String openId) {
		return dao.hasEmpInfo(openId);
	}

	@Override
	public List<Integer> getOpenIdFromEmp(String openId) {
		return dao.getOpenIdFromEmp(openId);
	}

	@Override
	public List<AgentEmployeeVo> getEmpInfoById(Integer agentId) {
		return dao.getEmpInfoById(agentId);
	}

	@Override
	public int deleteAgentEmpById(Integer agentId) {
		return dao.deleteAgentEmpById(agentId);
	}

	@Override
	public int deleteAgentEmp(Integer empId) {
		return dao.deleteAgentEmp(empId);
	}
	
	
	
	
}
