package com.ken.wms.controller.Enum;

/**
 * 请求的服务器响应结果
 * @author Ken
 *
 */
public enum ResponseStatus {
	
	SUCCESS("success"),// 成功
	ERROR("error");// 失败
	
	private final String text;
	private ResponseStatus(final String text){
		this.text = text;
	}
	
	@Override
	public String toString(){
		return text;
	}
}
