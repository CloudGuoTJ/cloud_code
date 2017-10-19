package com.yunma.dao.vendorIntegral;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.vendorIntegral.VendorActivPlayer;
import com.yunma.entity.vendorIntegral.VendorIntegralExchange;
import com.yunma.entity.vendorIntegral.VendorIntegralExchangeHistory;
import com.yunma.entity.vendorIntegral.VendorIntegralRule;

/**
 * 积分系统功能dao层
 * 
 * @author cloud
 * 
 */
public interface VendorIntegralDao extends BaseMapper {
	/**
	 * 添加规则信息
	 * 
	 * @param vendorIntegralRule
	 * @return
	 */
	@Insert("INSERT INTO  yunma.tb_vendor_integral_rule_#{vendorId}  "
			+ "SET "
			+ " ruleName=#{ruleName},productId=#{productId},  productName=#{productName}, "
			+ " vendorId=#{vendorId},vendorName=#{vendorName},isUseing=1,orderId=#{orderId}, "
			+ "updateTime=now(),autoFlag=#{autoFlag},eveGetExchangesCount=#{eveGetExchangesCount},createTime=now(),"
			+ " integralActionScope=#{integralActionScope},explanation=#{explanation},"
			+ "eveAddIntegralCount=#{eveAddIntegralCount},startTime=#{startTime},expireTime=#{expireTime};")
	int addAntiFakeIntegralRule(VendorIntegralRule vendorIntegralRule);

	/**
	 * 修改规则信息
	 * 
	 * @param vendorIntegralRule
	 * @return
	 */
	@Insert("UPDATE "
			+ " yunma.tb_vendor_integral_rule_#{vendorId} "
			+ " SET "
			+ "  ruleName=#{ruleName},  productId=#{productId},  productName=#{productName},"
			+ "  vendorId=#vendorId},  vendorName=#{vendorName},"
			+ "  orderId=#{orderId},updateTime=now(),autoFlag=#{autoFlag},"
			+ " eveGetExchangesCount=#{eveGetExchangesCount},updateTime=now(),"
			+ " integralActionScope=#{integralActionScope},explanation=#{explanation}, "
			+ " eveAddIntegralCount#{eveAddIntegralCount},startTime=#{startTime},expireTime=#{expireTime} "
			+ "WHERE inteRuleId=#{inteRuleId};")
	int updateAntiFakeIntegralRule(VendorIntegralRule vendorIntegralRule);

	/**
	 * 查询对应订单的规则
	 * 
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE inteRuleId=#{inteRuleId} ")
	VendorIntegralRule findVendorIntegralRuleByInteRuleId(@Param("vendorId")Integer vendorId,
			@Param("inteRuleId")Integer inteRuleId);

	/**
	 * 添加兑奖信息 兑奖信息表
	 * 
	 * @param vendorIntegralExchangeHistory
	 * @return
	 * 
	 * 
	 * 
	 */

	@Insert("INSERT INTO yunma.tb_vendor_integral_exchanges_history_#{vendorId} "
			+ " SET "
			+ " vendorId=#{vendorId}, orderId=#{orderId}, ruleId=#{ruleId},"
			+ " exchangesLv=#{exchangesLv},eveCostIntegralCount=#{eveCostIntegralCount},"
			+ " exchangesName=#{exchangesName}, exchangesUrl=#{exchangesUrl},"
			+ " drawCostIntegralCount=#{drawCostIntegralCount}, openId=#{openId}, "
			+ "recipientName=#{recipientName}, recipientPhone=#{recipientPhone},"
			+ " reveiceTime=now(), createTime=now(), address=#{address},"
			+ "getExchangesCount=#{getExchangesCount},lastIntegral=#{lastIntegral};")
	VendorIntegralExchangeHistory addPlayerDrawAwardRecordInfo(
			VendorIntegralExchangeHistory vendorIntegralExchangeHistory);

	/**
	 * 添加参与者信息
	 * 
	 * @param vendorActivPlayer
	 * @return
	 */
	@Insert("INSERT INTO yunma.tb_vendor_activ_player_#{vendorId}"
			+ " SET  vendorId=#{vendorId},openId=#{openId},createDate=now(),orderId=#{orderId},integral=#{integral},headImgurl=#{headImgurl}; ")
	int saveVendorIntegralActivPlayerInfo(VendorActivPlayer vendorActivPlayer);

