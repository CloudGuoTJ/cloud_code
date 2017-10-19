package com.yunma.service.vendorIdIntegral.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.vendorIntegral.VendorIntegralDao;
import com.yunma.entity.vendorIntegral.VendorActivPlayer;
import com.yunma.entity.vendorIntegral.VendorIntegralExchange;
import com.yunma.entity.vendorIntegral.VendorIntegralExchangeHistory;
import com.yunma.entity.vendorIntegral.VendorIntegralRule;
import com.yunma.service.vendorIdIntegral.VendorIntegralService;
import com.yunma.utils.PageBean;

@Service
public class VendorIntegralServiceImp extends BaseService implements
		VendorIntegralService {
	@Autowired
	private VendorIntegralDao integralDao;
	@Autowired
	private ProductOrderDao orderDao;

	@Override
	public JSONObject addAntiFakeIntegralRule(
			VendorIntegralRule vendorIntegralRule) {
		JSONObject result = new JSONObject();
		Integer eveAddIntegralCount = vendorIntegralRule
				.getEveAddIntegralCount();
		Integer eveGetExchangesCount = vendorIntegralRule
				.getEveGetExchangesCount();

		if (eveAddIntegralCount == null) {
			vendorIntegralRule.setEveAddIntegralCount(1);
		}
		if (eveGetExchangesCount == null) {
			vendorIntegralRule.setEveGetExchangesCount(0);// o代表无限
		}
		int resultCode = integralDao
				.addAntiFakeIntegralRule(vendorIntegralRule);

		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "新增失败请检查添加数据并重试!");

		}
		result.put("errorCode", 0);
		result.put("msg", "添加成功!");

		return result;
	}

	@Override
	public JSONObject playerGetExchangesAddInHistory(String openId,
			Integer vendorId, Integer orderId, Integer inteExchangeId,
			Integer ruleId) {
		JSONObject result = new JSONObject();
		// 获取对应 的兑奖信息,可能为空
		int integral;// 当前积分
		int exchangeCount;// 奖池兑奖物数量
		String exchangesName;
		String recipientName;
		String recipientPhone;
		Integer integralExchangeCostCount;
		String ruleName;
		String address;

		VendorActivPlayer player = integralDao.findPlayerByOpenId(vendorId,
				openId);// 获取player信息
		VendorIntegralExchange exchange = integralDao
				.findVendorIntegralExchangeByInteExchangeId(vendorId,
						inteExchangeId); // 获取兑奖物信息
		VendorIntegralExchangeHistory exchangeHistory = new VendorIntegralExchangeHistory();// 保存兑奖信息
		int integralCost = exchange.getIntegralExchangeCostCount();// 兑换需要的积分数
		int lastIntegral = player.getIntegral() - integralCost;// 剩余积分
		int lastExchangeCount = exchange.getExchangesCount() - 1;// 兑换数量可自行改变
		exchangesName = exchange.getExchangesName();
		recipientName = player.getPlayerName();
		recipientPhone = player.getRecipientPhone();
		integralExchangeCostCount = exchange.getIntegralExchangeCostCount();
		ruleName = player.getPlayerName();
		address = player.getAddress();

		integral = lastIntegral;
		// 更新积分和数量
		integralDao.updateIntegralByOpenId(openId, vendorId, integral);
		integralDao.updateExchangeCount(vendorId, lastExchangeCount,
				inteExchangeId);

		// 保存兑换记录
		exchangeHistory.setExchangesName(exchangesName);// 奖品名称

		exchangeHistory.setLastIntegral(lastIntegral);// 兑奖后的剩余积分积分

		exchangeHistory.setOpenId(openId);// 中奖微信用户ID与player表中对应

		exchangeHistory.setOrderId(orderId);// 订单ID

		exchangeHistory.setRecipientName(recipientName); // 中奖者姓名,真实信息.用于邮寄接收东西

		exchangeHistory.setRecipientPhone(recipientPhone);// 中奖者电话,真实信息.用于邮寄接收东西

		exchangeHistory.setVendorId(vendorId);// 商家id

		exchangeHistory.setEveCostIntegralCount(integralExchangeCostCount);// 兑换需要的积分

		exchangeHistory.setRuleId(ruleId);// 规则名称:指定兑换的规则名称,当前使用的规则Id

		exchangeHistory.setAddress(address);

		integralDao.addPlayerDrawAwardRecordInfo(exchangeHistory);

		return result;
	}

	@Override
	@Transactional
	public int createVendorIntegralActivPlayerDBTable(Integer vendorId) {
		int result = 0;
		// 创建积分相关的表,后缀 为vendorId
		String tableName = null;
		String tableNamePri = null;
		StringBuffer sqlTB = new StringBuffer();
		int lenght;
		int i = 0;
		tableName = "tb_vendor_activ_player_" + vendorId;
		// 判断表是否已存在
		tableNamePri = orderDao.selectDBTable(tableName);
		if (tableNamePri == null) {
			// lenght = sqlTB.length();
			// sqlTB.delete(0, lenght);//清空表,再添加数据
			sqlTB.append(" CREATE TABLE `tb_vendor_activ_player_");
			sqlTB.append(vendorId);
			sqlTB.append("`( ");
			sqlTB.append(" `playerId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',");
			sqlTB.append(" `vendorId` bigint(32) DEFAULT NULL COMMENT '厂商ID',");
			sqlTB.append(" `openId` varchar(64) NOT NULL COMMENT 'openID 微信id',");
			sqlTB.append(" `nickName` varchar(64) DEFAULT NULL COMMENT '微信昵称',");
			sqlTB.append(" `playerName` varchar(64) DEFAULT NULL COMMENT '用户领奖姓名',");
			sqlTB.append(" `recipientPhone` varchar(64) DEFAULT NULL COMMENT '用户领奖姓名',");
			sqlTB.append(" `address` varchar(256) DEFAULT NULL COMMENT '地址:用于领取实物奖品',");
			sqlTB.append(" `createDate` datetime NOT NULL COMMENT '创建时间',");
			sqlTB.append(" `orderId` int(64) DEFAULT NULL COMMENT '积分活动可以订单为单位,拓展项,以定订单做统计',");
			sqlTB.append(" `integral` int(11) NOT NULL DEFAULT '0' COMMENT '已获得的积分',");
			sqlTB.append(" `headImgurl` varchar(255) DEFAULT NULL COMMENT '微信头像URL',");
			sqlTB.append("  PRIMARY KEY (`playerId`),");
			sqlTB.append("  UNIQUE KEY `openId` (`openId`) ");
			sqlTB.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='积分活动参与者表';");
			String sql = sqlTB.toString();
			i += integralDao.createVendorIntegralActivPlayerDBTable(sql);
		}
		// 规则表创建

		tableName = "tb_vendor_integral_rule_" + vendorId;
		tableNamePri = orderDao.selectDBTable(tableName);
		if (tableNamePri == null) {
			lenght = sqlTB.length();
			sqlTB.delete(0, lenght);
			sqlTB.append(" CREATE TABLE `tb_vendor_integral_rule_");
			sqlTB.append(vendorId);
			sqlTB.append("`( ");
			sqlTB.append(" `inteRuleId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'规则Id',");
			sqlTB.append("  `ruleName` varchar(20) NOT NULL COMMENT '规则名称',");
			sqlTB.append(" `productId` bigint(20) DEFAULT NULL COMMENT '产品id',");
			sqlTB.append(" `productName` varchar(20) DEFAULT NULL COMMENT '产品名字',");
			sqlTB.append(" `vendorId` bigint(20) NOT NULL COMMENT '厂商id',");
			sqlTB.append(" `vendorName` varchar(20) NOT NULL COMMENT '厂商名字',");
			sqlTB.append(" `isUseing` tinyint(8) NOT NULL DEFAULT 1 COMMENT '是否启用规则,默认创建为未启用:1:未启用,2:启用',");
			sqlTB.append(" `orderId` bigint(20) DEFAULT NULL COMMENT '订单id,拓展值',");
			sqlTB.append(" `autoFlag` INT(11) NOT NULL DEFAULT 1  COMMENT '积分自动扣除和手动扣除,1,手动兑换扣除积分,2积分自动扣除(到指定积分自动兑换指定物品)',");
			sqlTB.append(" `ruleType` INT(11) NOT NULL DEFAULT 1 COMMENT '规则类型增加积分的方式,1.默认为扫码追加积分,2.厂家设置的方式',");
			sqlTB.append(" `eveGetExchangesCount` bigint(20) DEFAULT NULL COMMENT '每一个参与者能兑换对象数量',");
			sqlTB.append(" `createTime` DATETIME NOT NULL  COMMENT '创建时间',");
			sqlTB.append(" `updateTime` DATETIME NOT NULL  COMMENT '最新修改时间',");
			sqlTB.append(" `startTime` DATETIME NOT NULL  COMMENT '最新修改时间',");
			sqlTB.append(" `eveAddIntegralCount` int(20) NOT NULL DEFAULT 1 COMMENT'每一次积分追加值', ");
			sqlTB.append(" `integralActionScope` tinyint(8) not null DEFAULT 0 COMMENT'作用域:0,针对厂商下所有订单产品,1,指定订单产品',");
			sqlTB.append(" `expireTime`	DATETIME DEFAULT NULL COMMENT '过期时间',");
			sqlTB.append(" `explanation` text DEFAULT NULL COMMENT'积分说明' ,");
			sqlTB.append("  PRIMARY KEY (`inteRuleId`)");
			sqlTB.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='厂商积分规则表';");
			String sql = sqlTB.toString();
			i += integralDao.createVendorIntegralActivPlayerDBTable(sql);

		}

		// 创建积分兑奖 列表
		tableName = "tb_vendor_integral_exchanges_" + vendorId;
		tableNamePri = orderDao.selectDBTable(tableName);
		if (tableNamePri == null) {
			lenght = sqlTB.length();
			sqlTB.delete(0, lenght);
			sqlTB.append(" CREATE TABLE `tb_vendor_integral_exchanges_");
			sqlTB.append(vendorId);
			sqlTB.append("`( ");
			sqlTB.append(" `inteExchangeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'设置兑换对象Id',");
			sqlTB.append(" `exchangesName` varchar(20) NOT NULL COMMENT '兑换对象名称',");
			sqlTB.append(" `exchangesCount` bigint(20) DEFAULT NULL COMMENT '兑换对象数量',");
			sqlTB.append(" `integralExchangeCostCount` bigInt(20) DEFAULT NULL COMMENT '兑换所花费积分数量',");
			sqlTB.append(" `exchangesPicUrl` varchar(200) DEFAULT NULL COMMENT '如果有实际奖品的话需要传图片,奖品的图片位置',");
			sqlTB.append(" `vendorId` bigint(20) NOT NULL COMMENT '厂商Id',");
			sqlTB.append(" `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '使用状态,1:启用,2:停用',");
			sqlTB.append(" `exchangeType` tinyint(8) not NULL DEFAULT 1 COMMENT'兑换类型1,实物(包含红包在内),2,类似优惠券虚拟礼品,3,优质增值服务型,如会员奖励!(前提是商家有特定的会员系统,可进行兑奖)',");
			sqlTB.append(" `orderId` bigint(20) DEFAULT NULL COMMENT '订单id,拓展值,绑定订单时必填',");
			sqlTB.append(" `ruleId` INT(11) DEFAULT NULL  COMMENT '进行规则单个绑定时使用,不绑定为通用',");
			sqlTB.append(" `expireTime` datetime DEFAULT NULL  COMMENT '有效期:如果设置,超过有效期自动扣除',");
			sqlTB.append(" `createTime` timestamp NOT NULL COMMENT '创建时间',");
			sqlTB.append("  PRIMARY KEY (`inteExchangeId`)");
			sqlTB.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='厂商设定奖品列表';");
			String sql = sqlTB.toString();
			i += integralDao.createVendorIntegralActivPlayerDBTable(sql);
		}

		// 兑奖记录表
		tableName = "tb_vendor_integral_exchanges_history_" + vendorId;
		tableNamePri = orderDao.selectDBTable(tableName);
		if (tableNamePri == null) {
			lenght = sqlTB.length();
			sqlTB.delete(0, lenght);
			sqlTB.append(" CREATE TABLE `tb_vendor_integral_exchanges_history_");
			sqlTB.append(vendorId);
			sqlTB.append("`( ");
			sqlTB.append(" `hisId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '历史记录ID',");
			sqlTB.append(" `vendorId` bigint(20) NOT NULL COMMENT '厂商ID',");
			sqlTB.append(" `orderId` bigint(20) NOT NULL COMMENT '订单ID',");
			sqlTB.append(" `ruleId` int(20) DEFAULT NULL COMMENT '规则名称Id:控制当前兑换物的规则Id',");
			sqlTB.append(" `exchangesLv` bigint(20) DEFAULT NULL COMMENT '奖品等级',");
			sqlTB.append(" `exchangesName` varchar(64) DEFAULT NULL COMMENT '奖品名称',");
			sqlTB.append(" `wxUserUrl` varchar(255) DEFAULT NULL COMMENT '微信头像',");
			sqlTB.append(" `wxNickName` varchar(255) DEFAULT NULL COMMENT '微信昵称',");
			sqlTB.append(" `eveCostIntegralCount` varchar(32) NOT NULL COMMENT '单次兑换所花积分数量',");
			sqlTB.append(" `openId` varchar(64) NOT NULL COMMENT '中奖微信用户ID与player表中对应',");
			sqlTB.append(" `recipientName` varchar(20) DEFAULT '' COMMENT '中奖者姓名',");
			sqlTB.append(" `recipientPhone` varchar(11) DEFAULT '' COMMENT '中奖者电话',");
			sqlTB.append(" `reveiceTime` datetime DEFAULT NULL COMMENT '奖励领取时间',");
			sqlTB.append(" `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',");
			sqlTB.append(" `address` varchar(256) DEFAULT NULL COMMENT '地址:用于领取实物奖品从player表中取出',");
			sqlTB.append(" `comment` varchar(256) DEFAULT NULL COMMENT '备注',");
			sqlTB.append(" `getExchangesCount` int(11) DEFAULT NULL COMMENT '兑换次数',");
			sqlTB.append(" `lastIntegral` int(20) DEFAULT 0 COMMENT'兑奖后的剩余积分积分',");

			sqlTB.append("  PRIMARY KEY (`hisId`)");
			sqlTB.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动兑历史奖纪录表';");
			String sql = sqlTB.toString();
			i += integralDao.createVendorIntegralActivPlayerDBTable(sql);
		}
		result = i;
		return result;
	}

	@Override
	public int dropPlayerTableByVendorId(Integer vendorId) {
		int result = 0;
		String tableName = "yunma.tb_vendor_activ_player_" + vendorId;
		int i = integralDao.dropTableByTableName(tableName);
		if (i < 0) {
			result = -1;
		} else {
			result = 0;
		}
		return result;
	}

	@Override
	public int dropActivityByInteRuleId(Integer inteRuleId, Integer vendorId) {
		int result = 0;
		result = integralDao.deleteActivRuleByOrderId(vendorId, inteRuleId);
		return result;
	}

	@Override
	public PageBean getVendorIntegralRuleInfo(PageBean pageBean,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = integralDao.getVendorIntegralRulesCount(vendorId);
		VendorIntegralRule rule = new VendorIntegralRule();
		pageBean.setTotalRecords(temp);
		pageBean.setPageSize(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("VendorIntegralRule", rule);
		map.put("vendorId", vendorId);
		try {
			List<VendorIntegralRule> list = integralDao
					.getVendorIntegralRuleInfoList(map);
			if (list != null) {
				for (VendorIntegralRule priRule : list) {

					JSONObject object = new JSONObject();
					object.put("inteRuleId", priRule.getInteRuleId());
					object.put("ruleName", priRule.getRuleName());
					object.put("vendorId", priRule.getVendorId());
					object.put("productId", priRule.getProductId());
					object.put("productName", priRule.getProductName());
					object.put("ruleType", priRule.getRuleType());
					object.put("eveGetExchangesCount",
							priRule.getEveGetExchangesCount());
					object.put("createTime", priRule.getCreateTime());
					object.put("expireTime", priRule.getExpireTime());
					object.put("explanation", priRule.getExplanation());
					object.put("autoFlag", priRule.getAutoFlag());
					object.put("isUseing", priRule.getIsUseing());
					object.put("eveAddIntegralCount",
							priRule.getEveAddIntegralCount());

					object.put("startTime", priRule.getStartTime());

					array.add(object);
				}
			} else {
				result.put("error:", -1);
				result.put("msg", "还未建立积分获取规则!");
			}
		} catch (Exception e) {
			logger.error("error: " + e);

		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public PageBean getVendorIntegralPlayerInfo(PageBean pageBean,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int temp = integralDao.getIntegralPlayerCount(vendorId);
		VendorActivPlayer player = new VendorActivPlayer();
		pageBean.setTotalRecords(temp);
		pageBean.setPageSize(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("VendorActivPlayer", player);
		map.put("vendorId", vendorId);
		try {
			List<VendorActivPlayer> list = integralDao
					.getVendorActivPlayerInfoList(map);
			if (list != null) {
				for (VendorActivPlayer priPlayer : list) {

					JSONObject object = new JSONObject();
					object.put("playerId", priPlayer.getPlayerId());
					object.put("vendorId", priPlayer.getVendorId());
					object.put("openId", priPlayer.getOpenId());
					object.put("address", priPlayer.getAddress());
					object.put("createDate", priPlayer.getCreateDate());
					object.put("orderId", priPlayer.getOrderId());
					object.put("integral", priPlayer.getIntegral());
					object.put("headImgurl", priPlayer.getHeadImgurl());
					object.put("nickName", priPlayer.getNickName());
					array.add(object);
				}
			} else {
				result.put("error:", -1);
				result.put("msg", "还没有参与者!");
			}
		} catch (Exception e) {
			logger.error("error: " + e);

		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;

	}

	@Override
	public PageBean getExchangesInfoList(PageBean pageBean, Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int temp = integralDao.getVendorActivExchangesCount(vendorId);
		VendorIntegralExchange exchanges = new VendorIntegralExchange();
		pageBean.setTotalRecords(temp);
		pageBean.setPageSize(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("vendorIntegralExchange", exchanges);
		map.put("vendorId", vendorId);
		try {
			List<VendorIntegralExchange> list = integralDao
					.getVendorIntegralExchangeInfoList(map);
			if (list != null) {
				for (VendorIntegralExchange exchangesPri : list) {

					JSONObject object = new JSONObject();
					object.put("inteExchangeId",
							exchangesPri.getInteExchangeId());
					object.put("exchangesName", exchangesPri.getExchangesName());
					object.put("exchangesCount",
							exchangesPri.getExchangesCount());
					object.put("integralExchangeCostCount",
							exchangesPri.getIntegralExchangeCostCount());
					object.put("exchangesPicUrl",
							exchangesPri.getExchangesPicUrl());
					object.put("vendorId", exchangesPri.getVendorId());
					object.put("exchangeType", exchangesPri.getExchangeType());
					object.put("orderId", exchangesPri.getOrderId());
					object.put("ruleId", exchangesPri.getRuleId());
					object.put("createTime", exchangesPri.getCreateTime());
					object.put("expireTime", exchangesPri.getExpireTime());
					object.put("status", exchangesPri.getStatus());
					object.put("status", exchangesPri.getStatus());

					array.add(object);
				}
			} else {
				result.put("error:", -1);
				result.put("msg", "还没有参与者!");
			}
		} catch (Exception e) {
			logger.error("error: " + e);

		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public int updatePlayerIntegralByOpenId(Integer vendorId, String openId,
			int integral) {
		int i = 0;
		i += integralDao.updatePlayerIntegralByOpenId(vendorId, openId,
				integral);
		return i;
	}

	@Override
	public int addExchangeHistoryInfoList(
			VendorIntegralExchangeHistory vendorIntegralExchangeHistory) {
		int i = 0;
		i = integralDao
				.updateVendorIntegralExchangeHistory(vendorIntegralExchangeHistory);
		return i;
	}

	@Override
	public int updateVendorIntegeralRule(VendorIntegralRule vendorIntegralRule) {
		int i = 0;
		i += integralDao.updateAntiFakeIntegralRule(vendorIntegralRule);
		return i;
	}

	@Override
	public VendorIntegralRule getVendorIntegralRuleByInteRuleId(
			Integer vendorId, Integer inteRuleId) {

		return integralDao.findVendorIntegralRuleByInteRuleId(vendorId,
				inteRuleId);
	}

	@Override
	public JSONObject updateVendorIntegralRuleStatus(Integer vendorId,
			Integer inteRuleId) {

		JSONObject result = new JSONObject();
		// 已在列表显示的规则不用进行非空判断
		VendorIntegralRule rule = integralDao
				.findVendorIntegralRuleByInteRuleId(vendorId, inteRuleId);
		// 判断当前状态
		int isUseing = rule.getIsUseing();
		int useingCode = 0;
		// 当为1时为关闭状态,则开启状态
		if (isUseing == 1) {
			useingCode = 2;
			integralDao.updateVendorIntegralRuleByInteRuleId(vendorId,
					inteRuleId, useingCode);
			result.put("errorCode", 0);
			result.put("msg", "已启用规则!");

		} else if (isUseing == 2) {
			useingCode = 1;
			// 已开启状态
			integralDao.updateVendorIntegralRuleByInteRuleId(vendorId,
					inteRuleId, useingCode);
			result.put("errorCode", 1);
			result.put("msg", "已禁用规则!");
		}

		return result;
	}

	@Override
	public JSONObject vendorIntegralPlayerGainIntegral(Integer vendorId,
			String openId) {
		JSONObject result = new JSONObject();
		VendorActivPlayer player = integralDao.findPlayerByOpenId(vendorId,
				openId);
		Integer privateIntegral = player.getIntegral();// 获取当前积分
		VendorIntegralRule rule = integralDao
				.findVendorIntegralRuleInfoByVendorId(vendorId);// 获取当前使用的规则
																// ,全局规则
		Integer addIntegral = rule.getEveAddIntegralCount();
		Integer integral = privateIntegral + addIntegral;
		// 更新积分
		int i = integralDao.updatePlayerIntegralByOpenId(vendorId, openId,
				integral);
		if (i < 0) {
			result.put("msg", "更新失败!");
			result.put("errorCode", -1);
			return result;
		}

		result.put("msg", "更新成功!");
		result.put("errorCode", i);

		return result;
	}

	@Override
	public VendorIntegralRule findRuleByOrderId(int vendorId, int orderId) {

		return integralDao.findVendorIntegralRuleInfoByOrderId(orderId,
				vendorId);
	}

	@Override
	public VendorIntegralRule findRuleByProductId(int vendorId,
			Integer productId) {

		return integralDao.findVendorIntegralRuleInfoByProductId(productId,
				vendorId);
	}

	@Override
	public VendorIntegralRule findRuleByVendorId(int vendorId, int orderId) {

		return integralDao.findVendorIntegralRuleInfoByVendorId(vendorId);
	}

	@Override
	public PageBean getExchangeHistoryInfoList(PageBean pageBean,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int temp = integralDao.getHistoryCountByVendorId(vendorId);
		VendorIntegralExchangeHistory history = new VendorIntegralExchangeHistory();
		pageBean.setTotalRecords(temp);
		pageBean.setPageSize(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("VendorIntegralExchangeHistory", history);
		map.put("vendorId", vendorId);
		try {
			List<VendorIntegralExchangeHistory> list = integralDao
					.getVendorIntegralActivPlayerDrawAwardRecordInfoList(map);
			if (list != null) {
				for (VendorIntegralExchangeHistory historyPri : list) {

					JSONObject object = new JSONObject();
					object.put("hisId", historyPri.getHisId());
					object.put("exchangesName", historyPri.getExchangesName());
					object.put("wxUserUrl", historyPri.getWxUserUrl());
					object.put("reveiceTime", historyPri.getReveiceTime());
					object.put("vendorId", historyPri.getVendorId());
					object.put("comment", historyPri.getComment());
					object.put("orderId", historyPri.getOrderId());
					object.put("ruleId", historyPri.getRuleId());
					object.put("createTime", historyPri.getCreateTime());
					object.put("expireTime", historyPri.getExpireTime());
					object.put("wxNickName", historyPri.getWxNickName());

					array.add(object);
				}
			} else {
				result.put("error:", -1);
				result.put("msg", "还没有历史记录!");
			}
		} catch (Exception e) {
			logger.error("error: " + e);

		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public JSONObject addVendorIntegralExchange(
			VendorIntegralExchange vendorIntegralExchange) {
		JSONObject result = new JSONObject();
		int resultCode = integralDao
				.addVendorExchangesRule(vendorIntegralExchange);
		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "新增失败请检查添加数据并重试!");

		}
		result.put("errorCode", 0);
		result.put("msg", "添加成功!");

		return result;

	}

	@Override
	public JSONObject updateVendorIntegralExchange(
			VendorIntegralExchange vendorIntegralExchange,
			Integer inteExchangeId) {
		JSONObject result = new JSONObject();
		int resultCode = integralDao.updateHoleVendorExchangesRule(
				vendorIntegralExchange, inteExchangeId);
		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "更新失败请检查添加数据并重试!");

		}
		result.put("errorCode", 0);
		result.put("msg", "更新成功!");

		return result;
	}

	@Override
	public JSONObject deleteVendorIntegralExchange(Integer inteExchangeId,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		int resultCode = integralDao.deleteVendorExchangesRule(vendorId,
				inteExchangeId);
		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "删除 失败,数据异常!");

		}
		result.put("errorCode", 0);
		result.put("msg", "删除成功!");

		return result;
	}

	@Override
	public JSONObject deleteVendorIntegralRule(Integer inteRuleId,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		int resultCode = integralDao.deleteVendorIntegralRule(vendorId,
				inteRuleId);
		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "删除 失败,数据异常!");

		}
		result.put("errorCode", 0);
		result.put("msg", "删除成功!");

		return result;
	}

	@Override
	public JSONObject findRuleByInteRuleId(Integer inteRuleId, Integer vendorId) {
		JSONObject result = new JSONObject();
		VendorIntegralRule rule = integralDao.findRuleByInteRuleId(vendorId,
				inteRuleId);
		if (rule != null) {

			result.put("inteRuleId", rule.getInteRuleId());
			result.put("ruleName", rule.getRuleName());
			result.put("vendorId", rule.getVendorId());
			result.put("productId", rule.getProductId());
			result.put("productName", rule.getProductName());
			result.put("ruleType", rule.getRuleType());
			result.put("eveGetExchangesCount", rule.getEveGetExchangesCount());
			result.put("createTime", rule.getCreateTime());
			result.put("expireTime", rule.getExpireTime());
			result.put("explanation", rule.getExplanation());
			result.put("autoFlag", rule.getAutoFlag());
			result.put("isUseing", rule.getIsUseing());
			result.put("eveAddIntegralCount", rule.getEveAddIntegralCount());

			result.put("startTime", rule.getStartTime());
		}
		return result;
	}

	@Override
	public VendorActivPlayer findPlayerByOpenId(int vendorId, String openId) {

		return integralDao.findPlayerByOpenId(vendorId, openId);
	}

	@Override
	public int addPlayerInfo(VendorActivPlayer vendorActivPlayer) {

		return integralDao.saveVendorIntegralActivPlayerInfo(vendorActivPlayer);
	}

	@Override
	public JSONObject findPlayerByPlayerId(Integer playerId, Integer vendorId) {
		JSONObject result = new JSONObject();

		VendorActivPlayer player = integralDao.findPlayerByPlayerId(vendorId,
				playerId);
		if (player != null) {

			result.put("playerId", player.getPlayerId());
			result.put("vendorId", player.getVendorId());
			result.put("openId", player.getOpenId());
			result.put("nickName", player.getNickName());
			result.put("address", player.getAddress());
			result.put("playerName", player.getPlayerName());
			result.put("recipientPhone", player.getRecipientPhone());
			result.put("createTime", player.getCreateDate());
			result.put("orderId", player.getOrderId());
			result.put("integral", player.getIntegral());
			result.put("headImgurl", player.getHeadImgurl());

		}

		return result;
	}

	@Override
	public JSONObject getVendorSingleIntegralRule(Integer inteExchangeId,
			Integer vendorId) {
		JSONObject result = new JSONObject();

		VendorIntegralExchange exchange = integralDao
				.findVendorIntegralExchangeByExchangeId(inteExchangeId,
						vendorId);
		if (exchange != null) {

			result.put("inteExchangeId", exchange.getInteExchangeId());
			result.put("exchangesName", exchange.getExchangesName());
			result.put("exchangesCount", exchange.getExchangesCount());
			result.put("integralExchangeCostCount",
					exchange.getIntegralExchangeCostCount());
			result.put("exchangesPicUrl", exchange.getExchangesPicUrl());
			result.put("vendorId", exchange.getVendorId());
			result.put("exchangeType", exchange.getExchangeType());
			result.put("orderId", exchange.getOrderId());
			result.put("ruleId", exchange.getRuleId());
			result.put("createTime", exchange.getCreateTime());
			result.put("expireTime", exchange.getExpireTime());
			result.put("status", exchange.getStatus());
			result.put("status", exchange.getStatus());
		}

		return result;
	}

	@Override
	public JSONObject updateVendorIntegralPlayerAddConmment(Integer hisId,
			Integer vendorId, String conmment) {
		JSONObject result = new JSONObject();
		int resultCode = integralDao.updateVendorIntegralPlayerAddConmment(
				hisId,vendorId, conmment);
		if (resultCode < 0) {
			result.put("errorCode", -1);
			result.put("msg", "更新失败请检查添加数据并重试!");

		}
		result.put("errorCode", 0);
		result.put("msg", "更新成功!");

		return result;
	}

}
