package com.ken.wms.junitTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.ken.wms.domain.Supplier;
import com.ken.wms.service.util.ExcelUtil;

@ContextConfiguration(locations="classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ExcelUtilTest {

	@Test
	public void readTest() throws IOException{
		ExcelUtil excelUtil = new ExcelUtil("config/ExcelUtilConfig.xml");
		File file = new File("F:\\WorkSpace\\供应商信息表.xlsx");
		MultipartFile multipartFile = new MockMultipartFile("supplier", new FileInputStream(file));
		List<Object> entitys = excelUtil.excelReader(Supplier.class, multipartFile);
		for (Object object : entitys) {
			Supplier supplier = (Supplier) object;
			System.out.println(supplier);
		}
	}
}
