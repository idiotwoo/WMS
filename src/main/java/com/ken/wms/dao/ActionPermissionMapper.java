package com.ken.wms.dao;
import com.ken.wms.domain.ActionPermission;

import java.util.List;

/**
 * 
 * @author Ken
 *
 */
public interface ActionPermissionMapper {

	public ActionPermission selectById(Integer id);
	public List<ActionPermission> selectAll();
	public void insert(ActionPermission permission);
	public void update(ActionPermission permission);
	public void delete(ActionPermission permission);
	
	public List<ActionPermission> selectByRole(String roleName);
}
