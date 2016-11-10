package com.ken.wms.service.Interface;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ken.wms.domain.Customer;

/**
 * 客户信息管理 Service
 * @author Ken
 *
 */
public interface CustomerManageService {

	/**
	 * 返回指定customer id 的客户记录
	 * @param customerId 客户ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectById(Integer customerId);
	
	/**
	 * 返回指定 customer name 的客户记录
	 * 支持查询分页以及模糊查询
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @param customerName 客户的名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(int offset, int limit, String customerName);
	
	/**
	 * 返回指定 customer Name 的客户记录
	 * 支持模糊查询
	 * @param customerName 客户名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(String customerName);
	
	/**
	 * 分页查询客户的记录
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll(int offset,int limit);
	
	/**
	 * 查询所有客户的记录
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll();
	
	/**
	 * 添加客户信息
	 * @param customer 客户信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean addCustomer(Customer customer);
	
	/**
	 * 更新客户信息
	 * @param customer 客户信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean updateCustomer(Customer customer);
	
	/**
	 * 删除客户信息
	 * @param customerId 客户ID
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean deleteCustomer(Integer customerId);
	
	/**
	 * 从文件中导入客户信息
	 * @param file 导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	public Map<String, Object> importCustomer(MultipartFile file);
	
	/**
	 * 导出客户信息到文件中
	 * @param customers 包含若干条 customer 信息的 List
	 * @return Excel 文件
	 */
	public File exportCustomer(List<Customer> customers);
}
