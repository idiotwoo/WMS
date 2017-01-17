package com.ken.wms.junitTest;

import com.ken.wms.domain.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import com.ken.wms.service.util.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	public void writeTest() throws Exception{
		ExcelUtil excelUtil = new ExcelUtil("config/ExcelUtilConfig.xml");
		List<Supplier> suppliers = new ArrayList<>();
		Supplier supplier1 = new Supplier();
		Supplier supplier2 = new Supplier();
		supplier1.setName("DD");
		supplier1.setPersonInCharge("person1");
		supplier1.setTel("123456");
		supplier1.setEmail("123456@outlook.com");
		supplier1.setAddress("BeiJing");
		supplier2.setName("EE");
		supplier2.setPersonInCharge("person2");
		supplier2.setTel("456789");
		supplier2.setEmail("456789@outlook.com");
		supplier2.setAddress("TianJing");
		suppliers.add(supplier1);
		suppliers.add(supplier2);
		
		File file = excelUtil.excelWriter(Supplier.class, suppliers);
		if(file == null)
			System.out.println("null");
		else
			System.out.println(file.getName());
		
		MultipartFile multipartFile = new MockMultipartFile("supplier", new FileInputStream(file));
		List<Object> entitys = excelUtil.excelReader(Supplier.class, multipartFile);
		for (Object object : entitys) {
			Supplier supplier = (Supplier) object;
			System.out.println(supplier);
		}
	}
}
