package com.yunma.service.tracing;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.common.exception.BusinessException;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.product.Product;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.entity.tracing.ProductTracingCodeAgentScan;
import com.yunma.entity.tracing.TracingCodeCustomerScan;
import com.yunma.entity.tracing.VendorTracingStatistics;
import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.model.Location;
import com.yunma.utils.PageBean;

/**
 * 溯源系统Service
 * 
 * @author Cloud
 *
 */
public interface ProductsTracingService  {
	
	/**
	 * 
	 * 商家创建agent代理商 
	 * @param vendorId
	 * @param orderId
	 * @return
	 */
	 
	public  JSONObject vendorCreateAgent(Integer vendorId,
			Integer orderId);
	/**
	 * 查询商家代理树
	 *
	 * @param vendorId
	 * @return
	 */
	
	public	JSONObject vendorFindAgentTree(Integer vendorId);
	
	/**
	 * 创建商家产品溯源码
	 */
	public List<ProductTracingCode> vendorGenerateProductTracingCode(Integer orderId,
			Integer vendorId,Integer  rowCount ,Integer boxCount);
	/**
	 * 创建溯源码表
	 * 
	 * @param orderId
	 * @return
	 */
	public int createOrderTracingTable(int orderId);
	/**
	 * 创建agent扫描溯源码信息记录表
	 * @param vendorId
	 * @return
	 */
	
	public int createScanTable(int vendorId);
	
	/**
	 * public int createCustomerTracingScanTable(int vendorId)
	 * 创建customer扫描溯源码信息记录表
	 * @param vendorId
	 * @return
	 */
	 public int createCustomerTracingScanTable(int vendorId);
	
	/**
	 * 将生成的溯源码保存入数据库
	 * 
	 * @param list
	 * @param orderId
	 * @param vendorName
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	int createProductTracingCode(List<ProductTracingCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException;
	
	/**
	 * 查询是否存在表
	 * 
	 */
	String getTBName(String tableName);
	/**
	 * 改变生成溯源码的订单的状态值
	 * @param orderId
	 * @return
	 */
	public int updateOrderStatus(Integer orderId);
	/**
	 * findTracingCodeInfoByTracingCode
	 * 微信回调查询溯源码信息
	 * @param shortCode
	 * @return
	 */
	
	public  ProductTracingCode  findTracingCodeInfoByTracingCode(String shortCode);
	/**
	 * 插入agent扫码信息
	 */
	public int addAgentScanInfo(Integer vendorId, ProductTracingCodeAgentScan tracingCodeScan);
	
	/**
	 * 插入customer扫码信息
	 */
	public int addCustomerScanInfo(Integer vendorId,TracingCodeCustomerScan customerScanCode);
	/**
	 * 通过openId获取代理员工信息
	 * @param vendorId 
	 */
	
	
	
	AgentEmployee findAgentEmployeeInfoByOpenId(String openId, Integer vendorId);
	/**
	 * 通过agentId获取agent信息
	 * 
	 */
	
	
	
	AgentEntity findAgentInfoByAgentId(Integer agentId);
	/**
	 * 添加厂商物流溯源统计记录
	 *
	 * @param vendorTracingStatistics
	 * @return
	 */
	
	
	
	
	int addVendorTracingStatisticsInfo(VendorTracingStatistics vendorTracingStatistics);
	/**
	 *
	 * 根据agentId和vendorId
	 * 查询溯源物流信息
	 * @param vendorId
	 * @param agentId
	 * @return
	 */
	VendorTracingStatistics findVendorScanInfoByvendorIdAndAgentId(Integer vendorId,Integer agentId);
	/**
	 * 更新消费者扫码经纬度信息
	 */
	int updateCustomerScanLocation(String openId,Integer vendorId,String longitude,String latitude);
	
	/**
	 * 更新代理商扫码经纬度信息
	 */
	int updateAgenScantLocation(String openId,Integer vendorId,String longitude,String latitude);
	/**
	 * 查询是否存在代理信息,如果存在则替换当前信息
	 * @param orderId
	 * @param lv
	 * @return
	 */
	
	public VendorTracingStatistics findVendorTracingStatisticsInfoByOrderId(
			Integer orderId);
	
	/**
	 * 替换VendorTracingStatistics 信息
	 */
	
	int updateVendorTracingStatisticsInfo(VendorTracingStatistics vendorTracingStatistics,Integer orderId, Integer lv);
	
	
	/**
	 * 分页查询显示溯源码单码信息
	 * @param map
	 * @return
	 */
	PageBean getProductTracingCodeInfo(PageBean pageBean,
			Integer orderId);
	
