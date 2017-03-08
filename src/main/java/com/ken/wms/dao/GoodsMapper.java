package com.ken.wms.dao;


import com.ken.wms.domain.Goods;

import java.util.List;

/**
 * 货物信息映射器
 * @author Ken
 *
 */
public interface GoodsMapper {

	/**
	 * 选择所有的 Goods
	 * @return 返回所有的Goods
	 */
	List<Goods> selectAll();
	
	/**
	 * 选择指定 id 的 Goods
	 * @param id 货物的ID
	 * @return 返回执行ID对应的Goods
	 */
	Goods selectById(Integer id);
	
	/**
	 * 选择指定 Goods name 的 Goods
	 * @param goodsName 货物的名称
	 * @return 返回指定GoodsName对应的货物
	 */
	Goods selectByName(String goodsName);
	
	/**
	 * 选择制定 goods name 的 goods
	 * 模糊匹配
	 * @param goodsName 货物德名称
	 * @return 返回模糊匹配指定goodsName的货物
	 */
	List<Goods> selectApproximateByName(String goodsName);
	
	/**
	 * 插入一条新的记录到数据库
	 * @param goods 货物信息
	 */
	void insert(Goods goods);
	
	/**
	 * 批量插入新的记录到数据库中
	 * @param goods 存放 goods 信息的 List
	 */
	void insertBatch(List<Goods> goods);
	
	/**
	 * 更新 Goods 到数据库中
	 * 该 Customer 必须已经存在于数据库中，即已经分配主键，否则将更新失败
	 * @param goods 货物信息
	 */
	void update(Goods goods);
	
	/**
	 * 删除指定 id 的 goods
	 * @param id 货物ID
	 */
	void deleteById(Integer id);
	
	/**
	 * 删除指定 goods name 的 goods
	 * @param goodsName 货物的名称
	 */
	void deleteByName(String goodsName);
}
