package com.yunma.dao.tracing;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.tracing.LogisticCode;
import com.yunma.entity.tracing.LogisticCodeScan;
import com.yunma.model.VendorTracingInfo;
/**
 * 溯源
 * LogisticTracingDao
 * @author cloud
 *
 */
public interface LogisticTracingDao extends BaseMapper {
	/**
	 * 查询商家下所有扫码代理信息
	 * 
	 */
	@Select("SELECT * FROM tb_logistic_code_scan where vendorId=#{vendorId}")
	LogisticCodeScan findScanInfoByvendorId(@Param("vendorId") Integer vendorId);
	/**
	 * 查询商家代理
	 * 保证同一agentCode下只有一个授权码
	 */
	@Select("SELECT * FROM tb_logistic_code WHERE vendorId=#{vendorId}")
	LogisticCode findAgentInfoByVendorId(@Param("vendorId")Integer vendorId);
	/**
	 * 插入溯源表信息
	 * 
	 */
	@Insert("INSERT INTO " +
			" tb_logistic_code " +
			"SET" +
			" vendorId=#{vendorId}, productId=#{productId}, agentId=#{agentId}, logisticCode=#{logisticCode}, createTime=now(),lvCount=#{lvCount} ")
	int addTracingInfo(LogisticCode logisticCode);
	/**
	 * 查询agent信息
	 * 
	 */
	@Select("SELECT e.agentId, e.empFid ,e.empName, e.productId, e.createTime, e.workNum," +
			" e.empLevel, e.expireTime,e.empTel,e.empIdcard,a.agentName,a.vendorId,a.agentAddress," +
			"a.agentAuthorize,a.agentLevel,a.agentFirstChilId,a.agentTel," +
			"a.agentFid,a.businessLicence,a.broAgentId " +
			"FROM " +
			"tb_agent_employee e,tb_vendor_agent a " +
			"WHERE " +
			"e.agentId = a.id AND a.vendorId=#{vendorId} AND e.agentId=#{agentId}")
	VendorTracingInfo getVerndorInfo(@Param("vendorId")Integer vendorId,@Param("agentId")Integer agentId);
	/**
	 * 根据agentId查询授权码
	 * @param agentId
	 * @return
	 */
	@Select("SELECT logisticCode from tb_logistic_code WHERE agentId=#{agentId}  ")
	String findLogisticCodeByAgentId(@Param("agentId")Integer agentId);
	

	

}
