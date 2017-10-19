package com.yunma.dao.couponWd;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunma.entity.coupon.wd.WdConfig;

@Repository("wdConfigDao")
public interface WdConfigDao {

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
	 * 获取微店所有配置信息
	 * @return
	 */
	public List<WdConfig> getWdConfigAll();
}
