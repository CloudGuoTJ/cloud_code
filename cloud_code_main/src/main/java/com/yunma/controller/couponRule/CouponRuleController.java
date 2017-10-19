package com.yunma.controller.couponRule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.coupon.Coupon;
import com.yunma.entity.coupon.rule.CouponRule;
import com.yunma.entity.coupon.rule.CouponRuleItem;
import com.yunma.entity.coupon.rule.CouponRuleTag;
import com.yunma.entity.coupon.rule.CouponRuleTagItem;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.service.couponRule.CouponRuleService;
import com.yunma.service.couponWd.WdCouponService;
import com.yunma.service.product.ProductService;
import com.yunma.utils.PageBean;
import com.yunma.vo.couponRule.CouponRuleItemVo;

@Controller
public class CouponRuleController {
	
	@Resource
	private CouponRuleService couponRuleService;
	
	@Resource
	private WdCouponService wdCouponService;
	
	@Resource
	private ProductService productService;
	
	private Logger log = LoggerFactory.getLogger(CouponRuleController.class);
	
	
	
	/**
	 * 创建优惠券规则
	 * @return
	 */
	@RequestMapping("/ADD/couponRule/info.do")
	@ResponseBody
	public JSONObject addCouponRule(
			Integer vendorId,
			String ruleName,
			String startTime,
			String endTime,
			@RequestParam(value = "blackList[]", required = false) Integer [] blackList,
			@RequestParam(value = "whiteList[]", required = false)Integer [] whiteList, 
			/*Integer tagId,*/
			String timeType,//0 表示长期 1表示短期（自定义）    
			String isBlackWhite,//-1 不设置 0 黑白名单 1黑名单 2白名单
			String isScope,//3全部 4产品 5订单
			String isDefault,//是否为默认规则 
			@RequestParam(value = "productList[]", required = false)Integer [] productList,
			@RequestParam(value = "orderList[]", required = false)Integer [] orderList
			) {
		JSONObject result = new JSONObject();
		
		CouponRule rule = new CouponRule();
		rule.setRuleName(ruleName);
		rule.setVendorId(vendorId);
		/*rule.setTagId(tagId);*/
		rule.setCreateTime(new Date());
		rule.setStartTimeStr(startTime);
		rule.setEndTimeStr(endTime);
		rule.setTimeType(timeType);
		rule.setIsBlackWhite(isBlackWhite);
		rule.setIsScope(isScope);
		if (isDefault == null)
			isDefault = "0";
		rule.setIsDefault(isDefault);
		
		int resultNum = couponRuleService.addCouponRule(rule);
		if (resultNum > 0) {
			//如果 创建的是默认规则
			if ("1".equals(isDefault)) {
				CouponRuleItem item = new CouponRuleItem();
				item.setRuleId(rule.getId());
				item.setType("6");
				item.setItemId(vendorId);
				int resultNum1 = couponRuleService.addCouponRuleItem(item);
				if (resultNum1 <= 0) {
					result.put("statuscode", -1);
					result.put("msg", "失败");
					return result;
				}
			}
			//添加黑名单
			if (blackList != null && blackList.length > 0) {
				for (Integer integer : blackList) {
					CouponRuleItem item = new CouponRuleItem();
					item.setItemId(integer);
					item.setRuleId(rule.getId());
					item.setType("1");
					int resultNum1 = couponRuleService.addCouponRuleItem(item);
					if (resultNum1 <= 0) {
						result.put("statuscode", -1);
						result.put("msg", "添加黑名单失败");
						return result;
					}
				}
			}
			
			//添加白名单
			if (whiteList != null && whiteList.length > 0) {
				for (Integer integer : whiteList) {
					CouponRuleItem item = new CouponRuleItem();
					item.setItemId(integer);
					item.setRuleId(rule.getId());
					item.setType("2");
					int resultNum1 = couponRuleService.addCouponRuleItem(item);
					if (resultNum1 <= 0) {
						result.put("statuscode", -2);
						result.put("msg", "添加白名单失败");
						return result;
					}
				}
			}
			
			//添加全部
			if ("3".equals(isScope)) {
				CouponRuleItem item = new CouponRuleItem();
				item.setRuleId(rule.getId());
				item.setType("3");
				item.setItemId(vendorId);
				int resultNum1 = couponRuleService.addCouponRuleItem(item);
				if (resultNum1 <= 0) {
					result.put("statuscode", -1);
					result.put("msg", "失败");
					return result;
				}
				
				int resultNum2 = couponRuleService.updateProductCouponRuleFlag(vendorId,1);
				/*if (resultNum2 <= 0) {
					result.put("statuscode", -4);
					result.put("msg", "系统异常");
					return result;
				}*/
				
			//添加产品
			} else if ("4".equals(isScope)) {
				if (productList != null && productList.length > 0) {
					for (Integer integer : productList) {
						CouponRuleItem item = new CouponRuleItem();
						item.setRuleId(rule.getId());
						item.setItemId(integer);
						item.setType("4");
						int resultNum1 = couponRuleService.addCouponRuleItem(item);
						if (resultNum1 <= 0) {
							result.put("statuscode", -3);
							result.put("msg", "添加产品失败");
							return result;
						}
						Product product = new Product();
						product.setId(integer);
						product.setIsCouponRule("1");
						
						int resultNum2 = productService.updateProduct(product);
						if (resultNum2 <= 0) {
							result.put("statuscode", -4);
							result.put("msg", "系统异常");
							return result;
						}
					}
				}
				
			//添加订单
			} else if ("5".equals(isScope)) {
				if (orderList != null && orderList.length > 0) {
					for (Integer integer : orderList) {
						CouponRuleItem item = new CouponRuleItem();
						item.setRuleId(rule.getId());
						item.setItemId(integer);
						item.setType("5");
						int resultNum1 = couponRuleService.addCouponRuleItem(item);
						if (resultNum1 <= 0) {
							result.put("statuscode", -5);
							result.put("msg", "添加订单失败");
							return result;
						}
						int resultNum2 = couponRuleService.updateProductOrderFlag(integer,"1");
						if (resultNum2 <= 0) {
							result.put("statuscode", -6);
							result.put("msg", "系统异常");
							return result;
						}
					}
				}
			}
			
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		}
		
		return result;
	}
	
