package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 管理员用户表
 * Created by CloudGoo on 2017/5/2 0002.
 */
public class UserAdmin implements Serializable {
    private  Integer admin_id;//用户id
    private  String admin_name;//用户名
    private  String admin_pwd;//用户密码
    private  String admin_tel;//电话
    private Integer row_id;//用户组名
    private  String admin_empno;//管理员工号
    private Date login_time;//登录时间
    private  String change_log;//变更日志
    public Integer getAdmin_id() {
        return admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public String getAdmin_pwd() {
        return admin_pwd;
    }

    public String  getAdmin_tel() {
        return admin_tel;
    }

    public Integer getRow_id() {
        return row_id;
    }

    public String getAdmin_empno() {
        return admin_empno;
    }

    public Date getLogin_time() {
        return login_time;
    }

    public String getChange_log() {
        return change_log;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public void setAdmin_pwd(String admin_pwd) {
        this.admin_pwd = admin_pwd;
    }

    public void setAdmin_tel(String admin_tel) {
        this.admin_tel = admin_tel;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public void setAdmin_empno(String admin_empno) {
        this.admin_empno = admin_empno;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public void setChange_log(String change_log) {
        this.change_log = change_log;
    }



}
