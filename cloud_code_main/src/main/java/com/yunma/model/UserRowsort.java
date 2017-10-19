package com.yunma.model;

import java.io.Serializable;

/** 用户分组表
 * Created by CloudGoo on 2017/5/2 0002.
 */
public class UserRowsort implements Serializable {
    private  Integer rowsort_id;
    private  Integer row_id;
    private  String row_name;
    public void setRowsort_id(Integer rowsort_id) {
        this.rowsort_id = rowsort_id;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public void setRow_name(String row_name) {
        this.row_name = row_name;
    }
    public Integer getRowsort_id() {
        return rowsort_id;
    }

    public Integer getRow_id() {
        return row_id;
    }

    public String getRow_name() {
        return row_name;
    }
}
