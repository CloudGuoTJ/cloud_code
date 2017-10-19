package com.yunma.service.openweixin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.openweixin.OpenWeiXinDao;
import com.yunma.entity.openweixin.OpenWeiXinAuthVendor;
import com.yunma.entity.openweixin.OpenWeiXinAuthorizerAccount;
import com.yunma.entity.openweixin.OpenWeiXinConfig;
import com.yunma.service.openweixin.OpenWeiXinService;

@Service
public class OpenWeiXinServiceImpl implements OpenWeiXinService{

	@Resource
	private OpenWeiXinDao openWeiXinDao;
	
	@Override
	public int updateComponent_verify_ticketByAppid(OpenWeiXinConfig config) {
		return openWeiXinDao.updateComponent_verify_ticketByAppid(config);
	}

	@Override
	public OpenWeiXinConfig getComponent_verify_ticketByAppid(String appid) {
		return openWeiXinDao.getComponent_verify_ticketByAppid(appid);
	}

	@Override
	public int updateComponentAccessToken(OpenWeiXinConfig config) {
		return openWeiXinDao.updateComponentAccessToken(config);
	}

	@Override
	public int updatePreAuthCode(OpenWeiXinConfig config) {
		return openWeiXinDao.updatePreAuthCode(config);
	}

	@Override
	public void authVendor(Integer vendorId) {
		int result = openWeiXinDao.hasAuthVendor(vendorId);
		if (result == 0) {
			openWeiXinDao.addAuthVendor(vendorId);
		}
	}

	@Override
	public OpenWeiXinAuthVendor getAuthByVendorId(Integer vendorId) {
		return openWeiXinDao.getAuthByVendorId(vendorId);
	}

	@Override
	public int updateCredentialsAddress(String realPath,Integer vendorId) {
		return openWeiXinDao.updateCredentialsAddress(realPath,vendorId);
	}

	@Override
	public int updateAuthCode(OpenWeiXinConfig config) {
		return openWeiXinDao.updateAuthCode(config);
	}

	@Override
	public int hasAccount(String appid) {
		return openWeiXinDao.hasAccount(appid);
	}

	@Override
	public int addAccount(OpenWeiXinAuthorizerAccount account) {
		return openWeiXinDao.addAccount(account);
	}

	@Override
	public int updateAccount(OpenWeiXinAuthorizerAccount account) {
		return openWeiXinDao.updateAccount(account);
	}

}
