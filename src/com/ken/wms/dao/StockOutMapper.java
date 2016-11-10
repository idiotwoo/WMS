package com.ken.wms.dao;

import com.ken.wms.domain.StockOut;

import java.util.List;

/**
 * StockOut 映射器
 * @author Ken
 *
 */
public interface StockOutMapper {

	public List<StockOut> selectAll();
	public List<StockOut> selectByCustomerId(Integer customerId);
	public List<StockOut> selectByGoodId(Integer goodId);
	public StockOut selectById(Integer id);
	
	public void insert(StockOut stockOut);
	public void update(StockOut stockOut);
	public void deleteById(Integer id);
}
