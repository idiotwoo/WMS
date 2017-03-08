package com.ken.wms.dao;

import com.ken.wms.domain.RepositoryAdmin;

import java.util.List;

/**
 * RepositoryAdmin 映射器
 * @author Ken
 *
 */
public interface RepositoryAdminMapper {

	/**
	 * 选择指定 ID 的仓库管理员信息
	 * @param id 仓库管理员ID
	 * @return 返回指定 ID 的仓库管理员信息
	 */
	RepositoryAdmin selectByID(Integer id);
	
	/**
	 * 选择指定 name 的仓库管理员信息。
	 * 支持模糊查找
	 * @param name 仓库管理员名字
	 * @return 返回若干条指定 name 的仓库管理员信息
	 */
	List<RepositoryAdmin> selectByName(String name);
	
	/**
	 * 选择所有的仓库管理员信息
	 * @return 返回所有的仓库管理员信息
	 */
	List<RepositoryAdmin> selectAll();
	
	/**
	 * 选择已指派指定 repositoryID 的仓库管理员信息
	 * @param repositoryID 指派的仓库ID
	 * @return 返回已指派指定 repositoryID 的仓库管理员信息
	 */
	RepositoryAdmin selectByRepositoryID(Integer repositoryID);
	
	/**
	 * 插入一条仓库管理员信息
	 * @param repositoryAdmin 仓库管理员信息
	 */
	void insert(RepositoryAdmin repositoryAdmin);
	
	/**
	 * 批量插入仓库管理员信息
	 * @param repositoryAdmins 存放若干条仓库管理员信息的 List
	 */
	void insertBatch(List<RepositoryAdmin> repositoryAdmins);
	
	/**
	 * 更新仓库管理员信息
	 * @param repositoryAdmin 仓库管理员信息
	 */
	void update(RepositoryAdmin repositoryAdmin);
	
	/**
	 * 删除指定 ID 的仓库管理员信息
	 * @param id 仓库管理员 ID
	 */
	void deleteByID(Integer id);
}
