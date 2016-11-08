package com.ken.wms.controller.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ken.wms.controller.Enum.ResponseStatus;
import com.ken.wms.domain.Supplier;
import com.ken.wms.service.Interface.SupplierManageService;

/**
 * 
 * @author Ken
 *
 */
@RequestMapping(value = "/**/supplierManage")
@Controller
public class SupplierManageHandler {

	@Autowired
	private SupplierManageService supplierManageService;

	/**
	 * 搜索供应商信息
	 * 
	 * @param searchType
	 *            搜索类型
	 * @param offset
	 *            如有多条记录时分页的偏移值
	 * @param limit
	 *            如有多条记录时分页的大小
	 * @param keyWord
	 *            搜索的关键字
	 * @return
	 */
	@RequestMapping(value = "getSupplierList", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public @ResponseBody Map<String, Object> getSupplierList(@RequestParam("searchType") String searchType,
			@RequestParam("offset") int offset, @RequestParam("limit") int limit,
			@RequestParam("keyWord") String keyWord) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Supplier> rows = null;
		long total = 0;

		// 根据查询类型进行查询
		Map<String, Object> queryResult = null;
		if (searchType.equals("searchByID")) {
			if (keyWord != null && keyWord != "") {
				Integer id = Integer.valueOf(keyWord);
				queryResult = supplierManageService.selectById(id);
			}
		} else if (searchType.equals("searchByName")) {
			queryResult = supplierManageService.selectByName(offset, limit, keyWord);
		} else if (searchType.equals("searchAll")) {
			queryResult = supplierManageService.selectAll(offset, limit);
		} else {
			// do other things
		}

		// 结果转换
		if (queryResult != null) {
			rows = (List<Supplier>) queryResult.get("data");
			total = (long) queryResult.get("total");
		}

		resultSet.put("rows", rows);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加一条供应商信息
	 * 
	 * @param supplier
	 *            供应商信息
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "addSupplier", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addSupplier(@RequestBody Supplier supplier) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();

		// 添加记录
		String result = supplierManageService.addSupplier(supplier) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 查询指定 supplierID 供应商的信息
	 * 
	 * @param supplierID
	 *            供应商ID
	 * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
	 *         的值为供应商信息
	 */
	@RequestMapping(value = "getSupplierInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSupplierInfo(@RequestParam("supplierID") int supplierID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		String result = ResponseStatus.ERROR.toString();

		// 获取供应点信息
		Supplier supplier = null;
		Map<String, Object> queryResult = supplierManageService.selectById(supplierID);
		if (queryResult != null) {
			supplier = (Supplier) queryResult.get("data");
			if (supplier != null)
				result = ResponseStatus.SUCCESS.toString();
		}

		resultSet.put("result", result);
		resultSet.put("data", supplier);
		return resultSet;
	}

	/**
	 * 更新供应商信息
	 * 
	 * @param supplier
	 *            供应商信息
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "updateSupplier", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateSupplier(@RequestBody Supplier supplier) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();

		// 更新
		String result = supplierManageService.updateSupplier(supplier) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 删除供应商记录
	 * 
	 * @param supplierID
	 *            供应商ID
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "deleteSupplier", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> deleteSupplier(@RequestParam("supplierID") Integer supplierID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();

		// 刪除
		String result = supplierManageService.deleteSupplier(supplierID) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 导入供应商信息
	 * 
	 * @param file
	 *            保存有供应商信息的文件
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
	 *         error；key为total表示导入的总条数；key为available表示有效的条数
	 */
	@RequestMapping(value = "importSupplier", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> importSupplier(@RequestParam("file") MultipartFile file) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		String result = ResponseStatus.SUCCESS.toString();

		// 读取文件内容
		int total = 0;
		int available = 0;
		if(file == null)
			result = ResponseStatus.ERROR.toString();
		Map<String, Object> importInfo = supplierManageService.importSupplier(file);
		if (importInfo != null) {
			total = (int) importInfo.get("total");
			available = (int) importInfo.get("available");
		}

		resultSet.put("result", result);
		resultSet.put("total", total);
		resultSet.put("available", available);
		return resultSet;
	}
}
