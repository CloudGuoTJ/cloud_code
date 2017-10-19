package com.yunma.service.couponWechat.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.couponWechat.WeChatCouponConfigDao;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.service.couponWechat.WeChatCouponConfigService;

@Service
public class WeChatCouponConfigServiceImpl implements WeChatCouponConfigService{
	
	@Resource
	private WeChatCouponConfigDao weChatCouponConfigDao;

	@Override
	public int addWechatConfig(WeChatCouponConfig  config) {
		return weChatCouponConfigDao.addWechatCouponConfig(config);
	}

	@Override
	public WeChatCouponConfig getWeChatCouponConfig(Integer vendorId) {
		return weChatCouponConfigDao.getWeChatCouponConfig(vendorId);
	}

	@Override
	public int updateWeChatCouponConfig(WeChatCouponConfig config) {
		return weChatCouponConfigDao.updateWeChatCouponConfig(config);
	}

	@Override
	public List<WeChatCouponConfig> getWeChatCouponConfigAll() {
		return weChatCouponConfigDao.getWeChatCouponConfigAll();
	}

}
