package com.ken.wms.dao;

import com.ken.wms.domain.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色权限映射器
 * @author Ken
 *
 */
public interface UserPermissionMapper {

    /**
     * 为指定 userID 用户指派指定 roleID 的角色
     * @param userID 用户ID
     * @param roleID 角色ID
     */
	void insert(@Param("userID") Integer userID, @Param("roleID") Integer roleID);

    /**
     * 删除指定用户的角色
     * @param userID 用户ID
     */
	void deleteByUserID(Integer userID);

    /**
     * 获取指定 userID 对应用户拥有的角色
     * @param userID 用户ID
     * @return 返回 userID 指定用户的角色
     */
    List<RoleDO> selectUserRole(Integer userID);
}
