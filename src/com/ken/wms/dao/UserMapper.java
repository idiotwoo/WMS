package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.User;

/**
 * 
 * @author Ken
 *
 */
public interface UserMapper {

	public User selectById(Integer id);
	public User selectByName(String userName);
	public List<User> selectAll();

	public void updateById(User user);
	
	public void deleteById(Integer id);
	
	public void insert(User user);
}
