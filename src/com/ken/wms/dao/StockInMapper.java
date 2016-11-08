package com.ken.wms.dao;

import java.util.List;

import com.ken.wms.domain.StockIn;

/**
 * StockIn 映射器
 * @author Ken
 *
 */
public interface StockInMapper {

	public List<StockIn> selectAll();
	public List<StockIn> selectBySupplierId(Integer supplierID);
	public List<StockIn> selectByGoodID(Integer goodID);
	public StockIn selectByID(Integer id);
	
	public void insert(StockIn stockIn);
	public void update(StockIn stockIn);
	public void deleteByID(Integer id);
}
