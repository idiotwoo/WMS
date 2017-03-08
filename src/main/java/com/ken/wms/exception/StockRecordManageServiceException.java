package com.ken.wms.exception;

/**
 * StockRecordManageService异常
 *
 * @author Ken
 * @since 2017/3/8.
 */
public class StockRecordManageServiceException extends BusinessException {

    StockRecordManageServiceException(){
        super();
    }

    public StockRecordManageServiceException(Exception e){
        super(e);
    }

    StockRecordManageServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

}
