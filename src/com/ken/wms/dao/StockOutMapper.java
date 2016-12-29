package com.ken.wms.dao;

import com.ken.wms.domain.StockOut;

import java.util.List;

/**
 * 出库记录 映射器
 * @author Ken
 *
 */
public interface StockOutMapper {

	/**
	 * 选择全部的出库记录
	 * @return 返回所有的出库记录
	 */
	public List<StockOut> selectAll();
	
	/**
	 * 选择指定客户ID相关的出库记录
	 * @param customerId 指定的客户ID
	 * @return 返回指定客户相关的出库记录
	 */
	public List<StockOut> selectByCustomerId(Integer customerId);
	
	/**
	 * 选择指定货物ID相关的出库记录
	 * @param goodId 指定的货物ID
	 * @return 返回指定货物ID相关的出库记录
	 */
	public List<StockOut> selectByGoodId(Integer goodId);
	
	/**
	 * 选择指定ID的出库记录
	 * @param id 指定的出库记录ID
	 * @return 返回指定ID的出库记录
	 */
	public StockOut selectById(Integer id);
	
	/**
	 * 插入一条新的出库记录
	 * @param stockOut 出库记录
	 */
	public void insert(StockOut stockOut);
	
	/**
	 * 更新出库记录
	 * @param stockOut 出库记录
	 */
	public void update(StockOut stockOut);
	
	/**
	 * 删除指定ID的出库记录
	 * @param id 指定的出库记录ID
	 */
	public void deleteById(Integer id);
}
