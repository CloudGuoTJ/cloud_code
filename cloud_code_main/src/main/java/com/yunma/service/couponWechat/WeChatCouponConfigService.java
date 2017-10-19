package com.yunma.service.couponWechat;

import java.util.List;

import com.yunma.entity.coupon.wechat.WeChatCouponConfig;

public interface WeChatCouponConfigService {

	/**
	 * 添加微信配置
	 * @param vendorId
	 * @param appId
	 * @param secret
	 * @param apiKey
	 * @param mchId
	 * @param fileLocation
	 * @return
	 */
	public int addWechatConfig(WeChatCouponConfig config);

	/**
	 * 查询微信配置文件
	 * @param vendorId
	 * @return
	 */
	public WeChatCouponConfig getWeChatCouponConfig(Integer vendorId);
	
	/**
	 * 修改为配置文件
	 * @param config
	 * @return
	 */
	public int updateWeChatCouponConfig(WeChatCouponConfig config);
	
	/**
	 * 获取所有微信小店厂商的配置信息
	 * @return
	 */
	public List<WeChatCouponConfig> getWeChatCouponConfigAll();
}
