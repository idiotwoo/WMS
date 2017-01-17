package com.ken.wms.service.Interface;

/**
 * 出入库管理
 * @author Ken
 *
 */
public interface StockRecordManageService {

	/**
	 * 货物入库操作
	 * @param supplierID 供应商ID
	 * @param goodsID 货物ID
	 * @param repositoryID 入库仓库ID
	 * @param number 入库数量
	 * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
	 */
	public boolean stockInOperation(Integer supplierID, Integer goodsID, Integer repositoryID, long number, String personInCharge);
	
	/**
	 * 货物出库操作
	 * @param customerID 客户ID
	 * @param goodsID 货物ID
	 * @param repositoryID 出库仓库ID
	 * @param number 出库数量
	 * @return 返回一个boolean值，若值为true表示出库成功，否则表示出库失败
	 */
	public boolean stockOutOperation(Integer customerID, Integer goodsID, Integer repositoryID, long number, String personInCharge);
}
