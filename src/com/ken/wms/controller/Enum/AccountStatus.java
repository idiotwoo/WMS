package com.ken.wms.controller.Enum;

/**
 * 用户的登陆状态
 * @author Ken
 *
 */
public enum AccountStatus {
	
	SIGNIN("signin"),// 已登陆
	SIGNOUT("signOut");// 已注销
	
	private final String value;
	private AccountStatus(final String value){
		this.value = value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