	/**
	 * 
	 * 查询对应订单下的规则 信息
	 * 
	 */

	@Select("SLECET * FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE orderId=#{orderId} AND isUseing=2 ;")
	VendorIntegralRule findVendorIntegralRuleInfoByOrderId(
			@Param("orderId") Integer orderId,
			@Param("vendorId") Integer vendorId);

	/**
	 * 查询对应productId下的规则 信息
	 * 
	 */

	@Select("SLECET * FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE productId=#{productId} AND isUseing=2 ;")
	VendorIntegralRule findVendorIntegralRuleInfoByProductId(
			@Param("productId") Integer productId,
			@Param("vendorId") Integer vendorId);

	/**
	 * 查询对应productId下的规则 信息
	 * 
	 */

	@Select("SLECET * FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE integralActionScope=0 AND isUseing=2 limit 1;")
	VendorIntegralRule findVendorIntegralRuleInfoByVendorId(
			@Param("vendorId") Integer vendorId);

	/**
	 * 查询参与者信息
	 * 
	 * @param openId
	 * @param orderId
	 * @return
	 */
	@Select("SELECT * FROM TABLE yunma.tb_vendor_activ_player_#{vendorId} WHERE openId=#{openId}")
	VendorActivPlayer findVendorIntegralActivPlayerInfoByOpenId(
			@Param("openId") String openId, @Param("orderId") Integer orderId);

	/**
	 * 更新player表中的积分数
	 * 
	 * @param openId
	 * @param orderId
	 * @param integral
	 * @return
	 */
	@Update("UPDATE yunma.tb_vendor_activ_player_#{vendorId} SET integral=#{integral} WHERE openId=#{openId}")
	Integer updateIntegralByOpenId(@Param("openId") String openId,
			@Param("vendorId") Integer vendorId,
			@Param("integral") Integer integral);

	/**
	 * 删除订单时删除对应的参与者表
	 * 
	 * @param orderId
	 * @return
	 */
	@Update("DROP TABLE IF EXISTS  #{tableName};")
	public int dropTableByTableName(@Param("tableName") String tableName);

	/**
	 * 
	 * 创建参与活动者表
	 * 
	 * @param sql
	 * @return
	 */
	@Update("${sql}")
	int createVendorIntegralActivPlayerDBTable(@Param("sql") String sql);

	/***
	 * 删除对应的活动
	 * 
	 * @Param orderId
	 */
	@Delete("DELETE FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE inteRuleId=#{inteRuleId};")
	int deleteActivRuleByOrderId(@Param("vendorId") Integer vendorId,
			@Param("inteRuleId") Integer inteRuleId);

	/**
	 * 更新积分信息
	 * 
	 */
	@Update("UPDATE yunma.tb_vendor_activ_player_#{vendorId} SET integral=#{integral} WHERE openId=#{openId};")
	int updatePlayerIntegralByOpenId(Integer vendorId, String openId,
			int integral);

	/**
	 * 分页显示规则 VendorIntegralRule 获取count
	 */
	@Select("SELECT COUNT(*) FROM yunma.tb_vendor_integral_rule_#{vendorId} ;")
	int getVendorIntegralRulesCount(Integer vendorId);

	/**
	 * 分页显示规则 VendorIntegralRule 获取详细信息
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_rule_#{vendorId} ORDER BY createTime desc;")
	List<VendorIntegralRule> getVendorIntegralRuleInfoList(
			Map<String, Object> map);

	/***
	 * 分页显示 VendorActivPlayer 参与者信息count
	 */
	@Select("SELECT COUNT(*) FROM yunma.tb_vendor_activ_player_#{vendorId} ;")
	int getIntegralPlayerCount(Integer vendorId);

	/***
	 * 分页显示 VendorActivPlayer 参与者信息详情
	 */
	@Select("SELECT * FROM yunma.tb_vendor_activ_player_#{vendorId} ORDER BY createDate desc;")
	List<VendorActivPlayer> getVendorActivPlayerInfoList(Map<String, Object> map);

	/**
	 * 
	 * 分页显示领奖者信息count VendorIntegralExchangeHistory
	 * 
	 */
	@Select("SELECT COUNT(*) FROM yunma.tb_vendor_integral_exchanges_#{vendorId} ORDER BY createTime desc;")
	int getVendorActivExchangesCount(Integer vendorId);

