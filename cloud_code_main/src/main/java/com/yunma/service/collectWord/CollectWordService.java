package com.yunma.service.collectWord;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.collectWord.CollectWordGame;
import com.yunma.entity.collectWord.CollectWordWinner;
import com.yunma.entity.collectWord.UserGetedWord;
import com.yunma.utils.PageBean;
import com.yunma.vo.collectWord.CollectWordForJson;
import com.yunma.vo.product.ProductVo;

public interface CollectWordService {

	/**
	 * 新增集字规则
	 * @param vo
	 * @return
	 */
	JSONObject addCollectWordRule(CollectWordForJson json);
	
	/**
	 * 查询规则列表
	 * @param vendorId
	 * @return
	 */
	JSONObject getRuleList(Integer vendorId);
	
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
	JSONObject getCollectWordGameList(Integer vendorId);
	
	/**
	 * 规则详情
	 * @param id
	 * @return
	 */
	CollectWordForJson getRuleInfoById(Integer id);
	
	/**
	 * 修改规则
	 * @param obj
	 * @return
	 */
	JSONObject updateRuleInfoById(CollectWordForJson obj);
	
	/**
	 * 将规则删除标记设为1
	 * @param id
	 * @return
	 */
	int deleteRuleById(Integer id);
	
	/**
	 * 修改参加集字游戏的产品状态
	 * @param proId
	 * @return
	 */
	int updateProInfoForCollectWord(Integer proId);
	
	/**
	 * 修改集字游戏订单状态
	 * @param orderId
	 * @return
	 */
	int updateOrderInfoForCollectWord(Integer orderId);
	
	/**
	 * 分页显示参加集字游戏的订单
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	PageBean getOrderInfoForCollectWord(PageBean pageBean, Integer vendorId);
	
	/**
	 * 分页显示集字游戏的订单
	 * @param pageBean
	 * @param product
	 * @return
	 */
	public PageBean getProductForCollectWord(PageBean pageBean, ProductVo product);
	
	/**
	 * 集字游戏随机生成字
	 * @param orderOrProId
	 * @return
	 */
	JSONObject getWordForMobile(String code,Integer orderOrProId,String openId,Integer orderId,Integer vendorId);
	
	/**
	 * 新增用户获得的字
	 * @param userWord
	 * @return
	 */
	int addUserGetWord(UserGetedWord userWord);
	
	/**
	 * 根据openId查询用户已经获得的字
	 * @param openId
	 * @return
	 */
	JSONObject getUserCollectWords(String openId,Integer vendorId);
	
	/**
	 * 获取奖项列表
	 * @param orderOrProId
	 * @return
	 */
	JSONObject getCollectWordItemList(Integer orderOrProId);
	
	/**
	 * 查询是否获奖
	 * @param openId
	 * @return
	 */
	JSONObject cashPrizeForUser(String openId,Integer vendorId,Integer itemId);
	
	/**
	 * 新增获奖者信息
	 * @return
	 */
	int addCollectWordWinner(CollectWordWinner winner);
	
	/**
	 * 获奖者列表
	 * @param vendorId
	 * @return
	 */
	JSONObject getWinnerList(Integer vendorId);
	
}
