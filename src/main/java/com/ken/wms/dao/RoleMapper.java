package com.ken.wms.dao;

import com.ken.wms.domain.Role;

import java.util.List;

public interface RoleMapper {

	public Role selectById(Integer id);
	public Role selectByUserId(Integer userId);
	public List<Role> selectAll();
	
	public void insert(Role role);
	
	public void update(Role role);
	
	public void delete(Integer id);
}
