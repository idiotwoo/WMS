package com.ken.wms.controller.entity;

/**
 * 
 * @author Ken
 *
 */
public class PasswordModification {

	private String oldPassword;
	private String newPassword;
	private String rePassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public PasswordModification(String oldPassword, String newPassword, String rePassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.rePassword = rePassword;
	}

	public PasswordModification() {
		// TODO Auto-generated constructor stub
	}
}
