package com.yunma.dao.tracing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.entity.tracing.ProductTracingCodeAgentScan;
import com.yunma.entity.tracing.TracingCodeCustomerScan;
import com.yunma.entity.tracing.VendorTracingStatistics;
import com.yunma.model.AntiFake;
import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.model.Location;

public interface ProductTracingDao extends BaseMapper{
	/**
	 * 统计生成的溯源码数量
	 * 用于分页显示溯源码
	 * @param orderId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_tracing_code_#{orderId}")
	int getProductTracingCodeCount(@Param("orderId")Integer orderId);
	/**
	 * 分页显示溯源码信息
	 * @param map
	 * @return
	 */
	@Select("SELECT * " +
			" FROM tb_product_tracing_code_#{orderId} " +
			" ORDER BY " +
			" productTracingCodeId ASC " +
			" LIMIT #{pageBean.index},#{pageBean.pageSize} ")
	List<ProductTracingCode> getTracingCodeInfo(Map<String, Object> map);
	
	/**
	 * 根据订单和溯源码查询溯源表信息
	 * @param productTracingCode
	 * @param orderId
	 * @return
	 */
	@Select("SELECT * FROM tb_product_tracing_code_#{orderId} WHERE	productTracingCode=#{shortCode}")
	ProductTracingCode findCodeByVendorId(@Param("shortCode")String  shortCode,@Param("orderId")Integer orderId);
	/**
	 * 创建溯源码
	 * @param sql
	 * @return
	 */
	@Update("${sql}")
	int createTracingCodeDBTable(@Param("sql") String sql);
	
	
	/**
	 * 2代表已经生成溯源码
	 * 改变已生成溯源码信息的订单
	 * @param orderId
	 * @return
	 */
	@Update("update tb_product_order set tracingFlag=2 where orderId=#{0}")
	int updateOrderStatus(Integer orderId);
	

	/**
	 * 删除订单时删除对应的表
	 * 
	 * @param tableName
	 * @return
	 */
	@Update("DROP TABLE IF EXISTS  ${tableName}")
	public int dropTableByOrder(@Param("tableName")String tableName);
	
	/**
	 * 查找数据库中是否存在表
	 */
	@Select("SHOW TABLES LIKE '${tableName}'")
	String selectDBTable(@Param("tableName") String tableName);
	
	/**
	 * 插入溯源表溯源码信息
	 */
	@Insert("${sql}")
	int createProductTracingCode(@Param("sql") String sql);
	
	/**
	 * 将agent扫描溯源码存入数据库
	 * 
	 * @param orderId
	 * @param antiFake
	 * @return createRowNum
	 */

	@Insert("INSERT INTO tb_product_tracing_agent_scan_#{vendorId} SET " 
	        + "productTracingCodeId=#{tracingCodeScan.productTracingCodeId},"
			+ "productTracingCode=#{tracingCodeScan.productTracingCode}, " 
			+ "codeType=#{tracingCodeScan.codeType},"
			+ "productId=#{tracingCodeScan.productId},"
			+ "vendorId=#{tracingCodeScan.vendorId},"
			+ "createRowNum=#{tracingCodeScan.createRowNum},"
			+ "countPro=#{tracingCodeScan.countPro},"
			+ "productName=#{tracingCodeScan.productName},"
			+ "scanTime=now(),openId=#{tracingCodeScan.openId}," 
			+ "agentId=#{tracingCodeScan.agentId},"
			+ "agentName=#{tracingCodeScan.agentName},"
			+ "agentLv=#{tracingCodeScan.agentLv}")
	int addAgentTracingAntiFakeInfo(@Param("vendorId") Integer vendorId,
			@Param("tracingCodeScan") ProductTracingCodeAgentScan tracingCodeScan);
	
	/**
	 * 将消费者扫码信息存入数据库
	 * 
	 * @param orderId
	 * @param antiFake
	 * @return
	 */

	@Insert("INSERT INTO tb_product_tracing_customer_scan_#{vendorId}  SET "
			+ "productTracingCodeId=#{customerScanCode.productTracingCodeId}"
			+ ",productTracingCode=#{customerScanCode.productTracingCode}"
			+ ",vendorId=#{customerScanCode.vendorId}"
			+ " ,productId=#{customerScanCode.productId},"
			+ "productName=#{customerScanCode.productName},"
			+ "scanTime=now(),openId=#{customerScanCode.openId}" )
	int addCustomerTracingAntiFakeInfo(@Param("vendorId") Integer vendorId,
			@Param("customerScanCode") TracingCodeCustomerScan customerScanCode);

