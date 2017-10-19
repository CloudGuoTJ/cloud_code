package com.yunma.service.weChatVendorHtmlActivInfo;

import java.util.List;

import com.yunma.model.VendorHtmlActivInfo;
import com.yunma.vo.wechatHtml.VendorHtmlActivInfoVO;

public interface VendorHtmlActivInfoService {
	
	/**
	 * 存商家回调信息
	 */
	public int saveHtmlInfo(VendorHtmlActivInfo vendorHtmlActivInfo);
	
	/**
	 * 查询商家存储网页信息
	 */
	public VendorHtmlActivInfo findVendorHtmlActivInfoByVendorId(
			String htmlName);
/**
 * 根据厂商Id查询信息
 * @param vendorId
 * @return
 */
	public List<VendorHtmlActivInfoVO> findVendorHtmlActivInfoByVendorId(
			Integer vendorId);
	
	
	VendorHtmlActivInfoVO getVendorHtmlActivById(Integer actionId);

}
