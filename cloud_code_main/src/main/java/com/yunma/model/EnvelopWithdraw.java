package com.yunma.model;
import java.util.Date;
/**
 * Created by ${CloudGoo} on 2017/5/8 0008.
 */
public class EnvelopWithdraw {
    private  Integer id;
    private  String  userId;
    private  Integer userAccountId;
    private  String nickName;
    private  String bankSubbranch;
    private  String extractChannelName;
    private  String extractChannelRate;
    private  String payUserName;
    private  String payAccountNumber;
    private  String serialNumber;
    private  Date createTime;
    private  int  status;
    private  Double rateMoney;
    private  String remark;
    private  String bankNo;
    private  String insId;
    private  String statusErrMsg;
    private  String statusSeverity;
    private  String statusCode;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setBankSubbranch(String bankSubbranch) {
        this.bankSubbranch = bankSubbranch;
    }

    public void setExtractChannelName(String extractChannelName) {
        this.extractChannelName = extractChannelName;
    }

    public void setExtractChannelRate(String extractChannelRate) {
        this.extractChannelRate = extractChannelRate;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public void setPayAccountNumber(String payAccountNumber) {
        this.payAccountNumber = payAccountNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRateMoney(Double rateMoney) {
        this.rateMoney = rateMoney;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public void setStatusErrMsg(String statusErrMsg) {
        this.statusErrMsg = statusErrMsg;
    }

    public void setStatusSeverity(String statusSeverity) {
        this.statusSeverity = statusSeverity;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getBankSubbranch() {
        return bankSubbranch;
    }

    public String getExtractChannelName() {
        return extractChannelName;
    }

    public String getExtractChannelRate() {
        return extractChannelRate;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public String getPayAccountNumber() {
        return payAccountNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getStatus() {
        return status;
    }

    public Double getRateMoney() {
        return rateMoney;
    }

    public String getRemark() {
        return remark;
    }

    public String getBankNo() {
        return bankNo;
    }

    public String getInsId() {
        return insId;
    }

    public String getStatusErrMsg() {
        return statusErrMsg;
    }

    public String getStatusSeverity() {
        return statusSeverity;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
