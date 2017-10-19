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
// * 在A方案中更换二维码生成机制:根据产品Id产品 二维码 = prefix +
// * UUID一定长度(根据产品数量来生成如1000则追加位数为3位,100000则追加5位)
// * 
// * @author Administrator
// * 
// */
//public class GenerateCodeWayA {
//	ProductDao productDao;
//	ProductOrderDao productOrderDao;
//
//	@Transactional
//	public List<SecurityCode> generateSecurityCode(int orderId,
//			String vendorName) throws BusinessException {
//		ProductOrder order = productOrderDao.findOrderByProductOrderId(orderId);// 获取产品订单
//		int productId = order.getProductId();// 拿到产品id
//		int productCount = order.getProductCount();// 获取商品数量
//		Product product = productDao.findProductByProductId(productId);// 通过订单产品id查找订单
//		int numLength = Integer.toString(productCount).length() - 1;// 获取productCount的长度用于追加二维码长度
//		int productNum = product.getProductTotal();// 产品订单的累积数量,根据Base62
//		String securityCodePrefix;// 设置生成二维码的前缀数字设定为:订单prefixNo +
//									// 根据转换成的base62生成的4个字符
//		List<SecurityCode> list = new ArrayList<SecurityCode>();// 二维码数字序列表
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 使用Bitmap去重/设定set的长度
//		for (int i = 0; i < productCount; i++, productNum++) // 前提条件产品总数>
//																// =附码产品数,每附码一次,附码产品数增1
//		{
//
//			/**
//			 * 二维码前缀
//			 */
//			LinkedList<String> codeList = CommonUtils.getRandomStrList(
//					productNum, 2, bitSet);// 根据产品附码数生成随机数
//			securityCodePrefix = order.getPrefixOrderNo()
//					+ Radix.convert10To62(productCount, 4) + codeList;// 设置生成的二维码前缀值
//
//			/**
//			 * 二维码后缀
//			 */
//			String str = UUID.randomUUID().toString().replaceAll("\\-", "");// 使用UUID生成定长的唯一字符去除"-"
//			String tailTag = str.substring(0, numLength);// 从第一位开始截取UUID生成的值中指定长度(numLength),重复率与长度有关
//			/**
//			 * 保存二维码
//			 */
//			SecurityCode code = new SecurityCode();
//			code.setSecurityCode(securityCodePrefix + tailTag);// 二维码
//			code.setOrderId(orderId);
//			code.setProductId(productId);
//			code.setProductName(order.getProductName());
//			code.setCodePrefix(securityCodePrefix);
//			list.add(code);
//
//		}
//		productDao.updateProductNum(productNum, productId);
//
//		return list;
//
//	}
//}
