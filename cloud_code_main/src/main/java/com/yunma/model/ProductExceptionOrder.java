package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 产品异常订单提示表
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class ProductExceptionOrder implements Serializable {
    private  Integer pro_exc_order_id;
    private String exceptionMsg;
    private Integer exceptionCount;
    private Integer product_id;
    private String productName;
    private Integer vendorId;
    private String vendorName;
    private  Integer order_id;
    private Date createTime;
    private Integer status;
    private Integer isRemind;
    public Integer getPro_exc_order_id() {
        return pro_exc_order_id;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public Integer getExceptionCount() {
        return exceptionCount;
    }

    public Integer getProduct_id() {
        return product_id;
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

    public Integer getOrder_id() {
        return order_id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setPro_exc_order_id(Integer pro_exc_order_id) {
        this.pro_exc_order_id = pro_exc_order_id;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public void setExceptionCount(Integer exceptionCount) {
        this.exceptionCount = exceptionCount;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }



}
