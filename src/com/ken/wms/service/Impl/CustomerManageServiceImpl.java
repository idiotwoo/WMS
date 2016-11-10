package com.ken.wms.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.dao.CustomerMapper;
import com.ken.wms.dao.StockOutMapper;
import com.ken.wms.domain.Customer;
import com.ken.wms.domain.StockOut;
import com.ken.wms.service.Interface.CustomerManageService;
import com.ken.wms.service.util.ExcelUtil;

/**
 * 客户信息管理 Service 实现类
 * 
 * @author Ken
 *
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
	 * @param customerId
	 *            客户ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectById(Integer customerId) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Customer> customers = new ArrayList<>();
		long total = 0;

		// 查询
		Customer customer = customerMapper.selectById(customerId);
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
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页的大小
	 * @param customerName
	 *            客户的名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByName(int offset, int limit, String customerName) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Customer> customers = new ArrayList<>();
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		customers = customerMapper.selectApproximateByName(customerName);
		if (customers != null) {
			PageInfo<Customer> pageInfo = new PageInfo<>(customers);
			total = pageInfo.getTotal();
		}

		resultSet.put("data", customers);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 返回指定 customer Name 的客户记录 支持模糊查询
	 * 
	 * @param customerName
	 *            客户名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByName(String customerName) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Customer> customers = new ArrayList<>();
		long total = 0;

		customers = customerMapper.selectApproximateByName(customerName);
		if (customers != null) {
			total = customers.size();
		}

		resultSet.put("data", customers);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 分页查询客户的记录
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
		List<Customer> customers = new ArrayList<>();
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		customers = customerMapper.selectAll();
		if (customers != null) {
			PageInfo<Customer> pageInfo = new PageInfo<>(customers);
			total = pageInfo.getTotal();
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
	public Map<String, Object> selectAll() {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Customer> customers = new ArrayList<>();
		long total = 0;

		customers = customerMapper.selectAll();
		if (customers != null) {
			total = customers.size();
		}
		resultSet.put("data", customers);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加客户信息
	 * 
	 * @param customer
	 *            客户信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean addCustomer(Customer customer) {

		// 插入新的记录
		if (customer != null) {
			// 验证
			if (customer.getName() != null && customer.getPersonInCharge() != null && customer.getTel() != null
					&& customer.getEmail() != null && customer.getAddress() != null) {
				customerMapper.insert(customer);
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新客户信息
	 * @param customer 客户信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean updateCustomer(Customer customer) {
		
		// 更新记录
		if(customer != null){
			// 检验
			if (customer.getName() != null && customer.getPersonInCharge() != null && customer.getTel() != null
					&& customer.getEmail() != null && customer.getAddress() != null) {
				customerMapper.update(customer);
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除客户信息
	 * @param customerId 客户ID
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean deleteCustomer(Integer customerId) {
		
		// 查询该客户是否有出库记录
		List<StockOut> records = stockOutMapper.selectByCustomerId(customerId);
		if(records != null && records.size() > 0)
			return false;
		
		// 删除该条客户记录
		customerMapper.deleteById(customerId);
		return true;
	}

	/**
	 * 从文件中导入客户信息
	 * @param file 导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	@Override
	public Map<String, Object> importCustomer(MultipartFile file) {
		// 初始化结果集
		Map<String, Object> result = new HashMap<>();
		int total = 0;
		int available = 0;
		
		// 从 Excel 文件中读取
		List<Object> customers = excelUtil.excelReader(Customer.class, file);
		if(customers != null){
			total = customers.size();
			
			// 验证每一条记录
			Customer customer;
			List<Customer> availableList = new ArrayList<>();
			for (Object object : customers) {
				customer = (Customer) object;
				if (customer.getName() != null && customer.getPersonInCharge() != null && customer.getTel() != null
						&& customer.getEmail() != null && customer.getAddress() != null) {
					if(customerMapper.selectByName(customer.getName()) == null)
						availableList.add(customer);
				}
			}
			
			// 保存到数据库
			available = availableList.size();
			if(available > 0){
				customerMapper.insertBatch(availableList);
			}
		}
		
		result.put("total", total);
		result.put("available", available);
		return result;
	}

	/**
	 * 导出客户信息到文件中
	 * @param customers 包含若干条 customer 信息的 List
	 * @return Excel 文件
	 */
	@Override
	public File exportCustomer(List<Customer> customers) {
		if(customers == null)
			return null;
		
		return excelUtil.excelWriter(Customer.class, customers);
	}

}
