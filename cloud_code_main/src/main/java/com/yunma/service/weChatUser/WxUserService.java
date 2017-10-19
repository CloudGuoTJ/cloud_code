package com.yunma.service.weChatUser;

import com.common.util.ResultObject;
import com.yunma.entity.weChat.WeChatUser;
import com.yunma.model.User;

public interface WxUserService {
	
	/**
	 * 在微信用户表中添加用户信息
	 * @param user
	 * @return
	 */
	public int addWxUserInfo(WeChatUser user);
	
	
	
	
	
	
	
	/*----------------------------------------------------------*/
	
	/*int createWxUser(String fromUserName);

    int deleteWxUser(String fromUserName);

    int updateWxUserInfo(WxUserService wxUser);

    WxUserService findWxUserByOpenId(String openId);

    WxUserService findAllWxUserByOpenId(String openId);

    int createScanWxUser(String fromUserName);

    int activateWxUser(String fromUserName);

    int sendMoneyWxUser(String fromUserName);

    int updateWxUser(WxUserService wxUser);

    String getNickName(String openId);

    *//**
     * 获取微信用户中心信息
     * @param openId 微信用户标识
     * @param result 结果
     * @param securityCode 二维码
     *//*
    void getWechatUserInfo(String openId, String securityCode, ResultObject result);

    *//**
     * 查询用户列表接口
     * @param result
     * @param page
     * @param num
     * @param wxNameOrAddress
     * @param user
     *//*
    void findWxUserInfoByWxName(ResultObject result,int page, int num, String wxNameOrAddress,User user);


    *//**
     * 根据openId获取微信用户收货地址列表
     * @param openId 微信用户ID
     * @param result
     *//*
    void getWxUserRecipientList(String openId, ResultObject result);

    *//**
     * 根据ID获取微信用户收货地址信息
     * @param openId 微信用户ID
     * @param id 微信用户收货地址ID
     * @param result
     *//*
    void getWxUserRecipient(String openId, long id, ResultObject result);

    *//**
     * 根据ID确认微信用户收货地址
     * @param securityCode 二维码
     * @param openId 微信用户ID
     * @param id 微信用户收货地址ID（确认默认收货地址为：-1）
     * @param challengeAwardRecordId 中奖纪录ID
     * @param result
     *//*
    void confirmWxUserRecipient(String securityCode, String openId, long id, long challengeAwardRecordId, ResultObject result);
    *//**
     * 根据ID确认微信用户收货地址
     * @param securityCode 二维码
     * @param openId 微信用户ID
     * @param id 微信用户收货地址ID（确认默认收货地址为：-1）
     * @param challengeAwardRecordId 中奖纪录ID
     * @param result
     *//*
    void confirmWxUserRecipientForGame(String securityCode, String openId, long id, long challengeAwardRecordId, ResultObject result);


    *//**
     * 设置微信用户收货地址
     * @param wxUserRecipient 微信用户收货地址
     * @param result
     *//*
    void setWxUserRecipient(WxUserRecipient wxUserRecipient, ResultObject result);

    *//**
     * 微信用户收货地址设置默认状态
     * @param openId 微信用户ID
     * @param id 微信用户收货地址ID
     * @param result
     *//*
    void setWxUserRecipientDefault(String openId, long id, ResultObject result);

    *//**
     * 删除微信用户收货地址
     * @param openId 微信用户ID
     * @param id 微信用户收货地址ID
     * @param result
     *//*
    void deleteWxUserRecipient(String openId, long id, ResultObject result);

    *//**
     * 给微信用户添加昵称等信息
     * @param user
     * @return
     *//*
    int setNickName(WxUserService user);*/

}
