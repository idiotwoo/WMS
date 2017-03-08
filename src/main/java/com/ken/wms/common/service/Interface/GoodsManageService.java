package com.ken.wms.common.service.Interface;


import com.ken.wms.domain.Goods;
import com.ken.wms.exception.GoodsManageServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 货物信息管理 service
 * @author Ken
 *
 */
public interface GoodsManageService {

	/**
	 * 返回指定goods ID 的货物记录
	 * @param goodsId 货物ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectById(Integer goodsId) throws GoodsManageServiceException;
	
	/**
	 * 返回指定 goods name 的货物记录
	 * 支持查询分页以及模糊查询
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @param goodsName 货物的名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(int offset, int limit, String goodsName) throws GoodsManageServiceException;
	
	/**
	 * 返回指定 goods name 的货物记录
	 * 支持模糊查询
	 * @param goodsName 货物名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectByName(String goodsName) throws GoodsManageServiceException;
	
	/**
	 * 分页查询货物记录
	 * @param offset 分页的偏移值
	 * @param limit 分页的大小
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll(int offset, int limit) throws GoodsManageServiceException;
	
	/**
	 * 查询所有的货物记录
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	public Map<String, Object> selectAll() throws GoodsManageServiceException;
	
	/**
	 * 添加货物记录
	 * @param goods 货物信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean addGoods(Goods goods) throws GoodsManageServiceException;
	
	/**
	 * 更新货物记录
	 * @param goods 供应商信息
	 * @return	返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean updateGoods(Goods goods) throws GoodsManageServiceException;
	
	/**
	 * 删除货物记录
	 * @param goodsId 货物ID
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	public boolean deleteGoods(Integer goodsId) throws GoodsManageServiceException;
	
	/**
	 * 从文件中导入货物信息
	 * @param file 导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	public Map<String, Object> importGoods(MultipartFile file) throws GoodsManageServiceException;
	
	/**
	 * 导出货物信息到文件中
	 * 
	 * @param goods
	 *            包含若干条 Supplier 信息的 List
	 * @return excel 文件
	 */
	public File exportGoods(List<Goods> goods);
}
