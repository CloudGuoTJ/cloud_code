package com.yunma.service.couponRule.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.couponRule.CouponRuleDao;
import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.rule.CouponRule;
import com.yunma.entity.coupon.rule.CouponRuleItem;
import com.yunma.entity.coupon.rule.CouponRuleTag;
import com.yunma.entity.coupon.rule.CouponRuleTagItem;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.service.couponRule.CouponRuleService;
import com.yunma.utils.PageBean;
import com.yunma.vo.couponRule.CouponRuleItemVo;

@Service
public class CouponRuleServiceImpl implements CouponRuleService {

	@Resource
	private CouponRuleDao couponRuleDao;

	@Override
	public int addCouponRuleTag(CouponRuleTag couponRuleTag) {
		return couponRuleDao.addCouponRuleTag(couponRuleTag);
	}

	@Override
	public int addCouponRuleTagItem(CouponRuleTagItem item) {
		return couponRuleDao.addCouponRuleTagItem(item);
	}

	@Override
	public int deleteCouponTag(Integer tagId) {
		int resultNum = couponRuleDao.deleteCouponTag(tagId);
		if (resultNum > 0) {
			return deleteCouponTagItem(tagId);
		} else {
			return 0;
		}
	}

	@Override
	public int deleteCouponTagItem(Integer tagId) {
		return couponRuleDao.deleteCouponTagItem(tagId);
	}

	@Override
	public List<CouponRuleTag> getCouponRuleTag(Integer vendorId) {
		return couponRuleDao.getCouponRuleTagByVendorId(vendorId);
	}
	
	@Override
	public List<CouponRuleTagItem> getCouponRuleTagItemByTagId(Integer tagId) {
		return couponRuleDao.getCouponRuleTagItemByTagId(tagId);
	}

	@Override
	public int addCouponRule(CouponRule rule) {
		return couponRuleDao.addCouponRule(rule);
	}

	@Override
	public int addCouponRuleItem(CouponRuleItem item) {
		return couponRuleDao.addCouponRuleItem(item);
	}
	
	@Override
	public int getCouponRuleInfoCount(PageBean pageBean,Integer vendorId) {
		return couponRuleDao.getCouponRuleInfoCount(pageBean,vendorId);
	}

	@Override
	public List<CouponRule> getCouponRuleInfo(PageBean pageBean,Integer vendorId) {
		return couponRuleDao.getCouponRuleInfo(pageBean,vendorId);
	}

	@Override
	public List<CouponRuleItemVo> getCouponRuleItemBlackWhite(CouponRule couponRule) {
		String sql  = "";
		if ("0".equals(couponRule.getIsBlackWhite())) {
			sql = "select i.item_id id,d.brand_name itemName "+
				"FROM tb_coupon_rule_item i "+
				"LEFT JOIN tb_vendor_user_detail d ON i.item_id = d.vendor_id " +
				"WHERE i.rule_id = "+couponRule.getId()+" AND (i.type = 1 OR i.type = 2)";
			
		} else if ("1".equals(couponRule.getIsBlackWhite())){
			sql = "select i.item_id id,d.brand_name itemName "+
					"FROM tb_coupon_rule_item i "+
					"LEFT JOIN tb_vendor_user_detail d ON i.item_id = d.vendor_id " +
					"WHERE i.rule_id = "+couponRule.getId()+" AND i.type = 1";
			
		} else if ("2".equals(couponRule.getIsBlackWhite())) {
			sql = "select i.item_id id,d.brand_name itemName "+
					"FROM tb_coupon_rule_item i "+
					"LEFT JOIN tb_vendor_user_detail d ON i.item_id = d.vendor_id " +
					"WHERE i.rule_id = "+couponRule.getId()+" AND i.type = 2";
		} else {
			return null;
		}
		return couponRuleDao.getCouponRuleItemBlackWhite(sql);
	}
	
	@Override
	public List<CouponRuleItemVo> getCouponRuleItemScope(CouponRule couponRule) {
		String sql = "";
		if ("4".equals(couponRule.getIsScope())) {
			sql = "SELECT i.item_id id,p.product_name itemName "+
				"FROM tb_coupon_rule_item i "+
				"LEFT JOIN tb_product p ON i.`item_id` = p.id "+
				"WHERE i.`rule_id`="+couponRule.getId()+" AND i.`type`=4";
		} else if ("5".equals(couponRule.getIsScope())) {
			sql = "SELECT i.item_id id,o.productName itemName "+
					"FROM tb_coupon_rule_item i "+
					"LEFT JOIN `tb_product_order` o ON i.item_id = o.orderId "+
					"WHERE i.rule_id = "+couponRule.getId()+" AND i.type=5 ";
		} else if ("3".equals(couponRule.getIsScope())) {
			CouponRuleItemVo vo = new CouponRuleItemVo();
			vo.setId(-1);
			vo.setItemName("全部");
			
			List<CouponRuleItemVo> list = new ArrayList<CouponRuleItemVo>();
			list.add(vo);
			return list;
		} else {
			return null;
		}
		return couponRuleDao.getCouponRuleItemScope(sql);
	}

	@Override
	public List<VendorUserDetail> getVendorOther(Integer vendorId) {
		return couponRuleDao.getVendorOther(vendorId);
	}

	@Override
	public List<ProductOrder> getOrderByVendorId(Integer vendorId) {
		return couponRuleDao.getOrderByVendorId(vendorId);
	}
	
