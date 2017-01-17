package com.ken.wms.domain;

/**
 * 系统的使用用户
 * @author Ken
 *
 */
public class User {

	private Integer id;// 用户ID
	private String userName;// 用户名
	private String password;// 用户密码

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + "]";
	}

}
