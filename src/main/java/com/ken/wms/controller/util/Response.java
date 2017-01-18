package com.ken.wms.controller.util;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * controller 返回的信息载体 response
 * @author ken
 *
 * Created by Ken on 2017/1/18.
 */
public class Response {

    public static final String RESPONSE_RESULT_SUCCESS = "success";
    public static final String RESPONSE_RESULT_ERROR = "error";

    // response 中可能使用的值
    private static final String RESPONSE_RESULT = "result";
    private static final String RESPONSE_MSG = "msg";
    private static final String RESPONSE_DATA = "data";
    private static final String RESPONSE_TOTAL = "total";

    // 存放响应中的信息
    private Map<String,Object> responseContent;

    // Constructor
    Response() {
        this.responseContent = new HashedMap(10);
    }

    /**
     * 设置 response 的状态
     * @param result response 的状态，值为 success 或 error
     */
    public void setResponseResult(String result){
        this.responseContent.put(Response.RESPONSE_RESULT,result);
    }

    /**
     * 设置 response 的附加信息
     * @param msg response  的附加信息
     */
    public void setResponseMsg(String msg){
        this.responseContent.put(Response.RESPONSE_MSG,msg);
    }

    /**
     * 设置 response 中携带的数据
     * @param data response 中携带的数据
     */
    public void setResponseData(Object data){
        this.responseContent.put(Response.RESPONSE_DATA,data);
    }

    /**
     * 设置 response 中携带数据的数量，与 RESPONSE_DATA 配合使用
     * @param total 携带数据的数量
     */
    public void setResponseTotal(long total){
        this.responseContent.put(Response.RESPONSE_TOTAL,total);
    }

    /**
     * 设置 response 自定义信息
     * @param key 自定义信息的 key
     * @param value 自定义信息的值
     */
    public void setCustomerInfo(String key, Object value){
        this.responseContent.put(key,value);
    }

    /**
     * 生成 response
     * @return 代表 response 的一个 Map 对象
     */
    public Map<String, Object> generateResponse(){
        return this.responseContent;
    }
}
