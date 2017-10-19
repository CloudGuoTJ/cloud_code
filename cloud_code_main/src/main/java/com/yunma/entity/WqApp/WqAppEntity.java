package com.yunma.entity.WqApp;

import java.io.Serializable;

public class WqAppEntity implements Serializable{

	private Integer id; //id
	
	private String appName; //应用名称
	
	private String weChatUrl; //微信路径
	
	private String weqUrl; //微擎路径
	
	private Integer gzhId; //公众号id
	
	private Integer gzhUnionId; //公众号联合id
	
	private String appImg; //应用图片路径
	
	private String qrCodeUrl; //二维码路径
	
	private Integer vendorId; //厂商id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getWeChatUrl() {
		return weChatUrl;
	}

	public void setWeChatUrl(String weChatUrl) {
		this.weChatUrl = weChatUrl;
	}

	public String getWeqUrl() {
		return weqUrl;
	}

	public void setWeqUrl(String weqUrl) {
		this.weqUrl = weqUrl;
	}

	public Integer getGzhId() {
		return gzhId;
	}

	public void setGzhId(Integer gzhId) {
		this.gzhId = gzhId;
	}

	public Integer getGzhUnionId() {
		return gzhUnionId;
	}

	public void setGzhUnionId(Integer gzhUnionId) {
		this.gzhUnionId = gzhUnionId;
	}

	public String getAppImg() {
		return appImg;
	}

	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
