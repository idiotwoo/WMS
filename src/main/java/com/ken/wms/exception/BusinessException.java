package com.ken.wms.exception;

/**
 * 业务层异常的父类
 *
 * @author Ken
 * @since 2017/3/8.
 */
public class BusinessException extends Exception{

    private String exceptionDesc;

    public BusinessException(Exception e){
        super(e);
    }

    BusinessException(Exception e, String exceptionDesc){
        super(e);
        this.exceptionDesc = exceptionDesc;
    }

    BusinessException(String exceptionDesc){
        this.exceptionDesc = exceptionDesc;
    }

    BusinessException(){}

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }
}
