package com.yunma.model;

/**
 * Created by ${CloudGoo} on 2017/5/5 0005.
 */
public class DBHost {
    Integer id;
    String dbHost;
    String dbName;
    String dbUser;
    String dbPwd;
    String maxSpace;
    String surplusSpace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurplusSpace() {
        return surplusSpace;
    }

    public void setSurplusSpace(String surplusSpace) {
        this.surplusSpace = surplusSpace;
    }

    public String getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(String maxSpace) {
        this.maxSpace = maxSpace;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }
}
