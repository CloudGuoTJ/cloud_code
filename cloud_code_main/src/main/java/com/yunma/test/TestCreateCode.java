package com.yunma.test;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.common.util.CommonUtils;
import com.common.util.Radix;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.tracing.ProductTracingCode;

public class TestCreateCode {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_group_code");
		sb.append(15);
		sb.append("`(");
		sb.append("`groupCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '组码表 主键',");
		sb.append("`orderId` bigint(32) not null COMMENT '订单ID',");
		sb.append("`productId` bigint(32) not null COMMENT '产品Id',");
		sb.append("`vendorId` bigint(32) not null COMMENT '产品ID',");
		sb.append("`groupCount` bigint(32) not null COMMENT '商家ID',");
		sb.append("`groupCode`varchar(50) not null COMMENT '产品组号',");
		sb.append("`productName` varchar(50) not null COMMENT '产品名称',");
		sb.append("`codePrefix` varchar(64) not null COMMENT '前缀',");
		sb.append("`boxCode` varchar(128)  NOT NULL COMMENT '组码全码',");
		sb.append("`createTime` datetime  NOT NULL COMMENT '创建时间',");
		sb.append("`createRowNum` int(32)  NOT NULL COMMENT '纬度',");
		sb.append("`totalRowNum` int(32) NOT NULL COMMENT '微信openId 对应代理表中的openId',");
		sb.append("`boxCount` int(32) NOT NULL COMMENT '代理用户Id 对应代理表',");
		sb.append("`scanCount` int(20)  DEFAULT 0,");
		sb.append("PRIMARY KEY (groupCodeId)");
		sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");		
		System.out.println(sb);
		
		double b= 5;
		double a =8.00;
				a= (double)(a/b);
		boolean v = (a < b ? true : false) ;
		
//		NumberFormat nf = NumberFormat.getNumberInstance() ;//解决double默认四舍五入问题
//		nf.setMaximumFractionDigits(4); 
		double dev = (10 / 3);
		new java.text.DecimalFormat("0.00").format((10 / 3));
		String al = "00030203qt06235a";
		System.out.println(al.length());
//		String d= nf.format(dev);
		System.out.println(new java.text.DecimalFormat("0.00").format(dev));
	}
//
//	public List<ProductTracingCode> vendorGenerateProductTracingCode(Integer orderId,
//			Integer vendorId,Integer rowCount) {
//		
		
		
		/**
		 * rowCount  为用户指定生成每组溯源码数量
		 */
		
//		ProductOrder order = orderDao.findOrderByProductOrderId(orderId);// 获取产品订单
//		int productCount = order.getProductCount();
//		// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
//		// vendorName);
//
//		String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
//		int productId = order.getProductId();// 拿到产品id
//		/**
//		 * 先将需要附码的商品分组
//		 */
//
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
//		/**
//		 * 处理生成二维码数量低无法生成问题
//		 */
//		int rowNum;
//		if ((int) Math.ceil((productCount / (rowCount))) == 0
//				|| (int) Math.ceil((productCount / (rowCount))) == 1) {
//			rowNum = 1;
//		} else {
//			rowNum = (int) Math.ceil((productCount / (rowCount))) + 1;
//		}
//
//		int len = i.length() - 1;// 追加tailTag的位数
////		int rowNum = 1;// 设置组编号并初始化
//		/**
//		 * 原二维码生成规则:
//		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844以内" +
//		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//		 * 追加规则:
//		 * 单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount
//		 * 使用规则:
//		 * 用户扫码显示:产品流通的树形图
//		 * agent扫描显示父级代理之前的树
//		 * 并提示是否追加溯源信息
//		 */
//		List<ProductTracingCode> list = new ArrayList<ProductTracingCode>();
//		/**
//		 * 外层循环按照组数递增
//		 */
//		String codeTailTag = null;
//		String codePrefix = null;
//		int eveRowTotal = rowCount;// 默认每组使用base62 低位总数去重
//		String productName = order.getProductName();
//		String orderCode = Radix.convert10To62(orderId, 4);
//		String vendorCode = Radix.convert10To62(vendorId, 2);
//		String productCode = Radix.convert10To62(productId, 2);
//		// System.out.println("orderId:   " + orderId + "   vendorId:" +
//		// vendorId
//		// + "   rowCount " + rowCount + "  productCount" + productCount
//		// + "  len" + len);
//		for (int a = 1; a <= rowNum; a++) {
//			/**
//			 * 总控制,判断每组生成二维码3844以内
//			 */
//
//				if (eveRowTotal > productCount) {
//					eveRowTotal = productCount;
//				}
//				if (eveRowTotal * a < productCount) {
//					eveRowTotal = rowCount;
//				}
//				if (eveRowTotal * a >= productCount) {
//					eveRowTotal = productCount - (eveRowTotal*(a-1));
//				}
//
//				// System.out.println("step:" + 2);
//				try {
//					/**
//					 * 生成一组二维码
//					 */
//					LinkedList<String> codeList = CommonUtils.getRandomStrList(
//							eveRowTotal, 3, bitSet);
//					for (int num = 0; num < eveRowTotal; num++) {
//						/**
//						 * 生成二维码
//						 */
//						String set = codeList.poll();
//						ProductTracingCode tracingCode = new ProductTracingCode();
//						codeTailTag = (UUID.randomUUID().toString().replace(
//								"\\-", "")).substring(0, len);// 追加尾数
//						codePrefix = orderCode + vendorCode + productCode + set;
//						String code = codePrefix + a + codeTailTag;// 二维组合方式:前缀+rownum+后缀
//						/***
//						 * 保存二维码
//						 */
//						tracingCode.setCreateRowNum(a);
//						tracingCode.setOrderId(orderId);
//						tracingCode.setProductId(productId);
//						tracingCode.setProductTracingCode(code);
//						tracingCode.setProductName(productName);
//						tracingCode.setCodePrefix(codePrefix);
//						tracingCode.setCodeTailTag(codeTailTag);
//						tracingCode.setRowCount(eveRowTotal);
//						tracingCode.setTotalRowNum(rowNum-1);
//
//						/**
//						 * 存入数组
//						 */
//						list.add(tracingCode);
//						// System.out.println("code:" + list);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			
//			// System.out.println("code:" + list);
//		}
//		return list;
//
//																
//	}
}
