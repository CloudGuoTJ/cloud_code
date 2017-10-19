package com.yunma.service.product.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.exception.BusinessException;
import com.common.util.CommonConstants;
import com.common.util.CommonUtils;
import com.common.util.Radix;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.user.UserDao;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.User;
import com.yunma.service.product.ProductOrderService;
import com.yunma.utils.PageBean;
import com.yunma.vo.product.ProductOrderVO;

/**
 * ProductOrder 实现类 Created by ${CloudGoo} on 2017/5/5 0005.
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
	@Resource
	private ProductOrderDao productOrderDao;
	@Resource
	private UserDao userDao;

	/* 添加产品订单 */
	public int createOrder(ProductOrder order) {
		int result = productOrderDao.createOrder(order);
		return result;
	}

	/* 删除订单 */
	@Override
	public int deleteOrder(int orderId) {

		return productOrderDao.deleteOrder(orderId);
	}

	/* 查询订单在前端显示 */

	@Override
	public List<ProductOrder> findAllOrderByVendorId(User user) {
		Integer vendorId;
		vendorId = user.getVendorId();
		return productOrderDao.findAllOrderByVendorId(vendorId);
	}

	/* 清空订单信息 */

	@Override
	public int setIsClear(int orderId, int isClear) {

		return productOrderDao.setIsClear(orderId, isClear);
	}

	@Override
	public List<ProductOrder> findAllOrderByVendorIdOrProduct(User user,
			String productName) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return productOrderDao.findAllOrderOrProduct(productName);
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()
				|| userType == CommonConstants.User_Type_FindProductVendor
						.getState()) {
			Integer vendorId = user.getVendorId();
			return productOrderDao.findAllOrderOrByVendorIdOrProduct(vendorId,
					productName);
		}
		return null;

	}

	@Override
	public int findAllOrderByVendorIdOrProductNameCount(User user,
			String productName) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return productOrderDao.findOrderListProductNameCount(productName);
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()
				|| userType == CommonConstants.User_Type_FindProductVendor
						.getState()) {
			return productOrderDao.findOrderListCountByVendorIdAndProductName(
					user.getVendorId(), productName);
		} else if (userType == CommonConstants.User_Type_LogisticsVendor
				.getState()) {
			// 没有权限
			return 0;
		}
		return 0;
	}

	/**
	 * 分页查询
	 * 
	 * 参数: int: num; int: page User: user
	 * 
	 * 
	 */
	@Override
	public List<ProductOrder> findOrderList(int num, int page, User user) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return productOrderDao.findOrderList((page - 1) * num, num);
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()
				|| userType == CommonConstants.User_Type_FindProductVendor
						.getState()) {
			return productOrderDao.findOrderListByVendorId((page - 1) * num,
					num, user.getVendorId());
		} else if (userType == CommonConstants.User_Type_LogisticsVendor
				.getState()) {
			// 没有权限
			return null;
		}
		return null;
	}

	/**
	 * 通过User查找OrderList
	 */
	@Override
	public int findOrderListCount(User user) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return productOrderDao.findOrderListCount();
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()
				|| userType == CommonConstants.User_Type_FindProductVendor
						.getState()) {
			return productOrderDao.findOrderListCountByVendorId(user
					.getVendorId());
		}
		return 0;
	}

	/**
	 * 根据orderId创建新表保存二维码
	 * 
	 * @param orderId
	 * @param prefix
	 * @return
	 */
	@Transactional
	public int createOrderTable(int orderId) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_security_code_");
		sb.append(orderId);
		sb.append("`(");
		sb.append("`securityCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID',");
		sb.append("`orderId` bigint(20) NOT NULL COMMENT '订单ID',");
		sb.append("`productId` bigint(32) NOT NULL COMMENT '产品ID',");
		sb.append("`productName` varchar(64) NOT NULL COMMENT '产品名',");
		sb.append("`codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源',");
		sb.append("`codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成',");
		sb.append("`securityCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码全码',");
		sb.append("`codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关',");
		sb.append("`createRowNum` varchar(32) NOT NULL COMMENT '分组生成的组号，单次生成未超过3844个，组号为1',");
		sb.append("`createDate` datetime NOT NULL COMMENT '生成日期',");
		sb.append("`scanFlag` int(8) NOT NULL DEFAULT '0' COMMENT '是否被扫描',");
		sb.append(" PRIMARY KEY (`securityCodeId`)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码表';");
		String sql = sb.toString();

		productOrderDao.createOrderDBTable(sql);// 创建表

		return i;
	}

	/**
	 * 创建扫码记录表
	 */
	public int createScanTable(int vendorId) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_anti_fake_");
		sb.append(vendorId);
		sb.append("`(");
		sb.append("rowkey bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sb.append("securityCodeId bigint(32) not null COMMENT '防伪码ID',");
		sb.append("securityCode varchar(64) not null COMMENT '防伪码',");
		sb.append("productId bigint(32) not null COMMENT '产品ID',");
		sb.append("productName varchar(32) not null COMMENT '产品名称',");
		sb.append("scanTime datetime not null COMMENT '扫描日期',");
		sb.append("scanAddress varchar(128)  DEFAULT NULL COMMENT '扫描地点',");
		sb.append("longitude varchar(32)  DEFAULT NULL COMMENT '经度',");
		sb.append("latitude varchar(32)  DEFAULT NULL COMMENT '纬度',");
		sb.append("userId bigint(32)  DEFAULT NULL COMMENT '用户ID',");
		sb.append("openId varchar(32) DEFAULT NULL COMMENT '微信openId',");
		sb.append("lastUpdateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',");
		sb.append("province varchar(32)  DEFAULT null,");
		sb.append("city varchar(32)   DEFAULT null,");
		sb.append("district varchar(32)   DEFAULT null,");
		sb.append("scanCount int(20)  not null,");
		sb.append("productScanCount int(11) DEFAULT '0' COMMENT '当天产品扫码次数',");
		sb.append("PRIMARY KEY (rowkey)");
		// + "UNIQUE KEY `new_uk_name` (`securityCodeId`),"
		// + "UNIQUE KEY `new_uk1_name` (`userId`,`securityCodeId`)"
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='扫码记录表';");

		String sql = sb.toString();

		productOrderDao.createOrderDBTable(sql);// 创建表

		return i;
	}
	
	

	/**
	 * 生成二维码
	 * 
	 * @param orderId
	 * @param vendorName
	 * @return
	 * @throws BusinessException
	 */
	@Transactional
	public List<SecurityCode> generateSecurityCode(int orderId,
			String vendorName) throws BusinessException {

		ProductOrder order = productOrderDao.findOrderByProductOrderId(orderId);// 获取产品订单
		int productCount = order.getProductCount();
		User user = userDao.findUserByVendorId(order.getVendorId());
		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
		// vendorName);
		Integer userType = user.getUserType();//获取用户类型:如果为测试用户则限定生成防伪码数量为10000
		if(userType == 2){
			Integer testSecurityCodeCount = user.getTestSecurityCodeCount();
			if(productCount + testSecurityCodeCount > 10000){
				productCount = 10000 - testSecurityCodeCount ;
			}
			Integer testSecurityCodeCountPri = testSecurityCodeCount + productCount;
			Integer userId = user.getUserId();
			userDao.updateUserTestSecurityCount(testSecurityCodeCountPri,userId);			
		}
		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
		int productId = order.getProductId();// 拿到产品id
		int vendorId = order.getVendorId();
		/**
		 * 先将需要附码的商品分组
		 */

		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
		/**
		 * 处理生成二维码数量低无法生成问题
		 */
		int rowCount;
		if ((int) Math.ceil((productCount / (62 * 62))) == 0
				|| (int) Math.ceil((productCount / (62 * 62))) == 1) {
			rowCount = 1;
		} else {
			rowCount = (int) Math.ceil((productCount / (62 * 62))) + 1;
		}
		

		int len = i.length() - 1;// 追加tailTag的位数
		/**
		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
		 */
		List<SecurityCode> list = new ArrayList<SecurityCode>();
		/**
		 * 外层循环按照组数递增
		 */
		String codeTailTag = null;
		String codePrefix = null;
		int eveRowTotal = 3844;// 默认每组使用base62 低位总数去重
		String productName = order.getProductName();
		String orderCode = Radix.convert10To62(orderId, 4);
		String vendorCode = Radix.convert10To62(vendorId, 2);
		String productCode = Radix.convert10To62(productId, 2);
		for (int a = 1; a <= rowCount; a++) {
			/**
			 * 总控制,判断每组生成二维码3844个
			 */

				if (eveRowTotal > productCount) {
					eveRowTotal = productCount;
				}
				if (eveRowTotal * a < productCount) {
					eveRowTotal = 3844;
				}
				if (eveRowTotal * a >= productCount) {
					eveRowTotal = productCount - (3844*(a-1));
				}

				// System.out.println("step:" + 2);
				try {
					/**
					 * 生成一组二维码
					 */
					LinkedList<String> codeList = CommonUtils.getRandomStrList(
							eveRowTotal, 3, bitSet);
					for (int num = 0; num < eveRowTotal; num++) {
						/**
						 * 生成二维码
						 */
						String set = codeList.poll();
						SecurityCode securityCode = new SecurityCode();
						codeTailTag = (UUID.randomUUID().toString().replace(
								"\\-", "")).substring(0, len);// 追加尾数
						codePrefix = orderCode + vendorCode + productCode + set;
						String code = codePrefix + a + codeTailTag;// 二维组合方式:前缀+rownum+后缀
						/***
						 * 保存二维码
						 */
						securityCode.setCreateRowNum(a);
						securityCode.setOrderId(orderId);
						securityCode.setProductId(productId);
						securityCode.setSecurityCode(code);
						securityCode.setProductName(productName);
						securityCode.setCodePrefix(codePrefix);
						securityCode.setCodeTailTag(codeTailTag);

						/**
						 * 存入数组
						 */
						list.add(securityCode);
						// System.out.println("code:" + list);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			
			// System.out.println("code:" + list);
		}
		return list;

	}

	/**
	 * 将二维码存入数据库
	 * 
	 * @param list
	 * @param orderId
	 * @param vendorName
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	@Transactional
	public int createSecuritycode(List<SecurityCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException {
		try {
			int insertSqlCount = 0;// 插入二维码数据状态
			List<SecurityCode> listArray = null;
			int n = list.size() / 10000;
			for (int i = 0; i <= n; i++) {
				StringBuffer sql = new StringBuffer();
				sql.append("insert into `");
				sql.append("tb_security_code_");
				sql.append(orderId);
				sql.append("`");
				sql.append("(orderId,productId,productName,");
				sql.append("securityCode,codeTailTag,createRowNum,codeFlag,codePrefix,");
				sql.append("createDate) values ");
				listArray = list.subList(i * 10000, (i + 1) * 10000 > list
						.size() ? list.size() : (i + 1) * 10000);
				if (listArray.size() == 0) {
					return 1;
				}
				for (SecurityCode code : listArray) {
					Integer productId = code.getProductId();
					String productName = code.getProductName();
					Integer codeFlag = 1;
					String securityCode = code.getSecurityCode();
					String codePrefix = code.getCodePrefix();
					String codeTailTag = code.getCodeTailTag();
					int createRowNum = code.getCreateRowNum();
					/**
					 * 拼凑sql语句
					 */
					sql.append("(" + code.getOrderId() + ",");
					sql.append(productId + ",");
					sql.append("'" + productName + "',");
					sql.append("'" + securityCode + "',");
					sql.append("'" + codeTailTag + "',");
					sql.append(createRowNum + ",");
					sql.append(codeFlag + ",");
					sql.append("'" + codePrefix + "',");
					sql.append("now()),");
				}
				insertSqlCount += productOrderDao.createSecurityCode(sql
						.substring(0, sql.length() - 1));
			}
			return insertSqlCount;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(-28,
					"生成二维码失败！\n可能原因：1、生成二维码数量超出限制；\n   2.网络异常");
		}
	}

	@Transactional
	public int createOrderByDB(int orderId) {
		return this.createOrderTable(orderId);// 创建分表
	}

	/**
	 * 
	 */
	@Override
	public ProductOrder findOrderByProductOrderId(Integer orderId) {
		return productOrderDao.findOrderByProductOrderId(orderId);

	}

	/**
	 * 获取生成订单的表
	 */
	@Override
	public int findLastOrder(User user) {
		return productOrderDao.findLastOrderId(user.getVendorId());
	}

	/**
	 * 更新已生成的二维码订单的状态
	 */
	@Override
	public int updateOrderStatus(Integer orderId) {
		return productOrderDao.updateOrderStatus(orderId);
	}

	/**
	 * 根据已删除订单删除对应的表
	 * 
	 * @param orderId
	 * @return
	 */
	public int dropTableByOrder(Integer orderId) {
		int deletCountTable = 0;
		String tableName1 = "`tb_anti_fake" + orderId + "`";
		deletCountTable += productOrderDao.dropTableByOrder(tableName1);
		String tableName2 = "`tb_security_code`" + orderId + "`";
		deletCountTable += productOrderDao.dropTableByOrder(tableName2);
		// String tableName3 = "`tb_envelope_vendor_createredenvelope_" +orderId
		// +"`";
		// deletCountTable += productOrderDao.dropTableByOrder(tableName3);
		return deletCountTable;
	}

	/**
	 * 删除根据订单 生成的表
	 */
	@Override
	@Transactional
	public int dropBatchOrderTable(int orderId) {
		int i = 0;
		// StringBuffer sb = new StringBuffer();
		// StringBuffer sb1 = new StringBuffer();
		// List list = new ArrayList();
		// sb.append("tb_security_code_"+orderId);
		// list.add(sb.toString());
		// sb1.append("tb_anti_fake_"+orderId);
		// list.add(sb1.toString());
		// for(int i1 = 0; i1 < list.size(); i1++){
		// String tableName = (String)list.get(i1);
		// productOrderDao.dropTableByOrder(tableName);
		// }
		// for (String tableName : list) {
		// i++;
		// productOrderDao.dropTableByOrder(tableName);// 删除对应表
		// }
		System.out.println("step" + 1);
		String tableName = "tb_security_code_" + orderId;
		productOrderDao.dropTableByOrder(tableName);
		return i;

	}

	@Override
	public String selectDBTable(String tableName) {

		return productOrderDao.selectDBTable(tableName);
	}

	@Override
	/**
	 * 使用分页类实现分页
	 */
	@Transactional
	public PageBean getProductOrderInfo(PageBean pageBean, Integer vendorId, Integer connecTracingAndSecurty) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = productOrderDao.getOrderCount(vendorId,connecTracingAndSecurty);
		ProductOrderVO vo = new ProductOrderVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("ProductOrderVO", vo);
		map.put("vendorId", vendorId);
		map.put("connecTracingAndSecurty", connecTracingAndSecurty);
		List<ProductOrderVO> list = productOrderDao.getOrderInfo(map);
		if (list != null && list.size() > 0) {
			for (ProductOrderVO orderVO : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("orderId", orderVO.getOrderId());
				object.put("vendorId", orderVO.getVendorId());
				object.put("vendorName", orderVO.getVendorName());
				object.put("productId", orderVO.getProductId());
				object.put("productName", orderVO.getProductName());
				object.put("productCount", orderVO.getProductCount());
				object.put("createDate", orderVO.getCreateDate());
				object.put("status", orderVO.getStatus());
				object.put("deleteFlag", orderVO.getDeleteFlog());
				object.put("lastUpdateTime", orderVO.getLastUpdateTime());
				object.put("tracingFlag", orderVO.getTracingFlag());
				object.put("exprotTracingCodeCount", orderVO.getExprotTracingCodeCount());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	/**
	 * 分页显示领取红包的订单
	 */
	@Transactional
	public PageBean getOrderEnvInfo(PageBean pageBean, Integer vendorId,
			Integer envFlag) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = productOrderDao.getOrderEnvCount(vendorId, envFlag);
		ProductOrderVO vo = new ProductOrderVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("ProductOrderVO", vo);
		map.put("vendorId", vendorId);
		map.put("envFlag", envFlag);
		List<ProductOrderVO> list = productOrderDao.getOrderEnvInfo(map);
		if (list != null && list.size() > 0) {
			for (ProductOrderVO orderVO : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("orderId", orderVO.getOrderId());
				object.put("vendorId", orderVO.getVendorId());
				object.put("vendorName", orderVO.getVendorName());
				object.put("productId", orderVO.getProductId());
				object.put("productName", orderVO.getProductName());
				object.put("productCount", orderVO.getProductCount());
				object.put("createDate", orderVO.getCreateDate());
				object.put("status", orderVO.getStatus());
				object.put("deleteFlag", orderVO.getDeleteFlog());
				object.put("lastUpdateTime", orderVO.getLastUpdateTime());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public int updateRedEnvFlagById(Integer id, Integer envFlag) {
		return productOrderDao.updateRedEnvFlagById(id, envFlag);
	}

	@Override
	public int findVendorIdByOrderId(Integer orderId) {
		
		return productOrderDao.findVendorIdByOrderId(orderId);
	}

	@Override
	public ProductOrderVO getProductOrderInfoByOrderId(Integer orderId) {
		
		return productOrderDao.getProductOrderInfo(orderId);
	}

	@Override
	public String findIfDbName(String dbName) {
		
		return productOrderDao.selectDBTable(dbName);
	}

	@Override
	public String getVendorNameByVendorId(Integer orderId) {
		return productOrderDao.findVendorNameByVendorId(orderId);
	}

}
