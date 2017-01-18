package com.ken.wms.controller.util;

import org.springframework.stereotype.Component;

/**
 * Response Utils
 * Created by Ken on 2017/1/18.
 */
@Component
public class ResponseUtil {

    /**
     * 生成一个 Response 对象
     * @return response 对象
     */
    public Response newResponseInstance(){
        Response response = new Response();

        return response;
    }

}
