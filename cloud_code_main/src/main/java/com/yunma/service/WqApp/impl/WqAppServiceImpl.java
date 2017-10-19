package com.yunma.service.WqApp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.wqapp.WqAppDao;
import com.yunma.entity.WqApp.WqAppEntity;
import com.yunma.service.WqApp.WqAppService;

@Service
public class WqAppServiceImpl implements WqAppService{

	@Resource
	private WqAppDao dao;

	@Override
	public int addWqApp(WqAppEntity entity) {
		return dao.addWqApp(entity);
	}

	@Override
	public WqAppEntity getAppInfo(Integer appId) {
		return dao.getAppInfo(appId);
	}

	@Override
	public List<WqAppEntity> getAllAppInfo(Integer vendorId) {
		
		return dao.getAllAppInfo(vendorId);
	}

	@Override
	public int deleteApp(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteApp(id);
	}

	@Override
	public WqAppEntity getAppInfoByName(String appName) {
		// TODO Auto-generated method stub
		return dao.getAppInfoByName(appName);
	}

	@Override
	public int updateAppinfo(WqAppEntity entity) {
		// TODO Auto-generated method stub
		return dao.updateAppinfo(entity);
	}
	
	
}
