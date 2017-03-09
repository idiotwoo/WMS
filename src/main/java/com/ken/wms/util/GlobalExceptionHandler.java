package com.ken.wms.util;

import com.ken.wms.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger("ExceptionLogging");

    /**
     * 捕获并记录Controller层抛出的非BusinessException异常
     */
    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e){
        if (!(e instanceof BusinessException)){
            if (logger.isErrorEnabled()){
                StringBuilder builder = new StringBuilder();
                builder.append("cause:").append(e.getMessage());
                builder.append("\n\tstackTrack:\n");
                for (StackTraceElement stack : e.getStackTrace()) {
                    builder.append("\t\t");
                    builder.append(stack.toString());
                    builder.append("\n");
                }
                logger.error(builder.toString());
            }
        }
    }
}
