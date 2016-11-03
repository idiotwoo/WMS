package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.Menu;

/**
 * 
 * @author Ken
 *
 */
public interface MenuMapper {

	public List<Menu> selectByRoleName(String roleName);
	public List<Menu> selectAll();
}
