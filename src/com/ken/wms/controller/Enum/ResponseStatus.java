package com.ken.wms.controller.Enum;

public enum ResponseStatus {
	SUCCESS("success"),
	ERROR("error");
	
	private final String text;
	private ResponseStatus(final String text){
		this.text = text;
	}
	
	@Override
	public String toString(){
		return text;
	}
}
