package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;
/**红包规则
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class EnvelopeVendorRedenvelopeRule implements Serializable {
    private Integer id;
    private Integer accountId;
    private Integer tacticsId;//
    private String ruleName;
    private Integer productId;//
    private String productName;//
    private Integer vendorId;
    private String vendorName;
    private Double max_set_money;//
    private Double min_set_money;//最低金额
    private Integer orderId;//订单id
    private Double winningRate; //中奖率
    private Integer grant_manner_type;//使用规则类型
    private Date createTime;//创建的时间
    private Date lastUpdateTime;
    private int ruleType; //规则类型：1、层次红包规则；2、定量红包规则
    private int level;//红包层级
    private double oneWinningRate;//层次红包的时候填写：一等奖中奖率
    private double oneMaxMoney;//
    private double oneMinMoney;//
    private double twoWinningRate;
    private double twoMaxMoney;
    private double twoMinMoney;
    private double threeWinningRate;
    private double threeMaxMoney;
    private double threeMinMoney;
    private double fourWinningRate;
    private double fourMaxMoney;
    private double fourMinMoney;
    private double fiveWinningRate;
    private double fiveMaxMoney;
    private double fiveMinMoney;
    private int oneCheckedNum;//规则二适用：一等奖，中奖数
    private int twoCheckedNum;//规则二适用：二等奖，中奖数
    private int threeCheckedNum;//规则二适用：三等奖，中奖数
    private String sendName;//发送名称
    private String wishing;//祝福语
    public Integer getId() {
        return id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Integer getTacticsId() {
        return tacticsId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public Double getMax_set_money() {
        return max_set_money;
    }

    public Double getMin_set_money() {
        return min_set_money;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Double getWinningRate() {
        return winningRate;
    }

    public Integer getGrant_manner_type() {
        return grant_manner_type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public int getRuleType() {
        return ruleType;
    }

    public int getLevel() {
        return level;
    }

    public double getOneWinningRate() {
        return oneWinningRate;
    }

    public double getOneMaxMoney() {
        return oneMaxMoney;
    }

    public double getOneMinMoney() {
        return oneMinMoney;
    }

    public double getTwoWinningRate() {
        return twoWinningRate;
    }

    public double getTwoMaxMoney() {
        return twoMaxMoney;
    }

    public double getTwoMinMoney() {
        return twoMinMoney;
    }

    public double getThreeWinningRate() {
        return threeWinningRate;
    }

    public double getThreeMaxMoney() {
        return threeMaxMoney;
    }

    public double getThreeMinMoney() {
        return threeMinMoney;
    }

    public double getFourWinningRate() {
        return fourWinningRate;
    }

    public double getFourMaxMoney() {
        return fourMaxMoney;
    }

    public double getFourMinMoney() {
        return fourMinMoney;
    }

    public double getFiveWinningRate() {
        return fiveWinningRate;
    }

    public double getFiveMaxMoney() {
        return fiveMaxMoney;
    }

    public double getFiveMinMoney() {
        return fiveMinMoney;
    }

    public int getOneCheckedNum() {
        return oneCheckedNum;
    }

    public int getTwoCheckedNum() {
        return twoCheckedNum;
    }

    public int getThreeCheckedNum() {
        return threeCheckedNum;
    }

    public String getSendName() {
        return sendName;
    }

    public String getWishing() {
        return wishing;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setTacticsId(Integer tacticsId) {
        this.tacticsId = tacticsId;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setMax_set_money(Double max_set_money) {
        this.max_set_money = max_set_money;
    }

    public void setMin_set_money(Double min_set_money) {
        this.min_set_money = min_set_money;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setWinningRate(Double winningRate) {
        this.winningRate = winningRate;
    }

    public void setGrant_manner_type(Integer grant_manner_type) {
        this.grant_manner_type = grant_manner_type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setOneWinningRate(double oneWinningRate) {
        this.oneWinningRate = oneWinningRate;
    }

    public void setOneMaxMoney(double oneMaxMoney) {
        this.oneMaxMoney = oneMaxMoney;
    }

    public void setOneMinMoney(double oneMinMoney) {
        this.oneMinMoney = oneMinMoney;
    }

    public void setTwoWinningRate(double twoWinningRate) {
        this.twoWinningRate = twoWinningRate;
    }

    public void setTwoMaxMoney(double twoMaxMoney) {
        this.twoMaxMoney = twoMaxMoney;
    }

    public void setTwoMinMoney(double twoMinMoney) {
        this.twoMinMoney = twoMinMoney;
    }

    public void setThreeWinningRate(double threeWinningRate) {
        this.threeWinningRate = threeWinningRate;
    }

    public void setThreeMaxMoney(double threeMaxMoney) {
        this.threeMaxMoney = threeMaxMoney;
    }

    public void setThreeMinMoney(double threeMinMoney) {
        this.threeMinMoney = threeMinMoney;
    }

    public void setFourWinningRate(double fourWinningRate) {
        this.fourWinningRate = fourWinningRate;
    }

    public void setFourMaxMoney(double fourMaxMoney) {
        this.fourMaxMoney = fourMaxMoney;
    }

    public void setFourMinMoney(double fourMinMoney) {
        this.fourMinMoney = fourMinMoney;
    }

    public void setFiveWinningRate(double fiveWinningRate) {
        this.fiveWinningRate = fiveWinningRate;
    }

    public void setFiveMaxMoney(double fiveMaxMoney) {
        this.fiveMaxMoney = fiveMaxMoney;
    }

    public void setFiveMinMoney(double fiveMinMoney) {
        this.fiveMinMoney = fiveMinMoney;
    }

    public void setOneCheckedNum(int oneCheckedNum) {
        this.oneCheckedNum = oneCheckedNum;
    }

    public void setTwoCheckedNum(int twoCheckedNum) {
        this.twoCheckedNum = twoCheckedNum;
    }

    public void setThreeCheckedNum(int threeCheckedNum) {
        this.threeCheckedNum = threeCheckedNum;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }


}
