package com.ken.wms.dao;
import com.ken.wms.domain.Menu;

import java.util.List;

/**
 * 
 * @author Ken
 *
 */
public interface MenuMapper {

	 List<Menu> selectByRoleName(String roleName);
	 List<Menu> selectAll();
}
