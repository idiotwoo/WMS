package com.ken.wms.common.service.Interface;


import com.ken.wms.domain.RepositoryAdmin;
import com.ken.wms.exception.RepositoryAdminManageServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 仓库管理员管理 service
 * @author Ken
 *
 */
public interface RepositoryAdminManageService {

	/**
	 * 返回指定repository id 的仓库管理员记录
	 * @param repositoryAdminID 仓库管理员ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByID(Integer repositoryAdminID) throws RepositoryAdminManageServiceException;
	
	/**
	 * 返回所属指定 repositoryID 的仓库管理员信息
	 * @param repositoryID 仓库ID 其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 * @return 返回一个Map，
	 */
	public Map<String, Object> selectByRepositoryID(Integer repositoryID) throws RepositoryAdminManageServiceException;
	
	/**
	 * 返回指定 repository address 的仓库管理员记录
	 * 支持查询分页以及模糊查询
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @param name 仓库管理员的名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(int offset, int limit, String name);
	
	/**
	 * 返回指定 repository Name 的仓库管理员记录
	 * 支持模糊查询
	 * @param name 仓库管理员名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(String name);
	
	/**
	 * 分页查询仓库管理员的记录
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll(int offset, int limit) throws RepositoryAdminManageServiceException;
	
	/**
	 * 查询所有仓库管理员的记录
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll() throws RepositoryAdminManageServiceException;
	
	/**
	 * 添加仓库管理员信息
	 * @param repositoryAdmin 仓库管理员信息
	 * @return 返回一个boolean值，值为true代表添加成功，否则代表失败
	 */
	public boolean addRepositoryAdmin(RepositoryAdmin repositoryAdmin) throws RepositoryAdminManageServiceException;
	
	/**
	 * 更新仓库管理员信息
	 * @param repositoryAdmin 仓库管理员信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean updateRepositoryAdmin(RepositoryAdmin repositoryAdmin) throws RepositoryAdminManageServiceException;
	
	/**
	 * 删除仓库管理员信息
	 * @param repositoryAdminID 仓库管理员ID
	 * @return 返回一个boolean值，值为true代表删除成功，否则代表失败
	 */
	public boolean deleteRepositoryAdmin(Integer repositoryAdminID) throws RepositoryAdminManageServiceException;
	
	/**
	 * 为仓库管理员指派指定 ID 的仓库
	 * @param repositoryAdminID 仓库管理员ID
	 * @param repositoryID 所指派的仓库ID
	 * @return 返回一个 boolean 值，值为 true 表示仓库指派成功，否则表示失败
	 */
	public boolean assignRepository(Integer repositoryAdminID, Integer repositoryID) throws RepositoryAdminManageServiceException;
	
	/**
	 * 从文件中导入仓库管理员信息
	 * @param file 导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	public Map<String, Object> importRepositoryAdmin(MultipartFile file) throws RepositoryAdminManageServiceException;
	
	/**
	 * 导出仓库管理员信息到文件中
	 * @param repositoryAdmins 包含若干条 repository 信息的 List
	 * @return Excel 文件
	 */
	public File exportRepositoryAdmin(List<RepositoryAdmin> repositoryAdmins);
}