	/**
	 * 
	 * 分页显示领奖者信息infoList VendorIntegralExchangeHistory
	 * 
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_exchanges_#{vendorId} ;")
	List<VendorIntegralExchange> getVendorIntegralExchangeInfoList(
			Map<String, Object> map);

	/**
	 * 查询领奖历史条数:
	 * 
	 * @param vendorId
	 * 
	 */
	@Select("SELECT COUNT(*) FROM yunma.tb_vendor_integral_exchanges_history_#{vendorId}")
	int getHistoryCountByVendorId(Integer vendorId);

	/**
	 * 
	 * 分页显示领奖者信息详情
	 * 
	 * VendorIntegralExchangeHistory
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_exchanges_history_#{vendorId} ;")
	List<VendorIntegralExchangeHistory> getVendorIntegralActivPlayerDrawAwardRecordInfoList(
			Map<String, Object> map);

	/**
	 * 查询对应的领奖者信息,用于追加参数
	 * 
	 * 可能会出现同一openId兑换多个礼品记录信息需要截取时间排序下最新一个
	 * 
	 * @param orderId
	 * @param openId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_exchanges_history_#{vendorId} WHERE orderId=#{orderId} AND openId=#{openId} ORDER BY createTime DESC LIMIT 1; ")
	VendorIntegralExchangeHistory getVendorIntegralActivPlayerDrawAwardRecordInfoByOrderIdAndOpenId(
			@Param("vendorId") Integer vendorId,
			@Param("orderId") Integer orderId, @Param("openId") String openId);

	/**
	 * 追加兑换信息
	 * 
	 * @param vendorIntegralExchangeHistory
	 * @return
	 */
	@Update("INSERT INTO tb_vendor_integral_exchanges_history_#{vendorIntegralExchangeHistory.vendorId}"
			+ "SET " +
			"orderId=#{vendorIntegralExchangeHistory.orderId},vendorId=#{vendorIntegralExchangeHistory.vendorId},"
			+ "ruleName=#{vendorIntegralExchangeHistory.ruleName},exchangesName=#{vendorIntegralExchangeHistory.exchangesName},"
			+ "exchangesUrl=#{vendorIntegralExchangeHistory.exchangesUrl},eveCostIntegralCount=#{vendorIntegralExchangeHistory.eveCostIntegralCount},"
			+ "openId=#{vendorIntegralExchangeHistory.openId},recipientName=#{vendorIntegralExchangeHistory.recipientName},"
			+ "recipientPhone=#{vendorIntegralExchangeHistory.recipientPhone},address=#{vendorIntegralExchangeHistory.address},"
			+ "getExchangesCount=#{vendorIntegralExchangeHistory.getExchangesCount},lastIntegral=#{vendorIntegralExchangeHistory.lastIntegral} ;")
	int updateVendorIntegralExchangeHistory(
			VendorIntegralExchangeHistory vendorIntegralExchangeHistory);

	/**
	 * 更改规则启用状态
	 * 
	 * @param vendorId
	 * @param inteRuleId
	 * @param useingCode
	 */
	@Update("UPDATE yunma.tb_vendor_integral_rule_#{vendorId} SET isUseing=#{useingCode} WHERE inteRuleId=#{inteRuleId}; ")
	int updateVendorIntegralRuleByInteRuleId(@Param("vendorId") Integer vendorId,
			@Param("inteRuleId") Integer inteRuleId,@Param("useingCode")  int useingCode);

	/**
	 * 获取player信息
	 * 
	 * @param vendorId
	 * @param openId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_activ_player_#{vendorId} WHERE openId=#{openId} ;")
	VendorActivPlayer findPlayerByOpenId(@Param("vendorId") Integer vendorId,
			@Param("openId") String openId);

	/**
	 * 获取当前兑换物信息
	 * 
	 * @param vendorId
	 * @param inteExchangeId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_exchanges_#{vendorId} WHERE inteExchangeId=#{inteExchangeId};")
	VendorIntegralExchange findVendorIntegralExchangeByInteExchangeId(
			@Param("vendorId") Integer vendorId,
			@Param("inteExchangeId") Integer inteExchangeId);

	/**
	 * 更新兑换物数量
	 * 
	 * @param vendorId
	 * @param lastExchangeCount
	 * @param inteExchangeId
	 * @return
	 */
	@Update("UPDATE yunma.tb_vendor_integral_exchanges_#{vendorId} SET exchangesCount={lastExchangeCount} WHERE inteExchangeId=#{inteExchangeId};")
	int updateExchangeCount(Integer vendorId, int lastExchangeCount,
			Integer inteExchangeId);

