package com.yunma.dao.agent;

import java.util.List;

import com.yunma.entity.agent.AgentEmployee;
import com.yunma.vo.agentEmployeeVo.AgentEmployeeVo;

public interface AgentEmployeeDao {
	
	/**
	 * 新增代理商员工
	 * @param emp
	 * @return
	 */
	int addEmpLoyee(AgentEmployee emp);
	
	/**
	 * 修改代理员工信息
	 * @param emp
	 * @return
	 */
	int updateEmpInfo(AgentEmployee emp);
	
	/**
	 * 查询表里面是否存在
	 * @param openId
	 * @return
	 */
	int hasEmpInfo(String openId);
	
	/**
	 * 根据openId查询代理员工表中是否已经注入
	 * @param openId
	 * @return
	 */
	List<Integer> getOpenIdFromEmp(String openId);
	
	/**
	 * 查询某个代理商下面所有授权的员工
	 * @param agentId
	 * @return
	 */
	List<AgentEmployeeVo> getEmpInfoById(Integer agentId);
	
	/**
	 * 删除某个代理商下所有 的员工
	 * @param agentId
	 * @return
	 */
	int deleteAgentEmpById(Integer agentId);
	
	/**
	 * 根据id删除员工
	 * @param empId
	 * @return
	 */
	int deleteAgentEmp(Integer empId);

}
