package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.Repository;

/**
 * Repository 映射器
 * @author Ken
 *
 */
public interface RepositoryMapper {

	/**
	 * 选择全部的 Repository 记录
	 * @return 返回全部的 Repository
	 */
	public List<Repository> selectAll();
	
	/**
	 * 选择全部的未分配的 repository 记录
	 * @return 返回所有未分配的 Repository
	 */
	public List<Repository> selectUnassign();
	
	/**
	 * 选择指定 Repository ID 的 Repository 记录
	 * @param repositoryID 仓库ID
	 * @return 返回指定的Repository
	 */
	public Repository selectByID(Integer repositoryID);
	
	/**
	 * 选择指定 repository Address 的 repository 记录
	 * @param address 仓库地址
	 * @return 返回指定的Repository 
	 */
	public List<Repository> selectByAddress(String address);
	
	/**
	 * 插入一条新的 Repository 记录
	 * @param repository 仓库信息
	 */
	public void insert(Repository repository);
	
	/**
	 * 批量插入 Repository 记录
	 * @param repositories 存有若干条记录的 List
	 */
	public void insertbatch(List<Repository> repositories);
	
	/**
	 * 更新 Repository 记录
	 * @param repository 仓库信息
	 */
	public void update(Repository repository);
	
	/**
	 * 删除指定 Repository ID 的 Repository 记录
	 * @param repositoryID 仓库ID
	 */
	public void deleteByID(Integer repositoryID);
}
