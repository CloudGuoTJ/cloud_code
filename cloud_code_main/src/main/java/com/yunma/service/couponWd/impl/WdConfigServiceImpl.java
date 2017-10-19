package com.yunma.service.couponWd.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.couponWd.WdConfigDao;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.service.couponWd.WdConfigService;

@Service
public class WdConfigServiceImpl implements WdConfigService{
	
	@Resource
	private WdConfigDao wdConfigDao;

	@Override
	public int addWdConfig(WdConfig wdConfig) {
		return wdConfigDao.addWdConfig(wdConfig);
	}

	@Override
	public int updateWdConfig(WdConfig wdConfig) {
		return wdConfigDao.updateWdConfig(wdConfig);
	}

	@Override
	public WdConfig getWdConfig(Integer vendorId) {
		return wdConfigDao.getWdConfig(vendorId);
	}

	@Override
	public List<WdConfig> getWdConfigAll() {
		return wdConfigDao.getWdConfigAll();
	}

}
