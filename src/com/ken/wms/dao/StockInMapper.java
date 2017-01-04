package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.StockIn;

/**
 * 入库记录映射器
 * @author Ken
 *
 */
public interface StockInMapper {

	/**
	 * 选择全部的入库记录
	 * @return 返回全部的入库记录
	 */
	public List<StockIn> selectAll();
	
	/**
	 * 选择指定供应商ID相关的入库记录
	 * @param supplierID 指定的供应商ID
	 * @return 返回指定供应商相关的入库记录
	 */
	public List<StockIn> selectBySupplierId(Integer supplierID);
	
	/**
	 * 选择指定货物ID相关的入库记录
	 * @param goodID 指定的货物ID
	 * @return 返回指定货物相关的入库记录
	 */
	public List<StockIn> selectByGoodID(Integer goodID);
	
	/**
	 * 选择指定仓库ID相关的入库记录
	 * @param repositoryID 指定的仓库ID
	 * @return 返回指定仓库相关的入库记录
	 */
	public List<StockIn> selectByRepositoryID(Integer repositoryID);
	
	/**
	 * 选择指定入库记录的ID的入库记录
	 * @param id 入库记录ID
	 * @return 返回指定ID的入库记录
	 */
	public StockIn selectByID(Integer id);
	
	/**
	 * 添加一条新的入库记录
	 * @param stockIn 入库记录
	 */
	public void insert(StockIn stockIn);
	
	/**
	 * 更新入库记录
	 * @param stockIn 入库记录
	 */
	public void update(StockIn stockIn);
	
	/**
	 * 删除指定ID的入库记录
	 * @param id 指定删除入库记录的ID
	 */
	public void deleteByID(Integer id);
}
