package com.ken.wms.dao;

import com.ken.wms.domain.Storage;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 库存信息映射器
 * @author Ken
 *
 */
public interface StorageMapper {

	/**
	 * 选择所有的库存信息
	 * @return 返回所有的库存信息
	 */
	public List<Storage> selectAllAndRepositoryID(@Param("repositoryID") Integer repositoryID);
	
	/**
	 * 选择指定货物ID和仓库ID的库存信息
	 * @param goodsID 货物ID
	 * @param repositoryID 库存ID
	 * @return 返回所有指定货物ID和仓库ID的库存信息
	 */
	public List<Storage> selectByGoodsIDAndRepositoryID(@Param("goodsID") Integer goodsID,
                                                        @Param("repositoryID") Integer repositoryID);
	
	/**
	 * 选择指定货物名的库存信息
	 * @param goodsName 货物名称
	 * @return 返回所有指定货物名称的库存信息
	 */
	public List<Storage> selectByGoodsNameAndRepositoryID(@Param("goodsName") String goodsName,
                                                          @Param("repositoryID") Integer repositoryID);
	
	/**
	 * 选择指定货物类型的库存信息
	 * @param goodsType 货物类型
	 * @return 返回所有指定货物类型的库存信息
	 */
	public List<Storage> selectByGoodsTypeAndRepositoryID(@Param("goodsType") String goodsType,
                                                          @Param("repositoryID") Integer repositoryID);
	
	/**
	 * 更新库存信息
	 * 该库存信息必需已经存在于数据库当中，否则更新无效
	 * @param storage 库存信息
	 */
	public void update(Storage storage);
	
	/**
	 * 插入新的库存信息
	 * @param storage 库存信息
	 */
	public void insert(Storage storage);
	
	/**
	 * 批量导入库存信息
	 * @param storages 若干条库存信息
	 */
	public void insertBatch(List<Storage> storages);
	
	/**
	 * 删除指定货物ID的库存信息
	 * @param goodsID 货物ID
	 */
	public void deleteByGoodsID(Integer goodsID);
	
	/**
	 * 删除指定仓库的库存信息
	 * @param repositoryID 仓库ID
	 */
	public void deleteByRepositoryID(Integer repositoryID);
	
	/**
	 * 删除指定仓库中的指定货物的库存信息
	 * @param goodsID 货物ID
	 * @param repositoryID 仓库ID
	 */
	public void deleteByRepositoryIDAndGoodsID(@Param("goodsID") Integer goodsID, @Param("repositoryID") Integer repositoryID);
}