	@Override
	public List<Product> getProductByVendorId(Integer vendorId) {
		return couponRuleDao.getProductByVendorId(vendorId);
	}

	@Override
	public int updateProductOrderFlag(Integer orderId,String isCouponRule) {
		return couponRuleDao.updateProductOrderFlag(orderId,isCouponRule);
	}

	@Override
	public CouponRule getRuleInfoByOrderId(Integer orderId,Integer vendorId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(new Date());
		CouponRuleItem item = new CouponRuleItem();
		
		//查询是否设置的为  全部
		item.setType("3");
		item.setItemId(vendorId);
		CouponRule resultRule2 = couponRuleDao.getCouponRuleByOrderId(item,currDate);
		
		if (resultRule2 != null) {
			return resultRule2;
		} else {
		
			//在订单中查询是否存在
			item.setType("5");
			item.setItemId(orderId);
			CouponRule resultRule = couponRuleDao.getCouponRuleByOrderId(item,currDate);
			
			if (resultRule != null) {
				return resultRule;
			} else {
				
				//在产品中查询是否存在
				item.setType("4");
				CouponRule resultRule1 = couponRuleDao.getCouponRuleByOrderId(item,currDate);
				if (resultRule1 != null) {
					return resultRule1;
				} else {
					
					//查询是否 设置的为 默认
					item.setType("6");
					item.setItemId(vendorId);
					CouponRule resultRule3 = couponRuleDao.getCouponRuleByOrderId(item,currDate);
					if (resultRule3 != null) {
						return resultRule3;
					} else {
						return null;
					}
					
					
				}
			}
		}
		
		
		
	}
	
	/**
	 * //判断产品中是否存在
		if (resultRule != null) {
			return getBlackWhite(resultRule,item);
		//判断订单中是否存在
		} else {
			item.setType("4");
			CouponRule resultRule1 = couponRuleDao.getCouponRuleByOrderId(item);
			if (resultRule1 != null) {
				return getBlackWhite(resultRule1,item);
			} else {
				return null;
			}
		}
	 */
	
	private List<CouponRuleItem> getBlackWhite(CouponRule resultRule,CouponRuleItem item) {
		//黑名单
		if ("1".equals(resultRule.getIsBlackWhite())) {
			item.setType("1");
			item.setRuleId(resultRule.getId());
			
			List<CouponRuleItem> list = couponRuleDao.getCouponRuleItemByRuleId(item);
			return list;
		//白名单
		} else if ("2".equals(resultRule.getIsBlackWhite())) {
			item.setType("2");
			item.setRuleId(resultRule.getId());
			
			List<CouponRuleItem> list = couponRuleDao.getCouponRuleItemByRuleId(item);
			return list;
		//黑白名单
		} else if ("0".equals(resultRule.getIsBlackWhite())) {
			item.setType("1");
			item.setRuleId(resultRule.getId());
			//黑名单
			List<CouponRuleItem> list = couponRuleDao.getCouponRuleItemByRuleId(item);
			
			item.setType("2");
			item.setRuleId(resultRule.getId());
			//白名单
			List<CouponRuleItem> list1 = couponRuleDao.getCouponRuleItemByRuleId(item);
			if (list1 != null) {
				for (CouponRuleItem lists : list1) {
					list.add(lists);
				}
			}
			//返回黑白名单的信息
			return list;
		//不设置
		} else if ("-1".equals(resultRule.getIsBlackWhite())) {
			return null;
		} else {
		
			return null;
		}
	}

	@Override
	public List<CouponRuleItem> getCouponRuleItemByRuleId(CouponRuleItem item) {
		return couponRuleDao.getCouponRuleItemByRuleId(item);
	}

	@Override
	public List<Coupon> getDistinctCouponAllOther(Integer vendorId,
			String currTimeStr) {
		return couponRuleDao.getDistinctCouponAllOther(vendorId,currTimeStr);
	}

	@Override
	public int updateProductCouponRuleFlag(Integer vendorId,Integer flag) {
		return couponRuleDao.updateProductCouponRuleFlag(vendorId,flag);
	}

	@Override
	public CouponRule getCouponRuledetailById(Integer couponRuleId) {
		return couponRuleDao.getCouponRuledetailById(couponRuleId);
	}

	@Override
	public List<VendorUserDetail> getVendorOutBlack(List<CouponRuleItemVo> blackList) {
		String sql = "SELECT DISTINCT i.`vendorId` vendorId,d.`brand_name` brandName "+
				"FROM tb_coupon_info i,tb_vendor_user_detail d WHERE i.`vendorId` = d.`vendor_id` ";
		for (CouponRuleItemVo couponRuleItemVo : blackList) {
			sql += "and i.vendorId != "+couponRuleItemVo.getId() +" ";
		} 
		sql += "GROUP BY i.vendorId ";
		return couponRuleDao.getVendorOutBlack(sql);
	}
	
	@Override
	public int deleteCouponRule(Integer id) {
		return couponRuleDao.deleteCouponRule(id);
	}

	@Override
	public int deleteCouponRuleItem(CouponRuleItem item) {
		return couponRuleDao.deleteCouponRuleItem(item);
	}

	@Override
	public int updateCouponRuleFlag(CouponRule couponRule) {
		return couponRuleDao.updateCouponRuleFlag(couponRule);
	}
}
