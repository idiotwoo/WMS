package com.ken.wms.service.Impl;

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
import com.ken.wms.dao.StockInMapper;
import com.ken.wms.dao.SupplierMapper;
import com.ken.wms.domain.StockIn;
import com.ken.wms.domain.Supplier;
import com.ken.wms.service.Interface.SupplierManageService;
import com.ken.wms.service.util.ExcelUtil;

@Service
public class SupplierManageServiceImpl implements SupplierManageService {

	private static Logger log = Logger.getLogger("application");

	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private StockInMapper stockInMapper;
	@Autowired
	private ExcelUtil excelUtil;

	/**
	 * 返回指定supplierID 的供应商记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectById(Integer supplierId) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Supplier> suppliers = new ArrayList<>();
		long total = 0;

		// 查询
		Supplier supplier = supplierMapper.selectById(supplierId);
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
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页德大小
	 * @param supplierName
	 *            供应商德名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByName(int offset, int limit, String supplierName) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Supplier> suppliers = new ArrayList<>();
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		suppliers = supplierMapper.selectApproximateByName(supplierName);
		if (suppliers != null) {
			PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers);
			total = pageInfo.getTotal();
		}

		resultSet.put("data", suppliers);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 分页查询供应商记录
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
		List<Supplier> suppliers = new ArrayList<>();
		long total = 0;

		// 分页查询
		PageHelper.offsetPage(offset, limit);
		suppliers = supplierMapper.selectAll();
		if (suppliers != null) {
			PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers);
			total = pageInfo.getTotal();
		}

		resultSet.put("data", suppliers);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加供应商记录
	 * 
	 * @param supplier
	 *            供应商信息
	 * @return
	 */
	@Override
	public boolean addSupplier(Supplier supplier) {

		// 插入新的记录
		if (supplier != null) {
			supplierMapper.insert(supplier);

			if (supplier.getId() != null) {
				log.debug("insert a new supplier successfully");
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新供应商记录
	 * 
	 * @param supplier
	 *            供应商信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean updateSupplier(Supplier supplier) {

		// 更新记录
		if (supplier != null) {
			// 检验
			if (supplier.getId() != null && supplier.getName() != null && supplier.getPersonInCharge() != null
					&& supplier.getTel() != null && supplier.getEmail() != null && supplier.getAddress() != null) {
				supplierMapper.update(supplier);
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除供应商记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean deleteSupplier(Integer supplierId) {

		// 查询该供应商是否有入库记录
		List<StockIn> records = stockInMapper.selectBySupplierId(supplierId);
		if (records == null || records.size() > 0)
			return false;

		// 删除该条供应商记录
		supplierMapper.deleteById(supplierId);

		return true;
	}

	/**
	 * 从文件中导入供应商信息
	 * 
	 * @param file
	 *            导入信息的文件
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
				if (supplier.getName() != null && supplier.getPersonInCharge() != null && supplier.getTel() != null
						&& supplier.getEmail() != null && supplier.getAddress() != null) {
					if (supplierMapper.selectBuName(supplier.getName()) == null) {
						availableList.add(supplier);
					}
				}
			}

			// 保存到数据库
			available = availableList.size();
			if(available > 0){
				// supplierMapper.insertBatch(availableList);
			}
		}

		result.put("total", total);
		result.put("available", available);
		return result;
	}

}
