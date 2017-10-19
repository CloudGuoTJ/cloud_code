package com.yunma.service.weChatVendorHtmlActivInfo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunma.dao.weChart.VendorHtmlActivInfoDao;
import com.yunma.model.VendorHtmlActivInfo;
import com.yunma.service.weChatVendorHtmlActivInfo.VendorHtmlActivInfoService;
import com.yunma.vo.wechatHtml.VendorHtmlActivInfoVO;
@Service
public class VendorHtmlActivInfoServiceImpl implements VendorHtmlActivInfoService {
	
	@Autowired
	private VendorHtmlActivInfoDao activDao;

	@Override
	public int saveHtmlInfo(VendorHtmlActivInfo vendorHtmlActivInfo) {
		
		return activDao.saveHtmlInfo(vendorHtmlActivInfo);
	}

	@Override
	public VendorHtmlActivInfo findVendorHtmlActivInfoByVendorId(
			String htmlName) {
		return activDao.findVendorHtmlActivInfoByHtmlName(htmlName);
	}

	@Override
	public List<VendorHtmlActivInfoVO> findVendorHtmlActivInfoByVendorId(
			Integer vendorId) {
		// TODO Auto-generated method stub
		return activDao.findVendorHtmlActivInfoByVendorId(vendorId);
	}

	@Override
	public VendorHtmlActivInfoVO getVendorHtmlActivById(Integer actionId) {
		// TODO Auto-generated method stub
		return activDao.getVendorHtmlActivById(actionId);
	}

}