	/**
	 * 分页查询优惠券规则信息
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/info.do")
	@ResponseBody
	public PageBean getCouponRuleInfo(PageBean pageBean,Integer vendorId){
		
		int count = couponRuleService.getCouponRuleInfoCount(pageBean, vendorId);
		
		pageBean.setTotalRecords(count);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//分页查询优惠券规则
		List<CouponRule> rule = couponRuleService.getCouponRuleInfo(pageBean,vendorId);
		
		JSONObject result = new JSONObject();
		
		JSONArray array = new JSONArray();
		
		if (rule != null && rule.size() > 0) {
			
			for (CouponRule couponRule : rule) {
				
				JSONObject object = new JSONObject();
				object.put("id", couponRule.getId());
				object.put("ruleName", couponRule.getRuleName());
				object.put("state", couponRule.getState());
				object.put("timeType", couponRule.getTimeType());
				object.put("startTime", sdf.format(couponRule.getStartTime()));
				object.put("endTime", sdf.format(couponRule.getEndTime()));
				object.put("isBlackWhite", couponRule.getIsBlackWhite());
				object.put("isScope", couponRule.getIsScope());
				
				//黑名单
				List<CouponRuleItemVo> blackList = null;
				if ("1".equals(couponRule.getIsBlackWhite())) {
					blackList = couponRuleService.getCouponRuleItemBlackWhite(couponRule);
				}
				
				//白名单
				List<CouponRuleItemVo> whiteList = null;
				if ("2".equals(couponRule.getIsBlackWhite())) {
					whiteList = couponRuleService.getCouponRuleItemBlackWhite(couponRule);
				}
				
				//查询优惠券规则详情的发放载体的信息
				List<CouponRuleItemVo> scopeList = couponRuleService.getCouponRuleItemScope(couponRule);
				
				//JSONArray blackArray = new JSONArray();
				JSONArray whiteArray = new JSONArray();
				JSONArray scopeArray = new JSONArray();
				
				//黑名单
				if (blackList != null && blackList.size() > 0) {
					/*for (CouponRuleItemVo blackWhiteLists : blackList) {
						JSONObject object1 = new JSONObject();
						object1.put("id", blackWhiteLists.getId());
						object1.put("itemName", blackWhiteLists.getItemName());
						blackArray.add(object1);
					}*/
					List<VendorUserDetail> vendorList = couponRuleService.getVendorOutBlack(blackList);
					if (vendorList != null && vendorList.size() > 0) {
						for (VendorUserDetail vendorUserDetail : vendorList) {
							JSONObject object1 = new JSONObject();
							object1.put("id", vendorUserDetail.getVendorId());
							object1.put("itemName", vendorUserDetail.getBrandName());
							whiteArray.add(object1);
						}
					}
					
