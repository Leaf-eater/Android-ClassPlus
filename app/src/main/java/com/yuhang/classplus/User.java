package com.yuhang.classplus;

/**
 * 用户类，提供用户基本信息的get与set方法
 * Created by 宇航 on 2016/10/7.
 */

public class User {
    private String phone;
    private String pwd;
    private String name;
    private String StuNum;
    private String drom;
    private String access;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuNum() {
        return StuNum;
    }

    public void setStuNum(String stuNum) {
        StuNum = stuNum;
    }

    public String getDrom() {
        return drom;
    }

    public void setDrom(String drom) {
        this.drom = drom;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
