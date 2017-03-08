package com.ken.wms.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器
 *
 * @author Ken
 * @since 2017/3/8.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void testController(){
        System.out.println("exception has catch");
    }
}