	/**
	 * 从agentEmployee表中拿到employee
	 * 
	 * @param openId
	 * @return
	 */
	@Select("SELECT a.*,b.vendorId FROM yunma.tb_agent_employee a,yunma.tb_vendor_agent b WHERE a.agentId=b.id AND b.vendorId=#{vendorId} AND a.openId=#{openId};")
	AgentEmployee findAgentEmployeeInfoByOpenId(@Param("openId")String openId,@Param("vendorId")Integer vendorId);

	/**
	 * 根据agentId查询agent信息
	 * 
	 * @param agentId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_agent WHERE id=#{agentId};")
	AgentEntity findAgentInfoByAgentId(Integer agentId);

	/***
	 * 将溯源物流信息整理存入厂商溯源统计表 插入信息
	 * @param vendorTracingStatistics
	 * @return
	 */
	@Insert("INSERT ignore into tb_vendor_tracing_statistics SET "
			+ "vendorId=#{vendorTracingStatistics.vendorId},"
			+ "orderId=#{vendorTracingStatistics.orderId},"
			+ "productId=#{vendorTracingStatistics.productId},"
			+ "agentId=#{vendorTracingStatistics.agentId},"
			+ "agentName=#{vendorTracingStatistics.agentName},"
			+ "productName=#{vendorTracingStatistics.productName},"
			+ "productRowNum=#{vendorTracingStatistics.productRowNum},"
			+ "tracingCode=#{vendorTracingStatistics.tracingCode},"
			+ "count=#{vendorTracingStatistics.count},"
			+ "codeType=#{vendorTracingStatistics.codeType},"
			+ "scanTime=now()," 
			+ "openId=#{vendorTracingStatistics.openId},"
			+ "address=#{vendorTracingStatistics.address},"
			+ "province=#{vendorTracingStatistics.province},"
			+ "city=#{vendorTracingStatistics.city},"
			+ "district=#{vendorTracingStatistics.district},"	
			+ "lv=#{vendorTracingStatistics.lv} " )
	int addVendorTracingStatistics(@Param("vendorTracingStatistics")
			VendorTracingStatistics vendorTracingStatistics);
	/**
	 * 根据agentId和vendorId
	 * 查询溯源物流信息
	 */
	@Select("SELECT * FROM tb_vendor_tracing_statistics WHERE vendorId=#{vendorId} AND agentId=#{agentId};")
	VendorTracingStatistics findVendorScanInfoByvendorIdAndAgentId(Integer vendorId,Integer agentId);
	
	
	/**
	 * 更新消费者溯源信息表经纬度
	 */
	@Update("UPDATE tb_product_tracing_customer_scan_#{vendorId} SET longitude=#{longitude},latitude=#{latitude} where openId=#{openId} order by scanTime desc Limit 1;")
	int updateCustomerScanLocation(
			@Param("openId") String openId,
			@Param("vendorId") Integer vendorId,
			@Param("longitude") String longitude,
			@Param("latitude") String latitude);
	/**
	 * 更新agent溯源信息表经纬度
	 */
	@Update("UPDATE tb_product_tracing_agent_scan_#{vendorId} SET longitude=#{longitude},latitude=#{latitude} where openId=#{openId} order by scanTime desc Limit 1")
	int updateAgentScanLocation(
			@Param("openId") String openId,
			@Param("vendorId") Integer vendorId,
			@Param("longitude") String longitude,
			@Param("latitude") String latitude);
	
	/**
	 * 更新相同订单下同级代理的溯源信息
	 * @param vendorTracingStatistics
	 * @return
	 */
	@Update("UPDATE tb_vendor_tracing_statistics SET " 
			+ "vendorId=#{vendorTracingStatistics.vendorId},"
			+ "orderId=#{vendorTracingStatistics.orderId},"
			+ "productId=#{vendorTracingStatistics.productId},"
			+ "agentId=#{vendorTracingStatistics.agentId},"
			+ "agentName=#{vendorTracingStatistics.agentName},"
			+ "productName=#{vendorTracingStatistics.productName},"
			+ "productRowNum=#{vendorTracingStatistics.productRowNum},"
			+ "tracingCode=#{vendorTracingStatistics.tracingCode},"
			+ "count=#{vendorTracingStatistics.count},"
			+ "codeType=#{vendorTracingStatistics.codeType},"
			+ "scanTime=now()," + "openId=#{vendorTracingStatistics.openId},"
			+ "address=#{vendorTracingStatistics.address},"
			+ "province=#{vendorTracingStatistics.province},"
			+ "city=#{vendorTracingStatistics.city},"
			+ "district=#{vendorTracingStatistics.district},"	
			+ "lv=#{vendorTracingStatistics.lv} " 
			+ " WHERE orderId=#{orderId} AND lv=#{lv}" )
	int updateVendorTracingStatisticsInfo(
			@Param("vendorTracingStatistics") VendorTracingStatistics vendorTracingStatistics,@Param("orderId")Integer orderId,@Param("lv")Integer lv);
	
