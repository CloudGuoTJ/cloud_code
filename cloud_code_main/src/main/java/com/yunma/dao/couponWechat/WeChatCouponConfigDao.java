package com.yunma.dao.couponWechat;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.wechat.WeChatCouponConfig;

@Repository("weChatCouponConfigDao")
public interface WeChatCouponConfigDao {

	/**
	 * 添加微信配置文件
	 * @param config
	 * @return
	 */
	public int addWechatCouponConfig(WeChatCouponConfig config);
	
	/**
	 * 查询微信配置文件
	 * @param vendorId
	 * @return
	 */
	public WeChatCouponConfig getWeChatCouponConfig(@Param("vendorId") Integer vendorId);
	
	/**
	 * 修改为配置文件
	 * @param config
	 * @return
	 */
	public int updateWeChatCouponConfig(WeChatCouponConfig config);

	/**
	 * 获取所有微信小店厂商的配置文件
	 * @return
	 */
	public List<WeChatCouponConfig> getWeChatCouponConfigAll();

}
