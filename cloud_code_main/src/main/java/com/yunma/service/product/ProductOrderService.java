package com.yunma.service.product;

import java.util.List;

import com.common.exception.BusinessException;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.User;
import com.yunma.utils.PageBean;
import com.yunma.vo.product.ProductOrderVO;
import com.yunma.vo.user.UserVO;

/**
 * ProductOrder 接口 封装有关ProductOrder表参数的获取方法 Created by ${CloudGoo} on 2017/5/5
 * 0005.
 */
public interface ProductOrderService {

	/* 创建订单 */
	int createOrder(ProductOrder order);

	/* 根据厂商和订单号生成二维码 */
	// List<SecurityCode> generateSecurityCode(int orderId, String vendorName)
	// throws BusinessException;
	// /*删除订单*/
	int deleteOrder(int orderId);

	/* 查询商家名下所有订单 分页操作 */
	List<ProductOrder> findAllOrderByVendorId(User user);

	/* 清空记录 */
	int setIsClear(int orderId, int isClear);

	/* 通过OrderId查询订单信息 */
	ProductOrder findOrderByProductOrderId(Integer orderId);

	/* 通过商家ID或者商品名称查找订单 */
	List<ProductOrder> findAllOrderByVendorIdOrProduct(User user,
			String productName);

	/**/
	int findAllOrderByVendorIdOrProductNameCount(User user, String productName);

	List<ProductOrder> findOrderList(int num, int page, User user);

	int findOrderListCount(User user);

	int findLastOrder(User user);

	/**
	 * 生成二维码
	 * 
	 * @param orderId
	 * @param vendorName
	 * @return
	 * @throws BusinessException
	 */
	List<SecurityCode> generateSecurityCode(int orderId, String vendorName)
			throws BusinessException;

	/**
	 * 将生成的二维码保存入数据库
	 * 
	 * @param list
	 * @param orderId
	 * @param vendorName
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	int createSecuritycode(List<SecurityCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException;

	/**
	 * 新建表
	 * 
	 * @param orderId
	 * @return
	 */
	int createOrderTable(int orderId);

	/**
	 * 更新已生成二维码的订单的状态
	 * 
	 * @param orderId
	 * @return
	 */
	int updateOrderStatus(Integer orderId);
	/**
	 * 删除订单对应的表
	 * @param orderId
	 * @return
	 */
	int dropTableByOrder(Integer orderId);
	/**
	 * 查询是否存在表
	 * @param tableName
	 * @return
	 */
	String selectDBTable(String tableName);
	/**
	 * 删除对应的表
	 * @param orderId
	 * @return
	 */
	int dropBatchOrderTable(int orderId);
	/**
	 * 分页查询
	 * @param pageBean
	 * @param connecTracingAndSecurty 
	 * @param vo
	 * @return
	 */
	
	PageBean getProductOrderInfo(PageBean pageBean,Integer vendorId, Integer connecTracingAndSecurty);
	
	/**
	 * 分页查询红包order信息
	 * @param pageBean
	 * @param vendorId
	 * @param envFlag
	 * @return
	 */
	public PageBean getOrderEnvInfo(PageBean pageBean,Integer vendorId,Integer envFlag);
	
	/**
	 * 修改红包标识
	 * @param id
	 * @param envFlag
	 * @return
	 */
	public int updateRedEnvFlagById(Integer id,Integer envFlag);
	
	/**
	 * 根据orderId查询vendorId
	 * @param orderId
	 * @return
	 */

	int findVendorIdByOrderId(Integer orderId);
	
	/***
	 * 根据商家的vendorId创建扫码表
	 */
	int createScanTable(int vendorId);
	
	/**
	 * 根据新增订单的id查询订单信息 
	 * getProductOrderInfo
	 */
	ProductOrderVO getProductOrderInfoByOrderId(Integer orderId);
	/**
	 * 查询是否存在对应的表
	 */
	String findIfDbName(String dbName);
	/**
	 * 获取用户名
	 * @param vendorId
	 * @return
	 */
	String getVendorNameByVendorId(Integer orderId);
	
	
	
}
