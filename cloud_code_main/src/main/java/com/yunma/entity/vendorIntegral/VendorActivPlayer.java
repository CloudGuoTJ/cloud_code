package com.yunma.entity.vendorIntegral;

import java.io.Serializable;
import java.sql.Date;

/**
 * 商家额外设置扫码活动参与用户表
 * 
 * 
 * @author Administrator
 *
 */

public class VendorActivPlayer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3873471300892830706L;

	private  Integer playerId;//用户ID
	
	private Integer vendorId;//'厂商ID
	
	private String openId;//微信id
	
	private String nickName;//昵称
	
	private String address;//地址:用于领取实物奖品
	
	private String playerName;//用户自设领奖名
	
	private String recipientPhone;//player电话
	
	private Date createDate;//创建时间
	
	private Integer orderId;//扫码活动以订单为单位
	
	private Integer integral;//已获得的积分
	
	private String headImgurl;//微信头像URL

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getRecipientPhone() {
		return recipientPhone;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getHeadImgurl() {
		return headImgurl;
	}

	public void setHeadImgurl(String headImgurl) {
		this.headImgurl = headImgurl;
	}
	
	
	
	
	
}
