package com.ken.wms.util;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service层日志处理
 *
 * @author Ken
 * @since 2017/3/8.
 */
public class ServiceLogging {

    private static Logger exceptionLogger = LoggerFactory.getLogger("ExceptionLogging");
    private static Logger methodInvokeLogger = LoggerFactory.getLogger("MethodInvokeLogging");

    /**
     * 捕获Service层抛出的异常并做日志
     *
     * @param throwable 方法抛出的异常
     */
    public void loggingServiceException(Throwable throwable) {
        if (exceptionLogger.isErrorEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("cause:").append(throwable.getMessage());
            builder.append("\n\tstackTrack:\n");
            for (StackTraceElement stack : throwable.getStackTrace()) {
                builder.append("\t\t");
                builder.append(stack.toString());
                builder.append("\n");
            }
            exceptionLogger.error(builder.toString());
        }
    }

    /**
     * 记录Service方法的调用
     *
     * @param joinPoint 切入点
     */
    public void loggingMethodInvoke(JoinPoint joinPoint) {
        if (methodInvokeLogger.isDebugEnabled()) {
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            StringBuilder builder = new StringBuilder();
            builder.append("Invoked Method:").append(methodName);
            builder.append("\targs：");
            for (Object arg : args) {
                builder.append(arg.toString());
            }
            methodInvokeLogger.debug(builder.toString());
        }
    }

}
