package com.yunma.service.weChatUser;

import java.util.Map;

import com.yunma.entity.weChat.WeChatUser;
import com.yunma.entity.weChat.WeChatUserWallet;
import com.yunma.model.WxUser;


public interface WeChatService {
	
	/**
	 * 在微信用户表中添加用户信息
	 * @param user
	 * @return
	 */
	public int addWeChatUserInfo(WeChatUser user);

	/**
	 * 修改微信用户信息
	 * @param user
	 * @return
	 */
	public int updateWeChatUserInfo(WeChatUser user);
	
	/**
	 * 查询该openId是否存在
	 * @param openId
	 * @return
	 */
	public int hasWeChatUserInfo(String openId);
	
	/**
	 * 添加微信用户的红包记录
	 * @param openId
	 * @return
	 */
	public int addWeChatUserWallet(String openId);
	
	/**
	 * 发送微信红包
	 * @param openId
	 * @param amount
	 * @param nums
	 * @return
	 */
	public Map<Object, Object> sendRedEnv(String appId,String mchId,String apiKey,String openId,Integer amount,Integer nums,String fileLocation);
	
	/**
	 * 根据openId查询钱包信息
	 * @param openId
	 * @return
	 */
	public WeChatUserWallet getWalletByOpenId(String openId);

	/**
	 * 修改钱包信息
	 * @param wallet
	 * @return
	 */
	public int updateWalletById(WeChatUserWallet wallet);

	/**
	 * 添加提现记录
	 * @param openId
	 * @param finalMoney
	 */
	public int addWeChatUserRecord(WeChatUserWallet wallet);

	
	/***
	 * 通过openId获取NickName
	 * @param openid
	 * @return
	 */
	public String getNickNameBy(String openId);
	
	/***
	 * 获取微信用户信息
	 */
	public WeChatUser getUserInfoByOpenId(String openId);
	
}
