package com.yunma.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.util.Radix;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yunma.entity.agent.AgentEmployee;
import com.yunma.entity.agent.AgentEntity;

import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.entity.tracing.ProductTracingCodeAgentScan;
import com.yunma.entity.tracing.TracingCodeCustomerScan;
import com.yunma.entity.tracing.VendorTracingStatistics;
import com.yunma.entity.vendorIntegral.VendorActivPlayer;
import com.yunma.entity.vendorIntegral.VendorIntegralRule;
import com.yunma.entity.weChat.WeChatUser;
import com.yunma.model.AntiFake;
import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.service.agent.AgentService;
import com.yunma.service.antiFakeService.AntiFakeService;
import com.yunma.service.product.ProductOrderService;

import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.service.tracing.ProductsTracingService;
import com.yunma.service.vendorIdIntegral.VendorIntegralService;
import com.yunma.service.weChatUser.WeChatService;
import com.yunma.utils.WeChatConfig;


@Controller
@RequestMapping(value = "/wx")
public class WechatController {
	
	@Resource
	private WeChatService weChatService;
	
	@Resource
	private ApiConfig apiConfig;
	@Resource
	private SecurityCodeService codeService;
	@Autowired
	private ProductOrderService orderService;	
	@Resource
	private AntiFakeService antiFakeService;

	@Autowired
	private ProductsTracingService tracingService;

	@Autowired
	private AgentService agentService;
	@Autowired
	private VendorIntegralService integralService;

	
	
	private Logger log = LoggerFactory.getLogger(WechatController.class);

	/**
	 * 静默授权回调
	 * 
	 * @param code
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/wxCallback.do")
	public ModelAndView wechatChatCallback(String code, String source)
			throws UnsupportedEncodingException {
		log.info("进入微信回调");

		OauthAPI oauth = new OauthAPI(apiConfig);

		OauthGetTokenResponse token = oauth.getToken(code);
		log.debug("-------------------->token:" + token);

		String openId = token.getOpenid();
		
		String nickNamePri = weChatService.getNickNameBy(openId);

		String appId = WeChatConfig.APP_ID;
		String http = WeChatConfig.WX_HTTp;
		String domainName = WeChatConfig.DOMAIN_NAME;
		String projectName = WeChatConfig.PROJECT_NAME;
		/**
		 * 如果nickName为空则判断用户为
		 */
		if (nickNamePri == null) {
			return new ModelAndView(
					"redirect:"
							+ http
							+ "?appid="
							+ appId
							+ "&redirect_uri=https%3A%2F%2F"
							+ domainName
							+ "%2F"
							+ projectName
							+ "%2Fwx%2FwxCallback1.do?source="
							+ source
							+ "&flag=1&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
		}

		// URLDecoder.decode(nickname, "utf-8"); 需要取出昵称时使用对称解码获取

		WeChatUser userInfo = new WeChatUser();
		userInfo = weChatService.getUserInfoByOpenId(openId);
		// 记录表中将用户的扫码信息存入扫码表中(记录重复扫码数据)
		String securityCode = source;
		int orderId = Radix.convert62To10(securityCode.substring(0, 4));
		int vendorId = Radix.convert62To10(securityCode.substring(4, 6));
		/* 追加已扫二维码扫描次数追加1 */
		// 查询当前扫码次数
		int securityScanCountPri = codeService.getScanCountpri(orderId,
				securityCode);
		// 追加扫码次数
		Integer codeScanCount = securityScanCountPri + 1;
		codeService.updateCodeScanCount(orderId, codeScanCount, securityCode);
		/* 追加扫描记录 */
		SecurityCode privateCode = codeService.getSecurityCode(securityCode);
		if (privateCode != null) {
			AntiFake antiFake = new AntiFake();
			antiFake.setCity(userInfo.getCity());
			antiFake.setProvince(userInfo.getProvince());
			antiFake.setSecurityCode(securityCode);
			antiFake.setSecurityCodeId(privateCode.getSecurityCodeId());
			antiFake.setOrderId(privateCode.getOrderId());
			antiFake.setScanTime(new Date());
			antiFake.setScanCount(1);
			antiFake.setOpenId(userInfo.getOpenId());
			antiFake.setProductName(privateCode.getProductName());
			antiFake.setProductId(privateCode.getProductId());
			antiFakeService.createOldAntiFake(vendorId, antiFake);

		} else {
			log.debug("储存扫码用户信息失败!");

		}
		Integer funcFlag = 1;

		String urlName = antiFakeService.getVendorUrl(orderId,funcFlag);
		return new ModelAndView("redirect:https://" + WeChatConfig.DOMAIN_NAME
				+ "/wx/" + urlName + "?openId=" + token.getOpenid()
				+ "&securityCode=" + source + "&orderId="
				+ privateCode.getOrderId() + "&securityCodeId="
				+ privateCode.getSecurityCodeId()+"&securityFlag=true");

	}

