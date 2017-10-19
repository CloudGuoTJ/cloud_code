package com.yunma.service.tracing.impl;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.exception.BusinessException;
import com.common.service.BaseService;
import com.common.util.CommonUtils;
import com.common.util.Radix;
import com.common.util.TxtExportUtils;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.tracing.ProductTracingDao;
import com.yunma.dao.user.UserDao;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.entity.tracing.ProductTracingCodeAgentScan;
import com.yunma.entity.tracing.TracingCodeCustomerScan;
import com.yunma.entity.tracing.VendorTracingStatistics;
import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.model.Location;
import com.yunma.model.User;
import com.yunma.service.tracing.ProductsTracingService;
import com.yunma.utils.DoubleUtil;
import com.yunma.utils.PageBean;
import com.yunma.utils.WeChatConfig;

@Service
public class ProductTracingServiceImpl extends BaseService implements
		ProductsTracingService {
	@Autowired
	private ProductTracingDao tracingDao;
	@Autowired
	private ProductOrderDao orderDao;
	@Autowired
	private UserDao userDao;

	@Override
	public JSONObject vendorCreateAgent(Integer vendorId, Integer orderId) {

		return null;
	}

	@Override
	public JSONObject vendorFindAgentTree(Integer vendorId) {

		return null;
	}

	@Override
	@Transactional
	public List<ProductTracingCode> vendorGenerateProductTracingCode(
			Integer orderId, Integer vendorId, Integer rowCount,
			Integer boxCount) {

		/**
		 * rowCount 为用户指定生成每组溯源码数量
		 */
		ProductOrder order = orderDao.findOrderByProductOrderId(orderId);// 获取产品订单
		int productCount = order.getProductCount();
		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
		// vendorName);
		
		User user = userDao.findUserByVendorId(order.getVendorId());
		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
		// vendorName);
		Integer userType = user.getUserType();//获取用户类型:如果为测试用户则限定生成防伪码数量为10000
		if(userType == 2){
			Integer testTracingCodeCount = user.getTestTracingCodeCount();
			if(productCount + testTracingCodeCount > 10000){
				productCount = 10000 - testTracingCodeCount ;
			}
			Integer testTracingCodeCountPri = testTracingCodeCount + productCount;
			Integer userId = user.getUserId();
			userDao.updateUserTestTracingCodeCount(testTracingCodeCountPri,userId);			
		}
		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
		int productId = order.getProductId();// 拿到产品id
		/**
		 * 先将需要附码的商品分组
		 */

		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
		/**
		 * 处理生成二维码数量低无法生成问题
		 */
		int rowNum = (int)Math.ceil(DoubleUtil.divide((double)productCount, (double)rowCount, 2));
		

		int len = i.length() - 1;// 追加tailTag的位数
		// int rowNum = 1;// 设置组编号并初始化
		/**
		 * 原二维码生成规则: 需要参数 1 -> 生成code需要参数:
		 * (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844以内" +
		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重 追加规则: 单组数量,用户指定,必须满足规则:0
		 * < rowCount <= 1000 <= orderCount <= productCount 使用规则:
		 * 用户扫码显示:产品流通的树形图 agent扫描显示父级代理之前的树 并提示是否追加溯源信息
		 */
		List<ProductTracingCode> list = new ArrayList<ProductTracingCode>();
		/**
		 * 外层循环按照组数递增
		 */
		String codeTailTag = null;
		String codePrefix = null;
		int eveRowTotal = rowCount;// 默认每组使用base62 低位总数去重
		String productName = order.getProductName();
		String orderCode = Radix.convert10To62(orderId, 4);
		String vendorCode = Radix.convert10To62(vendorId, 2);
		String productCode = Radix.convert10To62(productId, 4);
		// System.out.println("orderId:   " + orderId + "   vendorId:" +
		// vendorId

		// + "   rowCount " + rowCount + "  productCount" + productCount
		// + "  len" + len);
		for (int a = 1; a <= rowNum; a++) {
			/**
			 * 总控制,判断每组生成二维码3844以内
			 */

			if (eveRowTotal > productCount) {
				eveRowTotal = productCount;
			}
			if (eveRowTotal * a < productCount) {
				eveRowTotal = rowCount;
			}
			if (eveRowTotal * a >= productCount) {
				eveRowTotal = productCount - (eveRowTotal * (a - 1));
			}
			
			/**
			 * 控制生成boxNum
			 * 
			 */
			
			int boxNumTotal = 0;
			if((rowNum % boxCount) != 0){
				boxNumTotal = (productCount / rowCount)+1;
				
			}else{
				boxNumTotal = productCount / rowCount;
			}
			int boxNum = 0;
			 
			
			double a1 = (double) a ;
			double bBoxC = (double)boxCount;
			double dev = DoubleUtil.divide(a1, bBoxC, 2);
			int charge = (int) Math.ceil(dev);
			if(dev <= 1){
				boxNum = 1;
			}else{
				boxNum = charge;
			}
			
			try {
				/**
				 * 生成一组二维码
				 */
				LinkedList<String> codeList = CommonUtils.getRandomStrList(
						eveRowTotal, 2, bitSet);
				for (int num = 0; num < eveRowTotal; num++) {
					/**
					 * 生成二维码
					 */
					String set = codeList.poll();
					ProductTracingCode tracingCode = new ProductTracingCode();
					codeTailTag = (UUID.randomUUID().toString().replace("\\-",
							"")).substring(0, len);// 追加尾数
					codePrefix = orderCode + vendorCode + productCode + set;
					String boxShortCode = Radix.convert10To62(boxNum, 2);
					String code = codePrefix +boxShortCode+ a + codeTailTag ;// 二维组合方式:前缀+rownum+后缀
					

					/***
					 * 保存二维码
					 */
					tracingCode.setCreateRowNum(a);
					tracingCode.setOrderId(orderId);
					tracingCode.setProductId(productId);
					tracingCode.setProductTracingCode(code);
					tracingCode.setProductName(productName);
					tracingCode.setCodePrefix(codePrefix);
					tracingCode.setCodeTailTag(codeTailTag);
					tracingCode.setRowCount(eveRowTotal);
					tracingCode.setTotalRowNum(rowNum);
					tracingCode.setBoxCount(boxNum);
					tracingCode.setBoxShortCode(boxShortCode);

					/**
					 * 存入数组
					 */
					list.add(tracingCode);
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
	 * 将溯源码存入数据库
	 * 
	 * @param list
	 * @param orderId
	 * @param vendorName
	 * @param product
	 * @return
	 * @throws BusinessException
	 */

	@Transactional
	public int createProductTracingCode(List<ProductTracingCode> list,
			Integer orderId, String vendorName, Product product)
			throws BusinessException {
		try {
			int insertSqlCount = 0;// 插入二维码数据状态
			List<ProductTracingCode> listArray = null;
			int n = list.size() / 10000;

			ProductOrder order = orderDao.findOrderByProductOrderId(orderId);
			int vendorId = order.getVendorId();
			for (int i = 0; i <= n; i++) {
				StringBuffer sql = new StringBuffer();
				sql.append("insert into `");
				sql.append("tb_product_tracing_code_");
				sql.append(orderId);
				sql.append("`");
				sql.append("(orderId,productId,vendorId,rowCount,productName,");
				sql.append("productTracingCode,codeTailTag,createRowNum,codeFlag,");
				sql.append("totalRowNum,codePrefix,boxCount,boxShortCode,");
				sql.append("createDate) values ");
				listArray = list.subList(i * 10000, (i + 1) * 10000 > list
						.size() ? list.size() : (i + 1) * 10000);
				if (listArray.size() == 0) {
					return 1;
				}
				for (ProductTracingCode code : listArray) {
					Integer productId = code.getProductId();
					String productName = code.getProductName();
					Integer codeFlag = 1;
					Integer rowCount = code.getRowCount();
					// Integer codeFlag = (Integer) (code.getCodeFlag() == null
					// ? "1"
					// : code.getCodeFlag());// 默认为防伪码
					String productTracingCode = code.getProductTracingCode();
					String codePrefix = code.getCodePrefix();
					String codeTailTag = code.getCodeTailTag();
					String boxShortCode = code.getBoxShortCode();
					Integer boxCount = code.getBoxCount();
					int createRowNum = code.getCreateRowNum();

					int totalRowNum = code.getTotalRowNum();
					/**
					 * 拼凑sql语句
					 */
					sql.append("(" + orderId + ",");
					sql.append(productId + ",");
					sql.append(vendorId + ",");
					sql.append(rowCount + ",");
					sql.append("'" + productName + "',");
					sql.append("'" + productTracingCode + "',");
					sql.append("'" + codeTailTag + "',");
					sql.append(createRowNum + ",");
					sql.append(codeFlag + ",");
					sql.append(totalRowNum + ",");
					sql.append("'" + codePrefix + "',");
					sql.append(boxCount + ",");
					sql.append("'" + boxShortCode + "',");

					// totalRowNum
					sql.append("now()),");
				}
				insertSqlCount += tracingDao.createProductTracingCode(sql
						.substring(0, sql.length() - 1));
			}
			return insertSqlCount;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(-28,
					"生成溯源码失败！\n可能原因：1、生成溯源码数量超出限制；\n   2.网络异常");
		}
	}

	@Override
	public int createOrderTracingTable(int orderId) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_code_");
		sb.append(orderId);
		sb.append("`(");
		sb.append("`productTracingCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID',");
		sb.append("`orderId` bigint(20) NOT NULL COMMENT '订单ID',");
		sb.append("`productId` bigint(32) NOT NULL COMMENT '产品ID',");
		sb.append("`vendorId` bigint(32) NOT NULL COMMENT '商家ID',");
		sb.append("`productName` varchar(64) NOT NULL COMMENT '产品名',");
		sb.append("`codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源',");
		sb.append("`codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成',");
		sb.append("`productTracingCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '溯源码全码',");
		sb.append("`codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关',");
		sb.append("`createRowNum` int(32) NOT NULL COMMENT '分组生成的组号，单次生成不超过rowCount的，组号为1',");
		sb.append("`rowCount` bigint(32) NOT NULL COMMENT '单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount',");
		sb.append("`createDate` datetime NOT NULL COMMENT '生成日期',");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '箱码编号',");
		sb.append("`boxShortCode` varchar(64) NOT NULL COMMENT '箱码定制',");
		sb.append("`totalRowNum` bigint(32) NOT NULL COMMENT '总组数',");
		sb.append(" PRIMARY KEY (`productTracingCodeId`)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='溯源码表';");
		tracingDao.createTracingCodeDBTable(sb.toString());// 创建表
		return i;
	}

	public int createScanTable(int vendorId) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_agent_scan_");
		sb.append(vendorId);
		sb.append("`(");
		sb.append("`tracingScanId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '溯源扫码主键',");
		sb.append("`productTracingCodeId` bigint(32) not null COMMENT '溯源码ID',");
		sb.append("`productTracingCode` varchar(64) not null COMMENT '溯源码',");
		sb.append("`productId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`codeType` tinyint(4) not null COMMENT '溯源码1:箱码,2:条码 ',");
		sb.append("`vendorId` bigint(32) not null COMMENT '商家ID',");
		sb.append("`createRowNum` int(32) DEFAULT null COMMENT '产品组号',");
		sb.append("`countPro` int(32) NOT NULL COMMENT '每一单位的数量如果是箱码那么存箱码数量,如果是组码存组码数量',");
		sb.append("`productName` varchar(32) not null COMMENT '产品名称',");
		sb.append("`scanTime` datetime not null COMMENT '扫描日期',");
		sb.append("`scanAddress` varchar(128)  DEFAULT NULL COMMENT '扫描地点',");
		sb.append("`longitude` varchar(32)  DEFAULT NULL COMMENT '经度',");
		sb.append("`latitude` varchar(32)  DEFAULT NULL COMMENT '纬度',");
		sb.append("`openId` varchar(100) DEFAULT NULL COMMENT '微信openId 对应代理表中的openId',");
		sb.append("`agentId` varchar(32) DEFAULT NULL COMMENT '代理用户Id 对应代理表',");
		sb.append("`agentName` varchar(32) DEFAULT NULL COMMENT '代理厂商名字',");
		sb.append("`agentLv` int(20) DEFAULT NULL COMMENT '扫码代理等级',");
		sb.append("`province` varchar(32)  DEFAULT null,");
		sb.append("`city` varchar(32)   DEFAULT null,");
		sb.append("`district` varchar(32)   DEFAULT null,");
		sb.append("`scanCount` int(20)  DEFAULT 0,");
		sb.append("PRIMARY KEY (tracingScanId)");
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		tracingDao.createTracingCodeDBTable(sb.toString());// 创建表

		return i;
	}

	public int createCustomerTracingScanTable(int vendorId) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_customer_scan_");
		sb.append(vendorId);
		sb.append("`(");
		sb.append("`customerTracingScanId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '溯源扫码主键',");
		sb.append("`productTracingCodeId` bigint(32) not null COMMENT '溯源码ID',");
		sb.append("`productTracingCode` varchar(64) not null COMMENT '溯源码',");
		sb.append("`productId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`vendorId` bigint(32) not null COMMENT '商家ID',");
		sb.append("`codeType` tinyint(4) not null COMMENT '溯源码1:箱码,2:条码 ',");
		sb.append("`createRowNum` int(32) not null COMMENT '产品组号',");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '箱码号',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`scanTime` datetime not null COMMENT '扫描日期',");
		sb.append("`scanAddress` varchar(128)  DEFAULT NULL COMMENT '扫描地点',");
		sb.append("`longitude` varchar(32)  DEFAULT NULL COMMENT '经度',");
		sb.append("`latitude` varchar(32)  DEFAULT NULL COMMENT '纬度',");
		sb.append("`openId` varchar(100) DEFAULT NULL COMMENT '微信openId ',");
		sb.append("`province` varchar(32)  DEFAULT null,");
		sb.append("`city` varchar(32)   DEFAULT null,");
		sb.append("`district` varchar(32)   DEFAULT null,");
		sb.append("`scanCount` int(20)  DEFAULT 1,");
		sb.append("PRIMARY KEY (customerTracingScanId)");
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		tracingDao.createTracingCodeDBTable(sb.toString());// 创建表

		return i;
	}

	@Override
	public String getTBName(String tableName) {
		return tracingDao.selectDBTable(tableName);
	}

	@Override
	public int updateOrderStatus(Integer orderId) {
		return tracingDao.updateOrderStatus(orderId);
	}

	@Override
	public ProductTracingCode findTracingCodeInfoByTracingCode(String shortCode) {
		Integer orderId = Radix.convert62To10(shortCode.substring(0, 4));
		return tracingDao.findCodeByVendorId(shortCode, orderId);
	}

	@Override
	public int addAgentScanInfo(Integer vendorId,
			ProductTracingCodeAgentScan tracingCodeScan) {

		return tracingDao
				.addAgentTracingAntiFakeInfo(vendorId, tracingCodeScan);
	}

	@Override
	public int addCustomerScanInfo(Integer vendorId,
			TracingCodeCustomerScan customerScanCode) {
		return tracingDao.addCustomerTracingAntiFakeInfo(vendorId,
				customerScanCode);
	}

	@Override
	public AgentEmployee findAgentEmployeeInfoByOpenId(String openId,Integer vendorId) {
		return tracingDao.findAgentEmployeeInfoByOpenId(openId,vendorId);
	}

	@Override
	public AgentEntity findAgentInfoByAgentId(Integer agentId) {

		return tracingDao.findAgentInfoByAgentId(agentId);
	}

	@Override
	public int addVendorTracingStatisticsInfo(
			VendorTracingStatistics vendorTracingStatistics) {

		return tracingDao.addVendorTracingStatistics(vendorTracingStatistics);
	}

	@Override
	public VendorTracingStatistics findVendorScanInfoByvendorIdAndAgentId(
			Integer vendorId, Integer agentId) {
		return tracingDao.findVendorScanInfoByvendorIdAndAgentId(vendorId,
				agentId);
	}

	@Override
	public int updateCustomerScanLocation(String openId, Integer vendorId,
			String longitude, String latitude) {
		return tracingDao.updateCustomerScanLocation(openId, vendorId,
				longitude, latitude);
	}

	@Override
	public int updateAgenScantLocation(String openId, Integer vendorId,
			String longitude, String latitude) {
		return tracingDao.updateAgentScanLocation(openId, vendorId, longitude,
				latitude);
	}

	@Override
	public VendorTracingStatistics findVendorTracingStatisticsInfoByOrderId(
			Integer orderId) {

		return tracingDao
				.findVendorTracingStatisticsInfoByOrderIdAndLv(orderId);
	}

	@Override
	public int updateVendorTracingStatisticsInfo(
			VendorTracingStatistics vendorTracingStatistics, Integer orderId,
			Integer lv) {

		return tracingDao.updateVendorTracingStatisticsInfo(
				vendorTracingStatistics, lv, orderId);
	}

	@Override
	public PageBean getProductTracingCodeInfo(PageBean pageBean,
			 Integer orderId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = tracingDao.getProductTracingCodeCount(orderId);
		ProductTracingCode code = new ProductTracingCode();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("productTracingCode", code);
		map.put("orderId", orderId);
		try {
			List<ProductTracingCode> list = tracingDao.getTracingCodeInfo(map);
			if (list != null && list.size() > 0) {
				for (ProductTracingCode tracingCode : list) {
					/*
					 * SELECT securityCodeId,orderId,productId,productName," +
					 * "codeFlag,codePrefix,securityCode,codeTailTag,createRowNum,createDate"
					 */
					JSONObject object = new JSONObject();
					object.put("productTracingCodeId",
							tracingCode.getProductTracingCodeId());
					object.put("vendorId", tracingCode.getVendorId());
					object.put("rowCount", tracingCode.getRowCount());
					object.put("createRowNum", tracingCode.getCreateRowNum());
					object.put("orderId", tracingCode.getOrderId());
					object.put("productId", tracingCode.getProductId());
					object.put("productName", tracingCode.getProductName());
					object.put("productTracingCode",
							tracingCode.getProductTracingCode());
					object.put("codeFlag", tracingCode.getCodeFlag());
					object.put("createDate", tracingCode.getCreateDate());
					object.put("codeTailTag", tracingCode.getCodeTailTag());
					object.put("codePrefix", tracingCode.getCodePrefix());
					array.add(object);
				}
			} else {
				result.put("error:", "没有订单相关的二维码,请确定是否生成二维码!");
			}
		} catch (Exception e) {
			logger.error("没有订单相关的二维码,请确定是否生成二维码!");

		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;

	}

	

	@Override
	@Transactional
	public List<VendorTracingStatistics> findAllAgentInfo(Integer orderId,
			Integer boxNum,Integer groupNum) {
		
		// 获取高级代理的信息
		String boxCode = null;
		String groupCode = null;
		BoxCode boxCodePri = tracingDao.getBoxInfoByboxNum(orderId,boxNum);
		GroupCode groupCodePri = tracingDao.getTracingGroupCodeByGroupNum(orderId, groupNum);
		boxCode = boxCodePri.getBoxCode();
		groupCode = groupCodePri.getGroupCode();
		
		List<VendorTracingStatistics> list = tracingDao.getListScanInfoByCode(boxCode, groupCode);
		//总的List
		
		
//		List<VendorTracingStatistics> groupLsit = new ArrayList<VendorTracingStatistics>();
//		groupLsit = tracingDao.getListAgentScanInfoByGroupCode(groupCode);
//		if(groupLsit != null){
//			list.addAll(groupLsit);
//		}
//		
//		
//		List<VendorTracingStatistics> boxList = new ArrayList<VendorTracingStatistics>();
//		boxList = tracingDao.getListAgentScanInfoByBoxCode(boxCode);
//		if(boxList != null){
//			list.addAll(boxList);			
//		}
//		
		
		
//		VendorTracingStatistics vendorTracingStatistics = tracingDao
//				.findAgentTracingStatisticsInfoByOrderIdAndLv(orderId,
//						lvPrivate);
		// 如果当期层级比总层级小则当前层级处于高级显示完整的线性溯源信息,如果高级扫码时间比低级扫码时间大则线性溯源信息不会增加高级信息

		

		// 除去上级代理误扫已到下级代理的产品导致追加的溯源信息
//		System.out.println(list);
//		for (int i = 0; i < list.size(); i++) {
//
//			if (list.get(i).getLv() < lvPrivate
//					&& (list.get(i).getScanTime()).getTime() > (vendorTracingStatistics
//							.getScanTime()).getTime()) {
//				list.remove(i);
//
//				i--;
//
//			}
//
//		}
		return list;
	}

	@Override
	public Integer getLastAgentLevel(Integer orderId) {
		return tracingDao.getMaxLvNum(orderId);
	}

	@Override
	public VendorTracingStatistics findAgentTracingStatisticsInfoByOrderIdAndLv( Integer orderId,
			Integer lv) {
		return tracingDao.findAgentTracingStatisticsInfoByOrderIdAndLv(orderId,
				lv);
	}

	@Override
	public int createBoxCodeTable(Integer orderId) {
		
		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_box_code_");
		sb.append(orderId);
		sb.append("`(");
		sb.append("`boxCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '箱码表主键',");
		sb.append("`orderId` bigint(32) not null COMMENT '溯源码ID',");
		sb.append("`productId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`vendorId` bigint(32) not null COMMENT '商家ID',");
		sb.append("`rowCount` bigint(32) not null COMMENT '每组数量',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`codePrefix` varchar(50) not null COMMENT '产品名称',");
		sb.append("`boxCode` varchar(50) not null COMMENT '产品名称',");
		sb.append("`createDate` datetime  not null,");
		sb.append("`totalRowNum` int(32)   DEFAULT null,");
		sb.append("`boxNum` varchar(32)   DEFAULT null,");
		sb.append("`boxShortCode` varchar(32)  DEFAULT null,");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '箱码号',");
		sb.append("PRIMARY KEY (boxCodeId)");
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		tracingDao.createTracingCodeDBTable(sb.toString());// 创建表

		return i;
		
		
	}

	@Override
	public int createGroupCodeTable(Integer orderId) {

		int i = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_group_code_");
		sb.append(orderId);
		sb.append("`(");
		sb.append("`groupCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '组码表 主键',");
		sb.append("`orderId` bigint(32) not null COMMENT '订单ID',");
		sb.append("`productId` bigint(32) not null COMMENT '产品Id',");
		sb.append("`vendorId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`groupCount` bigint(32) not null COMMENT '商家ID',");
		sb.append("`groupCode`varchar(50) not null COMMENT '产品组号',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`groupNum` int(32) not null COMMENT '组号',");
		sb.append("`codePrefix` varchar(64) not null COMMENT '前缀',");
		sb.append("`createDate` datetime  NOT NULL COMMENT '创建时间',");
		sb.append("`totalRowNum` int(32) NOT NULL COMMENT '总共多少组',");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '每组数量',");
		sb.append("`boxNum` int(32) NOT NULL COMMENT '所属箱号',");
		sb.append("`scanCount` int(20)  DEFAULT 0,");
		sb.append("PRIMARY KEY (groupCodeId)");
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		tracingDao.createTracingCodeDBTable(sb.toString());// 创建表

		return i;
	}



	@Override
	public List<BoxCode> vendorGenerateProductTracingBoxCode(Integer orderId,
			Integer vendorId, Integer rowCount, Integer boxCount) {
		Logger logger = LoggerFactory.getLogger(getClass());
		Runtime run = Runtime.getRuntime();
		
		/**
		 * rowCount 为用户指定生成每组溯源码数量
		 */
		ProductOrder order = orderDao.findOrderByProductOrderId(orderId);// 获取产品订单
		int productCount = order.getProductCount();
		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
		// vendorName);

		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
		int productId = order.getProductId();// 拿到产品id
		/**
		 * 先将需要附码的商品分组
		 */

		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
		/**
		 * 处理生成二维码数量低无法生成问题
		 */
		int rowNum = 0;
		rowNum = (int)Math.ceil(DoubleUtil.divide((double)productCount, (double)rowCount, 2));
		
		int eveBoxCount = rowCount * boxCount;
		int boxNumTotal = 0;
		if((  productCount  % eveBoxCount != 0) ){
			boxNumTotal = (productCount / eveBoxCount)+1;
			
		}else{
			boxNumTotal =productCount / eveBoxCount;
		}
		int lastBoxCount = 0;
		if((productCount % rowCount) == 0){
			
			eveBoxCount =  rowCount * boxCount;
		}else{
			lastBoxCount = productCount - (eveBoxCount * (boxNumTotal - 1));			
		}					
		/**
		 *箱码采用和溯源码盒码一致前缀,加上可变组名后缀生成
		 */
		List<BoxCode> list = new ArrayList<BoxCode>();
		 
		/**
		 * 外层循环按照组数递增
		 */
		String codeTailTag = null;
		String codePrefix = null;
		int eveRowTotal = rowCount;// 默认每组使用base62 低位总数去重
		String productName = order.getProductName();
		String orderCode = Radix.convert10To62(orderId, 4);
		String vendorCode = Radix.convert10To62(vendorId, 2);
		String productCode = Radix.convert10To62(productId, 2);
		codeTailTag = Radix.convert10To62(boxNumTotal, 2);
		LinkedList<String> codeList = CommonUtils.getRandomStrList(
				boxNumTotal, 2, bitSet);
		for (int a = 1; a <= boxNumTotal; a++) {
			String bit = codeList.poll();
			codePrefix = orderCode + vendorCode + productCode + bit;
			codeTailTag = Radix.convert10To62(a, 2);
			String code = codePrefix + codeTailTag;
			/**
			 * 			
			String rowCode = Radix.convert10To62(a, 3);
			String code =orderCode +  vendorCode + productCode + rowCode + bit ;
			String codePrefix = orderCode + vendorCode +productCode + rowCode;
			
			 */
			
			BoxCode boxCode = new BoxCode(); 
			
			if((eveBoxCount * a) <= productCount){
				
				boxCode.setBoxCount(eveBoxCount);
				
			}else{
				boxCode.setBoxCount(lastBoxCount);
			}
			boxCode.setBoxCode(code);
			boxCode.setBoxNum(a);
			boxCode.setBoxShortCode(codeTailTag);
			boxCode.setCodePrefix(codePrefix);
			boxCode.setOrderId(orderId);
			boxCode.setProductName(productName);
			boxCode.setProductId(productId);
			boxCode.setTotalRowNum(rowNum);
			boxCode.setRowCount(rowCount);
			boxCode.setVendorId(vendorId);
			list.add(boxCode);
		}
		return list;
	

	}

	@Override
	public List<GroupCode> vendorGenerateProductTracingGroupCode(
			Integer orderId, Integer vendorId, Integer rowCount,
			Integer boxCount) {
		/**
		 * rowCount 为用户指定生成每组溯源码数量
		 */
		ProductOrder order = orderDao.findOrderByProductOrderId(orderId);// 获取产品订单
		int productCount = order.getProductCount();
		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
		// vendorName);

		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
		int productId = order.getProductId();// 拿到产品id
		/**
		 * 先将需要附码的商品分组
		 */

		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
		/**
		 * 处理生成二维码数量低无法生成问题
		 */
		int rowNum = (int) Math.ceil( DoubleUtil.divide((double) productCount , (double)rowCount, 2));
		
		
		int eveBoxCount = rowCount * boxCount;
		int boxNumTotal = 0;
		if((  eveBoxCount % productCount != 0) ){
			boxNumTotal = (productCount / eveBoxCount)+1;
			
		}else{
			boxNumTotal =productCount / eveBoxCount;
		}
		int lastBoxCount = 0;
		if((productCount % rowCount) == 0){
			
			eveBoxCount =  rowCount * boxCount;
		}else{
			lastBoxCount = productCount - (eveBoxCount * (boxNumTotal - 1));			
		}				
		/**
		 *箱码采用和溯源码盒码一致前缀,加上可变组名后缀生成
		 */
		List<GroupCode> list = new ArrayList<GroupCode>();
		 
		/**
		 * 外层循环按照组数递增
		 */
		int eveRowTotal = rowCount;// 默认每组使用base62 低位总数去重
		String productName = order.getProductName();
		String orderCode = Radix.convert10To62(orderId, 4);
		String vendorCode = Radix.convert10To62(vendorId, 2);
		String productCode = Radix.convert10To62(productId, 2);
		String rowCode = Radix.convert10To62(rowNum, 3);
		LinkedList<String> codeList = CommonUtils.getRandomStrList(
				rowNum, 2, bitSet);
		for (int a = 1; a <= rowNum; a++) {
			String bit = codeList.poll();
			String code = orderCode +  vendorCode + productCode + rowCode + bit ;
			String codePrefix = orderCode + vendorCode +productCode + rowCode;
			GroupCode groupCode = new GroupCode(); 
			
			if(a > ((boxNumTotal - 1) * boxCount) && (boxNumTotal * eveBoxCount) > productCount ){
				
				
				groupCode.setBoxCount(lastBoxCount);
			}else{
				groupCode.setBoxCount(eveBoxCount);
			}
			//(int)Math.ceil(rowNum / boxCount)+1
			groupCode.setBoxNum((int)Math.ceil(DoubleUtil.divide((double)a, (double)boxCount, 2)));//追加组号					
			groupCode.setGroupCode(code);
			groupCode.setGroupNum(a);
			
			if((a * rowCount) <= productCount){
				
				groupCode.setGroupCount(rowCount);
			}else{
				groupCode.setGroupCount(productCount -(rowCount * (a - 1)));
			}
			
			groupCode.setCodePrefix(codePrefix);
			groupCode.setOrderId(orderId);
			groupCode.setProductName(productName);
			groupCode.setProductId(productId);
			groupCode.setTotalRowNum(rowNum);
			groupCode.setVendorId(vendorId);
			list.add(groupCode);
		}
		return list;
	
	}
	
	
	@Override
	public int insertBoxCodeInfo(List<BoxCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException {
		try {
			int insertSqlCount = 0;// 插入二维码数据状态
			List<BoxCode> listArray = null;
			ProductOrder order = orderDao.findOrderByProductOrderId(orderId);
			int vendorId = order.getVendorId();
			
				StringBuffer sql = new StringBuffer();
				sql.append("insert into `");
				sql.append("tb_product_tracing_box_code_");
				sql.append(orderId);
				sql.append("`");
				sql.append("(orderId,productId,vendorId,rowCount,productName,");
				sql.append("codePrefix,boxCode,");
				sql.append("totalRowNum,boxNum,boxCount,boxShortCode,");
				sql.append("createDate) values ");
				for (BoxCode code : list) {
					Integer productId = code.getProductId();
					String productName = code.getProductName();
					Integer rowCount = code.getRowCount();
					String boxCode = code.getBoxCode();
					String codePrefix = code.getCodePrefix();
					String boxShortCode = code.getBoxShortCode();
					Integer boxCount = code.getBoxCount();
					int boxNum = code.getBoxNum();
					int totalRowNum = code.getTotalRowNum();
					/**
					 * 拼凑sql语句
					 */
					sql.append("(" + orderId + ",");
					sql.append(productId + ",");
					sql.append(vendorId + ",");
					sql.append(rowCount + ",");
					sql.append("'" + productName + "',");
					sql.append("'" + codePrefix + "',");
					sql.append("'" + boxCode + "',");
					sql.append(totalRowNum + ",");
					sql.append(boxNum + ",");
					sql.append(boxCount + ",");
					sql.append("'" + boxShortCode + "',");
					sql.append("now()),");
				}
				
				
				
				insertSqlCount += tracingDao.createProductTracingCode(sql
						.substring(0, sql.length() - 1));
			
			return insertSqlCount;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(-28,
					"生成溯源码失败！\n可能原因：1、生成溯源码数量超出限制；\n   2.网络异常");
		}

		
	}

	@Override
	public int insertGroupCodeInfo(List<GroupCode> list, Integer orderId,
			String vendorName, Product product) throws BusinessException {
		try {
			int insertSqlCount = 0;// 插入二维码数据状态
			List<GroupCode> listArray = null;
			ProductOrder order = orderDao.findOrderByProductOrderId(orderId);
			int vendorId = order.getVendorId();
			
				StringBuffer sql = new StringBuffer();
				sql.append("insert into `");
				sql.append("tb_product_tracing_group_code_");
				sql.append(orderId);
				sql.append("`");
				sql.append("(orderId,productId,vendorId,groupCount,productName,");
				sql.append("codePrefix,groupCode,");
				sql.append("totalRowNum,groupNum,boxCount,boxNum,");
				sql.append("createDate) values ");

				if (list.size() == 0) {
					return -1;
				}
				for (GroupCode code : list) {
					Integer productId = code.getProductId();
					String productName = code.getProductName();
					Integer groupCount = code.getGroupCount();
					String groupCode = code.getGroupCode();
					String codePrefix = code.getCodePrefix();
					Integer boxCount = code.getBoxCount();
					int groupNum = code.getGroupNum();
					int totalRowNum = code.getTotalRowNum();
					int boxNum =code.getBoxNum();
					/**
					 * 拼凑sql语句
					 */
					sql.append("(" + orderId + ",");
					sql.append(productId + ",");
					sql.append(vendorId + ",");
					sql.append(groupCount + ",");
					sql.append("'" + productName + "',");
					sql.append("'" + codePrefix + "',");
					sql.append("'" + groupCode + "',");
					sql.append(totalRowNum + ",");
					sql.append(groupNum + ",");
					sql.append(boxCount + ",");
					sql.append(boxNum + ",");
					sql.append("now()),");
				}
				insertSqlCount += tracingDao.createProductTracingCode(sql
						.substring(0, sql.length() - 1));
			
			return insertSqlCount;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(-28,
					"生成失败！\n可能原因：1、生成数量超出限制；\n   2.网络异常");
		}

	}

	@Override
	public int dropTable(String tableName) {
		return tracingDao.dropTableByOrder(tableName);
	}
	/**
	 * 需要重写!
	 */
	@Override
	public void exportTracingBatchCodeToTXT(HttpServletResponse response,
			Integer orderId) {

		try {

			/**
			 * 按需求将生成的溯源码包含单码条码箱码的格式导出来 格式:箱码-->组码表-->单码表
			 */
			List<String> codeList = new ArrayList<String>();
			// 导出溯源码需要查询顶级包装溯源码表
			List<BoxCode> boxCodeList = tracingDao
					.findBoxCodeInfoListByOrderId(orderId);
			String boxCodeStr = null;
			String groupCodeStr = null;
			Integer boxNum = 0;
			BoxCode boxCo = new BoxCode();
			Integer groupNum = 0;
			String prefix = "  ";
			GroupCode groupCo = new GroupCode();

			for (int i = 0; i < boxCodeList.size(); i++) {
				boxCo = boxCodeList.get(i);
				// prefix = "第"+i+"箱,箱码:" ;
				codeList.add(prefix);
				boxCodeStr = boxCo.getBoxCode();
				codeList.add(boxCodeStr);// 加入第一个boxCode,再追加对应的组码和单码
				boxNum = boxCo.getBoxNum();
				List<GroupCode> groupCodeList = tracingDao
						.findGroupCodeInfoListByBoxNum(orderId, boxNum);
				for (int j = 0; j < groupCodeList.size(); j++) {
					groupCo = groupCodeList.get(j);
					groupCodeStr = groupCo.getGroupCode();
					// prefix = "第"+j+1+"组,组码:" ;
					codeList.add(prefix);
					codeList.add(groupCodeStr);
					// prefix = "第"+j+"组,溯源全码:" ;
					codeList.add(prefix);
					groupNum = groupCo.getGroupNum();
					List<ProductTracingCode> tracingCodeList = tracingDao
							.getTracingCodeByGroupNum(orderId, groupNum);
					for (int b = 0; b < tracingCodeList.size(); b++) {
						codeList.add(tracingCodeList.get(b)
								.getProductTracingCode());
					}
				}

			}
			System.out.println("------->>>>" + codeList);
			ProductOrder order = new ProductOrder();
			order = orderDao.findOrderByProductOrderId(orderId);
			/**
			 * 异常,无法获取vendorId 处理:在创建订单时添加vendorName
			 */
			Integer vendorId = order.getVendorId();
			String vendorName = userDao.getVendorNameByVendorId(vendorId);
			String productName = order.getProductName();
			int downCountExtra = tracingDao.findDownCountByOrderId(orderId);
			int downCount = 0;
			/**
			 * 须在其他接口追加下载次数提醒 即超过1次则告知异常,会出现扫码不正常现象
			 */
			downCount = downCountExtra + 1;
			String url = "https://" + WeChatConfig.DOMAIN_NAME + "/t/";
			String urlOut = url.trim();

			// 2. 调用导出txt工具，导出txt
			String plus = "该批溯源码为："

			+ vendorName + "厂商下:" + productName + "产品 " + "订单号为:" + orderId+"格式为:二级包装码,一级包装码,最小包装码,再循环往复!";
			TxtExportUtils.exportTxtPriavate(response, codeList, orderId + "",
					urlOut, plus);
			tracingDao.updateDownloadCount(orderId, downCount);

		} catch (Exception e) {
			logger.error("导出溯源码异常 ：", e);
		}

	}

	@Override
	public PageBean getProductTracingBoxCodeInfo(PageBean pageBean,
			Integer orderId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = tracingDao.getBoxCodeCount(orderId);
		BoxCode boxCode = new BoxCode();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("BoxCode", boxCode);
		map.put("orderId", orderId);
		List<BoxCode> list = tracingDao.getBoxCodeInfo(map);
		if (list != null && list.size() > 0) {
			for (BoxCode code : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("boxCodeId", code.getBoxCodeId());
				object.put("orderId", code.getOrderId());
				object.put("vendorId", code.getVendorId());				
				object.put("productId", code.getProductId());
				object.put("productName", code.getProductName());
				object.put("createDate", code.getCreateDate());
				object.put("boxCount", code.getBoxCount());
				object.put("boxCode", code.getBoxCode());
				object.put("rowCount", code.getRowCount());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public PageBean getProductTracingGroupCodeInfo(PageBean pageBean,
			Integer orderId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = tracingDao.getGroupCodeCount(orderId);
		GroupCode groupCode = new GroupCode();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("GroupCode", groupCode);
		map.put("orderId", orderId);
		List<GroupCode> list = tracingDao.getGroupCodeInfo(map);
		if (list != null && list.size() > 0) {
			for (GroupCode code : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("groupCodeId", code.getGroupCodeId());
				object.put("orderId", code.getOrderId());
				object.put("vendorId", code.getVendorId());				
				object.put("productId", code.getProductId());
				object.put("productName", code.getProductName());
				object.put("createDate", code.getCreateDate());
				object.put("boxCount", code.getBoxCount());
				object.put("groupCode", code.getGroupCode());
				object.put("rowCount", code.getGroupCount());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public BoxCode getTracingBoxCodeByBoxCode(Integer orderId,String shortCode) {
		
		return tracingDao.getBoxCodeInfoByBoxCode(orderId, shortCode);
	}

	@Override
	public GroupCode getTracingGroupCodeByCode(Integer orderId ,String shortCode) {

		return tracingDao.getTracingGroupCodeByCode(orderId,shortCode);
	}

	@Override
	public ProductTracingCodeAgentScan getAgentSanInfo(String openId,
			String shortCode) {
		
		return tracingDao.getAgentSanInfoByOpenIdAndTracingCode(openId,shortCode);
	}

	@Override
	public VendorTracingStatistics findAgentTracingStatisticsInfoIfExist(
			Integer orderId, Integer lv, String shortCode, String openId) {
		return tracingDao.findAgentTracingStatisticsInfoIfExist(orderId, openId, shortCode, lv);
	}

	@Override
	public List<VendorTracingStatistics> agentFindAllAgentInfo(Integer orderId,
			Integer lv, String openId, String shortCode) {
		VendorTracingStatistics agentScanStatistics = tracingDao.findAgentTracingStatisticsInfoIfExist(orderId, openId, shortCode, lv);
		List<VendorTracingStatistics> list =new ArrayList<VendorTracingStatistics>();
		int boxNum = 0;
		int groupNum = 0;
		String boxCode =null;
		String groupCode = null;
		if(shortCode.length() == 12){
			BoxCode boxCodePri1 = tracingDao.getBoxCodeInfoByBoxCode(orderId, shortCode);
			boxNum = boxCodePri1.getBoxNum();
			boxCode = boxCodePri1.getBoxCode();
			List<VendorTracingStatistics> boxScan = tracingDao.getListAgentScanInfoByBoxCode(boxCode);
			list.addAll(boxScan);
		}
		if(shortCode.length() == 13){
			GroupCode groupCodePri = tracingDao.getTracingGroupCodeByCode(orderId, shortCode);
			groupCode = groupCodePri.getGroupCode();
			boxNum = groupCodePri.getBoxNum();
			BoxCode boxCode1 = tracingDao.getBoxInfoByboxNum(orderId, boxNum);
			boxCode = boxCode1.getBoxCode();
			groupNum =groupCodePri.getGroupCodeId();
			List<VendorTracingStatistics> groupScan = tracingDao.getListScanInfoByCode(boxCode, groupCode);
			list.addAll(groupScan);
		}
		
		return list;
	}

	@Override
	public int exchangeTracingDegree(Integer orderId) {
		
		return tracingDao.updateTracingDegreeByOrderId(orderId);
	}

	@Override
	public ProductTracingCodeAgentScan getAgentFirstSanInfo(Integer vendorId,String shortCode) {
		
		return tracingDao.getAgentFirstScanInfoByShortCode(vendorId,shortCode);
	}

	@Override
	public int updateAgentInfoSetLocation(Integer vendorId,Location location, String openId,
			String shortCode) {

		return tracingDao.updateAgentInfoSetLocation(vendorId,location,openId,shortCode);
	}

	@Override
	public ProductTracingCodeAgentScan findExtraScanByShortCode(
			Integer vendorId, String shortCode) {
		
		return tracingDao.findExtraAgentScanInfo(vendorId,shortCode);
	}




	
	
	
	
	

}