	/**
	 * 创建兑换物
	 * 
	 * @param vendorIntegralExchange
	 * @return
	 */
	@Update("INSERT INTO yunma.tb_vendor_integral_exchanges_#{vendorId} "
			+ "SET "
			+ "exchangesName=#{exchangesName}, exchangesCount=#{exchangesCount}, integralExchangeCostCount=#{integralExchangeCostCount},"
			+ "exchangesPicUrl=#{exchangesPicUrl}, vendorId=#{vendorId}, exchangeType=#{exchangeType},"
			+ " orderId=#{orderId}, ruleId=#{ruleId}, expireTime=#{expireTime}, createTime=#{createTime};")
	int addVendorExchangesRule(VendorIntegralExchange vendorIntegralExchange);

	/**
	 * 修改兑换物
	 * 
	 * @param vendorIntegralExchange
	 * @return
	 */
	@Update("UPDATE yunma.tb_vendor_integral_exchanges_#{vendorIntegralExchange.vendorId} "
			+ "SET "
			+ "exchangesName=#{vendorIntegralExchange.exchangesName}, exchangesCount=#{vendorIntegralExchange.exchangesCount},"
			+ " integralExchangeCostCount=#{vendorIntegralExchange.integralExchangeCostCount},exchangeType=#{vendorIntegralExchange.exchangeType}, "
			+ " ruleId=#{vendorIntegralExchange.ruleId}, expireTime=#{vendorIntegralExchange.expireTime} WHERE inteExchangeId=#{vendorIntegralExchange.inteExchangeId};")
	int updateHoleVendorExchangesRule(
			@Param("vendorIntegralExchange") VendorIntegralExchange vendorIntegralExchange,
			@Param("inteExchangeId") Integer inteExchangeId);

	/**
	 * 删除兑换物
	 * 
	 * @param vendorId
	 * @param inteExchangeId
	 * @return
	 */

	@Delete("DELETE FROM yunma.tb_vendor_integral_exchanges_#{vendorId} WHERE inteExchangeId=#{inteExchangeId};")
	int deleteVendorExchangesRule(@Param("vendorId") Integer vendorId,
			@Param("inteExchangeId") Integer inteExchangeId);

	/**
	 * 删除规则
	 * 
	 * @param vendorId
	 * @param inteRuleId
	 * @return
	 */
	@Delete("DELETE FROM yunma.tb_vendor_integral_rule_#{0} WHERE inteRuleId=#{1};")
	int deleteVendorIntegralRule(Integer vendorId, Integer inteRuleId);

	/**
	 * 获取对应的规则
	 * 
	 * @param vendorId
	 * @param inteRuleId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_rule_#{vendorId} WHERE inteRuleId=#{inteRuleId};")
	VendorIntegralRule findRuleByInteRuleId(
			@Param("vendorId") Integer vendorId,
			@Param("inteRuleId") Integer inteRuleId);

	/**
	 * 根据参与者id查询参与者信息
	 * 
	 * @param vendorId
	 * @param playerId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_activ_player_#{vendorId} WHERE playerId=#{playerId} ")
	VendorActivPlayer findPlayerByPlayerId(@Param("vendorId") Integer vendorId,
			@Param("playerId") Integer playerId);

	/**
	 * 根据单个兑换品id查询兑换品信息
	 * 
	 * @param inteExchangeId
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_vendor_integral_exchanges_#{vendorId} WHERE inteExchangeId=#{inteExchangeId};")
	VendorIntegralExchange findVendorIntegralExchangeByExchangeId(
			@Param("inteExchangeId") Integer inteExchangeId,
			@Param("vendorId") Integer vendorId);
	/**
	 * 追加注释
	 * @param hisId
	 * @param vendorId
	 * @param conmment
	 * @return
	 */
	@Update("UPDATE tb_vendor_integral_exchanges_history_#{vendorId} SET comment=#{conmment} where hisId=#{hisId}")
	int updateVendorIntegralPlayerAddConmment(@Param("hisId")Integer hisId,@Param("vendorId") Integer vendorId,
			@Param("conmment")String conmment);

}
