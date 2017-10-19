package com.yunma.entity.jdcoupon;



public class JDCouponCreateInfo {
	
	private Integer id;
	private Integer vendorId;
	private String jdUid;//京东商户id
	private String couponId;//京东返回的优惠券id
	private String ip;//调用方IP
	private Integer port;//调用方端口
	private String name;//优惠券名称
	private Integer type;//优惠券类型 0京券 1东券
	private Integer bindType;//绑定类型 1全店参加 2指定sku参加
	private Integer grantType;//发放类型 1促销绑定 2推送 3免费领取 4京豆换券 5互动平台
	private Integer num;//优惠券数量
	private Integer discount;//优惠券面额
	private Integer quota;//优惠限额
	private Integer validityType;//有效期类型 1相对时间 5绝对时间
	private Integer days;//有效期(validityType为1时必填)
	private String beginTime;//有效期开始时间（validityType为5时必填）
	private String endTime;//有效期结束时间（validityType为5时必填）
	private String password;
	private String batchKey;
	private Integer member;//会员等级 50注册会员 56铜牌 61银牌 62金牌 105钻石 110VIP 90企业会员
	private String takeBeginTime;//领券开始时间
	private String takeEndTime;//领券结束时间
	private Integer takeRule;//领券规则 5限领一张 4每天限领一张 3自定义每天限量数量
	private Integer takeNum;//限制条件内可以领取张数
	private Integer display;//是否公开 1不公开 3公开(grantType如设值5此参数必须为3)
	private Integer platformType;//使用平台 1全平台 3限平台
	private String platform;//
	private String imgUrl;
	private Integer boundStatus;
	private Integer jdNum;
	private Long itemId;
	private Integer shareType;//分享类型 1分享 2不分享（如设置京券type=0,此参数必填2不分享）
	private String skuId;
	private String link;//优惠券领取链接
	 private String activityLink;//优惠券活动地址
	
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
	public String getJdUid() {
		return jdUid;
	}
	public void setJdUid(String jdUid) {
		this.jdUid = jdUid;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getBindType() {
		return bindType;
	}
	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}
	public Integer getGrantType() {
		return grantType;
	}
	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getQuota() {
		return quota;
	}
	public void setQuota(Integer quota) {
		this.quota = quota;
	}
	public Integer getValidityType() {
		return validityType;
	}
	public void setValidityType(Integer validityType) {
		this.validityType = validityType;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBatchKey() {
		return batchKey;
	}
	public void setBatchKey(String batchKey) {
		this.batchKey = batchKey;
	}
	public Integer getMember() {
		return member;
	}
	public void setMember(Integer member) {
		this.member = member;
	}
	public String getTakeBeginTime() {
		return takeBeginTime;
	}
	public void setTakeBeginTime(String takeBeginTime) {
		this.takeBeginTime = takeBeginTime;
	}
	public String getTakeEndTime() {
		return takeEndTime;
	}
	public void setTakeEndTime(String takeEndTime) {
		this.takeEndTime = takeEndTime;
	}
	public Integer getTakeRule() {
		return takeRule;
	}
	public void setTakeRule(Integer takeRule) {
		this.takeRule = takeRule;
	}
	public Integer getTakeNum() {
		return takeNum;
	}
	public void setTakeNum(Integer takeNum) {
		this.takeNum = takeNum;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Integer getPlatformType() {
		return platformType;
	}
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getBoundStatus() {
		return boundStatus;
	}
	public void setBoundStatus(Integer boundStatus) {
		this.boundStatus = boundStatus;
	}
	public Integer getJdNum() {
		return jdNum;
	}
	public void setJdNum(Integer jdNum) {
		this.jdNum = jdNum;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Integer getShareType() {
		return shareType;
	}
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getActivityLink() {
		return activityLink;
	}
	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}
	
	
}
