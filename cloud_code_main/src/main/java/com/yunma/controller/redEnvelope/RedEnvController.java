package com.yunma.controller.redEnvelope;

import java.math.BigDecimal;
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
import com.yunma.controller.product.ProductController;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.redEnvelope.RedEnv;
import com.yunma.entity.redEnvelope.RedEnvRule;
import com.yunma.entity.redEnvelope.RedEnvRuleArea;
import com.yunma.entity.redEnvelope.RedEnvelope;
import com.yunma.entity.vendor.VendorAccountPayDetail;
import com.yunma.entity.weChat.WeChatUserWallet;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.redEnvelope.RedEnvService;
import com.yunma.service.vendor.VendorService;
import com.yunma.service.weChatUser.WeChatService;
import com.yunma.utils.PageBean;

@Controller
public class RedEnvController {

	@Resource
	private RedEnvService redEnvService;
	
	@Resource
	private VendorService vendorService;
	
	@Resource
	private ProductOrderService productOrderService;
	
	@Resource
	private WeChatService weChatSerice;
	
	@Resource
	private WeChatCouponConfigService wechatCouponConfigService;
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	/**
	 * 创建红包规则
	 * @return
	 */
	@RequestMapping("/ADD/redEnv/rule.do")
	@ResponseBody
	public JSONObject addRedEnvRule(
			Integer vendorId,	//厂商id
			String ruleName,	//规则名
			Integer ruleLvel,	//红包等级/层数
			Double oneRate,		//一等奖中奖概率
			Double oneMinMoney,	//一等奖最小金额
			Double oneMaxMoney,	//一等奖最大金额
			Double twoRate,		//二等奖中奖概率
			Double twoMinMoney,	//二等奖最小金额
			Double twoMaxMoney,	//二等奖最大金额
			Double threeRate,	//三等奖中奖概率
			Double threeMinMoney,//三等奖最小金额
			Double threeMaxMoney,//三等奖最大金额
			Double fourRate,		//四等奖中奖概率
			Double fourMinMoney,	//四等奖最小金额
			Double fourMaxMoney,	//四等奖最大金额
			Double fiveRate,		//五等奖中奖概率
			Double fiveMinMoney,	//五等奖最小金额
			Double fiveMaxMoney,		//五等奖最大金额
			String rule_type,  //规则类型
			String send_name,  //发放者名称
			String wishing, //祝福语
			@RequestParam(value = "adcode[]", required = false)String [] adcode,
			@RequestParam(value = "province[]", required = false)String [] province,
			@RequestParam(value = "city[]", required = false)String [] city,
			@RequestParam(value = "district[]", required = false)String [] district
			) {
		
		JSONObject result = new JSONObject();
		
		int temp1 = redEnvService.hasRedEnvRuleName(vendorId, ruleName);
		if (temp1 > 0) {
			result.put("statuscode", -1);
			result.put("msg", "添加失败，请勿添加重复的规则名");
			return result;
		}
		
		RedEnvRule rule = new RedEnvRule();
		
		rule.setVendorId(vendorId);
		rule.setRuleName(ruleName);
		rule.setRuleLvel(ruleLvel);
		rule.setOneRate(oneRate);
		rule.setOneMinMoney(oneMinMoney);
		rule.setOneMaxMoney(oneMaxMoney);
		rule.setTwoRate(twoRate);
		rule.setTwoMinMoney(twoMinMoney);
		rule.setTwoMaxMoney(twoMaxMoney);
		rule.setThreeRate(threeRate);
		rule.setThreeMinMoney(threeMinMoney);
		rule.setThreeMaxMoney(threeMaxMoney);
		rule.setFourRate(fourRate);
		rule.setFourMinMoney(fourMinMoney);
		rule.setFourMaxMoney(fourMaxMoney);
		rule.setFiveRate(fiveRate);
		rule.setFiveMinMoney(fiveMinMoney);
		rule.setFiveMaxMoney(fiveMaxMoney);
		rule.setCreateTime(new Date());
		rule.setRule_type(rule_type);
		rule.setSend_name(send_name);
		rule.setWishing(wishing);
		
		int temp = redEnvService.addRedEnvRule(rule);
		if (temp > 0) {
			if (adcode != null && province != null && city != null && district != null) {
				for (int i=0 ; i<adcode.length ; i++) {
					RedEnvRuleArea area = new RedEnvRuleArea();
					area.setRuleId(rule.getId());
					area.setAdcode(adcode[i]);
					area.setProvince(province[i]);
					area.setCity(city[i]);
					area.setDistrict(district[i]);
					
					int temp2 = redEnvService.addRedEnvRuleArea(area);
				}
			}
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -2);
			result.put("msg", "新增失败");
		}
		return result;
	}
	
	/**
	 * 查询该厂商id下的所有红包规则
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/redEnv/list.do")
	@ResponseBody
	public JSONObject getRedEnvRuleByVendorId(Integer vendorId){
		JSONObject result = new JSONObject ();
		JSONArray array = new JSONArray();
		List<RedEnvRule> rule = redEnvService.getRedEnvRuleByVendorId(vendorId);
		for (RedEnvRule redEnvRule : rule) {
			JSONObject object = new JSONObject();
			object.put("id", redEnvRule.getId());
			object.put("ruleName", redEnvRule.getRuleName());
			object.put("ruleLvel", redEnvRule.getRuleLvel());
			object.put("oneRate", redEnvRule.getOneRate());
			object.put("oneMinMoney", redEnvRule.getOneMinMoney());
			object.put("oneMaxMoney", redEnvRule.getOneMaxMoney());
			object.put("twoRate", redEnvRule.getTwoRate());
			object.put("twoMinMoney", redEnvRule.getTwoMinMoney());
			object.put("twoMaxMoney", redEnvRule.getTwoMaxMoney());
			object.put("threeRate", redEnvRule.getThreeRate());
			object.put("threeMinMoney", redEnvRule.getThreeMinMoney());
			object.put("threeMaxMoney", redEnvRule.getThreeMaxMoney());
			object.put("fourRate", redEnvRule.getFourRate());
			object.put("fourMinMoney", redEnvRule.getFourMinMoney());
			object.put("fourMaxMoney", redEnvRule.getFourMaxMoney());
			object.put("fiveRate", redEnvRule.getFiveRate());
			object.put("fiveMinMoney", redEnvRule.getFiveMinMoney());
			object.put("fiveMaxMoney", redEnvRule.getFiveMaxMoney());
			object.put("createTime", redEnvRule.getCreateTime());
			object.put("rule_type", redEnvRule.getRule_type());
			object.put("send_name", redEnvRule.getSend_name());
			object.put("wishing", redEnvRule.getWishing());
			array.add(object);
		}
		result.put("data",array);
		
		return result;
	}
	
	
	/**
	 * 新增红包
	 * @param vendorId
	 * @param orderId
	 * @param ruleId
	 * @param money
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/ADD/redEnv/info.do")
	@ResponseBody
	public JSONObject addRedEnv(Integer vendorId, Integer orderId, Integer ruleId,
			Double money, String startTime, String endTime) {
		
		JSONObject result = new JSONObject();
		
		/*//查询厂商钱包余额
		Double balance = vendorService.getBalanceByVendorId(vendorId);
		if (balance < money) {
			result.put("statuscode", -1);
			result.put("msg", "您的账户余额不足，请充值！");
			return result;
		}*/
		
		RedEnv redEnv = new RedEnv();
		redEnv.setVendorId(vendorId);
		redEnv.setOrderId(orderId);
		redEnv.setRuleId(ruleId);
		redEnv.setMoney(money);
		redEnv.setStartTimeStr(startTime);
		redEnv.setEndTimeStr(endTime);
		
		/*BigDecimal moneyBig = new BigDecimal(money);
		BigDecimal balanceBig = new BigDecimal(balance);
		
		//计算余额
		Double finalMoney = balanceBig.subtract(moneyBig).doubleValue();*/
		
		VendorAccountPayDetail detail = new VendorAccountPayDetail();
		detail.setVendorId(vendorId);
		detail.setBizMoney(money);
		detail.setBizTime(new Date());
		//添加消费记录
		vendorService.addPayRecord(detail);
		
		/*VendorAccount account = new VendorAccount();
		account.setBalance(finalMoney);
		account.setVendorId(vendorId);
		
		//修改厂商余额
		vendorService.updateAccount(account);*/
		
		RedEnvRule rule = redEnvService.getRedEnvRuleById(ruleId);
		ProductOrder  order = productOrderService.findOrderByProductOrderId(orderId);
		int temp = redEnvService.addRedEnv(order,rule,redEnv,result,money);
		if (temp == 1) {
			productOrderService.updateRedEnvFlagById(orderId,1);
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else if (temp == 0) {
			result.put("statuscode", 0);
			result.put("msg", "异常错误！");
		} else if (temp == -1) {
			result.put("statuscode", -1);
			result.put("msg", "您选择的订单具有红包功能的二维码数量为零或规则中奖率设置不正确，请重新确认！");
		} else if (temp == -2) {
			result.put("statuscode", -2);
			result.put("msg", "您设置的金额太小，发放失败！");
		} else if (temp == -3) {
			result.put("statuscode", -3);
			result.put("msg", "您设置的金额太大，发放失败！");
		}
		return result;
	}
	
	
	/**
	 * 分页查询规则信息
	 * @param pageBean
	 * @param vendorId 厂商id
	 * @param keyword 查询关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@RequestMapping("/GET/redEnv/ruleInfo.do")
	@ResponseBody
	public PageBean getRedEnvRuleList(PageBean pageBean,Integer vendorId,String keyword,String startTime,String endTime) {
		return redEnvService.getRedEnvRuleList(pageBean,vendorId,keyword,startTime,endTime);
	}
	
	/**
	 * 分页查询红包信息
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/redEnv/info.do")
	@ResponseBody
	public PageBean getgetRedEnvList(PageBean pageBean,Integer vendorId) {
		return redEnvService.getgetRedEnvList(pageBean,vendorId);
	}
	
	/**
	 * 计算红包金额范围
	 * @param ruleId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/GET/redEnv/moneyScope.do")
	@ResponseBody
	public JSONObject getMoneyScope(Integer ruleId,Integer orderId) {
		JSONObject result = new JSONObject();
		return redEnvService.getMoneyScope(ruleId,orderId,result);
	}
	
	/**
	 * 打开红包
	 * @param redEnvId
	 * @param orderId
	 * @param openId 别人公众号的openId
	 * @param currAdcode 当前地理位置 的 区划编码
	 * @return
	 */
	@RequestMapping("/GET/redEnv/openRedEnv.do")
	@ResponseBody
	public JSONObject openEnv(Integer securityCodeId,Integer vendorId,Integer orderId,String openId,String currAdcode) {
		JSONObject result = new JSONObject();
		
		RedEnv redEnv1 = new RedEnv();
		redEnv1.setOrderId(orderId);
		redEnv1.setSecurityCodeId(securityCodeId);
		redEnv1.setVendorId(vendorId);
		
		//根据二维码id查询 红包的openId
		String dbopenId = null;
		try {
			dbopenId = redEnvService.getSecurityCodeByIdRedEnvId(redEnv1);
		} catch (Exception e) {
			log.debug("yunma.tb_anti_fake_"+vendorId+" 表不存在！！！");
			result.put("statuscode", -6);
			result.put("msg", "该订单未创建红包");
			return result;
		}
		
		log.debug("openId"+openId);
		log.debug("dbopenId"+openId);
		
		//判断 该二维码的openId 是否和 当前openId相等
		/*if (dbopenId.equals(openId)) {*/
			RedEnv redEnv = redEnvService.openEnv(securityCodeId,orderId);
			if (redEnv == null) {
				result.put("statuscode", -5);
				result.put("msg", "红包不存在");
				return result;
			}
			
			if (currAdcode != null && !currAdcode.equals("")) {
				List<RedEnvRuleArea>  list = redEnvService.getRedEnvRuleArea(orderId);
				boolean flag = false;
				if (list != null && list.size() > 0) {
					for (RedEnvRuleArea redEnvRuleArea : list) {
						String adcode = redEnvRuleArea.getAdcode();
						String provinceCode = adcode.substring(2,adcode.length());
						String cityCode = adcode.substring(4, adcode.length());
						
						String currProvinceCode = currAdcode.substring(0,2);
						String currCityCode = currAdcode.substring(0, 4);
						
						String provinceCode1 = adcode.substring(0,2);
						String cityCode1 = adcode.substring(0, 4);
						System.out.println("adcode:"+adcode);
						//全国
						if ("100000".equals(adcode)) {
							
						//省
						} else if ("0000".equals(provinceCode)) {
							if (currProvinceCode.equals(provinceCode1)) {
								flag = true;
								break;
							}
						//市
						} else if ("00".equals(cityCode)) {
							if (currCityCode.equals(cityCode1)) {
								flag = true;
								break;
							}
						//区县
						} else {
							if (currAdcode.equals(adcode)) {
								flag = true;
								break;
							}
						}
					}
					
					if (flag == false) {
						result.put("statuscode", -6);
						result.put("msg", "抱歉，您所在的城市暂未开通红包功能！");
						return result;
					}
				}
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Long currDate = new Date().getTime();
			
			//判断是否超出时间范围
			if (currDate < redEnv.getStartTime().getTime()) {
				result.put("statuscode", -2);
				result.put("msg", "还未到打开时间，打开时间为："+sdf.format(redEnv.getStartTime()));
				return result;
				
			} else if (currDate > redEnv.getEndTime().getTime()) {
				result.put("statuscode", -3);
				result.put("msg", "超出打开时间，最后打开时间为："+sdf.format(redEnv.getEndTime()));
				return result;
				
			} else {
				RedEnv redEnv2 = new RedEnv();
				redEnv2.setOrderId(orderId);
				redEnv2.setSecurityCodeId(securityCodeId);
				redEnv2.setState(1);
				redEnv2.setOpenTime(new Date());
				int temp = redEnvService.updateRedEnvById(redEnv2);
				
				if (temp < 0) {
					result.put("statuscode", -4);
					result.put("msg", "失败");
					return result;
				} else {
					result.put("money", redEnv.getMoney());
					result.put("statuscode", 1);
					result.put("msg", "成功");
					return result;
				}
				
				
			}
		/*} else {
				result.put("statuscode", -1);
				result.put("msg", "不能打开别人的红包");
				return result;
		}*/
		
	}
	
	public static void main(String[] args) {
		String a = "210010";
		a = a.substring(0, 4);
		System.out.println(a);
	}
	
	/**
	 * 领取红包
	 * @param redEnvId
	 * @param orderId
	 * @param openId 别人公众号的openId
	 * @return
	 */
	@RequestMapping("/GET/redEnv/getRedEnv.do")
	@ResponseBody
	public JSONObject getRedEnv(Integer securityCodeId,Integer orderId,String openId) {
		JSONObject result = new JSONObject();
		
		//获取红包信息
		RedEnv redEnv = redEnvService.openEnv(securityCodeId, orderId);
		if (redEnv != null) {
			RedEnv env = new RedEnv();
			env.setSecurityCodeId(securityCodeId);
			env.setOrderId(orderId);
			env.setVendorId(redEnv.getVendorId());
			
			//根据二维码id查询二维码的所属人
			/*String dbopenId = redEnvService.getSecurityCodeByIdRedEnvId(env);
			if (!openId.equals(dbopenId)) {
				result.put("statuscode", -1);
				result.put("msg", "不能打开别人的红包");
				return result;
			}*/
			
			if (redEnv.getStatus() == 1) {
				result.put("statuscode", -2);
				result.put("msg", "红包已领取，不能重复领取");
				return result;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Long currDate = new Date().getTime();
			
			//判断是否超出时间范围
			if (currDate < redEnv.getStartTime().getTime()) {
				result.put("statuscode", -3);
				result.put("msg", "还未到打开时间，打开时间为："+sdf.format(redEnv.getStartTime()));
				return result;
				
			} else if (currDate > redEnv.getEndTime().getTime()) {
				result.put("statuscode", -4);
				result.put("msg", "超出打开时间，最后打开时间为："+sdf.format(redEnv.getEndTime()));
				return result;
			}
			
			//查询钱包信息
			WeChatUserWallet wallet = weChatSerice.getWalletByOpenId(openId);
			
			BigDecimal money = new BigDecimal(Double.toString(redEnv.getMoney()));
			BigDecimal dbMoney = new BigDecimal(Double.toString(wallet.getMoney()));
			
			wallet.setMoney(money.add(dbMoney).doubleValue());
			wallet.setLastUpdateTime(new Date());
			
			//修改花费后的金额
			weChatSerice.updateWalletById(wallet);
			
			RedEnv red = new RedEnv();
			red.setSecurityCodeId(securityCodeId);
			red.setOrderId(orderId);
			red.setStatus(1);
			red.setReceiveTime(new Date());
			//修改红包领取状态
			redEnvService.updateRedEnvById(red);
			
			//红包总表信息
			RedEnvelope redEnvelope = redEnvService.getEnvelopeByOrderId(orderId);
			BigDecimal consumeMoney = new BigDecimal(redEnvelope.getConsumeMoney());
			
			Double finalConsume = consumeMoney.add(money).doubleValue();
			redEnvelope.setConsumeMoney(finalConsume);
			
			//修改总表中的消费金额
			redEnvService.updateEnvelope(redEnvelope);
			
			//添加红包领取记录
			redEnv.setOpenId(openId);
			redEnvService.addDrawRecord(redEnv);
			result.put("statuscode", 1);
			result.put("msg", " 成功");
		}
		
		return result;
	}
	
	/**
	 * 根据openId 查询对应的红包信息
	 * @param openId
	 * @return
	 */
	@RequestMapping("/GET/redEnv/wallet.do")
	@ResponseBody
	public JSONObject getWallet(String openId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject result = new JSONObject();
		
		WeChatUserWallet wallet = weChatSerice.getWalletByOpenId(openId);
		if (wallet != null) {
			result.put("id", wallet.getId());
			result.put("openId", wallet.getOpenId());
			result.put("money", wallet.getMoney());
			if (wallet.getLastUpdateTime() != null) {
				result.put("lastUpdateTime", sdf.format(wallet.getLastUpdateTime()));
			} 
		}
		return result;
	}
	
	/**
	 * 发送微信红包（真钱！！！） 提现
	 * @param openId 别人公众号的openId
	 * @param money 单位 分
	 * @param vendorId 厂商id
	 * @return
	 */
	@RequestMapping("/GET/redEnv/withdrawDeposit.do")
	@ResponseBody
	public JSONObject withdrawDeposit(String openId,Integer money,Integer vendorId) {
		JSONObject result = new JSONObject();
		
		if (money < 1) {
			result.put("statuscode", -4);
			result.put("msg", "提现金额必须大于1元");
			return result;
		}
		
		synchronized (openId) {
		
			WeChatUserWallet wallet = weChatSerice.getWalletByOpenId(openId);
			
			if (wallet == null) {
				result.put("statuscode", -1);
				result.put("msg", "用户不存在");
			}
			
			BigDecimal hundred = new BigDecimal(100);
			BigDecimal bigMoney = new BigDecimal(money.toString());
			BigDecimal dbMoney = new BigDecimal(Double.toString(wallet.getMoney())).multiply(hundred);
			
			if (dbMoney.compareTo(bigMoney) == -1) {
				result.put("statuscode", -2);
				result.put("msg", "余额不足");
				return result;
			}
			
			Double changeMoney = (dbMoney.subtract(bigMoney).divide(hundred)).doubleValue();
			
			WeChatUserWallet wallet1 = new WeChatUserWallet();
			wallet1.setOpenId(openId);
			wallet1.setMoney(changeMoney);
			wallet1.setLastUpdateTime(new Date());
			
			WeChatCouponConfig config = wechatCouponConfigService.getWeChatCouponConfig(vendorId);
			
			//添加体现记录
			int temp = weChatSerice.addWeChatUserRecord(wallet1);
			if (temp > 0) {
				if (weChatSerice.updateWalletById(wallet1) > 0) {
					
					//设置花费金额
					wallet1.setMoney(money.doubleValue());
					
					//调用微信接口 发送红包
					weChatSerice.sendRedEnv(config.getAppId(),config.getMchId(),config.getApiKey(),openId, money, 1,config.getCredentialsLocation());
					result.put("statuscode", 1);
					result.put("msg", "成功");
					return result;
				}
			}
			
			result.put("statuscode", -3);
			result.put("msg", "失败");
			return result;
		}
		
	}
	
	
}
