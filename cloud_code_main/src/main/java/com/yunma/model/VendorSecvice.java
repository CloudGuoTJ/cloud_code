package com.yunma.model;

import java.io.Serializable;

/** 商家服务表
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class VendorSecvice implements Serializable {
    private  Integer service_id;
    private  Integer vendor_id;
    private String service_name;
    private Double service_expense;
    private int service_status;
    private String service_explain;

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public void setVendor_id(Integer vendor_id) {
        this.vendor_id = vendor_id;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public void setService_expense(Double service_expense) {
        this.service_expense = service_expense;
    }

    public void setService_status(int service_status) {
        this.service_status = service_status;
    }

    public void setService_explain(String service_explain) {
        this.service_explain = service_explain;
    }


    public Integer getService_id() {
        return service_id;
    }

    public Integer getVendor_id() {
        return vendor_id;
    }

    public String getService_name() {
        return service_name;
    }

    public Double getService_expense() {
        return service_expense;
    }

    public int getService_status() {
        return service_status;
    }

    public String getService_explain() {
        return service_explain;
    }
}
