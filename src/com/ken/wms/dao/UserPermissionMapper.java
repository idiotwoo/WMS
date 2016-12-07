package com.ken.wms.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 用户角色权限映射器
 * @author Ken
 *
 */
public interface UserPermissionMapper {

	public void insert(@Param("userID")Integer userID, @Param("roleID")Integer roleID);
	
	public void deleteByUserID(Integer userID);
}
