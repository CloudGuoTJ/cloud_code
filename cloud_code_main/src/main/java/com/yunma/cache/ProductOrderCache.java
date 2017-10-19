package com.yunma.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.service.BaseService;
import com.common.util.XMemcachedHelper;
import com.sun.istack.internal.logging.Logger;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.product.ProductOrder;

/**
 * 参照元系统使用缓存
 * 订单Cache
 * @author Cloud_Goo
 *
 */
@Service
public class ProductOrderCache extends BaseService
{
	/**
	 * 根据id查询订单查询信息
	 */
	@Autowired
	private ProductOrderDao productOrderDao;
	public ProductOrder findById(Integer id)
	{
		ProductOrder productOrder;
		try
		{
			/*从缓存中能拿到数据*/
			productOrder = (ProductOrder)XMemcachedHelper.findCache("ProductOrder_" + id);
			if(productOrder != null)
			{
				logger.info("从Memcached中取得了Order信息");
				return productOrder;
			}
			productOrder = productOrderDao.findOrderByProductOrderId(id);
			/*从缓存中没有取得数据*/
			if(productOrder == null)
			{
				logger.info("没有取得订单信息,订单ID: " + id);
				return null;
			}
			/*将查出的数据存入缓存*/
			XMemcachedHelper.set("productOrder_" + id,productOrder, 30 * 60);
			
		}
		catch(Exception e)
		{
			logger.info("根据ID查询订单时出现:" + e);
			return null;
		}
		logger.info("已将数据库读取信息存入到Memcached中");
		return productOrder;
		
		
	}

}
