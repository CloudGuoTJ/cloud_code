//package com.yunma.utils;
//
//import java.util.ArrayList;
//import java.util.BitSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.common.exception.BusinessException;
//import com.common.util.CommonUtils;
//import com.common.util.Radix;
//import com.yunma.dao.product.ProductDao;
//import com.yunma.dao.product.ProductOrderDao;
//import com.yunma.entity.product.Product;
//import com.yunma.entity.product.ProductOrder;
//import com.yunma.entity.securityCode.SecurityCode;
//
///**
// * B方案中进行优化 1. 进行分库分表保存,组合规则:产品Id + prefix + RowId + 储存位置 + tailTag
// * 储存位置：分库分表的特殊字段 rowNum:将产品生成二维码进行分组生成的组号, tailTag:使用UUID生成并截取一定长度字符
// * (根据产品数量来生成如1000则追加位数为3位,100000则追加5位)
// * 
// * @author Cloud_Guo
// * 
// */
//public class GenerateCodeWayB {
//	ProductDao productDao;
//	ProductOrderDao productOrderDao;
//	/**
//	 * 生成分表数据
//	 * @param orderId
//	 * @param prefix
//	 * @return
//	 */
//	@Transactional
//	public int createOrderTable(int orderId) {
//		int i = 0;
//		StringBuffer sb = new StringBuffer();
//		StringBuffer sb1 = new StringBuffer();
//
//		List list = new ArrayList();
//
//		sb.append("CREATE TABLE `tb_security_code_"
//				+ orderId
//				+ "`("
//				+ "`securityCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID',"
//				+ "`orderId` bigint(20) NOT NULL COMMENT '订单ID',"
//				+ "`productId` bigint(32) NOT NULL COMMENT '产品ID',"
//				+ "`productName` varchar(64) NOT NULL COMMENT '产品名',"
//				+ "`codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源',"
//				+ "`codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成',"
//				+ "`securityCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码全码',"
//				+ "`codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关',"
//				+ "`createRowNum` varchar(32) NOT NULL COMMENT '分组生成的组号，单次生成未超过3844个，组号为1',"
//				+ "`createDate` datetime NOT NULL COMMENT '生成日期',"
//				+ " PRIMARY KEY (`securityCodeId`)"
//				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码表';");
//		list.add(sb);// 扫描信息记录表
//
//		sb1.append("CREATE TABLE `tb_anti_fake_"
//				+ orderId
//				+ "`("
//				+ "rowkey bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',"
//				+ "securityCodeId bigint(32) not null COMMENT '防伪码ID',"
//				+ "securityCode varchar(64) not null COMMENT '防伪码',"
//				+ "productId bigint(32) not null COMMENT '产品ID',"
//				+ "productName varchar(32) not null COMMENT '产品名称',"
//				+ "scanTime datetime not null COMMENT '扫描日期',"
//				+ "scanAddress varchar(128)  DEFAULT NULL COMMENT '扫描地点',"
//				+ "longitude varchar(32)  DEFAULT '' COMMENT '经度',"
//				+ "latitude varchar(32)  DEFAULT NULL COMMENT '纬度',"
//				+ "userId bigint(32)  DEFAULT NULL COMMENT '用户ID',"
//				+ "openId varchar(32) DEFAULT NULL COMMENT '微信openId',"
//				+ "lastUpdateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',"
//				+ "province varchar(32)  DEFAULT null,"
//				+ "city varchar(32)   DEFAULT null,"
//				+ "district varchar(32)   DEFAULT null,"
//				+ "scanCount int(20)  not null,"
//				+ "productScanCount int(11) DEFAULT '0' COMMENT '当天产品扫码次数',"
//				+ "PRIMARY KEY (rowkey),"
//				+ "UNIQUE KEY `new_uk_name` (`securityCodeId`),"
//				+ "UNIQUE KEY `new_uk1_name` (`userId`,`securityCodeId`)"
//				+ ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
//		list.add(sb1);// 订单二维码信息表
//
//		for (Object obj : list) {
//			i++;
//			productOrderDao.createOrderDBTable(obj.toString());// 创建表
//		}
//
//		return i;
//	}
//
//	@Transactional
//	public List<SecurityCode> generateSecurityCode(int orderId,
//			String vendorName) throws BusinessException {
//
//		ProductOrder order = productOrderDao.findOrderByProductOrderId(orderId);// 获取产品订单
//		int productCount = order.getProductCount();
//		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
//		int productId = order.getProductId();// 拿到产品id
//		int vendorId = order.getVendorId();
//		/**
//		 * 先将需要附码的商品分组
//		 */
//		SecurityCode securityCode = new SecurityCode();
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
//		int rowCount = (int) Math.ceil((productCount / (62 * 62)));// 总的组数
//		int len = i.length() - 1;// 追加tailTag的位数
//		int rowNum = 1;// 设置组编号并初始化
//		/**
//		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
//		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//		 */
//		List<SecurityCode> list = new ArrayList<SecurityCode>();
//		/**
//		 * 外层循环按照组数递增
//		 */
//		String codeTailTag = null;
//		String codePrefix = null;
//		int productCountNum = 3844;// 默认每组使用base62 低位总数去重
//
//		String orderCode = Radix.convert10To62(orderId, 4);
//		String vendorCode = Radix.convert10To62(vendorId, 2);
//		String productCode = Radix.convert10To62(productId, 2);
//
//		for (int a = 1; a < rowCount; a++) {
//			rowNum = a;
//			if (productCountNum * a < productCount) {
//				productCountNum = 3844;
//			}
//			if (productCountNum * a >= productCount) {
//				productCountNum = productCount - (productCountNum * (a - 1));
//			}
//			LinkedList<String> codeList = CommonUtils.getRandomStrList(
//					productCountNum, 2, bitSet);
//			// System.out.println("step:" + 2);
//			for (String set : codeList) {
//				/**
//				 * 生成二维码
//				 */
//				codeTailTag = (UUID.randomUUID().toString().replace("\\-", ""))
//						.substring(0, len);// 追加尾数
//				codePrefix = orderCode + vendorCode + productCode + set;
//				String code = codePrefix + rowNum + codeTailTag;// 二维组合方式:前缀+rownum+后缀
//				/***
//				 * 保存二维码
//				 */
//				securityCode.setCreateRowNum(rowNum);
//				securityCode.setOrderId(orderId);
//				securityCode.setProductId(productId);
//				securityCode.setSecurityCode(code);
//				securityCode.setProductName(order.getProductName());
//				securityCode.setCodePrefix(codePrefix);
//				securityCode.setCodeTailTag(codeTailTag);
//				/**
//				 * 存入数组
//				 */
//				list.add(securityCode);
//			}
//			System.out.println("code:" + list);
//
//		}
//		return list;
//
//	}
//
//	@Transactional
//	public int createSecuritycode(List<SecurityCode> list, String orderId,
//			String vendorName, Product product) throws BusinessException {
//		try {
//			int insertSqlCount = 0;// 插入二维码数据状态
//			List<SecurityCode> listArray = null;
//			int n = list.size() / 10000;
//			for (int i = 0; i <= n; i++) {
//				StringBuffer sql = new StringBuffer();
//				sql.append("insert into `"
//						+ "tb_security_code_"
//						+ orderId
//						+ "`"
//						+ "(orderId,productId,productName,"
//						+ "securityCode,codeTailTag,createRowNum,codeFlag,codePrefix,"
//						+ "createDate) values ");
//				listArray = list.subList(i * 10000, (i + 1) * 10000 > list
//						.size() ? list.size() : (i + 1) * 10000);
//				if (listArray.size() == 0) {
//					return 1;
//				}
//				for (SecurityCode code : listArray) {
//					Integer productId = code.getProductId();
//					String productName = code.getProductName();
//					int codeFlag = (Integer) (code.getCodeFlag() == null ? "1"
//							: code.getCodeFlag());// 默认为防伪码
//					String securityCode = code.getSecurityCode();
//					String codePrefix = code.getCodePrefix();
//					String codeTailTag = code.getCodeTailTag();
//					int createRowNum = code.getCreateRowNum();
//					/**
//					 * 拼凑sql语句
//					 */
//					sql.append("(" + code.getOrderId() + ",");
//					sql.append(productId + ",");
//					sql.append("'" + productName + "',");
//					sql.append("'" + securityCode + "',");
//					sql.append("'" + codeTailTag + "',");
//					sql.append(createRowNum + ",");
//					sql.append(codeFlag + ",");
//					sql.append("'" + codePrefix + "',");
//					sql.append("now()),");
//				}
//				insertSqlCount += productOrderDao.createSecurityCode(sql
//						.substring(0, sql.length() - 1));
//			}
//			return insertSqlCount;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException(-28,
//					"生成二维码失败！\n可能原因：1、生成二维码数量超出限制；\n   2.网络异常");
//		}
//	}
//	
//	 @Transactional
//	    public int createOrderByDB(int orderId) {
//	        return this.createOrderTable(orderId);//创建分表
//	    }
//
//}
