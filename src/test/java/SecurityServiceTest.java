package com.ken.wms.junitTest;

import com.ken.wms.service.Impl.MenuComponentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ken.wms.domain.Role;
import com.ken.wms.security.SecurityService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ContextConfiguration(locations = "classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityServiceTest {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private MenuComponentService menuComponentService;

	@Test
	public void testIsAuthorise() {
		boolean result = securityService.isAuthorise("systemAdmin", "/supplierManagement/addSupplie");
		System.out.println(result);
	}

	// @Test
	// public void testUrlPrefix(){
	// securityService.testUrlPrefix();
	// }

	@Test
	public void testIsRole() {
		boolean result = securityService.isRole("systemAdmin", "systemAdmin");
		System.out.println(result);
	}

	@Test
	public void getRoleTest() {
		List<Role> roles = securityService.getAllRole();
		System.out.println(roles);
	}

	@Test
	public void test() {
//		 menuComponentService.test();
		Map<String, List<String[]>> menus = menuComponentService.getMenuByRole("systemAdmin");
		if (menus != null) {
			Set<String> parentMenuTitles = menus.keySet();
			for (String parentMenuTitle : parentMenuTitles) {
				System.out.println("--->" + parentMenuTitle);
				List<String[]> subMenus = menus.get(parentMenuTitle);
				for (String[] subMenu : subMenus) {
					System.out.println("->" + subMenu[0] + "--" + subMenu[1]);
				}
			}
		}else
			System.out.println("null");

		// menuComponentService.test();
		// List<Map<String, String[]>> result =
		// menuComponentService.getMenuByRole("systemAdmin");
		// for (Map<String, String[]> map : result) {
		// Set<String> parentMenuKeys = map.keySet();
		// for (String parentMenu : parentMenuKeys) {
		// System.out.println("--->" + parentMenu);
		// String[] subMenus = map.get(parentMenu);
		// for (String subMenu : subMenus) {
		// System.out.println("->" + subMenu);
		// }
		// }
		// }
		// List<Map<String, List<String[]>>> result =
		// menuComponentService.getMenuByRole("systemAdmin");
		// for (Map<String, List<String[]>> map : result) {
		// Set<String> parentMenuKeys = map.keySet();
		// for (String parentMenuTitle : parentMenuKeys) {
		// System.out.println("--->" + parentMenuTitle);
		//
		// List<String[]> subMenus = map.get(parentMenuTitle);
		// for (String[] subMenuTitle : subMenus) {
		// System.out.println("->" + subMenuTitle[0] + "--" + subMenuTitle[1]);
		// }
		// }
		// }
	}
}
