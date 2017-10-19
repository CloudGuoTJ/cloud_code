package com.yunma.entity.openweixin;

/**
 * 厂商授权类
 * @author LUO
 *
 */
public class OpenWeiXinAuthVendor {
	
	private Integer id;
	private Integer vendorId;
	private Integer isAuth;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getIsAuth() {
		return isAuth;
	}
	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}
	
}
