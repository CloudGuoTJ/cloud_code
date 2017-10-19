package com.yunma.controller.couponWd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

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
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.service.couponRule.CouponRuleService;
import com.yunma.service.couponWd.WdConfigService;
import com.yunma.service.couponWd.WdCouponService;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.service.couponWechat.WeChatCouponService;
import com.yunma.service.couponWq.WqCouponService;
import com.yunma.service.wxConfig.WxConfigService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.PageBean;
import com.yunma.utils.coupon.RuleUtil;
import com.yunma.utils.coupon.WdUtil;

/**
 * 微店
 * @author LUO
 *
 */
@Controller
public class WdCouponController {
	
	@Resource
	private WdCouponService wdCouponService;
	
	@Resource
	private CouponRuleService couponRuleService;
	
	@Resource
	private WeChatCouponService weChatCouponService;
	
	@Resource
	private WxConfigService wxConfigService;
	
	@Resource
	private WdConfigService wdConfigService;
	
	@Resource
	private WeChatCouponConfigService weChatCouponConfigService;
	
	@Resource
	private WqCouponService wqCouponService;
	
	private Logger log = LoggerFactory.getLogger(WdCouponController.class);


	@RequestMapping("/wd/push.do")
	@ResponseBody
	public JSONObject push(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		try {
			BufferedReader   bufferedReader =   new BufferedReader(
			        new InputStreamReader( (ServletInputStream)request.getInputStream(),"utf-8")); 
			StringBuffer stringBuffer =new StringBuffer("");
			String temp;
			while((temp=bufferedReader.readLine())!=null){
			    stringBuffer.append(temp);
			}
			bufferedReader.close();
			String   msg  =  stringBuffer.toString();
			log.debug("微店订单状态推送:"+msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.put("status", "success");
		return result;
	}
	
	/**
	 * 添加优惠券
	 * @param vendorId
	 * @param name
	 * @param reduce
	 * @param leastCost
	 * @param stock
	 * @param buyerLimit
	 * @param beginTime
	 * @param endTime
	 * @param openGet
	 * @param showFinish
	 * @return
	 */
	@RequestMapping("/add/wd/coupon.do")
	@ResponseBody
	public JSONObject addCoupon(
			Integer vendorId,
			String name,
			String reduce,
			String leastCost,
			Integer stock,
			Integer buyerLimit,
			String beginTime,
			String endTime,
			int openGet,
			int showFinish
			) {
		JSONObject result = new JSONObject();
		
		JSONObject param = new JSONObject();
		//优惠券信息
		param.put("title", name);
		param.put("leastCost", leastCost);
		param.put("openGet", openGet == 0 ? false : true);
		param.put("startTime", beginTime);
		param.put("endTime", endTime);
		param.put("stock", stock);
		param.put("buyerLimit", buyerLimit);
		param.put("reduce", reduce);
		param.put("showFinish", showFinish == 0 ? false : true);
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			return result;
		}
		
		//微店配置
		
		
		JSONObject _public = new JSONObject();
		_public.put("method", "create.shop.coupon");
		_public.put("access_token", wdConfig.getAccessToken());
		_public.put("version", "1.1");
		_public.put("format", "json");
		
		HttpRequestUtil util = new HttpRequestUtil();
		//调用微店创建优惠券接口
		String result1 = util.sendPost("https://api.vdian.com/api", "param="+param.toString()+"&public="+_public.toString());
		log.debug("result1:"+result1);
		//接口返回的JSON数据
		JSONObject object = JSONObject.parseObject(result1);
		
		String resultStr = object.get("result").toString();
		JSONObject resultJSON = JSONObject.parseObject(resultStr);
		
		String success = resultJSON.getString("success").toString();
		String couponId = resultJSON.getString("couponId").toString();
		
		if ("true".equals(success)) {
			//获取微店优惠券详细信息
			WdCoupon wdCoupon = WdUtil.getWdCoupon(wdConfig, couponId);
			wdCoupon.setCount(wdCoupon.getStock());//新建优惠券时将剩余优惠券数量的值设为 总量的值
			log.debug("---title:"+wdCoupon.getTitle());
			wdCoupon.setTitle(name);//微店返回的优惠券 名称 有可能是乱码的  这里我们使用 用户填写的优惠券名称即可
			
			int resultNum = wdCouponService.addCoupon(wdCoupon);
			if (resultNum > 0) {
				result.put("statuscode", 1);
				result.put("msg", "成功");
				return result;
			} 
			
		}
		
		result.put("statuscode", -2);
		result.put("msg", "新增失败");
		
		return result;
	}
	
	/**
	 * 获取厂商微店的所有优惠券 100张
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getWdCouponList.json")
    @ResponseBody
    public JSONObject getWdCouponList(Integer vendorId) {
		JSONObject result = new JSONObject();
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			return result;
		}
		
		return null;
	}
	
	/**
	 * 根据规则查询4张优惠券
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/wd/getCouponRule.do")
    @ResponseBody
    public JSONObject getCouponRule(Integer orderId,Integer vendorId) {
		JSONObject result = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray array = new JSONArray();

		String[] blacklistS=null;
		String[] whitelisttS=null;
		
		
		CouponRule resultRule = couponRuleService.getRuleInfoByOrderId(orderId,vendorId);
		if (resultRule == null) {
			result.put("statuscode", -2);
			result.put("msg", "未获取到相应的订单信息，请检查是否设置了优惠券规则");
			return result;
		}
		CouponRuleItem item = new CouponRuleItem();
		//黑名单
		if ("1".equals(resultRule.getIsBlackWhite())) {
			item.setType("1");
			item.setRuleId(resultRule.getId());
			
			List<CouponRuleItem> list = couponRuleService.getCouponRuleItemByRuleId(item);
			if (list != null && list.size() > 0) {
				blacklistS = new String[list.size()];
				for (int i=0 ; i<list.size() ; i++) {
					blacklistS[i] = list.get(i).getItemId()+"";
				}
			}
		//白名单
		} else if ("2".equals(resultRule.getIsBlackWhite())) {
			item.setType("2");
			item.setRuleId(resultRule.getId());
			
			List<CouponRuleItem> list = couponRuleService.getCouponRuleItemByRuleId(item);
			if (list != null && list.size() > 0) {
				whitelisttS = new String[list.size()];
				for (int i=0 ; i<list.size() ; i++) {
					whitelisttS[i] = list.get(i).getItemId()+"";
				}
			}
		//黑白名单
		} else if ("0".equals(resultRule.getIsBlackWhite())) {
			item.setType("1");
			item.setRuleId(resultRule.getId());
			//黑名单
			List<CouponRuleItem> list = couponRuleService.getCouponRuleItemByRuleId(item);
			if (list != null && list.size() > 0) {
				blacklistS = new String[list.size()];
				for (int i=0 ; i<list.size() ; i++) {
					blacklistS[i] = list.get(i).getItemId()+"";
				}
			}
			
			item.setType("2");
			item.setRuleId(resultRule.getId());
			//白名单
			List<CouponRuleItem> list1 = couponRuleService.getCouponRuleItemByRuleId(item);
			if (list1 != null && list1.size() > 0) {
				whitelisttS = new String[list.size()];
				for (int i=0 ; i<list.size() ; i++) {
					whitelisttS[i] = list1.get(i).getItemId()+"";
				}
			}
		}
		
        /*WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			return result;
		}*/
        
		String currTimeStr = sdf.format(new Date());
		//currTimeStr = "2017-06-25";
		
		List<Coupon> listWxDate = weChatCouponService.getWeChatCouponAll(vendorId,currTimeStr);
		List<Coupon> listWdDate = wdCouponService.getWdCouponAll(vendorId,currTimeStr);
		List<Coupon> listWdDateOther = couponRuleService.getDistinctCouponAllOther(vendorId,currTimeStr);
		List<Coupon> listWxDateOther = weChatCouponService.getCouponAllOther(vendorId,currTimeStr);
		List<Coupon> listWqDate = wqCouponService.getCouponAll(vendorId,currTimeStr);
		List<Coupon> listWqDateOther = wqCouponService.getCouponAllOther(vendorId,currTimeStr);
		
		if (listWxDateOther != null && listWxDateOther.size() > 0) {
			listWdDateOther.addAll(listWxDateOther);
		}
		if (listWqDateOther != null && listWqDateOther.size() > 0) {
			listWdDateOther.addAll(listWdDateOther);
		}
		if (listWdDateOther != null && listWdDateOther.size() > 0) {
			Collections.shuffle(listWdDateOther);
		}
		
		/*if (listWdDate == null || listWdDate.size() <= 0) {
			result.put("statuscode", -1);
			result.put("msg", "您没有优惠券，请添加优惠券。");
			return result;
		}*/
		
		/*List<Coupon> listDate = new ArrayList<Coupon>();
		if (listWdDate.size() > 0) {
			listDate.addAll(listWdDate);
		}*/
		
		Coupon coupon1 = null;
		
		listWdDate.addAll(listWxDate);
		listWdDate.addAll(listWqDate);
		Collections.shuffle(listWdDate);
		if (listWdDate != null && listWdDate.size() > 0) {
			coupon1 = listWdDate.get(0);
		}
		
		
		//缺少时间范围判断
		/*if (listWdDate != null && listWdDate.size() > 0) {
			coupon1 = listWdDate.get(0);
			if (listWxDate != null && listWxDate.size() > 0) {
				//Collections.shuffle(listWdDate);
				coupon1 = listWdDate.get(0);
			}
		}*/
		System.out.println("OOO"+coupon1);
		List<Coupon> list = RuleUtil.GetWxCoupnListByRule(coupon1,listWdDateOther,blacklistS,whitelisttS);
		for (Coupon coupon : list) {
			System.out.println("aaa:"+coupon);
		}
		for (Coupon coupon : list) {
			if (1 == coupon.getStyle()) {
				coupon = weChatCouponService.getCouponByCouponId(coupon.getId());
				
				/*WxConfigGzhVo wxConfig = wxConfigService.getWxGzhInfo(vendorId);*/
				WeChatCouponConfig wxConfig = weChatCouponConfigService.getWeChatCouponConfig(coupon.getVendorId());
				
				JSONObject object = new JSONObject();
				object.put("couponId", coupon.getId());
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
				object.put("appid", coupon.getAppid());
				object.put("secret", coupon.getSecret());
				object.put("productUrl",wxConfig.getProductUrl());
				/*object.put("oauthAddress", wxConfig.getOauthAddress());*/
				
				array.add(object);
			}
			if (2 == coupon.getStyle()) {
				coupon = wdCouponService.getCouponByCouponId(coupon.getCouponId());
				WdConfig wdConfig = wdConfigService.getWdConfig(coupon.getVendorId());
				
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
				object.put("productUrl", wdConfig.getProductUrl());
				object.put("wenUrl", wdConfig.getWenUrl());
				array.add(object);
			}
			if (4 == coupon.getStyle()) {
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
	 * 分页查询优惠券信息
	 * @param vendorId
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/GET/wdCoupon/couponInfoList.do")
	@ResponseBody
	public PageBean getWdCouponInfo(@RequestParam("vendorId") Integer vendorId,PageBean pageBean) {

		return wdCouponService.getWdCouponInfo(pageBean, vendorId);
	}
	
	/**
	 * 判断该厂商 是否有优惠券
	 * @return
	 */
	@RequestMapping("/GET/coupon/hasCoupon.do")
	@ResponseBody
	public JSONObject hasCoupon(Integer vendorId) {
		JSONObject result = new JSONObject();
		
		int resultNum = wdCouponService.hasCoupon(vendorId);
		if (resultNum > 0) {
			result.put("statuscode", 1);
			result.put("msg", "存在");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "不存在");
		}
		
		return result;
	}
	
	/**
	 * 删除优惠券
	 * @param id
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/DELETE/coupon/info.do")
	@ResponseBody
	public JSONObject deleteCoupon(String couponId,Integer vendorId) {
		JSONObject result = new JSONObject();
		
		JSONObject param = new JSONObject();
		param.put("couponId", couponId);
		
		WdConfig wdConfig = wdConfigService.getWdConfig(vendorId);
		if (wdConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "未获取到厂商的微店配置信息");
			return result;
		}
		
		JSONObject _public = new JSONObject();
		_public.put("method", "delete.coupon");
		_public.put("access_token", wdConfig.getAccessToken());
		_public.put("version", "1.0");
		_public.put("format", "json");
		
		HttpRequestUtil util = new HttpRequestUtil();
		String resusltStr = util.sendPost("https://api.vdian.com/api", "param="+param.toString()+"&public="+_public.toString());
		JSONObject resultJSON = JSONObject.parseObject(resusltStr);
		
		String statusStr = resultJSON.getString("status");
		String resultStr1 = resultJSON.getString("result");
		
		JSONObject statusJSON = JSONObject.parseObject(statusStr);
		String status_codeStr = statusJSON.getString("status_code");
		
		if ("0".equals(status_codeStr) && "true".equals(resultStr1)) {
			int temp = wdCouponService.deleteCoupon(Integer.parseInt(couponId));
			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "删除成功");
			} else {
				result.put("statuscode", -1);
				result.put("msg", "删除失败");
			}
		} else {
			result.put("statuscode", -2);
			result.put("msg", "无效的优惠券id");
		}
		return result;
	}
	
	/**
	 * 获取所有优惠券
	 * @return
	 */
	@RequestMapping("/GET/coupon/getcouponall.do")
	@ResponseBody
	public JSONObject getCouponAll(String vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Coupon> list = wdCouponService.getCouponAll(vendorId);
		for (Coupon coupon : list) {
			if (1 == coupon.getStyle()) {
				/*coupon = weChatCouponService.getCouponByCouponId(coupon.getId());*/
				
				/*WxConfigGzhVo wxConfig = wxConfigService.getWxGzhInfo(vendorId);*/
				WeChatCouponConfig wxConfig = weChatCouponConfigService.getWeChatCouponConfig(coupon.getVendorId());
				
				JSONObject object = new JSONObject();
				object.put("couponId", coupon.getId());
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
				object.put("appid", coupon.getAppid());
				object.put("secret", coupon.getSecret());
				object.put("productUrl",wxConfig.getProductUrl());
				
				array.add(object);
			}
			if (2 == coupon.getStyle()) {
				/*coupon = wdCouponService.getCouponByCouponId(coupon.getCouponId());*/
				WdConfig wdConfig = wdConfigService.getWdConfig(coupon.getVendorId());
				
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
				object.put("productUrl", wdConfig.getProductUrl());
				object.put("wenUrl", wdConfig.getWenUrl());
				
				array.add(object);
			}
		}
		
		result.put("data", array);
		
		return result;
	}

	
}
