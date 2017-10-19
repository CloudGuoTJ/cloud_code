package com.yunma.dao.couponRule;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

@Repository("couponRuleDao")
public interface CouponRuleDao {

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
	public List<CouponRuleTag> getCouponRuleTagByVendorId(Integer vendorId);

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
	 * 添加优惠券详情
	 * @param item
	 * @return
	 */
	public int addCouponRuleItem(CouponRuleItem item);

	/**
	 * 分页查询优惠券信息
	 * @param pageBean
	 * @param vendorId 
	 * @return
	 */
	public List<CouponRule> getCouponRuleInfo(@Param("pageBean")PageBean pageBean, @Param("vendorId")Integer vendorId);

	/**
	 * 分页查询优惠券信息统计
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	public int getCouponRuleInfoCount(@Param("pageBean")PageBean pageBean, @Param("vendorId")Integer vendorId);

	/**
	 * 查询优惠券规则详情的发放内容的信息
	 * @param sql
	 * @return
	 */
	public List<CouponRuleItemVo> getCouponRuleItemBlackWhite(@Param("sql") String sql);
	
	/**
	 * 查询优惠券规则详情的发放载体的信息
	 * @param isScope
	 * @param ruleId
	 * @return
	 */
	public List<CouponRuleItemVo> getCouponRuleItemScope(@Param("sql") String sql);

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
	 * 修改订单中 规则标识
	 * @param orderId
	 * @param isCouponRule 
	 * @return
	 */
	public int updateProductOrderFlag(@Param("orderId") Integer orderId, @Param("isCouponRule") String isCouponRule);

	/**
	 * 根据订单id查询该订单属于哪个优惠券
	 * @param currDate 当前时间
	 * @return
	 */
	public CouponRule getCouponRuleByOrderId(@Param("couponRuleItem") CouponRuleItem couponRuleItem, @Param("currDate") String currDate);
	
	/**
	 * 根据规则id查询 规则详情的信息
	 * @return
	 */
	public List<CouponRuleItem> getCouponRuleItemByRuleId(CouponRuleItem couponRuleItem);

	/**
	 * 获取不重复的其他厂商信息
	 * @param vendorId
	 * @param currTimeStr
	 * @return
	 */
	public List<Coupon> getDistinctCouponAllOther(@Param("vendorId") Integer vendorId,@Param("currTimeStr")
			String currTimeStr);

	/**
	 * 修改根据这个厂商下的所有订单——优惠券规则标识
	 * @param vendorId
	 * @return
	 */
	public int updateProductCouponRuleFlag(@Param("vendorId") Integer vendorId,@Param("flag") Integer flag);

	/**
	 * 根据规则id查询  规则详情
	 * @param couponRuleId
	 * @return
	 */
	public CouponRule getCouponRuledetailById(Integer couponRuleId);

	/**
	 * 获取除去黑名单的厂商
	 * @param sql
	 * @return
	 */
	public List<VendorUserDetail> getVendorOutBlack(@Param("sql") String sql);
	
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