	/**
	 * 第一次扫码授权回调
	 * 
	 * @param code
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "/wxCallback1.do")
	public ModelAndView wechatChatCallbackGetUserInfo(String code, String source)
			throws UnsupportedEncodingException {
		log.info("进入微信回调");

		OauthAPI oauth = new OauthAPI(apiConfig);
		OauthGetTokenResponse token = oauth.getToken(code);
		GetUserInfoResponse userInfo = oauth.getUserInfo(
				token.getAccessToken(), token.getOpenid());
		String nickName = URLEncoder.encode(userInfo.getNickname(), "utf-8");
		String openId = userInfo.getOpenid();
		// 将第一次进入公众号的人的信息存入 微信用户表中
		int hasUser = weChatService.hasWeChatUserInfo(userInfo.getOpenid());
		if (hasUser <= 0) {
			WeChatUser user = new WeChatUser();
			user.setOpenId(userInfo.getOpenid());
			user.setNickName(nickName);
			user.setSex(userInfo.getSex());
			user.setCity(userInfo.getCity());
			user.setProvince(userInfo.getProvince());
			user.setHeadImgurl(userInfo.getHeadimgurl());
			user.setCreateDate(new Date());
			weChatService.addWeChatUserInfo(user);
		}

		// 将用户的扫码信息存入扫码表中
		String securityCode = source;
		int orderId = Radix.convert62To10(securityCode.substring(0, 4));
		int vendorId = Radix.convert62To10(securityCode.substring(4, 6));
		/* 追加已扫二维码扫描次数追加1 */
		// 查询当前扫码次数
		int securityScanCountPri = codeService.getScanCountpri(orderId,
				securityCode);
		// 追加扫码次数
		Integer codeScanCount = securityScanCountPri + 1;
		codeService.updateCodeScanCount(orderId, codeScanCount, securityCode);
		/* 追加扫描记录 */
		SecurityCode privateCode = codeService.getSecurityCode(securityCode);
		/**
		 * 追加积分系统新功能:
		 *  显示player积分--> player积分足够 --> 选择兑换物 --> 填写兑奖信息(实物需增加邮寄地址和电话) -->
		 *  player扣除对应积分 && 兑换记录保存信息 && 奖池减少兑奖物数量  --> 兑奖结束返回验证消息 --> 刷新积分数值和兑换记录}
		 */
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		/**
		 * 获取当前控制积分获取及兑换的规则
		 * 
		 */
		String tbName = orderService.findIfDbName("tb_vendor_integral_rule_"
				+ vendorId);
		if (tbName != null) {

			Integer productId = order.getProductId();
			int eveAddIntegralCount;
			VendorIntegralRule vendorIntegralRule = new VendorIntegralRule();// 获取规则

			vendorIntegralRule = integralService.findRuleByOrderId(vendorId,
					orderId);
			if (vendorIntegralRule == null) {
				vendorIntegralRule = integralService.findRuleByProductId(
						vendorId, productId);
				if (vendorIntegralRule == null) {
					vendorIntegralRule = integralService.findRuleByVendorId(
							vendorId, orderId);
					if (vendorIntegralRule != null) {

						eveAddIntegralCount = vendorIntegralRule
								.getEveGetExchangesCount();
						int integral = 0;
						int integralNow = 0;
						VendorActivPlayer vendorActivPlayer = new VendorActivPlayer();// 创建player,商家有积分活动为前提
						vendorActivPlayer = integralService.findPlayerByOpenId(
								vendorId, openId);
						if (vendorActivPlayer != null) {
							integralNow = eveAddIntegralCount + integral;
							integralService.updatePlayerIntegralByOpenId(
									vendorId, openId, integralNow);
						} else {
							integralNow = eveAddIntegralCount;
							vendorActivPlayer.setOpenId(openId);
							vendorActivPlayer.setNickName(nickName);
							vendorActivPlayer.setHeadImgurl(userInfo
									.getHeadimgurl());
							vendorActivPlayer.setVendorId(vendorId);
							vendorActivPlayer.setOrderId(orderId);
							vendorActivPlayer.setIntegral(integralNow);
							integralService.addPlayerInfo(vendorActivPlayer);
						}

					}
				}
			}

		}
		
		
		