	/**
	 * 查询相同订单和相同等级下的代理信息是否存在
	 * @param orderId
	 * @param lv
	 * @return
	 */
	@Select("select tsm.vendorTracingId,tsm.vendorId,tsm.orderId,tsm.productId,tsm.productName,tsm.agentName,tsm.agentId,tsm.productRowNum,tsm.openId,tsm.lv,tsm.scanTime,tsm.address,tsm.province, tsm.city,tsm.district from (select vendorTracingId, vendorId, orderId,productRowNum, productId, productName, agentName, agentId,openId,lv,scanTime,address,province, city, district,max(lv) as maxlv from yunma.tb_vendor_tracing_statistics where orderId=#{orderId})tsm ;")
	VendorTracingStatistics findVendorTracingStatisticsInfoByOrderIdAndLv(
			@Param("orderId") Integer orderId);
/**
 * 
 * @param orderId
 * @return
 */
	@Select("SELECT productTracingCode from tb_product_tracing_code_#{orderId}")
	List<String> getTracingCodeList(@Param("orderId")Integer orderId);
	/**
	 * 查询溯源码导出次数
	 * @param orderId
	 * @return
	 */
	@Select("SELECT exprotTracingCodeCount from tb_product_order WHERE orderId=#{orderId}")
	int findDownCountByOrderId(@Param("orderId") Integer orderId);
	/**
	 * 更新导出溯源码次数
	 * @param orderId
	 * @param downLoadCount
	 * @return
	 */
	@Update("UPDATE tb_product_order SET exprotTracingCodeCount=#{downLoadCount} WHERE orderId=#{orderId}")
	int  updateDownloadCount(@Param("orderId")Integer orderId, @Param("downLoadCount")int downLoadCount);

	/**
	 *  查询agent溯源信息是否存在重复
	 * @param orderId
	 * @param lv
	 * @return
	 */
	 @Select("select * from tb_vendor_tracing_statistics " +
	 		" where lv=#{lv} AND orderId=#{orderId} AND tracingCode=#{shortCode} AND openId=#{openId} Order by scanTime DESC Limit 1; ")
	VendorTracingStatistics findAgentTracingStatisticsInfoIfExist(
			@Param("orderId")Integer orderId,
			@Param("openId")String openId, 
			@Param("shortCode")String shortCode, 
			@Param("lv")Integer lv);;
	/**
	 * 获取当前订单最大代理等级
	 * @param orderId
	 * @return
	 */
	@Select("SELECT MAX(lv) FROM tb_vendor_tracing_statistics WHERE orderId=#{orderId}; ")
	int getMaxLvNum(@Param("orderId")Integer orderId);

	/**
	 * 插入组码 信息(预留接口使用)
	 * @param groupCode
	 * @param orderId
	 * @return
	 */
	@Insert("INSERT ignore into tb_product_tracing_group_code_#{orderId} SET "
			+ "orderId=#{groupCode.orderId},"
			+ "productId=#{groupCode.productId},"
			+ "vendorId=#{groupCode.vendorId},"
			+ "groupCount=#{groupCode.groupCount},"
			+ "groupCode=#{groupCode.groupCode},"
			+ "productName=#{groupCode.productName},"
			+ "createTime=now()," 
			+ "createRowNum=#{groupCode.createRowNum},"
			+ "totalRowNum=#{groupCode.totalRowNum},"
			+ "boxCount=#{groupCode.boxCount}")
	int addGroupCode(@Param("groupCode")
			GroupCode groupCode,@Param("orderId")Integer orderId);
	/**
	 * 插入箱码信息 (预留接口)
	 * @param boxCode
	 * @param orderId
	 * @return
	 */
	
