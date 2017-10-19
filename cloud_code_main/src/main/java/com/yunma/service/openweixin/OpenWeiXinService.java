package com.yunma.service.openweixin;

import com.yunma.entity.openweixin.OpenWeiXinAuthVendor;
import com.yunma.entity.openweixin.OpenWeiXinAuthorizerAccount;
import com.yunma.entity.openweixin.OpenWeiXinConfig;



public interface OpenWeiXinService {
	
	/**
	 * 修改component_verify_ticket
	 * @param config
	 * @return
	 */
	public int updateComponent_verify_ticketByAppid(OpenWeiXinConfig config);
	
	/**
	 * 根据appid获取component_verify_ticket
	 * @param appid
	 * @return
	 */
	public OpenWeiXinConfig getComponent_verify_ticketByAppid(String appid);
	
	/**
	 * 更新token
	 * @param config
	 * @return
	 */
	public int updateComponentAccessToken(OpenWeiXinConfig config);
	
	/**
	 * 更新预授权码
	 * @return
	 */
	public int updatePreAuthCode(OpenWeiXinConfig config);

	/**
	 * 厂商授权
	 * @param vendorId
	 */
	public void authVendor(Integer vendorId);

	/**
	 * 获取厂商授权信息
	 * @param vendorId
	 * @return
	 */
	public OpenWeiXinAuthVendor getAuthByVendorId(Integer vendorId);

	/**
	 * 修改 证书位置
	 * @param realPath
	 */
	public int updateCredentialsAddress(String realPath,Integer vendorId);
	
	/**
	 * 修改授权码
	 * @param config
	 * @return
	 */
	public int updateAuthCode(OpenWeiXinConfig config);

	/**
	 * 判断授权小程序中是否存在该账号
	 * @param appid
	 * @return
	 */
	public int hasAccount(String appid);

	/**
	 * 添加授权小程序账号信息
	 * @param account
	 * @return
	 */
	public int addAccount(OpenWeiXinAuthorizerAccount account);

	/**
	 * 更新小程序审核结果信息
	 * @param account
	 * @return
	 */
	public int updateAccount(OpenWeiXinAuthorizerAccount account);
}
