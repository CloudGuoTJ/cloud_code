package com.yunma.service.couponWd;

import java.util.List;

import com.yunma.entity.coupon.wd.WdConfig;

public interface WdConfigService {

	/**
	 * 新增微店配置
	 * @param wdConfig
	 * @return
	 */
	public int addWdConfig(WdConfig wdConfig);
	
	/**
	 * 修改微店配置
	 * @param wdConfig
	 * @return
	 */
	public int updateWdConfig(WdConfig wdConfig);
	
	/**
	 * 获取微店配置
	 * @param vendorId
	 * @return
	 */
	public WdConfig getWdConfig(Integer vendorId);

	/**
	 * 获取所有微店配置
	 * @return 
	 */
	public List<WdConfig> getWdConfigAll();
}
