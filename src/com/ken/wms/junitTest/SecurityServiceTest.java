package com.ken.wms.junitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ken.wms.security.SecurityService;

@ContextConfiguration(locations="classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityServiceTest {

	@Autowired
	private SecurityService securityService;
	
	@Test
	public void testIsAuthorise(){
		boolean result = securityService.isAuthorise("systemAdmin", "/supplierManagement/addSupplie");
		System.out.println(result);
	}
	
//	@Test
//	public void testUrlPrefix(){
//		securityService.testUrlPrefix();
//	}
	
	@Test
	public void testIsRole(){
		boolean result = securityService.isRole("systemAdmin", "systemAdmin");
		System.out.println(result);
	}
}
