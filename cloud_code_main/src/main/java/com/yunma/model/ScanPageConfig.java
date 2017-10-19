package com.yunma.model;

import com.alibaba.fastjson.JSONObject;
import com.common.util.DateUtils;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

/**  厂商活动信息链接表
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class ScanPageConfig implements Serializable {
    private Integer id;
    private Integer productId;
    private String flashBackgroundImg;//动画背景图片
    private String flashLogoImg;//动画logo
    private String flashProductImg;//动画产品图片
    private String redEnvelopeBackgroundImg;//红包页面背景
    private String redEnvelopeImg;//红包图片
    private String activityDetailImg;//活动说明图片
    private String productImg;//产品图片
    private String vendorDetailImg;//厂商介绍页面图片
    private String vendorMsgImg;//厂商介绍页面介绍信息图片
    private String activityImg;//活动页面图片
    private String buttonImg;//菜单按钮图片
    private Date createTime;//创建时间
    private String time; //日期格式的时间
    private boolean redEnvelopeMenu;//红包菜单
    private boolean integralMenu;//积分菜单
    private boolean productMsgMenu;//产品详情菜单
    private boolean challengeMenu;//全民大挑战菜单
    private boolean customerMenu;//客户服务菜单
    private boolean personalCenterMenu;//个人中心菜单
    private boolean activityMenu;//活动菜单
    private boolean gamesMenu;//互动游戏菜单
    private String cssName;//需要使用的css样式
    private String jsName;//需要使用的js
    private String tracingMenus;//溯源模块菜单json K-V对
    private boolean isDisplayProduct;//非本微信用户扫码，产品页面是否显示产品信息：false、不显示；true、显示（因为此需求少，默认不显示）
    private Map<String, String> tracingMenusMap;

    public Map<String, String> getTracingMenusMap() {
        return tracingMenusMap;
    }

    public void setTracingMenusMap(Map<String, String> tracingMenusMap) {
        this.tracingMenusMap = tracingMenusMap;
    }

    public String getTracingMenus() {
        return tracingMenus;
    }

    public void setTracingMenus(String tracingMenus) {
        this.tracingMenus = tracingMenus;
        this.tracingMenusMap = (Map<String, String>) JSONObject.parse(tracingMenus);
    }

    public boolean isGamesMenu() {
        return gamesMenu;
    }

    public void setGamesMenu(boolean gamesMenu) {
        this.gamesMenu = gamesMenu;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public boolean isActivityMenu() {
        return activityMenu;
    }

    public void setActivityMenu(boolean activityMenu) {
        this.activityMenu = activityMenu;
    }

    public String getCssName() {
        return cssName;
    }

    public void setCssName(String cssName) {
        this.cssName = cssName;
    }

    public String getJsName() {
        return jsName;
    }

    public void setJsName(String jsName) {
        this.jsName = jsName;
    }

    public String getButtonImg() {
        return buttonImg;
    }

    public void setButtonImg(String buttonImg) {
        this.buttonImg = buttonImg;
    }

    public String getVendorMsgImg() {
        return vendorMsgImg;
    }

    public void setVendorMsgImg(String vendorMsgImg) {
        this.vendorMsgImg = vendorMsgImg;
    }

    public boolean isRedEnvelopeMenu() {
        return redEnvelopeMenu;
    }

    public void setRedEnvelopeMenu(boolean redEnvelopeMenu) {
        this.redEnvelopeMenu = redEnvelopeMenu;
    }

    public boolean isIntegralMenu() {
        return integralMenu;
    }

    public void setIntegralMenu(boolean integralMenu) {
        this.integralMenu = integralMenu;
    }

    public boolean isProductMsgMenu() {
        return productMsgMenu;
    }

    public void setProductMsgMenu(boolean productMsgMenu) {
        this.productMsgMenu = productMsgMenu;
    }

    public boolean isChallengeMenu() {
        return challengeMenu;
    }

    public void setChallengeMenu(boolean challengeMenu) {
        this.challengeMenu = challengeMenu;
    }

    public boolean isCustomerMenu() {
        return customerMenu;
    }

    public void setCustomerMenu(boolean customerMenu) {
        this.customerMenu = customerMenu;
    }

    public boolean isPersonalCenterMenu() {
        return personalCenterMenu;
    }

    public void setPersonalCenterMenu(boolean personalCenterMenu) {
        this.personalCenterMenu = personalCenterMenu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getFlashBackgroundImg() {
        return flashBackgroundImg;
    }

    public void setFlashBackgroundImg(String flashBackgroundImg) {
        this.flashBackgroundImg = flashBackgroundImg;
    }

    public String getFlashLogoImg() {
        return flashLogoImg;
    }

    public void setFlashLogoImg(String flashLogoImg) {
        this.flashLogoImg = flashLogoImg;
    }

    public String getFlashProductImg() {
        return flashProductImg;
    }

    public void setFlashProductImg(String flashProductImg) {
        this.flashProductImg = flashProductImg;
    }

    public String getRedEnvelopeBackgroundImg() {
        return redEnvelopeBackgroundImg;
    }

    public void setRedEnvelopeBackgroundImg(String redEnvelopeBackgroundImg) {
        this.redEnvelopeBackgroundImg = redEnvelopeBackgroundImg;
    }

    public String getRedEnvelopeImg() {
        return redEnvelopeImg;
    }

    public void setRedEnvelopeImg(String redEnvelopeImg) {
        this.redEnvelopeImg = redEnvelopeImg;
    }

    public String getActivityDetailImg() {
        return activityDetailImg;
    }

    public void setActivityDetailImg(String activityDetailImg) {
        this.activityDetailImg = activityDetailImg;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getVendorDetailImg() {
        return vendorDetailImg;
    }

    public void setVendorDetailImg(String vendorDetailImg) {
        this.vendorDetailImg = vendorDetailImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTime() {
        return DateUtils.format(createTime);
    }

    public boolean isDisplayProduct() {
        return isDisplayProduct;
    }

    public void setIsDisplayProduct(boolean isDisplayProduct) {
        this.isDisplayProduct = isDisplayProduct;
    }

}
