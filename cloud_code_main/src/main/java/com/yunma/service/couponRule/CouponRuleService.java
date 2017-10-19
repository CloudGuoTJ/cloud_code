package com.yunma.service.couponRule;

import java.util.List;

import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.rule.CouponRule;
import com.yunma.entity.coupon.rule.CouponRuleItem;
import com.yunma.entity.coupon.rule.CouponRuleTag;
import com.yunma.entity.coupon.rule.CouponRuleTagItem;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.utils.PageBean;
import com.yunma.vo.couponRule.CouponRuleItemVo;

public interface CouponRuleService {

	/**
	 * 添加优惠券规则标签
	 * @param couopnId
	 * @param vendorId
	 * @return
	 */
	public int addCouponRuleTag(CouponRuleTag couponRuleTag);

	/**
	 * 添加优惠券规则标签详情
	 * @param item
	 * @return
	 */
	public int addCouponRuleTagItem(CouponRuleTagItem item);

	/**
	 * 删除优惠券规则标签
	 * @param tagId
	 * @return
	 */
	public int deleteCouponTag(Integer tagId);
	
	/**
	 * 删除优惠券规则标签详情
	 * @param tagId
	 * @return
	 */
	public int deleteCouponTagItem(Integer tagId);

	/**
	 * 获取该厂商下的所有优惠券规则标签
	 * @param vendorId
	 * @return
	 */
	public List<CouponRuleTag> getCouponRuleTag(Integer vendorId);

	/**
	 * 根据标签id获取优惠券规则详情信息
	 * @param tagId
	 * @return
	 */
	public List<CouponRuleTagItem> getCouponRuleTagItemByTagId(Integer tagId);

	/**
	 * 添加优惠券规则
	 * @param rule
	 * @return
	 */
	public int addCouponRule(CouponRule rule);

	/**
	 * 添加优惠券规则详情
	 * @param item
	 * @return
	 */
	public int addCouponRuleItem(CouponRuleItem item);

	/**
	 * 分页查询优惠券规则
	 * @param pageBean
	 * @param vendorId 
	 * @return
	 */
	public List<CouponRule> getCouponRuleInfo(PageBean pageBean, Integer vendorId);
	
	/**
	 * 统计 分页查询优惠券规则总记录数
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	public int getCouponRuleInfoCount(PageBean pageBean ,Integer vendorId);
	
	/**
	 * 查询优惠券规则详情的发放内容的信息
	 * @param couponRule
	 * @return
	 */
	public List<CouponRuleItemVo> getCouponRuleItemBlackWhite(CouponRule couponRule);

	/**
	 * 查询优惠券规则详情的发放载体的信息
	 * @param isScope
	 * @param ruleId
	 * @return
	 */
	public List<CouponRuleItemVo> getCouponRuleItemScope(CouponRule couponRule);

	/**
	 * 获取其他厂商的厂商信息
	 * @param vendorId
	 * @return
	 */
	public List<VendorUserDetail> getVendorOther(Integer vendorId);

	/**
	 * 获取自己厂商下的所有订单
	 * @param vendorId
	 * @return
	 */
	public List<ProductOrder> getOrderByVendorId(Integer vendorId);

	/**
	 * 获取自己厂商下的所有产品
	 * @param vendorId
	 * @return
	 */
	public List<Product> getProductByVendorId(Integer vendorId);

	/**
	 * 修改订单 中的规则标识
	 * @param integer
	 */
	public int updateProductOrderFlag(Integer orderId,String isCouponRule);

	/**
	 * 根据orderId获取优惠券信息
	 * @param orderId
	 * @return 
	 */
	public CouponRule getRuleInfoByOrderId(Integer orderId,Integer vendorId);
	
	/**
	 * 根据规则id查询 规则详情的信息
	 * @param item
	 * @return
	 */
	public List<CouponRuleItem> getCouponRuleItemByRuleId(CouponRuleItem item);

	/**
	 * 获取不重复的优惠券信息
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getDistinctCouponAllOther(Integer vendorId,String currTimeStr);

	/**
	 * 修改根据这个厂商下的所有订单——优惠券规则标识
	 * @param vendorId
	 * @return
	 */
	public int updateProductCouponRuleFlag(Integer vendorId,Integer flag);

	/**
	 * 根据规则id查询  规则详情
	 * @param couponRuleId
	 * @return
	 */
	public CouponRule getCouponRuledetailById(Integer couponRuleId);
	
	/**
	 * 获取除去黑名单之后的所有厂商
	 * @return
	 */
	public List<VendorUserDetail> getVendorOutBlack(List<CouponRuleItemVo> blackList);
	
	/**
	 * 删除优惠券规则
	 * @param id
	 * @return
	 */
	public int deleteCouponRule(Integer id);
	
	/**
	 * 删除优惠券规则详情
	 * @param item
	 * @return
	 */
	public int deleteCouponRuleItem(CouponRuleItem item);
	
	/**
	 * 修改优惠券规则中的  标识
	 * @param couponRule
	 * @return
	 */
	public int updateCouponRuleFlag(CouponRule couponRule);
	
}
