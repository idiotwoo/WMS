package com.ken.wms.common.controller;

import com.ken.wms.common.util.Response;
import com.ken.wms.common.util.ResponseUtil;
import com.ken.wms.domain.Supplier;
import com.ken.wms.common.service.Interface.SupplierManageService;
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
 * 供应商信息管理请求 Handler
 *
 * @author Ken
 */
@RequestMapping(value = "/**/supplierManage")
@Controller
public class SupplierManageHandler {

    @Autowired
    private SupplierManageService supplierManageService;
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
     * @return 返回所有符合条件的记录
     */
    private Map<String, Object> query(String searchType, String keyWord, int offset, int limit) {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyWord)) {
                    queryResult = supplierManageService.selectById(Integer.valueOf(keyWord));
                }
                break;
            case SEARCH_BY_NAME:
                queryResult = supplierManageService.selectByName(offset, limit, keyWord);
                break;
            case SEARCH_ALL:
                queryResult = supplierManageService.selectAll(offset, limit);
                break;
            default:
                // do other thing
                break;
        }

        return queryResult;
    }

    /**
     * 搜索供应商信息
     *
     * @param searchType 搜索类型
     * @param offset     如有多条记录时分页的偏移值
     * @param limit      如有多条记录时分页的大小
     * @param keyWord    搜索的关键字
     * @return
     */
    @RequestMapping(value = "getSupplierList", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public
    @ResponseBody
    Map<String, Object> getSupplierList(@RequestParam("searchType") String searchType,
                                        @RequestParam("offset") int offset, @RequestParam("limit") int limit,
                                        @RequestParam("keyWord") String keyWord) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        List<Supplier> rows = null;
        long total = 0;

        Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

        // 结果转换
        if (queryResult != null) {
            rows = (List<Supplier>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 添加一条供应商信息
     *
     * @param supplier 供应商信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addSupplier", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addSupplier(@RequestBody Supplier supplier) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加记录
        String result = supplierManageService.addSupplier(supplier) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 查询指定 supplierID 供应商的信息
     *
     * @param supplierID 供应商ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为供应商信息
     */
    @RequestMapping(value = "getSupplierInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getSupplierInfo(@RequestParam("supplierID") int supplierID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 获取供应点信息
        Supplier supplier = null;
        Map<String, Object> queryResult = supplierManageService.selectById(supplierID);
        if (queryResult != null) {
            supplier = (Supplier) queryResult.get("data");
            if (supplier != null)
                result = Response.RESPONSE_RESULT_SUCCESS;
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseData(supplier);
        return responseContent.generateResponse();
    }

    /**
     * 更新供应商信息
     *
     * @param supplier 供应商信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "updateSupplier", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateSupplier(@RequestBody Supplier supplier) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 更新
        String result = supplierManageService.updateSupplier(supplier) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除供应商记录
     *
     * @param supplierID 供应商ID
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "deleteSupplier", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteSupplier(@RequestParam("supplierID") Integer supplierID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 刪除
        String result = supplierManageService.deleteSupplier(supplierID) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 导入供应商信息
     *
     * @param file 保存有供应商信息的文件
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
     * error；key为total表示导入的总条数；key为available表示有效的条数
     */
    @RequestMapping(value = "importSupplier", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> importSupplier(@RequestParam("file") MultipartFile file) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_SUCCESS;

        // 读取文件内容
        int total = 0;
        int available = 0;
        if (file == null)
            result = Response.RESPONSE_RESULT_ERROR;
        Map<String, Object> importInfo = supplierManageService.importSupplier(file);
        if (importInfo != null) {
            total = (int) importInfo.get("total");
            available = (int) importInfo.get("available");
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseTotal(total);
        responseContent.setCustomerInfo("available", available);
        return responseContent.generateResponse();
    }

    /**
     * 导出供应商信息
     *
     * @param searchType 查找类型
     * @param keyWord    查找关键字
     * @param response   HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportSupplier", method = RequestMethod.GET)
    public void exportSupplier(@RequestParam("searchType") String searchType, @RequestParam("keyWord") String keyWord,
                               HttpServletResponse response) {

        String fileName = "supplierInfo.xlsx";

        // 根据查询类型进行查询
        List<Supplier> suppliers = null;
        Map<String, Object> queryResult;
        queryResult = query(searchType, keyWord, -1, -1);

        if (queryResult != null) {
            suppliers = (List<Supplier>) queryResult.get("data");
        }

        // 获取生成的文件
        File file = supplierManageService.exportSupplier(suppliers);

        // 写出文件
        if (file != null) {
            // 设置响应头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            try {
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

            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }
}
