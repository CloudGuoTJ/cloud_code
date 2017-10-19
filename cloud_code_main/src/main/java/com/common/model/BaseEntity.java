package com.common.model;

import java.io.Serializable;

/**
 * Entity支持类
 * Created by qun on 2016/9/3.
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
