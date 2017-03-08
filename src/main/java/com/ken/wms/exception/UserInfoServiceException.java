package com.ken.wms.exception;

/**
 * UserInfoService异常
 *
 * @author Ken
 * @since 2017/3/8.
 */
public class UserInfoServiceException extends BusinessException {

    UserInfoServiceException(){
        super();
    }

    public UserInfoServiceException(Exception e){
        super(e);
    }

    UserInfoServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

}
