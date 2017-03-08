package com.ken.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.common.service.Interface.StorageManageService;
import com.ken.wms.common.util.ExcelUtil;
import com.ken.wms.dao.GoodsMapper;
import com.ken.wms.dao.RepositoryMapper;
import com.ken.wms.dao.StorageMapper;
import com.ken.wms.domain.Goods;
import com.ken.wms.domain.Repository;
import com.ken.wms.domain.Storage;
import com.ken.wms.exception.StorageManageServiceException;
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
 * 库存信息管理 service 实现类
 *
 * @author Ken
 */
@Service
public class StorageManageServiceImpl implements StorageManageService {

    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private ExcelUtil excelUtil;

    /**
     * 返回所有的库存记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(Integer repository) throws StorageManageServiceException {
        return selectAll(repository, -1, -1);
    }

    /**
     * 分页返回所有的库存记录
     *
     * @param offset 分页偏移值
     * @param limit  分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(Integer repositoryID, int offset, int limit) throws StorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Storage> storageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                storageList = storageMapper.selectAllAndRepositoryID(repositoryID);
                if (storageList != null) {
                    PageInfo<Storage> pageInfo = new PageInfo<>(storageList);
                    total = pageInfo.getTotal();
                } else
                    storageList = new ArrayList<>();
            } else {
                storageList = storageMapper.selectAllAndRepositoryID(repositoryID);
                if (storageList != null)
                    total = storageList.size();
                else
                    storageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }

        resultSet.put("data", storageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定货物ID的库存记录
     *
     * @param goodsID 指定的货物ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer repository) throws StorageManageServiceException {
        return selectByGoodsID(goodsID, repository, -1, -1);
    }

    /**
     * 分页返回指定的货物库存记录
     *
     * @param goodsID 指定的货物ID
     * @param offset  分页偏移值
     * @param limit   分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer repositoryID, int offset, int limit) throws StorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Storage> storageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
                if (storageList != null) {
                    PageInfo<Storage> pageInfo = new PageInfo<>(storageList);
                    total = pageInfo.getTotal();
                } else
                    storageList = new ArrayList<>();
            } else {
                storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
                if (storageList != null)
                    total = storageList.size();
                else
                    storageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }

        resultSet.put("data", storageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定货物名称的库存记录
     *
     * @param goodsName 货物名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer repository) throws StorageManageServiceException {
        return selectByGoodsName(goodsName, repository, -1, -1);
    }

    /**
     * 分页返回指定货物名称的库存记录
     *
     * @param goodsName 货物名称
     * @param offset    分页偏移值
     * @param limit     分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer repositoryID, int offset, int limit) throws StorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Storage> storageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                storageList = storageMapper.selectByGoodsNameAndRepositoryID(goodsName, repositoryID);
                if (storageList != null) {
                    PageInfo<Storage> pageInfo = new PageInfo<>(storageList);
                    total = pageInfo.getTotal();
                } else
                    storageList = new ArrayList<>();
            } else {
                storageList = storageMapper.selectByGoodsNameAndRepositoryID(goodsName, repositoryID);
                if (storageList != null)
                    total = storageList.size();
                else
                    storageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }

        resultSet.put("data", storageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定货物类型的库存记录
     *
     * @param goodsType 指定的货物类型
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsType(String goodsType, Integer repositoryID) throws StorageManageServiceException {
        return selectByGoodsType(goodsType, repositoryID, -1, -1);
    }

    /**
     * 分页返回指定货物类型的库存记录
     *
     * @param goodsType 指定的货物类型
     * @param offset    分页偏移值
     * @param limit     分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByGoodsType(String goodsType, Integer repositoryID, int offset, int limit) throws StorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Storage> storageList;
        long total = 0;
        boolean isPaginatin = true;

        // validate
        if (offset < 0 || limit < 0)
            isPaginatin = false;

        // query
        try {
            if (isPaginatin) {
                PageHelper.offsetPage(offset, limit);
                storageList = storageMapper.selectByGoodsTypeAndRepositoryID(goodsType, repositoryID);
                if (storageList != null) {
                    PageInfo<Storage> pageInfo = new PageInfo<>(storageList);
                    total = pageInfo.getTotal();
                } else
                    storageList = new ArrayList<>();
            } else {
                storageList = storageMapper.selectByGoodsTypeAndRepositoryID(goodsType, repositoryID);
                if (storageList != null)
                    total = storageList.size();
                else
                    storageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }

        resultSet.put("data", storageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean addNewStorage(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException {
        try {
            boolean isAvailable = true;

            // validate
            Goods goods = goodsMapper.selectById(goodsID);
            Repository repository = repositoryMapper.selectByID(repositoryID);
            if (goods == null)
                isAvailable = false;
            if (repository == null)
                isAvailable = false;
            if (number < 0)
                isAvailable = false;
            List<Storage> storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (!(storageList != null && storageList.isEmpty()))
                isAvailable = false;

            if (isAvailable) {
                // insert
                Storage storage = new Storage();
                storage.setGoodsID(goodsID);
                storage.setRepositoryID(repositoryID);
                storage.setNumber(number);
                storageMapper.insert(storage);
            }

            return isAvailable;
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }
    }

    /**
     * 更新一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       更新的库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateStorage(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException {
        try {
            boolean isUpdate = false;

            // validate
            List<Storage> storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (storageList != null && !storageList.isEmpty()) {
                if (number >= 0) {
                    // update
                    Storage storage = storageList.get(0);
                    storage.setNumber(number);
                    storageMapper.update(storage);
                    isUpdate = true;
                }
            }

            return isUpdate;
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }
    }

    /**
     * 删除一条库存记录
     * 货物ID与仓库ID可唯一确定一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteStorage(Integer goodsID, Integer repositoryID) throws StorageManageServiceException {
        try {
            boolean isDelete = false;

            // validate
            List<Storage> storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (storageList != null && !storageList.isEmpty()) {
                // delete
                storageMapper.deleteByRepositoryIDAndGoodsID(goodsID, repositoryID);
                isDelete = true;
            }

            return isDelete;
        } catch (PersistenceException e) {
            throw new StorageManageServiceException(e);
        }
    }

    /**
     * 导入库存记录
     *
     * @param file 保存有的库存记录的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    @Override
    public Map<String, Object> importStorage(MultipartFile file) throws StorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        int total = 0;
        int available = 0;

        // 从文件中读取
        List<Object> storageList = excelUtil.excelReader(Storage.class, file);
        if (storageList != null) {
            total = storageList.size();

            try {
                Storage storage;
                boolean isAvailable;
                List<Storage> availableList = new ArrayList<>();
                Goods goods;
                Repository repository;
                for (Object object : storageList) {
                    isAvailable = true;
                    storage = (Storage) object;

                    // validate
                    goods = goodsMapper.selectById(storage.getGoodsID());
                    repository = repositoryMapper.selectByID(storage.getRepositoryID());
                    if (goods == null)
                        isAvailable = false;
                    if (repository == null)
                        isAvailable = false;
                    if (storage.getNumber() < 0)
                        isAvailable = false;
                    List<Storage> temp = storageMapper.selectByGoodsIDAndRepositoryID(storage.getGoodsID(), storage.getRepositoryID());
                    if (!(temp != null && temp.isEmpty()))
                        isAvailable = false;

                    if (isAvailable) {
                        availableList.add(storage);
                    }
                }
                // 保存到数据库
                available = availableList.size();
                System.out.println(available);
                if (available > 0)
                    storageMapper.insertBatch(availableList);
            } catch (PersistenceException e) {
                throw new StorageManageServiceException(e);
            }
        }

        resultSet.put("total", total);
        resultSet.put("available", available);
        return resultSet;
    }

    /**
     * 导出库存记录
     *
     * @param storageList 保存有库存记录的List
     * @return excel 文件
     */
    @Override
    public File exportStorage(List<Storage> storageList) {
        if (storageList == null)
            return null;
        return excelUtil.excelWriter(Storage.class, storageList);
    }

