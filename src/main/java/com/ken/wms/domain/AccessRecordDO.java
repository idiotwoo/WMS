package com.ken.wms.domain;

import java.util.Date;

/**
 * 用户登入登出记录
 *
 * @author Ken
 * @since 2017/3/5.
 */
public class AccessRecordDO {

    /**
     * 登入登出记录ID
     * 仅当该记录从数据库取出时有效
     */
    private Integer id;

    /**
     * 登陆用户ID
     */
    private Integer userID;

    /**
     * 登陆用户名
     */
    private String userName;

    /**
     * 记录类型，登入或登出
     */
    private String accessType;

    /**
     * 登入或登出时间
     */
    private Date accessTime;

    /**
     * 用户登入或登出对应的IP地址
     */
    private String accessIP;

    public Integer getId() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccessIP() {
        return accessIP;
    }

    public String getAccessType() {
        return accessType;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public void setAccessIP(String accessIP) {
        this.accessIP = accessIP;
    }

    @Override
    public String toString() {
        return "AccessRecordDO{" +
                "id=" + id +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", accessType='" + accessType + '\'' +
                ", accessTime=" + accessTime +
                ", accessIP='" + accessIP + '\'' +
                '}';
    }
}
