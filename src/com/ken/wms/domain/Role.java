package com.ken.wms.domain;

/**
 * 
 * @author Ken
 *
 */
public class Role {

	private Integer id;
	private String roleName;
	private String roleDesc;
	private String rolePrefix;

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
