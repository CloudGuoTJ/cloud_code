package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 商家礼品表
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class VendorPresent implements Serializable {
    private  long pres_id;//礼品id
    private  long order_id;//订单id
    private  long vendor_id;//商家id
    private long product_id;//产品id
    private long securityCodeId;//二维码id
    private  int  present_row;//礼品组
    private  int present_weight;//礼品重量
    private  String presentName;//礼品名称
    private String  present_img;//礼品图片
    private  String present_spe;//礼品规格
    private String present_num;//礼品数量
    private Double present_price;//礼品实际价格
    private int isOnSell;//礼品是否在售
    private Date startTime;//礼品发放时间
    private Date endTime;//礼品失效时间
    private int status;//状态:是否领取
    private String remark;//备注
    private java.util.Date createTime;//创建时间
    public long getPres_id() {
        return pres_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public long getVendor_id() {
        return vendor_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public long getSecurityCodeId() {
        return securityCodeId;
    }

    public int getPresent_row() {
        return present_row;
    }

    public int getPresent_weight() {
        return present_weight;
    }

    public String getPresentName() {
        return presentName;
    }

    public String getPresent_img() {
        return present_img;
    }

    public String getPresent_spe() {
        return present_spe;
    }

    public String getPresent_num() {
        return present_num;
    }

    public Double getPresent_price() {
        return present_price;
    }

    public int getIsOnSell() {
        return isOnSell;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setPres_id(long pres_id) {
        this.pres_id = pres_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public void setVendor_id(long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public void setSecurityCodeId(long securityCodeId) {
        this.securityCodeId = securityCodeId;
    }

    public void setPresent_row(int present_row) {
        this.present_row = present_row;
    }

    public void setPresent_weight(int present_weight) {
        this.present_weight = present_weight;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public void setPresent_img(String present_img) {
        this.present_img = present_img;
    }

    public void setPresent_spe(String present_spe) {
        this.present_spe = present_spe;
    }

    public void setPresent_num(String present_num) {
        this.present_num = present_num;
    }

    public void setPresent_price(Double present_price) {
        this.present_price = present_price;
    }

    public void setIsOnSell(int isOnSell) {
        this.isOnSell = isOnSell;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }




}
