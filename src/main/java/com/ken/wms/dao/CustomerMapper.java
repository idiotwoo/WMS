package com.ken.wms.dao;

import com.ken.wms.domain.Customer;

import java.util.List;

/**
 * 客户信息 Customer 映射器
 * @author Ken
 *
 */
public interface CustomerMapper {

	/**
	 * 选择所有的 Customer
	 * @return 返回所有的 Customer
	 */
	List<Customer> selectAll();
	
	/**
	 * 选择指定 id 的 Supplier
	 * @param id Customer的ID
	 * @return 返回指定ID对应的Customer
	 */
	Customer selectById(Integer id);
	
	/**
	 * 选择指定 Customer name 的 customer
	 * @param customerName 客户的名称
	 * @return 返回指定CustomerName对应的Customer
	 */
	Customer selectByName(String customerName);
	
	/**
	 * 选择指定 customer name 的 Customer
	 * 与 selectByName 方法的区别在于本方法将返回相似匹配的结果
	 * @param customerName Customer 供应商名
	 * @return 返回模糊匹配指定customerName 对应的Customer
	 */
	List<Customer> selectApproximateByName(String customerName);
	
	/**
	 * 插入 Customer 到数据库中
	 * 不需要指定 Customer 的主键，采用的数据库 AI 方式
	 * @param customer Customer 实例
	 */
	void insert(Customer customer);
	
	/**
	 * 批量插入 Customer 到数据库中
	 * @param customers 存放 Customer 实例的 List
	 */
	void insertBatch(List<Customer> customers);
	
	/**
	 * 更新 Customer 到数据库
	 * 该 Customer 必须已经存在于数据库中，即已经分配主键，否则将更新失败
	 * @param customer customer 实例
	 */
	void update(Customer customer);
	
	/**
	 * 删除指定 id 的 customer
	 * @param id customer ID
	 */
	void deleteById(Integer id);
	
	/**
	 * 删除指定 customerName 的 customer
	 * @param customerName 客户名称
	 */
	void deleteByName(String customerName);
}
