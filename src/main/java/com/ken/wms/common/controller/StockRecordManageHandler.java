package com.ken.wms.common.controller;

import com.ken.wms.common.service.Interface.StockRecordManageService;
import com.ken.wms.common.util.Response;
import com.ken.wms.common.util.ResponseUtil;
import com.ken.wms.domain.StockRecordDTO;
import com.ken.wms.exception.StockRecordManageServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品出入库管理请求Handler
 *
 * @author Ken
 * @since 017/4/5.
 */
@Controller
@RequestMapping(value = "stockRecordManage")
public class StockRecordManageHandler {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private StockRecordManageService stockRecordManageService;

    /**
     * 货物出库操作
     *
     * @param customerID   客户ID
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       出库数量
     * @param request      http请求
     * @return 返回一个map，key为result的值表示操作是否成功
     */
    @RequestMapping(value = "stockOut", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> stockOut(@RequestParam("customerID") Integer customerID,
                                 @RequestParam("goodsID") Integer goodsID, @RequestParam("repositoryID") Integer repositoryID,
                                 @RequestParam("number") long number, HttpServletRequest request) throws StockRecordManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        HttpSession session = request.getSession();
        String personInCharge = (String) session.getAttribute("userName");

        String result = stockRecordManageService.stockOutOperation(customerID, goodsID, repositoryID, number, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 货物入库操作
     *
     * @param supplierID   供应商ID
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       入库数目
     * @param request      http 请求
     * @return 返回一个map，key为result的值表示操作是否成功
     */
    @RequestMapping(value = "stockIn", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> stockIn(@RequestParam("supplierID") Integer supplierID,
                                @RequestParam("goodsID") Integer goodsID, @RequestParam("repositoryID") Integer repositoryID,
                                @RequestParam("number") long number, HttpServletRequest request) throws StockRecordManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        HttpSession session = request.getSession();
        String personInCharge = (String) session.getAttribute("userName");

        String result = stockRecordManageService.stockInOperation(supplierID, goodsID, repositoryID, number, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 查询出入库记录
     *
     * @param searchType   查询类型（查询所有或仅查询入库记录或仅查询出库记录）
     * @param repositoryID 查询记录所对应的仓库ID
     * @param endDateStr   查询的记录起始日期
     * @param startDateStr 查询的记录结束日期
     * @param limit        分页大小
     * @param offset       分页偏移值
     * @return 返回一个Map，其中：Key为rows的值代表所有记录数据，Key为total的值代表记录的总条数
     */
    @SuppressWarnings({"SingleStatementInBlock", "unchecked"})
    @RequestMapping(value = "searchStockRecord", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> searchStockRecord(@RequestParam("searchType") String searchType,
                                          @RequestParam("repositoryID") Integer repositoryID,
                                          @RequestParam("startDate") String startDateStr,
                                          @RequestParam("endDate") String endDateStr,
                                          @RequestParam("limit") int limit,
                                          @RequestParam("offset") int offset) throws ParseException, StockRecordManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 转到 Service 执行查询
        Map<String, Object> queryResult = stockRecordManageService.selectStockRecord(repositoryID, startDateStr, endDateStr, searchType, offset, limit);
        List<StockRecordDTO> stockRecordDTOS;
        long total = 0;
        if (queryResult != null) {
            stockRecordDTOS = (List<StockRecordDTO>) queryResult.get("data");
            total = (long) queryResult.get("total");
        } else
            stockRecordDTOS = new ArrayList<>(10);

        responseContent.setCustomerInfo("rows", stockRecordDTOS);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }
}
