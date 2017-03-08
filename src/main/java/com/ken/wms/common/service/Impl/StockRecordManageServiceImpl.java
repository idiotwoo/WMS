package com.ken.wms.common.service.Impl;

import com.ken.wms.common.service.Interface.StockRecordManageService;
import com.ken.wms.common.service.Interface.StorageManageService;
import com.ken.wms.dao.*;
import com.ken.wms.domain.*;
import com.ken.wms.exception.StockRecordManageServiceException;
import com.ken.wms.exception.StorageManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class StockRecordManageServiceImpl implements StockRecordManageService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private StorageManageService storageManageService;
    @Autowired
    private StockInMapper stockinMapper;
    @Autowired
    private StockOutMapper stockOutMapper;

    /**
     * 货物入库操作
     *
     * @param supplierID   供应商ID
     * @param goodsID      货物ID
     * @param repositoryID 入库仓库ID
     * @param number       入库数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    @Override
    public boolean stockInOperation(Integer supplierID, Integer goodsID, Integer repositoryID, long number, String personInCharge) throws StockRecordManageServiceException {

        // ID对应的记录是否存在
        if (!(supplierValidate(supplierID) && goodsValidate(goodsID) && repositoryValidate(repositoryID)))
            return false;

        if (personInCharge == null)
            return false;

        // 检查入库数量有效性
        if (number < 0)
            return false;

        try {
            // 更新库存记录
            boolean isSuccess;
            isSuccess = storageManageService.storageIncrease(goodsID, repositoryID, number);

            // 保存入库记录
            if (isSuccess) {
                StockIn stockIn = new StockIn();
                stockIn.setGoodID(goodsID);
                stockIn.setSupplierID(supplierID);
                stockIn.setNumber(number);
                stockIn.setPersonInCharge(personInCharge);
                stockIn.setTime(new Date(new java.util.Date().getTime()));
                stockIn.setRepositoryID(repositoryID);
                stockinMapper.insert(stockIn);
            }

            return isSuccess;
        } catch (PersistenceException | StorageManageServiceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

    /**
     * 货物出库操作
     *
     * @param customerID   客户ID
     * @param goodsID      货物ID
     * @param repositoryID 出库仓库ID
     * @param number       出库数量
     * @return 返回一个boolean值，若值为true表示出库成功，否则表示出库失败
     */
    @Override
    public boolean stockOutOperation(Integer customerID, Integer goodsID, Integer repositoryID, long number, String personInCharge) throws StockRecordManageServiceException {

        // 检查ID对应的记录是否存在
        if (!(customerValidate(customerID) && goodsValidate(goodsID) && repositoryValidate(repositoryID)))
            return false;

        // 检查出库数量范围是否有效
        if (number < 0)
            return false;

        try {
            // 更新库存信息
            boolean isSuccess;
            isSuccess = storageManageService.storageDecrease(goodsID, repositoryID, number);

            // 保存出库记录
            if (isSuccess) {
                StockOut stockOut = new StockOut();
                stockOut.setCustomerID(customerID);
                stockOut.setGoodID(goodsID);
                stockOut.setNumber(number);
                stockOut.setPersonInCharge(personInCharge);
                stockOut.setRepositoryID(repositoryID);
                stockOut.setTime(new Date(new java.util.Date().getTime()));
                stockOutMapper.insert(stockOut);
            }

            return isSuccess;
        } catch (PersistenceException | StorageManageServiceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param goodsID 货物ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean goodsValidate(Integer goodsID) throws StockRecordManageServiceException {
        try {
            Goods goods = goodsMapper.selectById(goodsID);
            return goods != null;
        } catch (PersistenceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

    /**
     * 检查仓库ID对应的记录是否存在
     *
     * @param repositoryID 仓库ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean repositoryValidate(Integer repositoryID) throws StockRecordManageServiceException {
        try {
            Repository repository = repositoryMapper.selectByID(repositoryID);
            return repository != null;
        } catch (PersistenceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

    /**
     * 检查供应商ID对应的记录是否存在
     *
     * @param supplierID 供应商ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean supplierValidate(Integer supplierID) throws StockRecordManageServiceException {
        try {
            Supplier supplier = supplierMapper.selectById(supplierID);
            return supplier != null;
        } catch (PersistenceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

    /**
     * 检查客户ID对应的记录是否存在
     *
     * @param cumtomerID 客户ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean customerValidate(Integer cumtomerID) throws StockRecordManageServiceException {
        try {
            Customer customer = customerMapper.selectById(cumtomerID);
            return customer != null;
        } catch (PersistenceException e) {
            throw new StockRecordManageServiceException(e);
        }
    }

}