	@Insert("INSERT ignore into tb_product_tracing_box_code_#{orderId} SET "
			+ "orderId=#{groupCode.orderId},"
			+ "productId=#{groupCode.productId},"
			+ "vendorId=#{groupCode.vendorId},"
			+ "groupCount=#{groupCode.groupCount},"
			+ "productName=#{groupCode.productName},"
			+ "codePrefix=#{groupCode.codePrefix},"
			+ "boxCode=#{groupCode.boxCode},"
			+ "totalRowNum=#{groupCode.productName},"
			+ "boxNum=#{groupCode.productName},"
			+ "boxShortCode=#{groupCode.boxShortCode},"
			+ "boxCount=#{groupCode.boxCount},"
			+ "createTime=now()" )
	int addBoxCode(@Param("boxCode")
			BoxCode boxCode,@Param("orderId")Integer orderId);
	
	
	/**
	 * 箱码分页显示 
	 * @param orderId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_tracing_box_code_#{orderId}")
	int getBoxCodeCount(@Param("orderId")Integer orderId);	
	@Select("SELECT * " +
			" FROM tb_product_tracing_box_code_#{orderId} " +
			" ORDER BY " +
			" boxCodeId ASC " +
			" LIMIT #{pageBean.index},#{pageBean.pageSize} ")
	List<BoxCode> getBoxCodeInfo(Map<String, Object> map);
	
	/**
	 * 组码码分页显示
	 */	
	@Select("SELECT * " +
			" FROM tb_product_tracing_group_code_#{orderId} " +
			" ORDER BY " +
			" groupCodeId ASC " +
			" LIMIT #{pageBean.index},#{pageBean.pageSize} ")
	List<GroupCode> getGroupCodeInfo(Map<String, Object> map);
	
	
	@Select("SELECT COUNT(*) FROM tb_product_tracing_group_code_#{orderId}")
	int getGroupCodeCount(@Param("orderId")Integer orderId);
	/**
	 * 根据boxCode获取BoxCode信息
	 * @param shortCode
	 * @return
	 */
	@Select("SELECT * FROM tb_product_tracing_box_code_#{orderId} WHERE	boxCode=#{shortCode}")
	BoxCode getBoxCodeInfoByBoxCode(@Param("orderId")Integer orderId,@Param("shortCode")String shortCode);
	
	/**
	 * 根据shortCode获取groupCode 信息
	 * @param orderId
	 * @param shortCode
	 * @return
	 */
	@Select("SELECT * FROM tb_product_tracing_group_code_#{orderId} WHERE	groupCode=#{shortCode}")
	GroupCode getTracingGroupCodeByCode(@Param("orderId")Integer orderId,@Param("shortCode")String shortCode);
	
	/**
	 * 根据shortCode和openId查询代理扫码信息
	 * @param openId
	 * @param shortCode
	 * @return
	 */
	@Select("SELECT * FROM tb_vendor_tracing_statistics WHERE openId=#{openId} AND tracingCode=#{shortCode} order by scanTime LIMIT 1;")
	ProductTracingCodeAgentScan getAgentSanInfoByOpenIdAndTracingCode(
			@Param("openId")String openId,@Param("shortCode") String shortCode);
	
	/**
	 * 查询当前商家溯源统计信息
	 * @param orderId
	 * @param lv
	 * @return
	 */
	 @Select("select * from tb_vendor_tracing_statistics " +
		 		" where lv=#{lv} AND orderId=#{orderId} Order by scanTime DESC Limit 1; ")
	VendorTracingStatistics findAgentTracingStatisticsInfoByOrderIdAndLv(
			Integer orderId, Integer lv);
	 
	 /**
	  * 根据箱号查询箱码信息
	  * @param orderId
	  * @param boxNum
	  * @return
	  */
	 @Select("select * from tb_product_tracing_box_code_#{orderId} " +
		 		" where boxNum=#{boxNum}; ")
	BoxCode getBoxInfoByboxNum(@Param("orderId")Integer orderId, @Param("boxNum")Integer boxNum);
	/**
	 * 根据组号查询组码信息
	 * @param orderId
	 * @param groupNum
	 * @return
	 */
	 @Select("select * from tb_product_tracing_group_code_#{orderId} " +
		 		" where groupCodeId=#{groupNum}; ")
	GroupCode getTracingGroupCodeByGroupNum(@Param("orderId")Integer orderId,@Param("groupNum") Integer groupNum);
	 
