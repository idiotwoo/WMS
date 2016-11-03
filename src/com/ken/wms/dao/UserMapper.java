package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.User;

/**
 * User 用户信息映射器
 * @author Ken
 *
 */
public interface UserMapper {

	/**
	 * 选择指定 id 的 user 信息
	 * @param id 用户ID
	 * @return
	 */
	public User selectById(Integer id);
	
	/**
	 * 选择指定 userName 的 user 信息
	 * @param userName 用户名
	 * @return
	 */
	public User selectByName(String userName);
	
	/**
	 * 选择全部的 user 信息
	 * @return
	 */
	public List<User> selectAll();

	
	/**
	 * 更新 user 对象信息
	 * @param user
	 */
	public void update(User user);
	
	/**
	 * 删除指定 id 的user 信息
	 * @param id 用户ID
	 */
	public void deleteById(Integer id);
	
	/**
	 * 插入一个 user 对象信息
	 * 不需指定对象的主键id，数据库自动生成
	 * @param user
	 */
	public void insert(User user);
}