				//白名单
				} else if (whiteList != null && whiteList.size() > 0) {
					for (CouponRuleItemVo blackWhiteLists : whiteList) {
						JSONObject object1 = new JSONObject();
						object1.put("id", blackWhiteLists.getId());
						object1.put("itemName", blackWhiteLists.getItemName());
						whiteArray.add(object1);
					}
				}
				
				if (scopeList != null && scopeList.size() > 0) {
					for (CouponRuleItemVo scopeLists : scopeList) {
						JSONObject object1 = new JSONObject();
						object1.put("id", scopeLists.getId());
						object1.put("itemName", scopeLists.getItemName());
						scopeArray.add(object1);
					}
				}
				
				//object.put("blackArray", blackArray);
				object.put("whiteArray", whiteArray);
				object.put("scopeArray", scopeArray);
				
				array.add(object);
			}
			
			result.put("data", array);
		}
		
		pageBean.setResult(result);
		
		
		return pageBean;
	}
	
	/**
	 * 根据规则id查询  规则详情
	 * @param couponRuleId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/detail.do")
	@ResponseBody
	public JSONObject getCouponRuleDetailById(Integer id) {
		JSONObject result = new JSONObject();
		
		CouponRule couponRule = couponRuleService.getCouponRuledetailById(id);
		if (couponRule == null) {
			result.put("statuscode", -1);
			result.put("msg", "优惠券规则不存在");
		} else {
			result = getCouponRule(couponRule);
		}
		
		return result;
	}
	
	/**
	 * 修改优惠券规则
	 * @return
	 */
	@RequestMapping("/UPDATE/couponRule/info.do")
	@ResponseBody
	public JSONObject updateCouponRule(
			Integer id,
			String ruleName,
			String timeType,
			String startTime,
			String endTime,
			String isBlackWhite,//-1 不设置 0 黑白名单 1黑名单 2白名单
			String isScope,//3全部 4产品 5订单
			@RequestParam(value = "vendorId[]", required = false)Integer [] vendorId,
			@RequestParam(value = "productList[]", required = false)Integer [] productList,
			@RequestParam(value = "orderList[]", required = false)Integer [] orderList
			) {
		JSONObject result = new JSONObject();
		
		CouponRule couponRule = couponRuleService.getCouponRuledetailById(id);
		if (couponRule == null) {
			result.put("statuscode", -1);
			result.put("msg", "优惠券规则不存在");
			return result;
		}
		
		CouponRuleItem item = new CouponRuleItem();
		
		//如果有黑名单
		if ("1".equals(couponRule.getIsBlackWhite())) {
			item.setType("1");
			item.setRuleId(couponRule.getId());
			int resultNum = couponRuleService.deleteCouponRuleItem(item);
			if (resultNum < 0) {
				result.put("statuscode", -2);
				result.put("msg", "删除失败");
				return result;
			}
			
			//如果修改的是黑名单
			if ("1".equals(isBlackWhite)) {
				item.setType("1");
			//如果修改的是
			} else if ("2".equals(isBlackWhite)) {
				item.setType("2");
			}
			
			//新增优惠券详情
			int resultNum1 = addCouponRuleItem(vendorId,item,result);
			if (resultNum1 == 0) {
				result.put("statuscode", -3);
				result.put("msg", "新增失败");
				return result;
			}
			
			//修改规则表中的标识
			CouponRule rule = new CouponRule();
			rule.setId(couponRule.getId());
			rule.setIsBlackWhite(isBlackWhite);
			couponRuleService.updateCouponRuleFlag(rule);
			
		//如果有白名单
		} else if ("2".equals(couponRule.getIsBlackWhite())) {
			item.setType("2");
			item.setRuleId(couponRule.getId());
			int resultNum = couponRuleService.deleteCouponRuleItem(item);
			if (resultNum < 0) {
				result.put("statuscode", -2);
				result.put("msg", "删除失败");
				return result;
			}
			
			//如果修改的是黑名单
			if ("1".equals(isBlackWhite)) {
				item.setType("1");
			//如果修改的是
			} else if ("2".equals(isBlackWhite)) {
				item.setType("2");
			}
			
			//新增优惠券详情
			int resultNum1 = addCouponRuleItem(vendorId,item,result);
			if (resultNum1 == 0) {
				result.put("statuscode", -3);
				result.put("msg", "新增失败");
				return result;
			}
			
			//修改规则表中的标识
			CouponRule rule = new CouponRule();
			rule.setId(couponRule.getId());
			rule.setIsBlackWhite(isBlackWhite);
			couponRuleService.updateCouponRuleFlag(rule);
			
		}
		
		//是否修改 发放载体
		//改为全部
		if ("3".equals(isScope)) {
			
		//改为产品
		} else if ("4".equals(isScope)) {
			
		//改为订单
		} else if ("5".equals(isScope)) {
			
		}
		
		
		return result;
	}
	
	/**
	 * 新增优惠券详情
	 * @param vendorId
	 * @param item
	 * @param result
	 * @return
	 */
	private int addCouponRuleItem(Integer [] vendorId,CouponRuleItem item,JSONObject result) {
		for (Integer integer : vendorId) {
			item.setItemId(integer);
			int resultNum = couponRuleService.addCouponRuleItem(item);
			if (resultNum <= 0) {
				return 0;
			}
		}
		return 1;
	}
	
	
	
	/**
	 * 获取该厂商下的所有优惠券规则标签信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/tag.do")
	@ResponseBody
	public JSONObject getCouponRuleTag(Integer vendorId) {
		JSONObject result = new JSONObject();
		
		List<CouponRuleTag> tag = couponRuleService.getCouponRuleTag(vendorId);
		if (tag != null && tag.size() > 0) {
			JSONArray array = new JSONArray();
			
			for (CouponRuleTag tags : tag) {
				JSONObject object = new JSONObject();
				
				
				object.put("id", tags.getId());
				object.put("tagName", tags.getName());
				
				
				List<CouponRuleTagItem> item = couponRuleService.getCouponRuleTagItemByTagId(tags.getId());
				if (item != null && item.size() > 0) {
					JSONArray array1 = new JSONArray();
					
					for (CouponRuleTagItem tagItem : item) {
						JSONObject object1 = new JSONObject();
						object1.put("id", tagItem.getId());
						object1.put("tagId", tagItem.getTagId());
						object1.put("couponId", tagItem.getCouponId());
						object1.put("couponName", tagItem.getCouponName());
						object1.put("scale",tagItem.getScale());
						array1.add(object1);
					}
					object.put("childs", array1);
				}
				
				array.add(object);
			}
			result.put("data", array);
		}
		System.out.println(result.toString());
		
		return result;
	}
	
	/**
	 * 添加优惠券规则标签
	 * @return
	 */
	@RequestMapping("/ADD/couponRule/tag.do")
	@ResponseBody
	public JSONObject addCouponRuleTag(
			String tagName,
			@RequestParam(value = "couopnId[]", required = false)Integer[] couopnId,
			@RequestParam(value = "scale[]", required = false)Integer[] scale,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		
		CouponRuleTag couponRuleTag = new CouponRuleTag();
		couponRuleTag.setName(tagName);
		couponRuleTag.setVendorId(vendorId);
		couponRuleTag.setCreateTime(new Date());
		
		//添加优惠券规则标签
		int resultNum = couponRuleService.addCouponRuleTag(couponRuleTag);
		if (resultNum > 0) {
			
			for (int i=0 ; i<couopnId.length ; i++) {
				CouponRuleTagItem item = new CouponRuleTagItem();
				item.setCouponId(couopnId[i]);
				item.setScale(scale[i]);
				item.setTagId(couponRuleTag.getId());
				item.setCreateTime(new Date());
				int resultNum1 = couponRuleService.addCouponRuleTagItem(item);
				if (resultNum1 <= 0) {
					result.put("statuscode", -2);
					result.put("msg", "添加标签详情失败！");
					return result;
				}
			}
			
			result.put("tagId", couponRuleTag.getId());
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		} else {
			result.put("statuscode", -1);
			result.put("msg", "添加标签失败！");
			return result;
		}
	}
	
	/**
	 * 删除优惠券规则标签 及其详情信息
	 * @param tagId
	 * @return
	 */
	@RequestMapping("/DELETE/coupon/tag.do")
	@ResponseBody
	public  JSONObject deleteCouponTag(Integer tagId) {
		JSONObject result = new JSONObject();
		int tagNum = couponRuleService.deleteCouponTag(tagId);
		if (tagNum > 0) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "失败");
		}
		return result;
	}
	
	/**
	 * 删除优惠券规则标签详情
	 * @param tagId
	 * @return
	 */
	@RequestMapping("/DELETE/coupon/tagItem.do")
	@ResponseBody
	public JSONObject deleteCouponTagItem(Integer tagId) {
		JSONObject result = new JSONObject();
		int tagNum = couponRuleService.deleteCouponTagItem(tagId);
		if (tagNum > 0) {
			result.put("statuscode", 1);
			result.put("msg", "失败");
		}
		return result;
	}
	
	/**
	 * 查询其他厂商的信息
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/vendorOther.do")
	@ResponseBody
	public JSONObject getVendorOther(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<VendorUserDetail> vendor = couponRuleService.getVendorOther(vendorId);
		if (vendor != null && vendor.size() > 0) {
			for (VendorUserDetail vendors : vendor) {
				JSONObject object = new JSONObject();
				object.put("id", vendors.getVendorId());
				object.put("vendorName", vendors.getBrandName());
				array.add(object);
			}
			result.put("data", array);
		}
		
		return result;
	}
	
	/**
	 * 获取自己厂商的订单
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/order.do")
	@ResponseBody
	public JSONObject getOrderByVendorId(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<ProductOrder> order = couponRuleService.getOrderByVendorId(vendorId);
		if (order != null && order.size() > 0) {
			for (ProductOrder orders : order) {
				JSONObject object = new JSONObject();
				object.put("id", orders.getOrderId());
				array.add(object);
			}
		}
		result.put("data", array);
		
		return result;
	}

	/**
	 * 获取自己厂商下的所有产品
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/product.do")
	@ResponseBody
	public JSONObject getProductByVendorId(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<Product> list = couponRuleService.getProductByVendorId(vendorId);
		if (list != null && list.size() > 0) {
			for (Product product : list) {
				JSONObject object = new JSONObject();
				object.put("id", product.getId());
				object.put("productName", product.getProductName());
				array.add(object);
			}
			
		}
		result.put("data", array);
		return result;
	}
	
	
	/**
	 * 获取其他厂商的所有优惠券
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/couponRule/otherList.do")
	@ResponseBody
	public JSONObject otherList(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String currTimeStr = sdf.format(new Date());
		
		List<Coupon> listWdDateOther = wdCouponService.getWdCouponAllOther(vendorId,currTimeStr);
		if (listWdDateOther != null && listWdDateOther.size() > 0) {
			for (Coupon coupon : listWdDateOther) {
				JSONObject object = new JSONObject();
				object.put("couponId", coupon.getCouponId());
				object.put("couponName", coupon.getCouponName());
				object.put("beginTime", sdf.format(coupon.getBeginTime()));
				object.put("endTime", sdf.format(coupon.getEndTime()));
				object.put("style", coupon.getStyle());
				object.put("couponTotal", coupon.getCouponTotal());
				object.put("money", coupon.getMoney());
				object.put("stockCount", coupon.getStockCount());
				object.put("leastCost", coupon.getLeastCost());
				object.put("vendorId", coupon.getVendorId());
				object.put("weidianFetchUrl", coupon.getWeidianFetchUrl());

				array.add(object);
			}
		}
		result.put("data", array);
		result.put("statuscode", 1);
		result.put("msg", "成功");
		return result;
	}
	
	/**
	 * 测试   添加默认规则
	 * @return
	 */
	@RequestMapping("/test11112.do")
	@ResponseBody
	public JSONObject test() {
		JSONObject result = new JSONObject();
		
		Integer vendorId = 6;
		String ruleName = "默认规则";
		String startTime = "2016-01-01";
		String endTime = "2100-01-01";
		Integer [] blackList = null;
		Integer [] whiteList = null;
		String timeType = "0";
		String isBlackWhite = null;
		String isScope = null;
		String isDefault = "1";
		Integer [] productList = null;
		Integer [] orderList = null;
		
		CouponRuleController rule = new CouponRuleController();
		rule.addCouponRule(vendorId,ruleName,startTime,endTime,blackList,whiteList,timeType,isBlackWhite,isScope,isDefault,productList,orderList);
		
		return result;
	}
	
	/**
	 * 获取优惠券规则详情
	 * @param couponRule
	 * @return
	 */
	private JSONObject getCouponRule(CouponRule couponRule) {
		JSONObject object = new JSONObject();
		object.put("id", couponRule.getId());
		object.put("ruleName", couponRule.getRuleName());
		object.put("state", couponRule.getState());
		object.put("timeType", couponRule.getTimeType());
		object.put("startTime", couponRule.getStartTime());
		object.put("endTime", couponRule.getEndTime());
		object.put("isBlackWhite", couponRule.getIsBlackWhite());
		object.put("isScope", couponRule.getIsScope());
		
		//黑名单
		List<CouponRuleItemVo> blackList = couponRuleService.getCouponRuleItemBlackWhite(couponRule);
		
		//白名单
		List<CouponRuleItemVo> whiteList = couponRuleService.getCouponRuleItemBlackWhite(couponRule);
		
		//查询优惠券规则详情的发放载体的信息
		List<CouponRuleItemVo> scopeList = couponRuleService.getCouponRuleItemScope(couponRule);
		
		JSONArray blackArray = new JSONArray();
		JSONArray whiteArray = new JSONArray();
		JSONArray scopeArray = new JSONArray();
		
		//黑名单
		if (blackList != null && blackList.size() > 0) {
			for (CouponRuleItemVo blackWhiteLists : blackList) {
				JSONObject object1 = new JSONObject();
				object1.put("id", blackWhiteLists.getId());
				object1.put("itemName", blackWhiteLists.getItemName());
				blackArray.add(object1);
			}
		}
		
		//白名单
		if (whiteList != null && whiteList.size() > 0) {
			for (CouponRuleItemVo blackWhiteLists : whiteList) {
				JSONObject object1 = new JSONObject();
				object1.put("id", blackWhiteLists.getId());
				object1.put("itemName", blackWhiteLists.getItemName());
				whiteArray.add(object1);
			}
		}
		
		if (scopeList != null && scopeList.size() > 0) {
			for (CouponRuleItemVo scopeLists : scopeList) {
				JSONObject object1 = new JSONObject();
				object1.put("id", scopeLists.getId());
				object1.put("itemName", scopeLists.getItemName());
				scopeArray.add(object1);
			}
		}
		
		object.put("blackArray", blackArray);
		object.put("whiteArray", whiteArray);
		object.put("scopeArray", scopeArray);
		
		return object;
	}
	
	/**
	 * 删除优惠券规则
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/couponRule/info.do")
	@ResponseBody
	public JSONObject deleteCouponRule(Integer id,Integer vendorId) {
		JSONObject result = new JSONObject();
		
		//查询产品信息
		CouponRule couponRule = couponRuleService.getCouponRuledetailById(id);
		if (couponRule == null) {
			result.put("statuscode", -1);
			result.put("msg", "优惠券规则不存在");
			return result;
		}
		
		//全部
		if ("3".equals(couponRule.getIsScope())) {
			couponRuleService.updateProductCouponRuleFlag(vendorId,0);
			
			//删除规则详情
			CouponRuleItem item = new CouponRuleItem();
			item.setRuleId(id);
			couponRuleService.deleteCouponRuleItem(item);

			//删除规则
			couponRuleService.deleteCouponRule(id);
		//产品
		} else if ("4".equals(couponRule.getIsScope())) {
			CouponRule couponRule1 = new CouponRule();
			couponRule1.setIsScope("4");
			couponRule1.setId(id);
			List<CouponRuleItemVo> list = couponRuleService.getCouponRuleItemScope(couponRule1);
			if (list != null && list.size() > 0) {
				for (CouponRuleItemVo couponRuleItemVo : list) {
					Product product = new Product();
					product.setId(couponRuleItemVo.getId());
					product.setIsCouponRule("0");
					int resultNum2 = productService.updateProduct(product);
				}
			}
			
			//删除规则详情
			CouponRuleItem item = new CouponRuleItem();
			item.setRuleId(id);
			couponRuleService.deleteCouponRuleItem(item);

			//删除规则
			couponRuleService.deleteCouponRule(id);
		//订单
		} else if ("5".equals(couponRule.getIsScope())) {
			CouponRule couponRule1 = new CouponRule();
			couponRule1.setIsScope("5");
			couponRule1.setId(id);
			List<CouponRuleItemVo> list = couponRuleService.getCouponRuleItemScope(couponRule1);
			if (list != null && list.size() > 0) {
				for (CouponRuleItemVo couponRuleItemVo : list) {
					couponRuleService.updateProductOrderFlag(couponRuleItemVo.getId(),"0");
				}
			}
			
			//删除规则详情
			CouponRuleItem item = new CouponRuleItem();
			item.setRuleId(id);
			couponRuleService.deleteCouponRuleItem(item);

			//删除规则
			couponRuleService.deleteCouponRule(id);
		} else {
			//删除规则
			couponRuleService.deleteCouponRule(id);
		}
		
		result.put("statuscode", 1);
		result.put("msg", "删除成功");
		
		return result;
	}
}
