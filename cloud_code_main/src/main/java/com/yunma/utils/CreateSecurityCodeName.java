package com.yunma.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.product.ProductOrder;

/**
 * 指定分表存放二维码的表名命名规则
 * @author Administrator
 *
 */
public class CreateSecurityCodeName {
	@Autowired
	private ProductOrderDao orderDao;
	
	public String createTableName(int orderId,int vendorId){
		String tailTagName ;
		ProductOrder order = orderDao.findOrderByProductOrderId(orderId);
		String orderStr = order.getProductId().toString();
		tailTagName  = orderStr +"_"+ orderId;
		return tailTagName;		
	}

}
