package com.ken.wms;

import com.ken.wms.common.service.Interface.StockRecordManageService;
import com.ken.wms.dao.StockInMapper;
import com.ken.wms.dao.StockOutMapper;
import com.ken.wms.domain.StockInDO;
import com.ken.wms.domain.StockOutDO;
import com.ken.wms.exception.StockRecordManageServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Ken on 2017/4/5.
 */
@ContextConfiguration(locations = "classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StockMapperTest {

    @Autowired
    private
    StockInMapper stockInMapper;
    @Autowired
    private StockOutMapper stockOutMapper;
    @Autowired
    private StockRecordManageService stockRecordManageService;

    @Test
    public void selectStockRecordTest() throws StockRecordManageServiceException {
        Map<String, Object> result = stockRecordManageService.selectStockRecord(-1, "", "", "all", 5, 5);
    }

    @Test
    public void selectByRepositoryIDAndDateTest() throws ParseException {
        String startDateString = "2017-04-03";
        String endDateString = "2017-04-06";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);
        Date newDate = new Date();
        System.out.println(endDate);
        System.out.println(newDate);
        List<StockInDO> stockInDOS = stockInMapper.selectByRepositoryIDAndDate(1005, startDate, endDate);
        stockInDOS.forEach(System.out::println);
    }

    @Test
    public void selectStockOutByRepositoryIDAndDateTest() {
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByRepositoryIDAndDate(1003, null, null);
        stockOutDOS.forEach(System.out::println);
    }
}
