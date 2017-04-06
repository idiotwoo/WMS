package com.ken.wms.dao;

import com.ken.wms.domain.StockOutDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 出库记录 映射器
 *
 * @author Ken
 */
public interface StockOutMapper {

    /**
     * 选择全部的出库记录
     *
     * @return 返回所有的出库记录
     */
    List<StockOutDO> selectAll();

    /**
     * 选择指定客户ID相关的出库记录
     *
     * @param customerId 指定的客户ID
     * @return 返回指定客户相关的出库记录
     */
    List<StockOutDO> selectByCustomerId(Integer customerId);

    /**
     * 选择指定货物ID相关的出库记录
     *
     * @param goodId 指定的货物ID
     * @return 返回指定货物ID相关的出库记录
     */
    List<StockOutDO> selectByGoodId(Integer goodId);

    /**
     * 选择指定仓库ID关联的出库记录
     *
     * @param repositoryID 指定的仓库ID
     * @return 返回指定仓库ID相关的出库记录
     */
    List<StockOutDO> selectByRepositoryID(Integer repositoryID);

    /**
     * 选择指定仓库ID以及指定日期范围内的出库记录
     *
     * @param repositoryID 指定的仓库ID
     * @param startDate    记录起始日期
     * @param endDate      记录结束日期
     * @return 返回所有符合指定要求的出库记录
     */
    List<StockOutDO> selectByRepositoryIDAndDate(@Param("repositoryID") Integer repositoryID,
                                                 @Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);

    /**
     * 选择指定ID的出库记录
     *
     * @param id 指定的出库记录ID
     * @return 返回指定ID的出库记录
     */
    StockOutDO selectById(Integer id);

    /**
     * 插入一条新的出库记录
     *
     * @param stockOutDO 出库记录
     */
    void insert(StockOutDO stockOutDO);

    /**
     * 更新出库记录
     *
     * @param stockOutDO 出库记录
     */
    void update(StockOutDO stockOutDO);

    /**
     * 删除指定ID的出库记录
     *
     * @param id 指定的出库记录ID
     */
    void deleteById(Integer id);
}
