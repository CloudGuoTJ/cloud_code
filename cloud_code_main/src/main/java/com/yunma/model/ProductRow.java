package com.yunma.model;

import java.io.Serializable;

/**
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class ProductRow implements Serializable {
    private  Integer row_id;
    private  Integer vendor_id;
    private  String row_name;
    private  Integer product_num;
    public Integer getRow_id() {
        return row_id;
    }

    public Integer getVendor_id() {
        return vendor_id;
    }

    public String getRow_name() {
        return row_name;
    }

    public Integer getProduct_num() {
        return product_num;
    }



    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public void setVendor_id(Integer vendor_id) {
        this.vendor_id = vendor_id;
    }

    public void setRow_name(String row_name) {
        this.row_name = row_name;
    }

    public void setProduct_num(Integer product_num) {
        this.product_num = product_num;
    }
}
