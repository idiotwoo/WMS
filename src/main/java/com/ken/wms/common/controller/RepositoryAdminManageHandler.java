package com.ken.wms.common.controller;

import com.ken.wms.common.util.Response;
import com.ken.wms.common.util.ResponseUtil;
import com.ken.wms.domain.RepositoryAdmin;
import com.ken.wms.common.service.Interface.RepositoryAdminManageService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 仓库管理员管理请求 Handler
 *
 * @author Ken
 */
@Controller
@RequestMapping(value = "/**/repositoryAdminManage")
public class RepositoryAdminManageHandler {

    @Autowired
    private RepositoryAdminManageService repositoryAdminManageService;
    @Autowired
    private ResponseUtil responseUtil;

    // 查询类型
    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_NAME = "searchByName";
    private static final String SEARCH_BY_REPOSITORY_ID = "searchByRepositoryID";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 通用记录查询
     *
     * @param keyWord    查询关键字
     * @param searchType 查询类型
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @return 返回所有符合条件的记录
     */
    private Map<String, Object> query(String keyWord, String searchType, int offset, int limit) {
        Map<String, Object> queryResult = null;

        // query
        switch (searchType) {
            case SEARCH_ALL:
                queryResult = repositoryAdminManageService.selectAll(offset, limit);
                break;
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = repositoryAdminManageService.selectByID(Integer.valueOf(keyWord));
                break;
            case SEARCH_BY_NAME:
                queryResult = repositoryAdminManageService.selectByName(offset, limit, keyWord);
                break;
            case SEARCH_BY_REPOSITORY_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = repositoryAdminManageService.selectByRepositoryID(Integer.valueOf(keyWord));
                break;
            default:
                // do other things
                break;
        }

        return queryResult;
    }

    /**
     * 查询仓库管理员信息
     *
     * @param searchType 查询类型
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @param keyWord    查询关键字
     * @return 返回一个Map，其中key=rows，表示查询出来的记录；key=total，表示记录的总条数
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getRepositoryAdminList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRepositoryAdmin(@RequestParam("searchType") String searchType,
                                           @RequestParam("keyWord") String keyWord, @RequestParam("offset") int offset,
                                           @RequestParam("limit") int limit) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        List<RepositoryAdmin> rows = null;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = query(keyWord, searchType, offset, limit);

        if (queryResult != null) {
            rows = (List<RepositoryAdmin>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 添加一条仓库管理员信息
     *
     * @param repositoryAdmin 仓库管理员信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addRepositoryAdmin", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addRepositoryAdmin(@RequestBody RepositoryAdmin repositoryAdmin) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加结果
        String result = repositoryAdminManageService.addRepositoryAdmin(repositoryAdmin)
                ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 查询指定 ID 的仓库管理员信息
     *
     * @param repositoryAdminID 仓库管理员ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库管理员信息
     */
    @RequestMapping(value = "getRepositoryAdminInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRepositoryAdminInfo(Integer repositoryAdminID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 查询
        RepositoryAdmin repositoryAdmin = null;
        Map<String, Object> queryResult = repositoryAdminManageService.selectByID(repositoryAdminID);
        if (queryResult != null) {
            if ((repositoryAdmin = (RepositoryAdmin) queryResult.get("data")) != null)
                result = Response.RESPONSE_RESULT_SUCCESS;
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseData(repositoryAdmin);
        return responseContent.generateResponse();
    }

    /**
     * 更新仓库管理员信息
     *
     * @param repositoryAdmin 仓库管理员信息
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库管理员信息
     */
    @RequestMapping(value = "updateRepositoryAdmin", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateRepositoryAdmin(@RequestBody RepositoryAdmin repositoryAdmin) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 更新
        String result = repositoryAdminManageService.updateRepositoryAdmin(repositoryAdmin)
                ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除指定 ID 的仓库管理员信息
     *
     * @param repositoryAdminID 仓库ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库管理员信息
     */
    @RequestMapping(value = "deleteRepositoryAdmin", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteRepositoryAdmin(Integer repositoryAdminID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 删除记录
        String result = repositoryAdminManageService.deleteRepositoryAdmin(repositoryAdminID)
                ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 从文件中导入仓库管理员信息
     *
     * @param file 保存有仓库管理员信息的文件
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
     * error；key为total表示导入的总条数；key为available表示有效的条数
     */
    @RequestMapping(value = "importRepositoryAdmin", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> importRepositoryAdmin(MultipartFile file) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 读取文件
        long total = 0;
        long available = 0;
        if (file != null) {
            Map<String, Object> importInfo = repositoryAdminManageService.importRepositoryAdmin(file);
            if (importInfo != null) {
                total = (long) importInfo.get("total");
                available = (long) importInfo.get("available");
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
     * 导出仓库管理员信息到文件中
     *
     * @param searchType 查询类型
     * @param keyWord    查询关键字
     * @param response   HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportRepositoryAdmin", method = RequestMethod.GET)
    public void exportRepositoryAdmin(@RequestParam("searchType") String searchType,
                                      @RequestParam("keyWord") String keyWord, HttpServletResponse response) {

        // 导出文件名
        String fileName = "repositoryAdminInfo.xlsx";

        // 查询
        List<RepositoryAdmin> repositoryAdmins;
        Map<String, Object> queryResult = query(keyWord, searchType, -1, -1);

        if (queryResult != null)
            repositoryAdmins = (List<RepositoryAdmin>) queryResult.get("data");
        else
            repositoryAdmins = new ArrayList<>();

        // 生成文件
        File file = repositoryAdminManageService.exportRepositoryAdmin(repositoryAdmins);

        // 输出文件
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
