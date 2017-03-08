package com.ken.wms.common.controller;

import com.ken.wms.common.service.Interface.GoodsManageService;
import com.ken.wms.common.util.Response;
import com.ken.wms.common.util.ResponseUtil;
import com.ken.wms.domain.Goods;
import com.ken.wms.domain.Supplier;
import com.ken.wms.exception.GoodsManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 货物信息管理请求 Handler
 *
 * @author Ken
 */
@RequestMapping(value = "/**/goodsManage")
@Controller
public class GoodsManageHandler {

    @Autowired
    private GoodsManageService goodsManageService;
    @Autowired
    private ResponseUtil responseUtil;

    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_NAME = "searchByName";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 通用的记录查询
     *
     * @param searchType 查询类型
     * @param keyWord    查询关键字
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @return 返回一个 Map ，包含所有符合要求的查询结果，以及记录的条数
     */
    private Map<String, Object> query(String searchType, String keyWord, int offset, int limit) throws GoodsManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = goodsManageService.selectById(Integer.valueOf(keyWord));
                break;
            case SEARCH_BY_NAME:
                queryResult = goodsManageService.selectByName(keyWord);
                break;
            case SEARCH_ALL:
                queryResult = goodsManageService.selectAll(offset, limit);
                break;
            default:
                // do other thing
                break;
        }

        return queryResult;
    }

    /**
     * 搜索货物信息
     *
     * @param searchType 搜索类型
     * @param offset     如有多条记录时分页的偏移值
     * @param limit      如有多条记录时分页的大小
     * @param keyWord    搜索的关键字
     * @return 返回所有符合要求的记录
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getGoodsList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getGoodsList(@RequestParam("searchType") String searchType,
                                     @RequestParam("offset") int offset, @RequestParam("limit") int limit,
                                     @RequestParam("keyWord") String keyWord) throws GoodsManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<Supplier> rows = null;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

        if (queryResult != null) {
            rows = (List<Supplier>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 添加一条货物信息
     *
     * @param goods 货物信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addGoods(@RequestBody Goods goods) throws GoodsManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加记录
        String result = goodsManageService.addGoods(goods) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);

        return responseContent.generateResponse();
    }

    /**
     * 查询指定 goods ID 货物的信息
     *
     * @param goodsID 货物ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为货物信息
     */
    @RequestMapping(value = "getGoodsInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getGoodsInfo(@RequestParam("goodsID") Integer goodsID) throws GoodsManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 获取货物信息
        Goods goods = null;
        Map<String, Object> queryResult = goodsManageService.selectById(goodsID);
        if (queryResult != null) {
            goods = (Goods) queryResult.get("data");
            if (goods != null) {
                result = Response.RESPONSE_RESULT_SUCCESS;
            }
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseData(goods);
        return responseContent.generateResponse();
    }

    /**
     * 更新货物信息
     *
     * @param goods 货物信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "updateGoods", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateGoods(@RequestBody Goods goods) throws GoodsManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 更新
        String result = goodsManageService.updateGoods(goods) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除货物记录
     *
     * @param goodsID 货物ID
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "deleteGoods", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteGoods(@RequestParam("goodsID") Integer goodsID) throws GoodsManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 删除
        String result = goodsManageService.deleteGoods(goodsID) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 导入货物信息
     *
     * @param file 保存有货物信息的文件
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
     * error；key为total表示导入的总条数；key为available表示有效的条数
     */
    @RequestMapping(value = "importGoods", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> importGoods(@RequestParam("file") MultipartFile file) throws GoodsManageServiceException {
        //  初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 读取文件内容
        int total = 0;
        int available = 0;
        if (file != null) {
            Map<String, Object> importInfo = goodsManageService.importGoods(file);
            if (importInfo != null) {
                total = (int) importInfo.get("total");
                available = (int) importInfo.get("available");
                result = Response.RESPONSE_RESULT_SUCCESS;
            }
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseTotal(total);
        responseContent.setCustomerInfo("available", available);
        return responseContent.generateResponse();
    }

    /**
     * 导出货物信息
     *
     * @param searchType 查找类型
     * @param keyWord    查找关键字
     * @param response   HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportGoods", method = RequestMethod.GET)
    public void exportGoods(@RequestParam("searchType") String searchType, @RequestParam("keyWord") String keyWord,
                            HttpServletResponse response) throws GoodsManageServiceException, IOException {

        String fileName = "goodsInfo.xlsx";

        List<Goods> goodsList = null;
        Map<String, Object> queryResult = query(searchType, keyWord, -1, -1);

        if (queryResult != null) {
            goodsList = (List<Goods>) queryResult.get("data");
        }

        // 获取生成的文件
        File file = goodsManageService.exportGoods(goodsList);

        // 写出文件
        if (file != null) {
            // 设置响应头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

            FileInputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[8192];

            int len;
            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }

            inputStream.close();
            outputStream.close();

        }
    }
}
