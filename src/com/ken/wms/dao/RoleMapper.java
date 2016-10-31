package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.Role;

public interface RoleMapper {

	public Role selectById(Integer id);
	public Role selectByUserId(Integer userId);
	public List<Role> selectAll();
	
	public void insert(Role role);
	
	public void update(Role role);
	
	public void delete(Integer id);
}
