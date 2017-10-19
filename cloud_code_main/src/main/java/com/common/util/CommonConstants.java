package com.common.util;

import java.util.ArrayList;
import java.util.List;

import com.yunma.entity.agent.AgentEntity;

/**
 *设定用户类型
 */
public enum CommonConstants {


    //用户类型 99：超级管理员 98：次级管理员 1：厂商用户 2：代理用户
    User_Type_ProductVendor(1,"厂商管理员用户"),
    User_Type_LogisticsVendor(2,"代理用户"),
    User_Type_FindProductVendor(3,"厂商管理员助理用户"),
    User_Type_FinanceProductVendor(4,"厂商财务用户"),
    User_Type_MarketProductVendor(5,"厂商市场用户"),
    User_Type_ProductionProductVendor(6,"厂商生产用户"),
    User_Type_SecondaryAdmin(98,"次级管理员"),
    User_Type_SuperAdmin(99,"超级管理员"),
    rsa_private_key(3,"MIICXAIBAAKBgQCyM+wP/4dXcg3nJ+QRABRMXCGQ6TZ6+SYS9OEjDOqL4/SwV+87u4ZEGRpUDLozdwV1egZu87h/x3v4PTmJRFs8pc4b5XxBSKSTgi2qTQNTamgdpWVgZ5OddjNUJQtgnkklwQTyo+3Wnp/0CjmBYdlUxs5NWr3TcC0XdV1yt+Y+mwIDAQABAoGAew/UM3AZPNAsvbcUjpDpc5okK/iBgQbRvkGRwCzJRHXVonn3Yd6saBaV0flAFQ5anLf5/WEdxlAyUkmllB+6iM4rRmDf+pZVAGb1RJdbvDL9Wo5sxe9jVenJAo3RmryewxgSusUTHDkYbI70eCBKbrBiNzCaw2nJDQW52/tfHiECQQDcUS4jdvBzWhs7YCWAcbfEHIj+MUVW7pSt30wCNR+CNZZONcCrAvUXUtOWivlD+Kl3InlDdWT9HbCAAUCNuU1pAkEAzxCX2MTefeO+kF9g9Muw5wfAQR6b8NwHxPOnth5wXMoERbHG38Ic+gtmkqaVe/mtLz5Gz4OZD+ZatqXMqIX3YwJBAMgPM31iyM+GeAn/sa4kFUIQw1lHdnaNKReGGCg9AwKjFHyqlOz1P4fRX29pfRX63C/hbwoRtQWfiyGBgZPwO1kCQEiYl7Q/KYZXj5tMZVDODUKinAv4sFGcwviROG6Ic3qEtUS+FGykwMYFszudPfGOUuKqYeclS0egypMYxA0dkuMCQD7ZzY2vzVi0MfPKzdGwcn80BDv/i/u1aMAzUpqwiPfpomRw6W5qpI9eT5EoncDEgbrtUR5m8tcAuvGX+Mn3gvw="),
    pingxx_public_key(4,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3i44Qjz/ZcgB8dR1IEa/7QWzRPLxYrdwx7n7uwM1+JIMsypoDGOp81CLxlItk4WpK+lHwxHrlF4YlCw8+tXt2mOB/j69ULbGtsxgZ99LPpkt9CFWA2vlh3palduQHGGPPKyK0Bq9z03GM8CD6SbVG6qKf1huo7XlF0zZduVUyHL1ZCRQyXAdSTRaezVMm3GDzaHkXiNSbslzA4hNJ85EiWf28xVElop5gXY5kNLHe20Iv1GWm/oAZ9HzsVeYLFMsr08GXVRxkRrqlsfqxJo6IaD/v3HREsqJX82tuysBiVbgKzkPBdKPPen6lz8k0Oq7LJF3jUb/rlzCbt532CqVYwIDAQAB"),
    End(0,null);

    private int state;
    private String dsc;
    
    public static List<AgentEntity> list=new ArrayList<AgentEntity>();

    private CommonConstants(int state, String dsc) {
        this.state = state;
        this.dsc = dsc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    /**
     * 十天后缓存失效
     */
    public static int TIME_DAY_10 = (int) (10 * 24 * 60 * 60 + Math.random() * 600);
    /**
     * 一天后缓存失效
     */
    public static int TIME_DAY_1 = (int) (24 * 60 * 60 + Math.random() * 600);
    /**
     * 三小时失效
     */
    public static int TIME_HOUR_3 = (int) (30 * 60 * 6 + Math.random() * 600);
    
    /**
     * 30分钟后缓存失效
     */
    public static int TIME_MINUTE_30 = (int) (30 * 60 + Math.random() * 600);
    
    /**
     * 5分钟后缓存失效
     */
    public static int TIME_MINUTE_5 = (int) (5 * 60 + Math.random() * 180);
    /**
     * 1分钟后缓存失效
     */
    public static int TIME_MINUTE_1 = (int) (60 + Math.random() * 30);

    /**
     * jsonp接收数据的格式
     */
    public static final String CHARSET = ";charset=UTF-8";

    /**
     * 红包规则类型：1、层次红包规则；2、定量红包规则
     */
    public static final int RULE_TYPE_ONE = 1;
    public static final int RULE_TYPE_TOW = 2;

    /**
     * 积分奖励类型：1、红包奖励；2、实物奖励；3、虚拟积分
     */
    public static final int INTEGRAL_REWARD_ONE = 1;
    public static final int INTEGRAL_REWARD_TWO = 2;
    public static final int INTEGRAL_REWARD_THREE = 3;

    /**
     * 积分兑奖方式：1、扫描得奖；2、自助兑奖
     */
    public static final int INTEGRAL_CONVERSION_ONE = 1;
    public static final int INTEGRAL_CONVERSION_TWO = 2;

    /**
     * 积分方式：1、积分累加；2、兑奖清零
     */
    public static final int INTEGRAL_TYPE_ONE = 1;
    public static final int INTEGRAL_TYPE_TWO = 2;

    /**
     * 挑战模式发送状态：1、未发送；2、已发送
     */
    public static final int CHALLENGE_AWARD_SEND_STATUS_ONE = 1;
    public static final int CHALLENGE_AWARD_SEND_STATUS_TWO = 2;

    /**
     * 二维码状态：1、外码；2、内码
     */
    public static final int SECURITYCODE_INTERNALCODEFLAG_OUTSIDE = 1;
    public static final int SECURITYCODE_INTERNALCODEFLAG_INSIDE = 2;

    //批码表名
    public static final String BATCH_CODE = "t_batch_code_";
    //图片存储文件夹
    public static final String IMAGE_CONTENT = "/imageContent";
    // 批码state
    public static final String BATCH_CODE_STATE = "batchCode";


    /*
       是否配置微信公众号信息1:公众号2：厂商自有公众号配置
     */
    public static final int product_isWxConfig_false = 1;
    public static final int product_isWxConfig_true = 2;

    /**
       默认公众号使用公众号信息厂商ID-1
     */
    public static final int isWxConfig_vendorId_default = -1;

    /**
     * 微信用户来源
     */
    public static final String wechat_user_scanSource_web = "微信网页";
    public static final String wechat_user_scanSource_official = "微信公众号";


    /**
     * ngix路由表默认RouteId
     */
    public static final Integer product_vendor_config_default_RouteId = 434;//默认机器编号
    public static final Integer product_vendor_config_init_RouteId= 433;//初始机器编号



}
