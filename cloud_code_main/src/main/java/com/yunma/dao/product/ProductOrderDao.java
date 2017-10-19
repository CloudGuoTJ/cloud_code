package com.yunma.dao.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.product.ProductOrder;
import com.yunma.model.TotalCount;
import com.yunma.vo.product.ProductOrderVO;
import com.yunma.vo.vendor.VendorBasicInfoVo;

    /**
     * 订单表dao层 使用注解的方式将sql注入到mapper Created by ${CloudGoo} on 2017/5/5 0005.
     */
	public interface ProductOrderDao extends BaseMapper {
	/**
	 * 获取基本信息
	 * 
	 * @param vendorId
	 * @return
	 */
	public VendorBasicInfoVo test(Integer vendorId);

	@Insert("insert into tb_product_order set vendorName=#{vendorName},vendorId=#{vendorId},productName=#{productName},"
			+ "productId=#{productId},productCount=#{productCount},status=1,"
			+ "referencePrice=#{referencePrice},expiryDate=#{expiryDate},"
			+ "connecTracingAndSecurty=#{connecTracingAndSecurty},"
			+ "createDate=now()")
	int createOrder(ProductOrder productOrder);

	/* 清空订单信息 */
	@Update("update tb_product_order set isClear=#{1}  where vendorId=#{0}")
	int setIsClear(int orderId, int isClear);

	/* 根据厂商和订单号生成二维码 */
	// @
	// List<SecurityCode> generateSecurityCode(int orderId, String vendorName)
	// throws BusinessException;
	/* 删除订单 */
	@Delete("delete from tb_product_order  where orderId=${orderId}")
	int deleteOrder(@Param("orderId") int orderId);

	/* 查询商家名下所有订单 分页操作,未指定分页显示数量 */
	@Select("select * from tb_product_order where vendorId=#{0} and deleteFlag=0 order by orderId desc limit #{0},#{1}")
	List<ProductOrder> findAllOrderByVendorId(Integer vendorId);

	@Select("select * from tb_product_order where deleteFlag=0 and productName=#{0} order by orderId desc")
	List<ProductOrder> findAllOrderOrProduct(String productName);

	@Select("select * from tb_product_order where vendorId=#{0} and productName=#{1} and deleteFlag=0 order by orderId desc")
	List<ProductOrder> findAllOrderOrByVendorIdOrProduct(Integer vendorId,
			String productName);

	@Select("select count(1) from tb_product_order where deleteFlag=0 and product_name=#{0}")
	int findOrderListProductNameCount(String productName);

	@Select("select count(1) from tb_product_order where vendorId=#{0}  and deleteFlag=0 and productName=#{1}")
	int findOrderListCountByVendorIdAndProductName(Integer vendorId,
			String productName);

	@Select("select * from tb_product_order where deleteFlag=0 order by orderId desc limit #{0},#{1}")
	public List<ProductOrder> findOrderList(int i, int num);

	@Select("select * from tb_product_order where vendorId=#{2} and deleteFlag=0 order by orderId desc limit #{0},#{1}")
	public List<ProductOrder> findOrderListByVendorId(int i, int num,
			Integer vendorId);

	@Select("select count(1) from tb_product_order where deleteFlag=0")
	int findOrderListCount();

	@Select("select count(1) from tb_product_order where vendorId=#{vendorId}  and deleteFlag=0")
	int findOrderListCountByVendorId(Integer vendorId);

	@Select("select * from tb_product_order where deleteFlag=0 and orderId=#{0}")
	ProductOrder findOrderByProductOrderId(Integer orderId);

	/* 与二维码相关通过分表的方式存表 */
	@Insert("${sql}")
	int createSecurityCode(@Param("sql") String sql);

	/**
	 * 创建二维码表
	 * 
	 * @param sql
	 * @return
	 */
	@Update("${sql}")
	int createOrderDBTable(@Param("sql") String sql);

	/**
	 * 2代表已经生成二维码
	 * 
	 * @param orderId
	 * @return
	 */
	@Update("update tb_product_order set status=2 where orderId=#{0}")
	int updateOrderStatus(Integer orderId);

	/**
	 * 取出用户订单下第一条信息的orderId
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("select orderId from tb_product_order where deleteFlag=0 and vendorId=#{vendorId} order by orderId desc limit 1")
	public int findLastOrderId(Integer vendorId);

	/**
	 * 删除订单时删除对应的表
	 * 
	 * @param tableName
	 * @return
	 */
	@Update("DROP TABLE IF EXISTS  ${tableName}")
	public int dropTableByOrder(@Param("tableName") String tableName);

	/**
	 * 查找数据库中是否存在表
	 */
	@Select("SHOW TABLES LIKE '${tableName}'")
	String selectDBTable(@Param("tableName") String tableName);

	/**
	 * 使用pageBeen分页 1.统计总数
	 */
	@Select("select count(*)  "
			+ "from "
			+ "((SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime,tracingFlag,exprotTracingCodeCount FROM tb_product_order where deleteFlag=0 AND vendorId=#{vendorId} AND connecTracingAndSecurty=1 ) "
			+ "union "
			+ "(SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime,tracingFlag,exprotTracingCodeCount FROM tb_product_order where deleteFlag=0 AND vendorId=#{vendorId} AND connecTracingAndSecurty=#{connecTracingAndSecurty}))"
			+ " as a")
	int getOrderCount(@Param("vendorId")Integer vendorId, @Param("connecTracingAndSecurty")Integer connecTracingAndSecurty);

	/**
	 * 查询数据 分页
	 */
	/*
	 * `orderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID', `vendorId`
	 * bigint(20) NOT NULL COMMENT '厂商ID', `vendorName` varchar(32) NOT NULL
	 * COMMENT '厂商名称', `productId` bigint(20) NOT NULL COMMENT '产品ID',
	 * `productName` varchar(32) NOT NULL COMMENT '产品名称', `productCount` int(8)
	 * NOT NULL COMMENT '产品数量', `codeLocation` varchar(255) DEFAULT NULL COMMENT
	 * '二维码下载地址', `createDate` datetime NOT NULL COMMENT '创建日期', `status`
	 * tinyint(4) NOT NULL COMMENT '订单状态 1、待审批，2、审批成功', `deleteFlag` tinyint(4)
	 * NOT NULL DEFAULT '0' COMMENT '删除标识', `lastUpdateTime` timestamp NOT NULL
	 * DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
	 * `downLoadCount` int(8) NOT NULL DEFAULT '0', `isClear` tinyint(4) NOT
	 * NULL DEFAULT '0' COMMENT '扫描记录清零状态 0、未清零，1、已清零', `isException` tinyint(4)
	 * NOT NULL DEFAULT '0' COMMENT '订单异常状态 0、正常，1、非正常', `codePrefix`
	 * varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT
	 * '订单前缀', `referencePrice` double(20,2) DEFAULT NULL COMMENT '参考价格',
	 * `expiryDate` datetime DEFAULT NULL COMMENT '有效期',
	 */

	@Select("select * from ("
			+ "(SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime,tracingFlag,exprotTracingCodeCount "
			+ "FROM "
			+ "tb_product_order where deleteFlag=0 AND vendorId=#{vendorId} AND connecTracingAndSecurty=#{connecTracingAndSecurty} )"
			+ " union "
			+ "(SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime,tracingFlag,exprotTracingCodeCount "
			+ "FROM "
			+ "tb_product_order where deleteFlag=0 AND vendorId=#{vendorId} AND connecTracingAndSecurty=1 ) ) as a "
			+ " ORDER BY a.createDate DESC  LIMIT #{pageBean.index},#{pageBean.pageSize};")
	List<ProductOrderVO> getOrderInfo(Map<String, Object> map);

	/**
	 * 分页查询没有生成和未红包的order 1
	 * 
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order WHERE vendorId=#{vendorId} AND redEnvFlag=#{envFlag} AND status=2")
	int getOrderEnvCount(@Param("vendorId") Integer vendorId,
			@Param("envFlag") Integer envFlag);

	/**
	 * 2
	 * 
	 * @param map
	 * @return
	 */
	@Select("SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime FROM tb_product_order where redEnvFlag=#{envFlag} AND status=2 AND vendorId=#{vendorId} ORDER BY createDate DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize} ")
	List<ProductOrderVO> getOrderEnvInfo(Map<String, Object> map);

	// @Param("envFlag")Integer envFlag,@Param("vendorId")Integer vendorId);

	/**
	 * 修改红包标识
	 * 
	 * @param id
	 * @param envFlag
	 * @return
	 */
	@Update("UPDATE tb_product_order SET redEnvFlag = #{envFlag} WHERE orderId = #{id}")
	public int updateRedEnvFlagById(@Param("id") Integer id,
			@Param("envFlag") Integer envFlag);

	/**
	 * 根据订单Id查询订单信息
	 * 
	 * @param orderId
	 * 
	 */
	@Select("SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime "
			+ "FROM" + " tb_product_order " + "WHERE orderId=#{orderId}")
	ProductOrderVO getProductOrderInfo(@Param("orderId") Integer orderId);

	/**
	 * 统计单个商家的订单总 数
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order WHERE vendorId=#{vendorId}")
	int getScanCount(@Param("vendordId") Integer vendorId);

	/**
	 * 统计总的订单数
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order")
	int getTotalScanCount();

	/**
	 * 获取单个商家已生成二维码的订单id
	 * 
	 */
	@Select("SELECT orderId FROM tb_product_order WHERE vendorId=#{vendorId} AND status=2")
	List<Integer> findTotalOrderId(@Param("vendorId") Integer vendorId);

	/**
	 * 通过订单号查询商家vendorId
	 * 
	 * @param orderId
	 * @return
	 */
	@Select("SELECT vendorId FROM tb_product_order WHERE orderId=#{orderId}")
	Integer findVendorIdByOrderId(Integer orderId);

	/**
	 * admin 获取订单扫码总量的orderId
	 * 
	 * @return
	 */
	@Select("SELECT vendorId FROM tb_product_order WHERE status=2")
	public List<Integer> findSysTotalVendorId();
	/**
	 * 查询所有VendorId
	 * @return
	 */
	@Select("SELECT vendorId FROM tb_user WHERE userType=1")
	public List<Integer> findAllVendorId();
	/**
	 * 查询所有vendorId和vendorName
	 * 
	 */
	@Select("SELECT vendorId ,vendorName FROM tb_user WHERE userType=1 order by vendorId;")
	public List<TotalCount> findAllVendorIdAndVendorName();
	/**
	 * 查询昨天新增订单总量
	 */
	@Select("SELECT count(*) FROM tb_product_order WHERE TO_DAYS( NOW( ) ) - TO_DAYS( createDate) <= 1")
	int findDayAddOrder();

	/**
	 * 统计已生成红包orderId
	 */
	@Select("SELECT orderId FROM tb_product_order WHERE redEnvFlag=1 AND vendorId=#{vendorId}")
	List<Integer> findRedEnvOrderId(@Param("vendorId") Integer vendorId);

	/**
	 * 获取扫码活动的总数
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order WHERE isJoinActiv=0 AND vendorId=#{vendorId}")
	int getOrderCountForActiv(Integer vendorId);

	/**
	 * 获取创建防伪扫码活动的订单
	 * 
	 * @param map
	 * @return
	 */
	@Select("SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime FROM tb_product_order where deleteFlag=0 AND isJoinActiv=0 AND vendorId=#{vendorId} ORDER BY createDate DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<ProductOrderVO> getOrderInfoForActiv(Map<String, Object> map);
	
	/**
	 * 获取扫码活动的总数
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order WHERE isJoinTracActiv=0 AND vendorId=#{vendorId}")
	int getOrderCountForTracyActiv(Integer vendorId);
	
	/**
	 * 获取创建溯源扫码活动的订单
	 * 
	 * @param map
	 * @return
	 */
	@Select("SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime FROM tb_product_order where deleteFlag=0 AND isJoinTracActiv=0 AND vendorId=#{vendorId} ORDER BY createDate DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<ProductOrderVO> getOrderInfoForTracyActiv(Map<String, Object> map);
	
	/**
	 * 修改订单防伪扫码活动管理
	 * @param orderId
	 * @return
	 */
	@Update("update tb_product_order set isJoinActiv=1 where orderId=#{orderId}")
	int updateOrderInfoForActiv(Integer orderId);
	
	/**
	 * 修改订单溯源扫码活动管理
	 * @param orderId
	 * @return
	 */
	@Update("update tb_product_order set isJoinTracActiv=1 where orderId=#{orderId}")
	int updateOrderInfoForTracingActiv(Integer orderId);
	
	/**
	 * 修改集字游戏订单状态
	 * @param orderId
	 * @return
	 */
	@Update("update tb_product_order set isCollectWord=1 where orderId=#{orderId}")
	int updateOrderInfoForCollectWord(Integer orderId);
	
	/**
	 * 获取扫码活动的总数
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM tb_product_order WHERE isCollectWord=0 AND vendorId=#{vendorId}")
	int getOrderCountForCollectWord(Integer vendorId);
	
	/**
	 * 获取集字游戏订单列表
	 * 
	 * @param map
	 * @return
	 */
	@Select("SELECT orderId, vendorId,vendorName,productId,productName,productCount,createDate,status,deleteFlag,lastUpdateTime FROM tb_product_order where deleteFlag=0 AND isCollectWord=0 AND vendorId=#{vendorId} ORDER BY createDate DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<ProductOrderVO> getOrderInfoForCollectWord(Map<String, Object> map);
	/**
	 * 通过vendorId查询vendorName
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT vendorName FROM tb_product_order WHERE orderId=#{orderId} ")
	public String findVendorNameByVendorId(Integer orderId);

}
