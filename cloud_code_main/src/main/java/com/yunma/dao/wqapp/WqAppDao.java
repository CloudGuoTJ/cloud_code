package com.yunma.dao.wqapp;

import java.util.List;

import com.yunma.entity.WqApp.WqAppEntity;

public interface WqAppDao {

	
	int addWqApp(WqAppEntity entity);
	
	
	WqAppEntity getAppInfo(Integer appId);
	
	
	List<WqAppEntity> getAllAppInfo(Integer vendorId);
	
	
	int deleteApp(Integer id);
	
	
	WqAppEntity getAppInfoByName(String appName);
	
	
	int updateAppinfo(WqAppEntity entity);
}