	 /**
	  * 追加获取厂商代理扫描箱码和条码的记录按时间排序不作处理直接返回
	  * @param boxCode
	  * @param groupCode
	  * @return
	  */
	 @Select("(SELECT * FROM tb_vendor_tracing_statistics where tracingCode=#{boxCode} order by scanTime DESC) " +
	 		"UNION ALL " +
	 		"(SELECT * FROM tb_vendor_tracing_statistics Where tracingCode=#{groupCode} order By scanTime DESC) ")
	List<VendorTracingStatistics> getListScanInfoByCode(@Param("boxCode")String boxCode, @Param("groupCode")String groupCode);
	 
	 /**
	  * 追加agent扫码显示上级代理信息显示
	  * @param groupCode
	  * @return
	  */
	@Select("SELECT * FROM tb_vendor_tracing_statistics Where tracingCode=#{groupCode} order By scanTime DESC")
	 List<VendorTracingStatistics> getListAgentScanInfoByGroupCode( @Param("groupCode")String groupCode);
	 /**
	  * 追加Agent扫码显示上级代理
	  * @param boxCode
	  * @return
	  */
	 @Select("SELECT * FROM tb_vendor_tracing_statistics where tracingCode=#{boxCode} order by scanTime DESC ")
	 List<VendorTracingStatistics> getListAgentScanInfoByBoxCode(@Param("boxCode")String boxCode);
	 /**
	  * 查询顶级溯源包装下的溯源码
	  * @param orderId
	  * @return
	  */
	 @Select("select * from tb_product_tracing_box_code_#{orderId}")
	List<BoxCode> findBoxCodeInfoListByOrderId(@Param("orderId")Integer orderId);
	 /**
	  * 查询boxCode对应的GroupCode
	  * @param orderId
	  * @param boxNum
	  * @return
	  */
	 @Select("select * from tb_product_tracing_group_code_#{orderId} WHERE boxNum=#{boxNum}")
	List<GroupCode> findGroupCodeInfoListByBoxNum(@Param("orderId")Integer orderId,@Param("boxNum")Integer boxNum);
	 
	 /**
	  * 查询溯源组码对应的单码信息
	  * @param orderId
	  * @param groupNum
	  * @return
	  */
	 @Select("select * from tb_product_tracing_code_#{orderId} WHERE createRowNum=#{groupNum}")
	List<ProductTracingCode> getTracingCodeByGroupNum(@Param("orderId")Integer orderId,
			@Param("groupNum")Integer groupNum);
	 
	 
	 /**
	  * 更改溯源等级,严格防止窜货
	  * @param orderId
	  * @return
	  */
	 @Update("update tb_product_order set tracingHigherDegreeControll=2 where orderId=#{orderId}")
	int updateTracingDegreeByOrderId(Integer orderId);
	 
	 /**
	  * 根据物流码获取第一个扫码代理信息
	  * @param orderId
	  * @param shortCode
	  * @return
	  */
	 @Select("SELECT * FROM tb_product_tracing_agent_scan_#{vendorId} WHERE productTracingCode=#{shortCode} order by scanTime asc LIMIT 1")
	ProductTracingCodeAgentScan getAgentFirstScanInfoByShortCode(
			 @Param("vendorId")Integer vendorId, @Param("shortCode")String shortCode);
	 /**
	  * 更新代理扫码信息
	  * @param location
	  * @param openId
	  * @param shortCode
	  * @return
	  */
	 @Update("UPDATE tb_product_tracing_agent_scan_#{vendorId} " +
	 		"SET " +
	 		"scanAddress=#{location.address},province=#{location.province}," +
	 		"city=#{location.province},district=#{location.district} " +
	 		"where " +
	 		"openId=#{openId} AND productTracingCode=#{shortCode} "  +
	 		"order by scanTime desc Limit 1")
	int updateAgentInfoSetLocation(@Param("vendorId")Integer vendorId,@Param("location")Location location, @Param("openId")String openId,
			@Param("shortCode")String shortCode);
	 /**
	  * 查询上一条代理或者第一条扫码数据
	  * @param vendorId
	  * @param shortCode
	  * @return
	  */
	 @Select("SELECT * FROM yunma.tb_product_tracing_agent_scan_#{vendorId} where productTracingCode=#{shortCode} AND scanTime < now() order by scanTime  limit 1;")
	ProductTracingCodeAgentScan findExtraAgentScanInfo(Integer vendorId,
			String shortCode);

	 
	 
	
	
	
	
	
	
	
	
	
}
