package com.ken.wms.common.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.common.service.Interface.GoodsManageService;
import com.ken.wms.common.util.ExcelUtil;
import com.ken.wms.dao.GoodsMapper;
import com.ken.wms.dao.StockInMapper;
import com.ken.wms.dao.StockOutMapper;
import com.ken.wms.dao.StorageMapper;
import com.ken.wms.domain.Goods;
import com.ken.wms.domain.StockInDO;
import com.ken.wms.domain.StockOutDO;
import com.ken.wms.domain.Storage;
import com.ken.wms.exception.GoodsManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货物信息管理Service 实现类
 *
 * @author Ken
 */
@Service
public class GoodsManageServiceImpl implements GoodsManageService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StockInMapper stockInMapper;
    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private ExcelUtil excelUtil;

    /**
     * 返回指定goods ID 的货物记录
     *
     * @param goodsId 货物ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectById(Integer goodsId) throws GoodsManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Goods> goodsList = new ArrayList<>();
        long total = 0;

        // 查询
        Goods goods;
        try {
            goods = goodsMapper.selectById(goodsId);
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }

        if (goods != null) {
            goodsList.add(goods);
            total = 1;
        }

        resultSet.put("data", goodsList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 goods name 的货物记录 支持查询分页以及模糊查询
     *
     * @param offset    分页的偏移值
     * @param limit     分页的大小
     * @param goodsName 货物的名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(int offset, int limit, String goodsName) throws GoodsManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Goods> goodsList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                goodsList = goodsMapper.selectApproximateByName(goodsName);
                if (goodsList != null) {
                    PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
                    total = pageInfo.getTotal();
                } else
                    goodsList = new ArrayList<>();
            } else {
                goodsList = goodsMapper.selectApproximateByName(goodsName);
                if (goodsList != null)
                    total = goodsList.size();
                else
                    goodsList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }

        resultSet.put("data", goodsList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 goods name 的货物记录 支持模糊查询
     *
     * @param goodsName 货物名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByName(String goodsName) throws GoodsManageServiceException {
        return selectByName(-1, -1, goodsName);
    }

    /**
     * 分页查询货物记录
     *
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(int offset, int limit) throws GoodsManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Goods> goodsList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                goodsList = goodsMapper.selectAll();
                if (goodsList != null) {
                    PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
                    total = pageInfo.getTotal();
                } else
                    goodsList = new ArrayList<>();
            } else {
                goodsList = goodsMapper.selectAll();
                if (goodsList != null)
                    total = goodsList.size();
                else
                    goodsList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }

        resultSet.put("data", goodsList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 查询所有的货物记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll() throws GoodsManageServiceException {
        return selectAll(-1, -1);
    }

    /**
     * 检查货物信息是否满足要求
     *
     * @param goods 货物信息
     * @return 若货物信息满足要求则返回true，否则返回false
     */
    private boolean goodsCheck(Goods goods) {
        if (goods != null) {
            if (goods.getName() != null && goods.getValue() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加货物记录
     *
     * @param goods 货物信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean addGoods(Goods goods) throws GoodsManageServiceException {

        try {
            // 插入新的记录
            if (goods != null) {
                // 验证
                if (goodsCheck(goods)) {
                    goodsMapper.insert(goods);
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }
    }

    /**
     * 更新货物记录
     *
     * @param goods 货物信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateGoods(Goods goods) throws GoodsManageServiceException {

        try {
            // 更新记录
            if (goods != null) {
                // 检验
                if (goodsCheck(goods)) {
                    goodsMapper.update(goods);
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }
    }

    /**
     * 删除货物记录
     *
     * @param goodsId 货物ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteGoods(Integer goodsId) throws GoodsManageServiceException {

        try {
            // 检查该货物是否有入库信息
            List<StockInDO> stockInDORecord = stockInMapper.selectByGoodID(goodsId);
            if (stockInDORecord != null && !stockInDORecord.isEmpty())
                return false;

            // 检查该货物是否有出库信息
            List<StockOutDO> stockOutDORecord = stockOutMapper.selectByGoodId(goodsId);
            if (stockOutDORecord != null && !stockOutDORecord.isEmpty())
                return false;

            // 检查该货物是否有存储信息
            List<Storage> storageRecord = storageMapper.selectByGoodsIDAndRepositoryID(goodsId, null);
            if (storageRecord != null && !storageRecord.isEmpty())
                return false;

            // 删除货物记录
            goodsMapper.deleteById(goodsId);
            return true;
        } catch (PersistenceException e) {
            throw new GoodsManageServiceException(e);
        }
    }

    /**
     * 从文件中导入货物信息
     *
     * @param file 导入信息的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    @Override
    public Map<String, Object> importGoods(MultipartFile file) throws GoodsManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        int total = 0;
        int available = 0;

        // 从 Excel 文件中读取
        List<Object> goodsList = excelUtil.excelReader(Goods.class, file);
        if (goodsList != null) {
            total = goodsList.size();

            // 验证每一条记录
            Goods goods;
            List<Goods> availableList = new ArrayList<>();
            for (Object object : goodsList) {
                goods = (Goods) object;
                if (goodsCheck(goods)) {
                    availableList.add(goods);
                }
            }
            // 保存到数据库
            try {
                available = availableList.size();
                if (available > 0) {
                    goodsMapper.insertBatch(availableList);
                }
            } catch (PersistenceException e) {
                throw new GoodsManageServiceException(e);
            }
        }

        resultSet.put("total", total);
        resultSet.put("available", available);
        return resultSet;
    }

    /**
     * 导出货物信息到文件中
     *
     * @param goods 包含若干条 Supplier 信息的 List
     * @return excel 文件
     */
    @Override
    public File exportGoods(List<Goods> goods) {
        if (goods == null)
            return null;

        return excelUtil.excelWriter(Goods.class, goods);
    }

}
