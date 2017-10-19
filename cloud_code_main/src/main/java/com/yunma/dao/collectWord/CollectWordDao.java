package com.yunma.dao.collectWord;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunma.entity.collectWord.CollectWordGame;
import com.yunma.entity.collectWord.CollectWordRate;
import com.yunma.entity.collectWord.CollectWordRule;
import com.yunma.entity.collectWord.CollectWordRuleItem;
import com.yunma.entity.collectWord.CollectWordWinner;
import com.yunma.entity.collectWord.UserGetedWord;
import com.yunma.vo.collectWord.CollectWordVo;
import com.yunma.vo.collectWord.CollectWordWinnerVo;

public interface CollectWordDao {
	
	/**
	 * 新增规则
	 * @param rule
	 * @return
	 */
	int addCollectWordRule(CollectWordRule rule);
	
	/**
	 * 新增奖项
	 * @param item
	 * @return
	 */
	int addCollectWordRuleItem(CollectWordRuleItem item);
	
	/**
	 * 新增字及概率
	 * @param rate
	 * @return
	 */
	int addCollectWordRuleRate(CollectWordRate rate);
	
	/**
	 * 查询规则列表
	 * @param vendorId
	 * @return
	 */
	List<CollectWordRule> getRuleList(Integer vendorId);
	
	/**
	 * 从概率表获取中奖字
	 * @param ruleId
	 * @return
	 */
	List<String> getWordsFromRate(Integer ruleId);
	
	/**
	 * 新增游戏
	 * @param game
	 * @return
	 */
	int addCollectWordGame(CollectWordGame game);
	
	/**
	 * 获取游戏列表
	 * @param vendorId
	 * @return
	 */
	List<CollectWordVo> getCollectWordGameList(Integer vendorId);
	
	/**
	 * 获取规则详情
	 * @param id
	 * @return
	 */
	CollectWordRule getRuleInfoById(Integer id);
	
	/**
	 * 获取规则详情中的字及概率
	 * @param id
	 * @return
	 */
	List<CollectWordRate> getRateInfoByRuleId(Integer ruleId);
	
	/**
	 * 获取规则详情中的奖项列表
	 * @param id
	 * @return
	 */
	List<CollectWordRuleItem> getItemInfoByRuleId(Integer ruleId);
	
	/**
	 * 修改规则信息
	 * @param rule
	 * @return
	 */
	int updateRuleInfoById(CollectWordRule rule);
	
	/**
	 * 修改规则中的字及概率
	 * @return
	 */
	int updateRuleRateById(CollectWordRate rate);
	
	/**
	 * 修改规则中奖项
	 * @param item
	 * @return
	 */
	int updateRuleItemById(CollectWordRuleItem item);
	
	/**
	 * 删除规则下面的字及概率
	 * @param ruleId
	 * @return
	 */
	int deleteRuleRateByRuleId(Integer ruleId);
	
	/**
	 * 删除规则下面的奖项
	 * @param ruleId
	 * @return
	 */
	int deleteRuleItemByRuleId(Integer ruleId);
	
	/**
	 * 将规则删除标记设为1
	 * @param id
	 * @return
	 */
	int deleteRuleById(Integer id);
	
	/**
	 * 查询当前规则名是否存在
	 * @param name
	 * @return
	 */
	String getRuleNameByName(String name);
	
	/**
	 * 根据订单id查询规则中字及概率
	 * @param orderOrProId
	 * @return
	 */
	List<CollectWordRate> getWordAndRate(Integer orderOrProId);
	
	/**
	 * 新增用户获得的字
	 * @param userWord
	 * @return
	 */
	int addUserGetWord(UserGetedWord userWord);
	
	/**
	 * 查询用户已经获得的字
	 * @param openId
	 * @return
	 */
	List<UserGetedWord> getUserCollectWords(@Param(value="openId")String openId,@Param(value="vendorId")Integer vendorId);
	
	/**
	 * 根据订单id查询规则id
	 * @param orderId
	 * @return
	 */
	Integer getRuleIdByOrderId(Integer orderId);
	
	/**
	 * 当前厂商是否已经创建过规则
	 * @param vendorId
	 * @return
	 */
	CollectWordRule isExistCurrentRule(Integer vendorId);
	
	/**
	 * 根据奖项id查询奖项信息
	 * @param itemId
	 * @return
	 */
	CollectWordRuleItem getRuleItemById(Integer itemId);
	
	/**
	 * 新增获奖者信息
	 * @return
	 */
	int addCollectWordWinner(CollectWordWinner winner);
	
	/**
	 * 获取获奖者
	 * @param vendorId
	 * @return
	 */
	List<CollectWordWinnerVo> getWinnerList(Integer vendorId);
	
	/**
	 * 根据短码查询当前扫描的码是否已经获得字
	 * @param shortCode
	 * @return
	 */
	UserGetedWord selectUserGetWordByShortCode(String shortCode);
	
}
