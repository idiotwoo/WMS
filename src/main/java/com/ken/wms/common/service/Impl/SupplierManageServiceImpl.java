package com.ken.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.common.service.Interface.SupplierManageService;
import com.ken.wms.common.util.ExcelUtil;
import com.ken.wms.dao.StockInMapper;
import com.ken.wms.dao.SupplierMapper;
import com.ken.wms.domain.StockInDO;
import com.ken.wms.domain.Supplier;
import com.ken.wms.exception.SupplierManageServiceException;
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
 * 供应商信息管理 service 实现类
 *
 * @author Ken
 */
@Service
public class SupplierManageServiceImpl implements SupplierManageService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private StockInMapper stockInMapper;
    @Autowired
    private ExcelUtil excelUtil;

    /**
     * 返回指定supplierID 的供应商记录
     *
     * @param supplierId 供应商ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectById(Integer supplierId) throws SupplierManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Supplier> suppliers = new ArrayList<>();
        long total = 0;

        // 查询
        Supplier supplier;
        try {
            supplier = supplierMapper.selectById(supplierId);
        } catch (PersistenceException e) {
            throw new SupplierManageServiceException(e);
        }
        if (supplier != null) {
            suppliers.add(supplier);
            total = 1;
        }

        resultSet.put("data", suppliers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 supplierName 的供应商记录 支持查询分页以及模糊查询
     *
     * @param offset       分页的偏移值
     * @param limit        分页德大小
     * @param supplierName 供应商德名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(int offset, int limit, String supplierName) throws SupplierManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Supplier> suppliers;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                suppliers = supplierMapper.selectApproximateByName(supplierName);
                if (suppliers != null) {
                    PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers);
                    total = pageInfo.getTotal();
                } else
                    suppliers = new ArrayList<>();
            } else {
                suppliers = supplierMapper.selectApproximateByName(supplierName);
                if (suppliers != null)
                    total = suppliers.size();
                else
                    suppliers = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new SupplierManageServiceException(e);
        }

        resultSet.put("data", suppliers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 supplierName 的供应商记录 支持模糊查询
     *
     * @param supplierName supplierName 供应商德名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(String supplierName) throws SupplierManageServiceException {
        return selectByName(-1, -1, supplierName);
    }

    /**
     * 分页查询供应商记录
     *
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(int offset, int limit) throws SupplierManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Supplier> suppliers;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                suppliers = supplierMapper.selectAll();
                if (suppliers != null) {
                    PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers);
                    total = pageInfo.getTotal();
                } else
                    suppliers = new ArrayList<>();
            } else {
                suppliers = supplierMapper.selectAll();
                if (suppliers != null)
                    total = suppliers.size();
                else
                    suppliers = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new SupplierManageServiceException(e);
        }

        resultSet.put("data", suppliers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 查询所有的供应商记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll() throws SupplierManageServiceException {
        return selectAll(-1, -1);
    }

    /**
     * 检验供应商信息是否满足要求
     *
     * @param supplier 供应商信息
     * @return 若供应商上的属性均有满足要求则返回true，否则返回false
     */
    private boolean supplierCheck(Supplier supplier) {
        // 检查是否已填写属性
        return supplier.getName() != null && supplier.getPersonInCharge() != null
                && supplier.getTel() != null && supplier.getEmail() != null && supplier.getAddress() != null;
    }

    /**
     * 添加供应商记录
     *
     * @param supplier 供应商信息
     * @return 返回添加结果
     */
    @Override
    public boolean addSupplier(Supplier supplier) throws SupplierManageServiceException {

        // 插入新的记录
        if (supplier != null) {
            try {
                if (supplierCheck(supplier)) {
                    // 检查重名
                    if (null == supplierMapper.selectBuName(supplier.getName())) {
                        supplierMapper.insert(supplier);
                        if (supplier.getId() != null) {
                            return true;
                        }
                    }
                }
            } catch (PersistenceException e) {
                throw new SupplierManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 更新供应商记录
     *
     * @param supplier 供应商信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateSupplier(Supplier supplier) throws SupplierManageServiceException {

        // 更新记录
        if (supplier != null) {
            // 检验
            try {
                if (supplierCheck(supplier)) {
                    if (supplier.getId() != null) {
                        // 检查重名
                        Supplier supplierFromDB = supplierMapper.selectBuName(supplier.getName());
                        if (supplierFromDB == null || supplier.getId().equals(supplierFromDB.getId())) {
                            supplierMapper.update(supplier);
                            return true;
                        }
                    }
                }
            } catch (PersistenceException e) {
                throw new SupplierManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 删除供应商记录
     *
     * @param supplierId 供应商ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteSupplier(Integer supplierId) {

        // 查询该供应商是否有入库记录
        List<StockInDO> records = stockInMapper.selectBySupplierId(supplierId);
        if (records == null || records.size() > 0)
            return false;

        // 删除该条供应商记录
        supplierMapper.deleteById(supplierId);
        return true;
    }

    /**
     * 从文件中导入供应商信息
     *
     * @param file 导入信息的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    @Override
    public Map<String, Object> importSupplier(MultipartFile file) {
        // 初始化结果集
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        int available = 0;

        // 从 Excel 文件中读取
        List<Object> suppliers = excelUtil.excelReader(Supplier.class, file);
        if (suppliers != null) {
            total = suppliers.size();

            // 验证每一条供应商记录
            Supplier supplier;
            List<Supplier> availableList = new ArrayList<>();
            for (Object object : suppliers) {
                supplier = (Supplier) object;
                if (supplierCheck(supplier)) {
                    // 检查重名
                    if (null == supplierMapper.selectBuName(supplier.getName()))
                        availableList.add(supplier);
                }
            }

            // 保存到数据库
            available = availableList.size();
            if (available > 0) {
                supplierMapper.insertBatch(availableList);
            }
        }

        result.put("total", total);
        result.put("available", available);
        return result;
    }

    /**
     * 导出供应商信息到文件中
     *
     * @param suppliers 包含若干条 Supplier 信息的 List
     * @return excel 文件
     */
    @Override
    public File exportSupplier(List<Supplier> suppliers) {
        if (suppliers == null)
            return null;

        return excelUtil.excelWriter(Supplier.class, suppliers);
    }
}
