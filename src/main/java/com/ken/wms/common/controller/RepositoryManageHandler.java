package com.ken.wms.common.controller;

import com.ken.wms.common.util.Response;
import com.ken.wms.common.util.ResponseUtil;
import com.ken.wms.domain.Repository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ken.wms.common.service.Interface.RepositoryService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库信息管理请求 Handler
 *
 * @author Ken
 */
@Controller
@RequestMapping(value = "/**/repositoryManage")
public class RepositoryManageHandler {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ResponseUtil responseUtil;

    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_ADDRESS = "searchByAddress";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 通用的记录查询
     *
     * @param searchType 查询方式
     * @param keyword    查询关键字
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @return 返回所有符合条件的查询结果
     */
    private Map<String, Object> query(String searchType, String keyword, int offset, int limit) {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyword)) {
                    queryResult = repositoryService.selectById(Integer.valueOf(keyword));
                }
                break;
            case SEARCH_BY_ADDRESS:
                queryResult = repositoryService.selectByAddress(offset, limit, keyword);
                break;
            case SEARCH_ALL:
                queryResult = repositoryService.selectAll(offset, limit);
                break;
            default:
                // do other thing
                break;
        }

        return queryResult;
    }

    /**
     * 查询仓库信息
     *
     * @param searchType 查询类型
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @param keyWord    查询关键字
     * @return 返回一个Map，其中key=rows，表示查询出来的记录；key=total，表示记录的总条数
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getRepositoryList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRepositoryList(@RequestParam("searchType") String searchType,
                                          @RequestParam("offset") int offset, @RequestParam("limit") int limit,
                                          @RequestParam("keyWord") String keyWord) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        List<Repository> rows = null;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

        if (queryResult != null) {
            rows = (List<Repository>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 查询所有未指派管理员的仓库
     *
     * @return 返回一个 map，其中key=data表示查询的记录，key=total表示记录的条数
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getUnassignRepository", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getUnassignRepository() {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Repository> data;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = repositoryService.selectUnassign();
        if (queryResult != null) {
            data = (List<Repository>) queryResult.get("data");
            total = (long) queryResult.get("total");
        } else
            data = new ArrayList<>();

        resultSet.put("data", data);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加一条仓库信息
     *
     * @param repository 仓库信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addRepository", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addRepository(@RequestBody Repository repository) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加记录
        String result = repositoryService.addRepository(repository) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 查询指定 ID 的仓库信息
     *
     * @param repositoryID 仓库ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库信息
     */
    @RequestMapping(value = "getRepositoryInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRepositoryInfo(@RequestParam("repositoryID") Integer repositoryID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 查询
        Repository repository = null;
        Map<String, Object> queryResult = repositoryService.selectById(repositoryID);
        if (queryResult != null) {
            repository = (Repository) queryResult.get("data");
            if (repository != null)
                result = Response.RESPONSE_RESULT_SUCCESS;
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseData(repository);
        return responseContent.generateResponse();
    }

    /**
     * 更新仓库信息
     *
     * @param repository 仓库信息
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库信息
     */
    @RequestMapping(value = "updateRepository", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateRepository(@RequestBody Repository repository) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 更新
        String result = repositoryService.updateRepository(repository) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除指定 ID 的仓库信息
     *
     * @param repositoryID 仓库ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库信息
     */
    @RequestMapping(value = "deleteRepository", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteRepository(@RequestParam("repositoryID") Integer repositoryID) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 删除记录
        String result = repositoryService.deleteRepository(repositoryID) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 从文件中导入仓库信息
     *
     * @param file 保存有仓库信息的文件
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
     * error；key为total表示导入的总条数；key为available表示有效的条数
     */
    @RequestMapping(value = "importRepository", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> importRepository(MultipartFile file) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;

        // 读取文件
        int total = 0;
        int available = 0;
        if (file != null) {
            Map<String, Object> importInfo = repositoryService.importRepository(file);
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
     * 导出仓库信息到文件中
     *
     * @param searchType 查询类型
     * @param keyWord    查询关键字
     * @param response   HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportRepository", method = RequestMethod.GET)
    public void exportRepository(@RequestParam("searchType") String searchType, @RequestParam("keyWord") String keyWord,
                                 HttpServletResponse response) {

        // 导出文件名
        String fileName = "repositoryInfo.xlsx";

        // 查询
        List<Repository> repositories;

        Map<String, Object> queryResult = query(searchType, keyWord, -1, -1);

        if (queryResult != null)
            repositories = (List<Repository>) queryResult.get("data");
        else
            repositories = new ArrayList<>();

        // 生成文件
        File file = repositoryService.exportRepository(repositories);

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
