package com.ken.wms.domain;

/**
 * 
 * @author Ken
 *
 */
public class ActionPermission {

	private Integer id;
	private String actionName;
	private String actionDesc;
	private String actionParam;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getActionParam() {
		return actionParam;
	}

	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	@Override
	public String toString() {
		return "ActionPermission [id=" + id + ", actionName=" + actionName + ", actionDesc=" + actionDesc
				+ ", actionParam=" + actionParam + "]";
	}

}
