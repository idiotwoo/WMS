package com.ken.wms.controller.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ken.wms.controller.Enum.ResponseStatus;
import com.ken.wms.domain.Goods;
import com.ken.wms.domain.Supplier;
import com.ken.wms.service.Interface.GoodsManageService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货物信息管理请求 Handler
 * 
 * @author Ken
 *
 */
@RequestMapping(value = "/**/goodsManage")
@Controller
public class GoodsManageHandler {

	@Autowired
	private GoodsManageService goodsManageService;
	
	private static final String SEARCH_BY_ID = "searchByID";
	private static final String SEARCH_BY_NAME = "searchByName";
	private static final String SEARCH_ALL = "searchAll";
	
	private Map<String, Object> query(String searchType, String keyWord, int offset, int limit){
		Map<String, Object> queryResult = null;
		
		if(searchType.equals(SEARCH_BY_ID)){
			if(StringUtils.isNumeric(keyWord))
				queryResult = goodsManageService.selectById(Integer.valueOf(keyWord));
		}else if(searchType.equals(SEARCH_BY_NAME)){
			queryResult = goodsManageService.selectByName(keyWord);
		}else if(searchType.equals(SEARCH_ALL)){
			queryResult = goodsManageService.selectAll(offset, limit);
		}else{
			// do other thing
		}
		
		return queryResult;
	}

	/**
	 * 搜索货物信息
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getGoodsList", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGoodsList(@RequestParam("searchType") String searchType,
									 @RequestParam("offset") int offset, @RequestParam("limit") int limit,
									 @RequestParam("keyWord") String keyWord) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<Supplier> rows = null;
		long total = 0;

//		// 根据查询类型进行查询
//		Map<String, Object> queryResult = null;
//		if (searchType.equals("searchByID")) {
//			if (keyWord != null && !keyWord.equals("") && StringUtils.isNumeric(keyWord)) {
//				Integer id = Integer.valueOf(keyWord);
//				queryResult = goodsManageService.selectById(id);
//			}
//		} else if (searchType.equals("searchByName")) {
//			queryResult = goodsManageService.selectByName(keyWord);
//		} else if (searchType.equals("searchAll")) {
//			queryResult = goodsManageService.selectAll();
//		} else {
//			// do other thing
//		}
		
		Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

		if (queryResult != null) {
			rows = (List<Supplier>) queryResult.get("data");
			total = (long) queryResult.get("total");
		}

		resultSet.put("rows", rows);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加一条货物信息
	 * 
	 * @param goods
	 *            货物信息
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "addGoods", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addGoods(@RequestBody Goods goods) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		
		// 添加记录
		String result = goodsManageService.addGoods(goods) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 查询指定 goods ID 货物的信息
	 * 
	 * @param goodsID
	 *            货物ID
	 * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
	 *         的值为货物信息
	 */
	@RequestMapping(value = "getGoodsInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGoodsInfo(@RequestParam("goodsID") Integer goodsID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		String result = ResponseStatus.ERROR.toString();

		// 获取货物信息
		Goods goods = null;
		Map<String, Object> queryResult = goodsManageService.selectById(goodsID);
		if (queryResult != null) {
			goods = (Goods) queryResult.get("data");
			if (goods != null) {
				result = ResponseStatus.SUCCESS.toString();
			}
		}

		resultSet.put("result", result);
		resultSet.put("data", goods);
		return resultSet;
	}

	/**
	 * 更新货物信息
	 * 
	 * @param goods
	 *            货物信息
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "updateGoods", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateGoods(@RequestBody Goods goods) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();

		// 更新
		String result = goodsManageService.updateGoods(goods) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 删除货物记录
	 * 
	 * @param goodsID
	 *            货物ID
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
	 */
	@RequestMapping(value = "deleteGoods", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> deleteGoods(@RequestParam("goodsID") Integer goodsID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();

		// 删除
		String result = goodsManageService.deleteGoods(goodsID) ? ResponseStatus.SUCCESS.toString()
				: ResponseStatus.ERROR.toString();

		resultSet.put("result", result);
		return resultSet;
	}

	/**
	 * 导入货物信息
	 * 
	 * @param file
	 *            保存有货物信息的文件
	 * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
	 *         error；key为total表示导入的总条数；key为available表示有效的条数
	 */
	@RequestMapping(value = "importGoods", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> importGoods(@RequestParam("file") MultipartFile file) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		String result = ResponseStatus.ERROR.toString();

		// 读取文件内容
		int total = 0;
		int available = 0;
		if (file != null) {
			Map<String, Object> importInfo = goodsManageService.importGoods(file);
			if (importInfo != null) {
				total = (int) importInfo.get("total");
				available = (int) importInfo.get("available");
				result = ResponseStatus.SUCCESS.toString();
			}
		}

		resultSet.put("result", result);
		resultSet.put("total", total);
		resultSet.put("available", available);
		return resultSet;
	}

	/**
	 * 导出货物信息
	 * @param searchType 查找类型
	 * @param keyWord 查找关键字
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportGoods",method=RequestMethod.GET)
	public void exportGoods(@RequestParam("searchType") String searchType, @RequestParam("keyWord") String keyWord,
			HttpServletResponse response) {

		String fileName = "goodsInfo.xlsx";

//		// 根据查询类型进行查询
//		List<Goods> goodsList = null;
//		Map<String, Object> queryResult = null;
//		if (searchType.equals("searchByID")) {
//			if (keyWord != null && !keyWord.equals("")) {
//				Integer id = Integer.valueOf(keyWord);
//				queryResult = goodsManageService.selectById(id);
//			}
//		} else if (searchType.equals("searchByName")) {
//			queryResult = goodsManageService.selectByName(keyWord);
//		} else if (searchType.equals("searchAll")) {
//			queryResult = goodsManageService.selectAll();
//		} else {
//			// do other thing
//		}
		
		List<Goods> goodsList = null;
		Map<String, Object> queryResult = query(searchType, keyWord, -1, -1);
		
		if (queryResult != null) {
			goodsList = (List<Goods>) queryResult.get("data");
		}

		// 获取生成的文件
		File file = goodsManageService.exportGoods(goodsList);

		// 写出文件
		if (file != null) {
			// 设置响应头
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			try {
				FileInputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[8192];

				int len;
				while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
					outputStream.write(buffer, 0, len);
					outputStream.flush();
				}

				inputStream.close();
				outputStream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
