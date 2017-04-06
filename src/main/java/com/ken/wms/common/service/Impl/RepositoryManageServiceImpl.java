package com.ken.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.common.service.Interface.RepositoryService;
import com.ken.wms.common.util.ExcelUtil;
import com.ken.wms.dao.*;
import com.ken.wms.domain.*;
import com.ken.wms.exception.RepositoryManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库信息管理 service 实现类
 *
 * @author Ken
 */
@Service
public class RepositoryManageServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private ExcelUtil excelUtil;
    @Autowired
    private StockInMapper stockInMapper;
    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private RepositoryAdminMapper repositoryAdminMapper;

    /**
     * 返回指定 repository ID 的仓库记录
     *
     * @param repositoryId 仓库ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectById(Integer repositoryId) throws RepositoryManageServiceException {
        // 初始化結果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Repository> repositories = new ArrayList<>();
        long total = 0;

        // 查詢
        Repository repository;
        try {
            repository = repositoryMapper.selectByID(repositoryId);
        } catch (PersistenceException e) {
            throw new RepositoryManageServiceException(e);
        }

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
     * @param offset  分页的偏移值
     * @param limit   分页的大小
     * @param address 仓库的地址
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByAddress(int offset, int limit, String address) throws RepositoryManageServiceException {
        // 初始化結果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Repository> repositories;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                repositories = repositoryMapper.selectByAddress(address);
                if (repositories != null) {
                    PageInfo<Repository> pageInfo = new PageInfo<>(repositories);
                    total = pageInfo.getTotal();
                } else
                    repositories = new ArrayList<>();
            } else {
                repositories = repositoryMapper.selectByAddress(address);
                if (repositories != null)
                    total = repositories.size();
                else
                    repositories = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new RepositoryManageServiceException(e);
        }

        resultSet.put("data", repositories);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 repository address 的仓库记录 支持模糊查询
     *
     * @param address 仓库名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByAddress(String address) throws RepositoryManageServiceException {
        return selectByAddress(-1, -1, address);
    }

    /**
     * 分页查询仓库记录
     *
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(int offset, int limit) throws RepositoryManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Repository> repositories;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        //query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                repositories = repositoryMapper.selectAll();
                if (repositories != null) {
                    PageInfo<Repository> pageInfo = new PageInfo<>(repositories);
                    total = pageInfo.getTotal();
                } else
                    repositories = new ArrayList<>();
            } else {
                repositories = repositoryMapper.selectAll();
                if (repositories != null)
                    total = repositories.size();
                else
                    repositories = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new RepositoryManageServiceException(e);
        }

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
    public Map<String, Object> selectAll() throws RepositoryManageServiceException {
        return selectAll(-1, -1);
    }

    /**
     * 检查仓库信息是否满足
     *
     * @param repository 仓库信息
     * @return 若仓库信息满足要求则返回true，否则返回false
     */
    private boolean repositoryCheck(Repository repository) {
        return repository.getAddress() != null && repository.getStatus() != null && repository.getArea() != null;
    }

    /**
     * 添加仓库记录
     *
     * @param repository 仓库信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean addRepository(Repository repository) throws RepositoryManageServiceException {

        // 插入一条新的记录
        if (repository != null) {
            try {
                // 有效性验证
                if (repositoryCheck(repository))
                    repositoryMapper.insert(repository);
                if (repository.getId() != null) {
                    return true;
                }
            } catch (PersistenceException e) {
                throw new RepositoryManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 更新仓库记录
     *
     * @param repository 仓库信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateRepository(Repository repository) throws RepositoryManageServiceException {

        // 更新仓库记录
        if (repository != null) {
            // 有效性验证
            try {
                if (repositoryCheck(repository)) {
                    if (repository.getId() != null) {
                        repositoryMapper.update(repository);
                        return true;
                    }
                }
            } catch (PersistenceException e) {
                throw new RepositoryManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 删除仓库记录
     *
     * @param repositoryId 仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteRepository(Integer repositoryId) throws RepositoryManageServiceException {

        try {
            // 检查是否存在出库记录
            List<StockOutDO> stockOutDOList = stockOutMapper.selectByRepositoryID(repositoryId);
            if (stockOutDOList != null && !stockOutDOList.isEmpty())
                return false;

            // 检查是否存在入库记录
            List<StockInDO> stockInDOList = stockInMapper.selectByRepositoryID(repositoryId);
            if (stockInDOList != null && !stockInDOList.isEmpty())
                return false;

            // 检查是否存在库存记录
            List<Storage> storageRecords = storageMapper.selectAllAndRepositoryID(repositoryId);
            if (storageRecords != null && !storageRecords.isEmpty())
                return false;

            // 检查是否已指派仓库管理员
            RepositoryAdmin repositoryAdmin = repositoryAdminMapper.selectByRepositoryID(repositoryId);
            if (repositoryAdmin != null)
                return false;

            // 删除记录
            repositoryMapper.deleteByID(repositoryId);

            return true;
        } catch (PersistenceException e) {
            throw new RepositoryManageServiceException(e);
        }
    }

    /**
     * 从文件中导入仓库信息
     *
     * @param file 导入信息的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    @Override
    public Map<String, Object> importRepository(MultipartFile file) throws RepositoryManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        int total = 0;
        int available = 0;

        // 从文件中读取
        List<Object> repositories = excelUtil.excelReader(Repository.class, file);

        if (repositories != null) {
            total = repositories.size();

            // 验证每一条记录
            Repository repository;
            List<Repository> availableList = new ArrayList<>();
            for (Object object : repositories) {
                repository = (Repository) object;
                if (repository.getAddress() != null && repository.getStatus() != null && repository.getArea() != null)
                    availableList.add(repository);
            }

            // 保存到数据库
            try {
                available = availableList.size();
                if (available > 0)
                    repositoryMapper.insertbatch(availableList);
            } catch (PersistenceException e) {
                throw new RepositoryManageServiceException(e);
            }
        }

        resultSet.put("total", total);
        resultSet.put("available", available);
        return resultSet;
    }

    /**
     * 导出仓库信息到文件中
     *
     * @param repositories 包含若干条 Supplier 信息的 List
     * @return excel 文件
     */
    @Override
    public File exportRepository(List<Repository> repositories) {
        if (repositories == null)
            return null;

        // 导出为文件
        return excelUtil.excelWriter(Repository.class, repositories);
    }

    /**
     * 查询所有未指派仓库管理员的仓库记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectUnassign() throws RepositoryManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Repository> repositories;
        long total = 0;

        // 查询
        try {
            repositories = repositoryMapper.selectUnassign();
        } catch (PersistenceException e) {
            throw new RepositoryManageServiceException(e);
        }
        if (repositories != null)
            total = repositories.size();
        else
            repositories = new ArrayList<>();

        resultSet.put("data", repositories);
        resultSet.put("total", total);
        return resultSet;
    }

}