		if (privateCode != null) {
			AntiFake antiFake = new AntiFake();
			antiFake.setCity(userInfo.getCity());
			antiFake.setProvince(userInfo.getProvince());
			antiFake.setSecurityCode(securityCode);
			antiFake.setSecurityCodeId(privateCode.getSecurityCodeId());
			antiFake.setOrderId(privateCode.getOrderId());
			antiFake.setScanTime(new Date());
			antiFake.setScanCount(1);
			antiFake.setOpenId(userInfo.getOpenid());
			antiFake.setProductName(privateCode.getProductName());
			antiFake.setProductId(privateCode.getProductId());
			antiFakeService.createOldAntiFake(vendorId, antiFake);

		} else {
			log.debug("储存扫码用户信息失败!");

		}
		Integer funcFlag = 1;
		String urlName = antiFakeService.getVendorUrl(orderId,funcFlag);
		return new ModelAndView("redirect:https://" + WeChatConfig.DOMAIN_NAME
				+ "/wx/" + urlName + "?openId=" + token.getOpenid()
				+ "&securityCode=" + source + "&orderId="
				+ privateCode.getOrderId() + "&securityCodeId="
				+ privateCode.getSecurityCodeId()+"&securityFlag=true");

	}
	
	
	/**
	 * 溯源码回调 Integer Flag:用于拓展回调而追加的参数
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/wxAntiFakeForTracingCode.do")
	public ModelAndView wxAntiFakeForTracingCode(String code, String source)
			throws UnsupportedEncodingException {
		log.info("进入微信回调");
		OauthAPI oauth = new OauthAPI(apiConfig);
		OauthGetTokenResponse token = oauth.getToken(code);
		GetUserInfoResponse userInfo = oauth.getUserInfo(
				token.getAccessToken(), token.getOpenid());// 通过fastweiAPI获取扫码用户信息,溯源时需要获取openId
		// String nickName = URLEncoder.encode(userInfo.getNickname(), "utf-8");
		String shortCode = source;
		// 通过二维码获取orderId
		Integer orderId = Radix.convert62To10(shortCode.substring(0, 4));
		Integer productId = Radix.convert62To10(shortCode.substring(6, 8));
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		Integer vendorId = order.getVendorId();
		/**
		 * 1.依据openId获取agentEmployee再获取agentId 1.1如果取不到值, ->1,普通的消费者
		 * ->2,商家未添加的代理或代理员工 统一当做普通消费者回调到消费者页面<消费者页面html>
		 * 
		 * 1.2获取到agentEmployee信息,那么会出现一下情况:
		 * 
		 * 
		 * 比较openId 如果是agent 表内的employee那么追加判断返回追加产品流通节点 存入ProductTracingCode表中
		 * 
		 * 如果是customer 则呈现产品当前的流向树形图 存入TracingCustomerScan表中
		 */

		String openId = userInfo.getOpenid();

		Integer codeType = 0;

		AgentEmployee agentEmployee = tracingService
				.findAgentEmployeeInfoByOpenId(openId, vendorId);

		/**
		 * 
		 * 
		 * 追加功能,设置严格溯源等级 tracingHigherDegreeControll = 1 时为当前溯源模式,
		 * tracingHigherDegreeControll = 2时为严格溯源防窜货模式
		 * 
		 */

		Integer tracingHigherDegreeControll = order
				.getTracingHigherDegreeControll();
		ProductTracingCodeAgentScan agentScan = new ProductTracingCodeAgentScan();

		if (tracingHigherDegreeControll == 1) {

			if (agentEmployee != null && shortCode.length() <= 13) {

				/**
				 * 长度为12的话是溯源码中的箱码 type = 1则为箱码 type = 2则为组码
				 * 
				 * 长度为13是溯源码中的条码
				 */
				Integer agentId = agentEmployee.getAgentId();
				AgentEntity agent = tracingService
						.findAgentInfoByAgentId(agentId);
				if (shortCode.length() == 12) {

					codeType = 1;
					BoxCode boxCode = tracingService
							.getTracingBoxCodeByBoxCode(orderId, shortCode);
					agentScan.setAgentId(agentId);
					agentScan.setAgentLv(agent.getAgentLevel());
					agentScan.setCountPro(boxCode.getBoxCount());
					agentScan.setAgentName(agent.getAgentName());
					agentScan.setCodeType(codeType);
					agentScan.setProductTracingCode(shortCode);
					agentScan.setOpenId(openId);
					agentScan.setProductId(boxCode.getProductId());
					agentScan.setProductName(boxCode.getProductName());
					agentScan.setProductTracingCodeId(boxCode.getBoxCodeId());
					agentScan.setVendorId(boxCode.getVendorId());
					tracingService.addAgentScanInfo(vendorId, agentScan);
				} else if (shortCode.length() == 13) {
					codeType = 2;
					/**
					 * 箱码扫码记录箱码和组码不在同一张表
					 */
					GroupCode groupCode = tracingService
							.getTracingGroupCodeByCode(orderId, shortCode);
					agentScan.setAgentId(agentId);
					agentScan.setAgentLv(agent.getAgentLevel());
					agentScan.setCountPro(groupCode.getBoxCount());
					agentScan.setAgentName(agent.getAgentName());
					agentScan.setCodeType(codeType);
					agentScan.setProductTracingCode(shortCode);
					agentScan.setOpenId(openId);
					agentScan.setProductId(groupCode.getProductId());
					agentScan.setProductName(groupCode.getProductName());
					agentScan.setProductTracingCodeId(groupCode
							.getGroupCodeId());
					agentScan.setVendorId(groupCode.getVendorId());
					agentScan.setCreateRowNum(groupCode.getGroupNum());
					tracingService.addAgentScanInfo(vendorId, agentScan);

				}

				/**
				 * 保存成功跳转代理扫码页面h5页面待编辑
				 */

				// suyuan.html跳转页面需要重新定义?

				return new ModelAndView("redirect:https://"
						+ WeChatConfig.DOMAIN_NAME + "/wx/" + "suyuan.html"
						+ "?openId=" + token.getOpenid() + "&shortCode="
						+ source + "&codeType=" + codeType
						+ "&securityFlag=false");

			}
			// 当agentEmployee不为空时同时二维码的长度小于等于13即箱码和条码针对代理
			// 扫描有用,消费者扫码只会显示基本信息,证明agent存在,将agent扫码信息记录下并跳转agent页面
			else if (agentEmployee == null && shortCode.length() <= 13) {

				return new ModelAndView("redirect:https://"
						+ WeChatConfig.DOMAIN_NAME + "/wx/"
						+ "errorsuyuan.html" + "?openId=" + token.getOpenid()
						+ "&shortCode=" + source + "&codeType=" + codeType
						+ "&securityFlag=false");
			} else if (shortCode.length() > 13) {

				// 当agentEmployee为空时统一当做customer扫码,存入消费者扫码信息表,并跳转customer页面
				codeType = 3;

				Integer funcFlag = 2;

				String urlName = antiFakeService
						.getVendorUrl(orderId, funcFlag);
				return new ModelAndView("redirect:https://"
						+ WeChatConfig.DOMAIN_NAME + "/wx/" + urlName
						+ "?openId=" + token.getOpenid() + "&shortCode="
						+ source + "&orderId=" + orderId + "&codeType="
						+ codeType + "&securityFlag=false");
			} else {
				return new ModelAndView(WeChatConfig.ERROR_HTML);
			}

		}
		// 严格溯源模式
		else {

			if (agentEmployee != null && shortCode.length() <= 13) {

				// 查询当前agent代理的等级
				Integer agentId = agentEmployee.getAgentId();

				AgentEntity agentPri = tracingService
						.findAgentInfoByAgentId(agentId);

				AgentEntity agent = tracingService
						.findAgentInfoByAgentId(agentId);

				int lvPri = agent.getAgentLevel();

				// 上次扫码等级
				ProductTracingCodeAgentScan extraAgentScan = tracingService
						.findExtraScanByShortCode(vendorId, shortCode);
				int lvOld = extraAgentScan.getAgentLv();
				if (lvPri > lvOld || extraAgentScan == null) {
					// 如果当期代理含有父级代理则需要再查询父级代理信息进行比较
					// 如果当前Fid为空则代理等级为顶级,
					if (agentPri.getAgentFid() == null) {

						/**
						 * 长度为12的话是溯源码中的箱码 type = 1则为箱码 type = 2则为组码
						 * 
						 * 长度为13是溯源码中的条码
						 */
						if (shortCode.length() == 12) {

							codeType = 1;
							BoxCode boxCode = tracingService
									.getTracingBoxCodeByBoxCode(orderId,
											shortCode);
							agentScan.setAgentId(agentId);
							agentScan.setAgentLv(agent.getAgentLevel());
							agentScan.setCountPro(boxCode.getBoxCount());
							agentScan.setAgentName(agent.getAgentName());
							agentScan.setCodeType(codeType);
							agentScan.setProductTracingCode(shortCode);
							agentScan.setOpenId(openId);
							agentScan.setProductId(boxCode.getProductId());
							agentScan.setProductName(boxCode.getProductName());
							agentScan.setProductTracingCodeId(boxCode
									.getBoxCodeId());
							agentScan.setVendorId(boxCode.getVendorId());
							tracingService
									.addAgentScanInfo(vendorId, agentScan);
						} else if (shortCode.length() == 13) {
							codeType = 2;
							/**
							 * 箱码扫码记录箱码和组码不在同一张表
							 */
							GroupCode groupCode = tracingService
									.getTracingGroupCodeByCode(orderId,
											shortCode);
							agentScan.setAgentId(agentId);
							agentScan.setAgentLv(agent.getAgentLevel());
							agentScan.setCountPro(groupCode.getBoxCount());
							agentScan.setAgentName(agent.getAgentName());
							agentScan.setCodeType(codeType);
							agentScan.setProductTracingCode(shortCode);
							agentScan.setOpenId(openId);
							agentScan.setProductId(groupCode.getProductId());
							agentScan
									.setProductName(groupCode.getProductName());
							agentScan.setProductTracingCodeId(groupCode
									.getGroupCodeId());
							agentScan.setVendorId(groupCode.getVendorId());
							agentScan.setCreateRowNum(groupCode.getGroupNum());
							tracingService
									.addAgentScanInfo(vendorId, agentScan);

						}

						/**
						 * 保存成功跳转代理扫码页面h5页面待编辑
						 */

						// suyuan.html跳转页面需要重新定义?

						return new ModelAndView("redirect:https://"
								+ WeChatConfig.DOMAIN_NAME + "/wx/"
								+ "suyuan.html" + "?openId="
								+ token.getOpenid() + "&shortCode=" + source
								+ "&codeType=" + codeType
								+ "&securityFlag=false");

					} else {
						// 不是顶级代理情况则需要判断是否窜货

						List<AgentEntity> agentList = agentService
								.getAllParentTreeNodeByChildForList(agentId);
						ProductTracingCodeAgentScan agentFirstScan = new ProductTracingCodeAgentScan();
						agentFirstScan = tracingService.getAgentFirstSanInfo(
								vendorId, shortCode);
						Integer agentIdOld = agentFirstScan.getAgentId();
						boolean flag = false;// 是否本分支代理
						if(agentList.size() >= 2 ){
							//去除第一节点(总节点,任何等级都是其子集,无法判断分支信息)
							for (int i = 0; i < agentList.size() - 1; i++) {
								
								if (agentList.get(i).getId() == agentIdOld) {
									
									flag = true;
								}
								
							}
							
						}
						if (flag == true || agentFirstScan == null) {

							if (shortCode.length() == 12) {

								codeType = 1;
								BoxCode boxCode = tracingService
										.getTracingBoxCodeByBoxCode(orderId,
												shortCode);
								agentScan.setAgentId(agentId);
								agentScan.setAgentLv(agent.getAgentLevel());
								agentScan.setCountPro(boxCode.getBoxCount());
								agentScan.setAgentName(agent.getAgentName());
								agentScan.setCodeType(codeType);
								agentScan.setProductTracingCode(shortCode);
								agentScan.setOpenId(openId);
								agentScan.setProductId(boxCode.getProductId());
								agentScan.setProductName(boxCode
										.getProductName());
								agentScan.setProductTracingCodeId(boxCode
										.getBoxCodeId());
								agentScan.setVendorId(boxCode.getVendorId());
								tracingService.addAgentScanInfo(vendorId,
										agentScan);
							} else if (shortCode.length() == 13) {
								codeType = 2;
								/**
								 * 箱码扫码记录箱码和组码不在同一张表
								 */
								GroupCode groupCode = tracingService
										.getTracingGroupCodeByCode(orderId,
												shortCode);
								agentScan.setAgentId(agentId);
								agentScan.setAgentLv(agent.getAgentLevel());
								agentScan.setCountPro(groupCode.getBoxCount());
								agentScan.setAgentName(agent.getAgentName());
								agentScan.setCodeType(codeType);
								agentScan.setProductTracingCode(shortCode);
								agentScan.setOpenId(openId);
								agentScan
										.setProductId(groupCode.getProductId());
								agentScan.setProductName(groupCode
										.getProductName());
								agentScan.setProductTracingCodeId(groupCode
										.getGroupCodeId());
								agentScan.setVendorId(groupCode.getVendorId());
								agentScan.setCreateRowNum(groupCode
										.getGroupNum());
								tracingService.addAgentScanInfo(vendorId,
										agentScan);

							}

							/**
							 * 保存成功跳转代理扫码页面h5页面待编辑
							 */

							// suyuan.html跳转页面需要重新定义?

							return new ModelAndView("redirect:https://"
									+ WeChatConfig.DOMAIN_NAME + "/wx/"
									+ "suyuan.html" + "?openId="
									+ token.getOpenid() + "&shortCode="
									+ source + "&codeType=" + codeType
									+ "&securityFlag=false");

						} 
						
						
						
						else {
							return new ModelAndView("redirect:https://"
									+ WeChatConfig.DOMAIN_NAME + "/wx/"
									+ "errordailitishi.html" + "?openId="
									+ token.getOpenid() + "&shortCode="
									+ source + "&codeType=" + codeType
									+ "&securityFlag=false");
						}

					}
				} else {

					codeType = 3;

					Integer funcFlag = 2;

					String urlName = antiFakeService.getVendorUrl(orderId,
							funcFlag);
					return new ModelAndView("redirect:https://"
							+ WeChatConfig.DOMAIN_NAME + "/wx/" + urlName
							+ "?openId=" + token.getOpenid() + "&shortCode="
							+ source + "&orderId=" + orderId + "&codeType="
							+ codeType + "&securityFlag=false");
				}

				/**
				 * 保存成功跳转代理扫码页面h5页面待编辑
				 */

				// suyuan.html跳转页面需要重新定义?

			} else {

				codeType = 3;

				Integer funcFlag = 2;

				String urlName = antiFakeService
						.getVendorUrl(orderId, funcFlag);
				return new ModelAndView("redirect:https://"
						+ WeChatConfig.DOMAIN_NAME + "/wx/" + urlName
						+ "?openId=" + token.getOpenid() + "&shortCode="
						+ source + "&orderId=" + orderId + "&codeType="
						+ codeType + "&securityFlag=false");

			}
		}

	}
	
	/**
	 * 获取微信的accessToken接口（用于测试 微信接口时需要）
	 */
	@RequestMapping("/getWeChatAccessToken.do")
	@ResponseBody
	public String getWeChatAccessToken() {
		String accessToken = apiConfig.getAccessToken();
		log.debug("accessToken:"+accessToken);
		return accessToken;
	}
	
	
}
