package com.ken.wms.domain;

/**
 * 请求资源动作权限信息
 * @author Ken
 *
 */
public class ActionPermission {

	private Integer id;// 动作ID
	private String actionName;// 动作名
	private String actionDesc;// 动作描述
	private String actionParam;// 动作 URL 参数

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
