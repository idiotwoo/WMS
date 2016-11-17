package com.ken.wms.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.dao.RepositoryMapper;
import com.ken.wms.domain.Repository;
import com.ken.wms.service.Interface.RepositoryService;
import com.ken.wms.service.util.ExcelUtil;

/**
 * 仓库信息管理 Service 实现类
 * @author Ken
 *
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

	private Logger log = Logger.getLogger("application");
	
	@Autowired
	private RepositoryMapper repositoryMapper;
	@Autowired
	private ExcelUtil excelUtil;

	/**
	 * 返回指定 repository ID 的仓库记录
	 * 
	 * @param repositoryId
	 *            仓库ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectById(Integer repositoryId) {
		// 初始化結果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Repository> repositories = new ArrayList<>();
		long total = 0;

		// 查詢
		Repository repository = repositoryMapper.selectByID(repositoryId);
		if (repository != null) {
			repositories.add(repository);
			total = 1;
		}

		resultSet.put("data", repositories);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 返回指定 repository address 的仓库记录 支持查询分页以及模糊查询
	 * 
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页的大小
	 * @param address
	 *            仓库的地址
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByAddress(int offset, int limit, String address) {
		// 初始化結果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Repository> repositories = null;
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		repositories = repositoryMapper.selectByAddress(address);
		if (repositories != null) {
			PageInfo<Repository> pageInfo = new PageInfo<>(repositories);
			total = pageInfo.getTotal();
		} else
			repositories = new ArrayList<>();

		resultSet.put("data", repositories);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 返回指定 repository address 的仓库记录 支持模糊查询
	 * 
	 * @param address
	 *            仓库名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByAddress(String address) {
		// 初始化結果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Repository> repositories = null;
		long total = 0;

		// 查询
		repositories = repositoryMapper.selectByAddress(address);
		if (repositories != null) {
			total = repositories.size();
		} else
			repositories = new ArrayList<>();

		resultSet.put("data", repositories);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 分页查询仓库记录
	 * 
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页的大小
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectAll(int offset, int limit) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Repository> repositories = null;
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		repositories = repositoryMapper.selectAll();
		if (repositories != null) {
			PageInfo<Repository> pageInfo = new PageInfo<>(repositories);
			total = pageInfo.getTotal();
		} else
			repositories = new ArrayList<>();

		resultSet.put("data", repositories);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 查询所有的仓库记录
	 * 
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectAll() {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Repository> repositories = null;
		long total = 0;

		// 查询
		repositories = repositoryMapper.selectAll();
		if (repositories != null) {
			total = repositories.size();
		} else
			repositories = new ArrayList<>();

		resultSet.put("data", repositories);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加仓库记录
	 * 
	 * @param repository
	 *            仓库信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean addRepository(Repository repository) {

		// 插入一条新的记录
		if (repository != null) {
			// 有效性验证
			if (repository.getAddress() != null && repository.getStatus() != null && repository.getArea() != null)
				repositoryMapper.insert(repository);
			if (repository.getId() != null){
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新仓库记录
	 * 
	 * @param repository
	 *            仓库信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean updateRepository(Repository repository) {

		// 更新仓库记录
		if (repository != null) {
			// 有效性验证
			if (repository.getId() != null && repository.getAddress() != null && repository.getStatus() != null
					&& repository.getArea() != null)
				repositoryMapper.update(repository);
			return true;
		}
		return false;
	}

	/**
	 * 删除仓库记录
	 * 
	 * @param repositoryId
	 *            仓库ID
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean deleteRepository(Integer repositoryId) {
	
		// 查询是否由出入库记录
		
		// 查询是否有仓存记录
		
		// 删除记录
		repositoryMapper.deleteByID(repositoryId);
		
		return true;
	}

	/**
	 * 从文件中导入仓库信息
	 * 
	 * @param file
	 *            导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	@Override
	public Map<String, Object> importRepository(MultipartFile file) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		int total = 0;
		int available = 0;
		
		// 从文件中读取
		List<Object> repositories = excelUtil.excelReader(Repository.class, file);
		
		if(repositories != null){
			total = repositories.size();
			
			// 验证每一条记录
			Repository repository;
			List<Repository> availableList = new ArrayList<>();
			for (Object object : repositories) {
				repository = (Repository) object;
				if(repository.getAddress() != null && repository.getStatus() != null && repository.getArea() != null)
					availableList.add(repository);
			}
			
			// 保存到数据库
			available = availableList.size();
			if(available > 0)
				repositoryMapper.insertbatch(availableList);
		}
		
		resultSet.put("total", total);
		resultSet.put("available", available);
		return resultSet;
	}

	/**
	 * 导出仓库信息到文件中
	 * 
	 * @param repositorys
	 *            包含若干条 Supplier 信息的 List
	 * @return excel 文件
	 */
	@Override
	public File exportRepository(List<Repository> repositories) {
		if(repositories == null)
			return null;
		
		// 导出为文件
		File file = excelUtil.excelWriter(Repository.class, repositories);
		
		return file;
	}

}
