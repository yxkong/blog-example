package com.yxkong.dto;

import java.io.Serializable;

/**
 * @Author: yxkong
 * @Date: 2021/7/25 10:35 上午
 * @version: 1.0
 */
public class LoginInfo implements Serializable {
    private Long userId;
    private String mobile;
    private String nickName;
    private long loginTime;
    private String loginSource;
    private int age;
    private long registerTime;

    public LoginInfo(Long userId, String mobile, String nickName, long loginTime, String loginSource, int age, long registerTime) {
        this.userId = userId;
        this.mobile = mobile;
        this.nickName = nickName;
        this.loginTime = loginTime;
        this.loginSource = loginSource;
        this.age = age;
        this.registerTime = registerTime;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public int getAge() {
        return age;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", nickName='" + nickName + '\'' +
                ", loginTime=" + loginTime +
                ", loginSource='" + loginSource + '\'' +
                ", age=" + age +
                ", registerTime=" + registerTime +
                '}';
    }
}