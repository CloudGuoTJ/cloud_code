package com.yunma.entity.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家产品表
 * Created by ${CloudGoo} on 2017/5/8 0008.
 */
public class ProductBatch implements Serializable {

    private Integer productId;		//
    private String productName;		//产品名称
    private Integer vendorId;		//厂商ID
    private String vendorName;		//厂商名称
    private Integer sortNum;		//每个厂商的产品排序
    private Integer productNum;		//产品流水号 每生成一个加1
    private Integer orderCount;		//产品的订单总数量
    private Integer levelOneNum;	//一级防伪码个数
    private String levelOneType;	//功能类别
    private Integer levelTwoNum;	//二级防伪码个数
    private String levelTwoType;	//功能类型
    private Integer levelThreeNum;	//三级防伪码个数
    private String levelThreeType;	//功能类别
    private Integer levelFourNum;	//四级防伪码个数
    private String levelFourType;	//功能类别
    private Integer internalFlag;	//
    private String internalOneType;	//功能类别
    private String internalTwoType;	//功能类别
    private String internalThreeType;//功能类别
    private String advert;			//广告语
    private String comment;			//备注
    private String deleteFlag;		//删除标识 0：未删除，1：删除
    private Date lastUpdateTime;	//最后更新时间
    private int renderCodeType;		//赋码方式1:激光打印2:喷墨3：印刷4：标贴打码
    private int innerMappingRelation;//内码对应方式： 0：非采集； 1： 采集
    private int outerMappingRelation;//
    private int viewType;			//1：微信图文消息,2：web,3:App,4:微信WEB,5:微信文字消息
    private String imgUrl;			//图片保存地址
    private Integer logisticsLogCode;//物流码数量
    private String website;//微信跳转网址
    private int scanCountsInDay; //该产品当天扫码了多少个二维码
    private Integer surveyId; //用户配置的调查问卷id

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getLogisticsLogCode() {
        return logisticsLogCode;
    }

    public void setLogisticsLogCode(Integer logisticsLogCode) {
        this.logisticsLogCode = logisticsLogCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getInnerMappingRelation() {
        return innerMappingRelation;
    }

    public void setInnerMappingRelation(int innerMappingRelation) {
        this.innerMappingRelation = innerMappingRelation;
    }

    public int getOuterMappingRelation() {
        return outerMappingRelation;
    }

    public void setOuterMappingRelation(int outerMappingRelation) {
        this.outerMappingRelation = outerMappingRelation;
    }

    public String getLevelOneType() {
        return levelOneType;
    }

    public void setLevelOneType(String levelOneType) {
        this.levelOneType = levelOneType;
    }

    public String getLevelTwoType() {
        return levelTwoType;
    }

    public void setLevelTwoType(String levelTwoType) {
        this.levelTwoType = levelTwoType;
    }

    public String getLevelThreeType() {
        return levelThreeType;
    }

    public void setLevelThreeType(String levelThreeType) {
        this.levelThreeType = levelThreeType;
    }

    public String getLevelFourType() {
        return levelFourType;
    }

    public void setLevelFourType(String levelFourType) {
        this.levelFourType = levelFourType;
    }

    public String getInternalOneType() {
        return internalOneType;
    }

    public void setInternalOneType(String internalOneType) {
        this.internalOneType = internalOneType;
    }

    public String getInternalTwoType() {
        return internalTwoType;
    }

    public void setInternalTwoType(String internalTwoType) {
        this.internalTwoType = internalTwoType;
    }

    public String getInternalThreeType() {
        return internalThreeType;
    }

    public void setInternalThreeType(String internalThreeType) {
        this.internalThreeType = internalThreeType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getRenderCodeType() {
        return renderCodeType;
    }

    public void setRenderCodeType(int renderCodeType) {
        this.renderCodeType = renderCodeType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getLevelOneNum() {
        return levelOneNum;
    }

    public void setLevelOneNum(Integer levelOneNum) {
        this.levelOneNum = levelOneNum;
    }

    public Integer getLevelTwoNum() {
        return levelTwoNum;
    }

    public void setLevelTwoNum(Integer levelTwoNum) {
        this.levelTwoNum = levelTwoNum;
    }

    public Integer getLevelThreeNum() {
        return levelThreeNum;
    }

    public void setLevelThreeNum(Integer levelThreeNum) {
        this.levelThreeNum = levelThreeNum;
    }

    public Integer getInternalFlag() {
        return internalFlag;
    }

    public void setInternalFlag(Integer internalFlag)
    {
        this.internalFlag = internalFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLevelFourNum() {
        return levelFourNum;
    }

    public void setLevelFourNum(Integer levelFourNum) {
        this.levelFourNum = levelFourNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
        this.advert = advert;
    }

    public int getScanCountsInDay() {
        return scanCountsInDay;
    }

    public void setScanCountsInDay(int scanCountsInDay) {
        this.scanCountsInDay = scanCountsInDay;
    }
}
