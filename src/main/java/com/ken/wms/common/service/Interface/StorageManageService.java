package com.ken.wms.common.service.Interface;


import com.ken.wms.exception.StorageManageServiceException;
import org.springframework.web.multipart.MultipartFile;
import com.ken.wms.domain.Storage;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 库存信息管理 service
 *
 * @author Ken
 */
public interface StorageManageService {

    /**
     * 返回所有的库存记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(Integer repositoryID) throws StorageManageServiceException;

    /**
     * 分页返回所有的库存记录
     *
     * @param offset 分页偏移值
     * @param limit  分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(Integer repositoryID, int offset, int limit) throws StorageManageServiceException;

    /**
     * 返回指定货物ID的库存记录
     *
     * @param goodsID 指定的货物ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsID(Integer goodsID, Integer repositoryID) throws StorageManageServiceException;

    /**
     * 分页返回指定的货物库存记录
     *
     * @param goodsID 指定的货物ID
     * @param offset  分页偏移值
     * @param limit   分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsID(Integer goodsID, Integer repositoryID, int offset, int limit) throws StorageManageServiceException;

    /**
     * 返回指定货物名称的库存记录
     *
     * @param goodsName 货物名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsName(String goodsName, Integer repositoryID) throws StorageManageServiceException;

    /**
     * 分页返回指定货物名称的库存记录
     *
     * @param goodsName 货物名称
     * @param offset    分页偏移值
     * @param limit     分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsName(String goodsName, Integer repositoryID, int offset, int limit) throws StorageManageServiceException;

    /**
     * 返回指定货物类型的库存记录
     *
     * @param goodsType 指定的货物类型
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsType(String goodsType, Integer Repository) throws StorageManageServiceException;

    /**
     * 分页返回指定货物类型的库存记录
     *
     * @param goodsType 指定的货物类型
     * @param offset    分页偏移值
     * @param limit     分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsType(String goodsType, Integer repositoryID, int offset, int limit) throws StorageManageServiceException;

    /**
     * 添加一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean addNewStorage(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException;

    /**
     * 更新一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       更新的库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean updateStorage(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException;

    /**
     * 为指定的货物库存记录增加指定数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       增加的数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    boolean storageIncrease(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException;

    /**
     * 为指定的货物库存记录减少指定的数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       减少的数量
     * @return 返回一个 boolean 值，若值为 true 表示数目减少成功，否则表示增加失败
     */
    boolean storageDecrease(Integer goodsID, Integer repositoryID, long number) throws StorageManageServiceException;

    /**
     * 删除一条库存记录
     * 货物ID与仓库ID可唯一确定一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean deleteStorage(Integer goodsID, Integer repositoryID) throws StorageManageServiceException;

    /**
     * 导入库存记录
     *
     * @param file 保存有的库存记录的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    Map<String, Object> importStorage(MultipartFile file) throws StorageManageServiceException;

    /**
     * 导出库存记录
     *
     * @param storages 保存有库存记录的List
     * @return excel 文件
     */
    File exportStorage(List<Storage> storages);
}
