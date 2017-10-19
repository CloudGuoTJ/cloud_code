package com.yunma.dao.openweixin;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yunma.entity.openweixin.OpenWeiXinAuthVendor;
import com.yunma.entity.openweixin.OpenWeiXinAuthorizerAccount;
import com.yunma.entity.openweixin.OpenWeiXinConfig;

@Repository("openWeiXinDao")
public interface OpenWeiXinDao {

	/**
	 * 修改Component_verify_ticket
	 * @param config
	 * @return
	 */
	public int updateComponent_verify_ticketByAppid(OpenWeiXinConfig config);

	/**
	 * 根据appid获取Component_verify_ticket
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
	 * @param config
	 * @return
	 */
	public int updatePreAuthCode(OpenWeiXinConfig config);
	
	/**
	 * 判断该厂商是否存在记录
	 * @param vendorId
	 * @return
	 */
	public int hasAuthVendor(Integer vendorId);
	
	/**
	 * 在厂商授权表中添加记录 
	 * @param vendorId
	 * @return
	 */
	public int addAuthVendor(Integer vendorId);

	/**
	 * 获取厂商授权信息
	 * @param vendorId
	 * @return
	 */
	public OpenWeiXinAuthVendor getAuthByVendorId(Integer vendorId);

	/**
	 * 修改证书位置
	 * @param realPath
	 * @return
	 */
	public int updateCredentialsAddress(@Param("realPath")String realPath,@Param("vendorId")Integer vendorId);
	
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
	public int hasAccount(@Param("appid") String appid);

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
