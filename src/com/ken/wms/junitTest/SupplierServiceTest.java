package com.ken.wms.junitTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ken.wms.domain.Supplier;
import com.ken.wms.service.Interface.SupplierManageService;

@ContextConfiguration(locations = "classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SupplierServiceTest {

	@Autowired
	private SupplierManageService supplierMapper;
	
	@SuppressWarnings("unchecked")
	@Test
	public void selectByIdTest(){
		Map<String, Object> suppliersResult = supplierMapper.selectById(1);
		List<Supplier> suppliers = (List<Supplier>) suppliersResult.get("data");
		int total = (int) suppliersResult.get("total");
		System.out.println("total:" + total);
		for (Supplier supplier : suppliers) {
			System.out.println(supplier);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void selectByNameTest(){
		Map<String, Object> supplierResult = supplierMapper.selectByName(0, 2, "A");
		List<Supplier> suppliers = (List<Supplier>) supplierResult.get("data");
		long total = (long) supplierResult.get("total");
		
		System.out.println("total:" + total);
		for (Supplier supplier : suppliers) {
			System.out.println(supplier);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void selectAllTest(){
		Map<String, Object> supplierResult = supplierMapper.selectAll(0, 4);
		List<Supplier> suppliers = (List<Supplier>) supplierResult.get("data");
		long total = (long) supplierResult.get("total");
		
		System.out.println("total:" + total);
		for (Supplier supplier : suppliers) {
			System.out.println(supplier);
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void importTest() throws IOException{
		File file = new File("F:\\WorkSpace\\供应商信息表.xlsx");
		MultipartFile multipartFile = new MockMultipartFile("supplier", new FileInputStream(file));
		Map<String, Object> result = supplierMapper.importSupplier(multipartFile);
		if(result != null){
			int total = (int) result.get("total");
			int available = (int) result.get("available");
			System.out.println("total:" + total);
			System.out.println("available" + available);
		}
	}
}
