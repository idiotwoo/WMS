package com.ken.wms.domain;

/**
 * 系统使用的角色信息
 * @author Ken
 *
 */
public class RoleDO {

	private Integer id;// 角色ID
	private String roleName;// 角色名
	private String roleDesc;// 角色描述

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

    @Override
    public String toString() {
        return "RoleDO{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