	/**
	 * 导出溯源码信息
	 * @param response
	 * @param orderId
	 */
	public void exportTracingBatchCodeToTXT(HttpServletResponse response, Integer orderId);
	/**
	 * 获取agent溯源信息
	 * @param orderId
	 * @param lv
	 * @param shortCode 
	 * @param openId 
	 * @return
	 */
	public List<VendorTracingStatistics> agentFindAllAgentInfo(Integer orderId,
			Integer lv, String openId, String shortCode);
	
	
	/**
	 * 获取最后一条溯源信息
	 * @return
	 */
	public Integer getLastAgentLevel(Integer orderId);
	
	
	
	/**
	 * 根据agentLv和orderId获取agent扫码信息
	 * @param shortCode 
	 * @param openId 
	 * 
	 */
	public VendorTracingStatistics findAgentTracingStatisticsInfoByOrderIdAndLv(Integer orderId,
			Integer lv);
	/**
	 * 创建箱码表
	 * 
	 * @param orderId
	 * @return
	 */
	public int createBoxCodeTable(Integer orderId);
	/**
	 * 创建箱码下的组表
	 * 
	 */
	public int createGroupCodeTable(Integer orderId);
	
	
	/**
	 * 创建箱码
	 * 
	 * @param orderId
	 * @param vendorId
	 * @param rowCount
	 * @param boxCount
	 * @return
	 */
	public List<BoxCode> vendorGenerateProductTracingBoxCode(Integer orderId,
			Integer vendorId,Integer  rowCount ,Integer boxCount);
	
	/**
	 * 创建组码
	 */
	public List<GroupCode> vendorGenerateProductTracingGroupCode(Integer orderId,
			Integer vendorId,Integer  rowCount ,Integer boxCount);
	
	/**
	 * 插入新箱表
	 * @throws BusinessException 
	 */
	public int insertBoxCodeInfo(List<BoxCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException;
	/**
	 * 插入新的组表
	 * @throws BusinessException 
	 */
	public int insertGroupCodeInfo(List<GroupCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException;
	
	/**
	 * 删除对应的表
	 * @param tableCodeTracingCodeName
	 */
	public int dropTable(String tableName);
	
	/**
	 * 分页查询显示溯源码箱码信息
	 * @param map
	 * @return
	 */
	PageBean getProductTracingBoxCodeInfo(PageBean pageBean,
			Integer orderId);
	/**
	 * 分页查询显示溯源码条码信息信息
	 * @param map
	 * @return
	 */
	PageBean getProductTracingGroupCodeInfo(PageBean pageBean,
			Integer orderId);
	/**
	 * 回调接口根据二维码信息获取box信息 
	 * @param shortCode
	 * @return
	 */
	public BoxCode getTracingBoxCodeByBoxCode(Integer orderId,String shortCode);
	
	/**
	 * 根据组码查询组码信息
	 * @param shortCode
	 * @return
	 */
	public GroupCode getTracingGroupCodeByCode(Integer orderId,String shortCode);
	/**
	 * 根据openId和shortCode查询 代理扫码信息
	 * @param openId
	 * @param shortCode
	 * @return
	 */
	public ProductTracingCodeAgentScan getAgentSanInfo(String openId,
			String shortCode);
	/**
	 * 用于去除重复扫码信息的查询
	 * @param openId
	 * @param orderId
	 * @param lv
	 * @param shortCode
	 * @return
	 */
	public VendorTracingStatistics findAgentTracingStatisticsInfoIfExist( Integer orderId,
			Integer lv, String shortCode,String openId);
	
	/**
	 * 获取所有扫码信息商家扫码显示
	 * cusTomerScanInfo
	 * @param orderId
	 * @param lv
	 * @return
	 */
	public List<VendorTracingStatistics> findAllAgentInfo(Integer orderId,
			Integer boxNum,Integer groupNum);
	
	/**
	 * 追加严密等级 溯源信息
	 * 
	 */
	public int exchangeTracingDegree(Integer orderId);
	/**
	 * 根据溯源码中物流码信息获取第一个扫码代理信息
	 * @param shortCode
	 * @return
	 */
	public ProductTracingCodeAgentScan getAgentFirstSanInfo(Integer vendorId,String shortCode);
	
	/**
	 * 更新agentScan的地理位置信息
	 * @param vendorId 
	 * @param location
	 * @return
	 */
	public int updateAgentInfoSetLocation(Integer vendorId, Location location, String openId,
			String shortCode);
	
	/**
	 * 查出上一个扫描物流码的代理信息
	 * @param vendorId
	 * @param shortCode
	 * @return
	 */
	public ProductTracingCodeAgentScan findExtraScanByShortCode(
			Integer vendorId, String shortCode);
	
	
	
}
