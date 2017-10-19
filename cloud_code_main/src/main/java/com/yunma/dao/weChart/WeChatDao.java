package com.yunma.dao.weChart;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.yunma.entity.weChat.WeChatUser;
import com.yunma.entity.weChat.WeChatUserWallet;

@Repository("weChatDao")
public interface WeChatDao {

	/**
	 * 添加微信用户信息
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
	 * 添加用户提现记录
	 * @param openId
	 * @param finalMoney
	 * @return
	 */
	public int addWeChatUserRecord(WeChatUserWallet wallet);
	/**
	 * cloud
	 * 暂时使用wx_user内总数充当会员和微信粉丝数
	 */
	public int findUserCount(Integer vendorId);
	/**
	 * cloud
	 * 暂时使用wx每日增加的用户数充当 增加的会员数
	 */
	public int getIncreaseVipCount(Integer vendorId);

	
	/**
	 * 获取昵称
	 * @param openId
	 * @return
	 */
	@Select("SELECT nickName FROM  tb_wx_user WHERE openId=#{openId}")
	public String findNickNameByOpenId(@Param("openId") String openId);

	/**
	 * 获取微信用户
	 * @param openId
	 * @return
	 */
	@Select("SELECT * FROM  tb_wx_user WHERE openId=#{openId}")
	public WeChatUser getUserInfoByOpenId(@Param("openId")String openId);
}
