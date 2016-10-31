package com.ken.wms.controller.Enum;

/**
 * 
 * @author Ken
 *
 */
public enum AccountType {
	COMMONSADMIN("commonsAdmin"),SYSTEMADMIN("systemAdmin");
	
	private final String value;
	private AccountType(final String value){
		this.value = value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
