package com.ken.wms.domain;

/**
 * 系统使用的角色信息
 * @author Ken
 *
 */
public class Role {

	private Integer id;// 角色ID
	private String roleName;// 角色名
	private String roleDesc;// 角色描述
	private String rolePrefix;// 角色 request URL 的前缀

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", rolePrefix=" + rolePrefix
				+ "]";
	}

}
