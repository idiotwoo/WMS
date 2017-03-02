package com.ken.wms.dao;

import com.ken.wms.domain.RolePermissionDO;

import java.util.List;

/**
 * 角色权限信息 Mapper
 *
 * @author ken
 * @since  2017/2/26.
 */
public interface RolePermissionMapper {

    List<RolePermissionDO> selectAll();
}
