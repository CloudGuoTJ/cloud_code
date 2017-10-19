package com.yunma.controller.agent;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.service.agent.AgentEmployeeService;
import com.yunma.vo.agentEmployeeVo.AgentEmployeeVo;

@Controller
public class AgentEmployeeController {

	@Resource
	private AgentEmployeeService service;

	/**
	 * 新增代理商员工
	 * 
	 * @param emp
	 * @return
	 */
	@RequestMapping("/ADD/AgentEmployee/addEmpInfo.do")
	@ResponseBody
	public JSONObject addEmpInfo(AgentEmployee emp) {
		JSONObject result = new JSONObject();
		
		List<Integer> vendorIds=service.getOpenIdFromEmp(emp.getOpenId());
		if (vendorIds == null || vendorIds.size()==0) {
			int temp = service.addEmpLoyee(emp);
			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}
		} else {
			for (Integer vendorId : vendorIds) {
				if (vendorId.equals(emp.getVendorId())) {
					result.put("status", -1);
					result.put("msg", "您已绑定过当前厂商的代理商，请勿重复绑定！");
					return result;
				}
			}
			int temp = service.addEmpLoyee(emp);
			if (temp > 0) {
				result.put("status", 1);
				result.put("msg", "成功");
			} else {
				result.put("status", -2);
				result.put("msg", "新增失敗");
			}
			
		}
		
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param emp
	 * @return
	 */
	@RequestMapping("/UPDATE/AgentEmployee/updateEmpInfo.do")
	@ResponseBody
	public JSONObject updateEmpInfo(AgentEmployee emp) {
		JSONObject result = new JSONObject();
		int temp = service.updateEmpInfo(emp);
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "成功");
		} else {
			result.put("status", -2);
			result.put("msg", "修改失敗");
		}
		return result;
	}
	
	/**
	 * 根据代理商id获取员工信息
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping("/GET/AgentEmployee/getEmpInfoById.do")
	@ResponseBody
	public JSONObject getEmpInfoById(Integer agentId){
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		List<AgentEmployeeVo> emps=service.getEmpInfoById(agentId);
		
		if (emps !=null && emps.size()>0) {
			
			for (AgentEmployeeVo emp : emps) {
				JSONObject obj=new JSONObject();
				obj.put("id", emp.getId());
				obj.put("agentId", emp.getAgentId());
				obj.put("agentName", emp.getAgentName());
				obj.put("empName", emp.getEmpName());
				obj.put("createTime", emp.getCreateTime());
				obj.put("workNum", emp.getWorkNum());
				obj.put("empTel", emp.getEmpTel());
				obj.put("empIdcard", emp.getEmpIdcard());
				array.add(obj);
			}
			result.put("data", array);
		}
		
		return result;
	}
	
	/**
	 * 删除代理员工
	 * @param empId
	 * @return
	 */
	@RequestMapping("/DELETE/AgentEmployee/deleteAgentEmp.do")
	@ResponseBody
	public JSONObject deleteAgentEmp(Integer empId){
		JSONObject result = new JSONObject();
		int temp = service.deleteAgentEmp(empId);
		
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "删除成功！");
		}else{
			result.put("status", -1);
			result.put("msg", "删除失败");
		}
		
		return result;
	}

}