    /**
     * 为指定的货物库存记录增加指定数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       增加的数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    @Override
    public boolean storageIncrease(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException {

        // 检查货物库存增加数目的有效性
        if (number < 0)
            return false;

        synchronized (this) {
            // 检查对应的库存记录是否存在
            Storage storage = getStorage(goodsID, repositoryID);
            if (storage != null) {
                long newStorage = storage.getNumber() + number;
                updateStorage(goodsID, repositoryID, newStorage);
            } else {
                addNewStorage(goodsID, repositoryID, number);
            }
        }
        return true;
    }

    /**
     * 为指定的货物库存记录减少指定的数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       减少的数量
     * @return 返回一个 boolean 值，若值为 true 表示数目减少成功，否则表示减少失败
     */
    @Override
    public boolean storageDecrease(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException {

        synchronized (this) {
            // 检查对应的库存记录是否存在
            Storage storage = getStorage(goodsID, repositoryID);
            if (null != storage) {
                // 检查库存减少数目的范围是否合理
                if (number < 0 || storage.getNumber() < number)
                    return false;

                long newStorage = storage.getNumber() - number;
                updateStorage(goodsID, repositoryID, newStorage);
                return true;
            } else
                return false;
        }
    }

    /**
     * 获取指定货物ID，仓库ID对应的库存记录
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @return 若存在则返回对应的记录，否则返回null
     */
    private Storage getStorage(Integer goodsID, Integer repositoryID) {
        Storage storage = null;
        List<Storage> storageList = storageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
        if (!storageList.isEmpty())
            storage = storageList.get(0);
        return storage;
    }
}
