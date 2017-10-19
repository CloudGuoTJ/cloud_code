package com.yunma.service.wxConfig;

import com.yunma.model.WxConfig;
import com.yunma.utils.PageBean;
import com.yunma.vo.wxConfig.WxConfigGzhVo;
import com.yunma.vo.wxConfig.WxConfigVo;

public interface WxConfigService {
	
	/**
	 * 查询微信公众号总记录数
	 * @return
	 */
	int countWxConfig() ;
	/**
	 * 新增微信公众号信息
	 * @param vo
	 * @return
	 */
	int addWxConfig(WxConfigVo vo);
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	int updateWxConfig(WxConfigVo vo);
	/**
	 * 分页查询
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	PageBean getWxConfigInfo(PageBean pageBean, WxConfig vo);
	
	
	/**
	 * 新增从微擎上提交的公众号信息
	 * @param vo
	 * @return
	 */
	int addWxGzh(WxConfigGzhVo vo);
	
	/**
	 * 点击下一步时获取token,url,appKey的修改操作
	 * @param vo
	 * @return
	 */
	int updateWxGzhInfo(WxConfigGzhVo vo);
	
	/**
	 * 获取公众号信息
	 * @param vendorId
	 * @return
	 */
	WxConfigGzhVo getWxGzhInfo(Integer vendorId);
	
	/**
	 * 添加公众号时候的修改操作
	 * @param vo
	 * @return
	 */
	int addWxGzhInfo(WxConfigGzhVo vo);
	
	/**
	 * 分页获取公众号信息
	 * @param page
	 * @return
	 */
	PageBean getAllWxGzhInfo(PageBean page);
}
