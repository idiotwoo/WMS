package com.ken.wms.common.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.common.service.Interface.CustomerManageService;
import com.ken.wms.common.util.ExcelUtil;
import com.ken.wms.dao.CustomerMapper;
import com.ken.wms.dao.StockOutMapper;
import com.ken.wms.domain.Customer;
import com.ken.wms.domain.StockOutDO;
import com.ken.wms.exception.CustomerManageServiceException;
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
 * 客户信息管理 service 实现类
 *
 * @author Ken
 */
@Service
public class CustomerManageServiceImpl implements CustomerManageService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ExcelUtil excelUtil;
    @Autowired
    private StockOutMapper stockOutMapper;

    /**
     * 返回指定customer id 的客户记录
     *
     * @param customerId 客户ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectById(Integer customerId) throws CustomerManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Customer> customers = new ArrayList<>();
        long total = 0;

        // 查询
        Customer customer;
        try {
            customer = customerMapper.selectById(customerId);
        } catch (PersistenceException e) {
            System.out.println("exception catch");
            e.printStackTrace();
            throw new CustomerManageServiceException(e);
        }

        if (customer != null) {
            customers.add(customer);
            total = 1;
        }

        resultSet.put("data", customers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 customer name 的客户记录 支持查询分页以及模糊查询
     *
     * @param offset       分页的偏移值
     * @param limit        分页的大小
     * @param customerName 客户的名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(int offset, int limit, String customerName) throws CustomerManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Customer> customers;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                customers = customerMapper.selectApproximateByName(customerName);
                if (customers != null) {
                    PageInfo<Customer> pageInfo = new PageInfo<>(customers);
                    total = pageInfo.getTotal();
                } else
                    customers = new ArrayList<>();
            } else {
                customers = customerMapper.selectApproximateByName(customerName);
                if (customers != null)
                    total = customers.size();
                else
                    customers = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new CustomerManageServiceException(e);
        }

        resultSet.put("data", customers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 customer Name 的客户记录 支持模糊查询
     *
     * @param customerName 客户名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(String customerName) throws CustomerManageServiceException {
        return selectByName(-1, -1, customerName);
    }

    /**
     * 分页查询客户的记录
     *
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(int offset, int limit) throws CustomerManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Customer> customers;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                customers = customerMapper.selectAll();
                if (customers != null) {
                    PageInfo<Customer> pageInfo = new PageInfo<>(customers);
                    total = pageInfo.getTotal();
                } else
                    customers = new ArrayList<>();
            } else {
                customers = customerMapper.selectAll();
                if (customers != null)
                    total = customers.size();
                else
                    customers = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new CustomerManageServiceException(e);
        }

        resultSet.put("data", customers);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 查询所有客户的记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll() throws CustomerManageServiceException {
        return selectAll(-1, -1);
    }

    /**
     * 检查客户信息是否满足要求
     *
     * @param customer 客户信息实体
     * @return 返回是否满足要求
     */
    private boolean customerCheck(Customer customer) {
        return customer.getName() != null && customer.getPersonInCharge() != null && customer.getTel() != null
                && customer.getEmail() != null && customer.getAddress() != null;
    }

    /**
     * 添加客户信息
     *
     * @param customer 客户信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    public boolean addCustomer(Customer customer) throws CustomerManageServiceException {

        // 插入新的记录
        if (customer != null) {
            // 验证
            if (customerCheck(customer)) {
                try {
                    if (null == customerMapper.selectByName(customer.getName())) {
                        customerMapper.insert(customer);
                        return true;
                    }
                } catch (PersistenceException e) {
                    throw new CustomerManageServiceException(e);
                }
            }
        }
        return false;
    }

    /**
     * 更新客户信息
     *
     * @param customer 客户信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateCustomer(Customer customer) throws CustomerManageServiceException {

        // 更新记录
        if (customer != null) {
            // 检验
            if (customerCheck(customer)) {
                try {
                    // 检查重名
                    Customer customerFromDB = customerMapper.selectByName(customer.getName());
                    if (customerFromDB == null || customerFromDB.getId().equals(customer.getId())) {
                        customerMapper.update(customer);
                        return true;
                    }
                } catch (PersistenceException e) {
                    throw new CustomerManageServiceException(e);
                }
            }
        }
        return false;
    }

    /**
     * 删除客户信息
     *
     * @param customerId 客户ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteCustomer(Integer customerId) throws CustomerManageServiceException {

        try {
            // 查询该客户是否有出库记录
            List<StockOutDO> records = stockOutMapper.selectByCustomerId(customerId);
            if (records != null && records.size() > 0) {
                return false;
            } else {
                // 删除该条客户记录
                customerMapper.deleteById(customerId);
                return true;

            }
        } catch (PersistenceException e) {
            throw new CustomerManageServiceException(e);
        }
    }

    /**
     * 从文件中导入客户信息
     *
     * @param file 导入信息的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    public Map<String, Object> importCustomer(MultipartFile file) throws CustomerManageServiceException {
        // 初始化结果集
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        int available = 0;

        // 从 Excel 文件中读取
        List<Object> customers = excelUtil.excelReader(Customer.class, file);
        if (customers != null) {
            total = customers.size();

            // 验证每一条记录
            try {
                Customer customer;
                List<Customer> availableList = new ArrayList<>();
                for (Object object : customers) {
                    customer = (Customer) object;
                    if (customerCheck(customer)) {
                        if (customerMapper.selectByName(customer.getName()) == null)
                            availableList.add(customer);
                    }
                }

                // 保存到数据库
                available = availableList.size();
                if (available > 0) {
                    customerMapper.insertBatch(availableList);
                }
            } catch (PersistenceException e) {
                throw new CustomerManageServiceException(e);
            }
        }

        result.put("total", total);
        result.put("available", available);
        return result;
    }

    /**
     * 导出客户信息到文件中
     *
     * @param customers 包含若干条 customer 信息的 List
     * @return Excel 文件
     */
    @Override
    public File exportCustomer(List<Customer> customers) {
        if (customers == null)
            return null;

        return excelUtil.excelWriter(Customer.class, customers);
    }

}
