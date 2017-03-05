package com.ken.wms.domain;

import java.util.Date;

/**
 * 用户登入登出记录
 *
 * @author Ken
 * @since 2017/3/5.
 */
public class AccessRecordDO {

    private Integer id;
    private Integer userID;
    private String userName;
    private String accessType;
    private Date accessTime;

    public Integer getId() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
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

    @Override
    public String toString() {
        return "AccessRecordDO{" +
                "id=" + id +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", accessType='" + accessType + '\'' +
                ", accessTime=" + accessTime +
                '}';
    }
}
