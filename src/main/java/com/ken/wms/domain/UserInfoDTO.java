package com.ken.wms.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户账户信息（数据传输对象）
 * @author ken
 * @since 2017/2/26.
 */
public class UserInfoDTO {

    /**
     * 用户ID
     */
    private Integer userID;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码（已加密）
     */
    private String password;

    /**
     * 用户角色
     */
    private List<String> role = new ArrayList<>();

    /**
     * 用户账户属性的 getter 以及 setter
     */

    public String getUserName() {
        return userName;
    }

    public List<String> getRole() {
        return role;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
