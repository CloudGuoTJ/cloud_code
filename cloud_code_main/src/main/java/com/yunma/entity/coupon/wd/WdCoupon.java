package com.yunma.entity.coupon.wd;

import java.util.Date;

public class WdCoupon {

	private static final long serialVersionUID = 1L;
	private Integer couponId;
	private Integer vendorId;//厂商ID
	private String name;//优惠卷名称
	private Integer type;//优惠券类型：1、代金券；2、折扣券；3、礼品券；4、体验券；5、免运费券
	private Integer reduce;// 代金券面值
	private String imgUrl;//优惠券图片地址
	private Integer stock;//优惠券库存
	private Integer count;//优惠券总量
	private Integer scope;//使用范围：1、在系统中使用；2、在商城中使用
	private Integer commodityId;//指定可使用优惠券的商品ID（默认-1：全场可用
	private Integer  leastCost;//代金券使用最低限额
	private Integer dateType;//有效时间类型：1、设定天数；2、设定某天；3、设定范围；4、永久有效
	private Integer validLenght;//有效时长（单位：天）
	private Date beginTime;//开始时间	
	private Date endTime;//结束时间
	private String explain;//优惠券说明
	private Integer status;//优惠券状态(可领取:1,已领完:2,已经过期:3,还未开始:4,已删除:-1)
	private Date gmtCreated;//记录生成时间
	private Date gmtModified;//记录跟新时间
	private String title;//分享文案 优惠券标题
	private Integer showFlag;//是否显示已领完的券
	private String weidianFetchUrl;//微信域名 领取链接
	private Integer openGet;//是否在店铺中公开领取
	private Integer deleted;//判断优惠券是否删除
	private Integer showFinish;//是否显示已领完的优惠券
	private String  userUrl;//领取Url
	private Integer buyerLimit;//每人限领数量
	private  Date createTime;//创建时间	
	
	private String beginTimeStr;
	private String endTimeStr;
	private String gmtCreatedStr;
	private String gmtModifiedStr;
	
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public Integer getReduce() {
		return reduce;
	}
	public void setReduce(Integer reduce) {
		this.reduce = reduce;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getScope() {
		return scope;
	}
	public void setScope(Integer scope) {
		this.scope = scope;
	}
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public Integer getLeastCost() {
		return leastCost;
	}
	public void setLeastCost(Integer leastCost) {
		this.leastCost = leastCost;
	}
	public Integer getDateType() {
		return dateType;
	}
	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	public Integer getValidLenght() {
		return validLenght;
	}
	public void setValidLenght(Integer validLenght) {
		this.validLenght = validLenght;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}
	public String getWeidianFetchUrl() {
		return weidianFetchUrl;
	}
	public void setWeidianFetchUrl(String weidianFetchUrl) {
		this.weidianFetchUrl = weidianFetchUrl;
	}
	public Integer getOpenGet() {
		return openGet;
	}
	public void setOpenGet(Integer openGet) {
		this.openGet = openGet;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Integer getShowFinish() {
		return showFinish;
	}
	public void setShowFinish(Integer showFinish) {
		this.showFinish = showFinish;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public Integer getBuyerLimit() {
		return buyerLimit;
	}
	public void setBuyerLimit(Integer buyerLimit) {
		this.buyerLimit = buyerLimit;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getGmtCreatedStr() {
		return gmtCreatedStr;
	}
	public void setGmtCreatedStr(String gmtCreatedStr) {
		this.gmtCreatedStr = gmtCreatedStr;
	}
	public String getGmtModifiedStr() {
		return gmtModifiedStr;
	}
	public void setGmtModifiedStr(String gmtModifiedStr) {
		this.gmtModifiedStr = gmtModifiedStr;
	}
	
    
}
