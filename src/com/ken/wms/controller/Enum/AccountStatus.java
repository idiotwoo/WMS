package com.ken.wms.controller.Enum;

/**
 * 
 * @author Ken
 *
 */
public enum AccountStatus {
	SIGNIN("signin"),SIGNOUT("signOut");
	
	private final String value;
	private AccountStatus(final String value){
		this.value = value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
