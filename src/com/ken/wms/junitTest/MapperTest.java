package com.ken.wms.junitTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ken.wms.controller.Enum.ResponseStatus;
import com.ken.wms.dao.ActionPermissionMapper;
import com.ken.wms.dao.MenuMapper;
import com.ken.wms.dao.RoleMapper;
import com.ken.wms.dao.SupplierMapper;
import com.ken.wms.dao.UserMapper;
import com.ken.wms.domain.ActionPermission;
import com.ken.wms.domain.Menu;
import com.ken.wms.domain.Role;
import com.ken.wms.domain.Supplier;
import com.ken.wms.domain.User;

@ContextConfiguration(locations="classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ActionPermissionMapper permissionMapper;
	@Autowired
	private MenuMapper menuMapper;
	
	@Test
	public void test() {
		List<Supplier> suppliers = supplierMapper.selectAll();
		
		for (Supplier supplier : suppliers) {
			System.out.println(supplier);
		}
	}
	
	@Test
	public void testMenuMapper(){
//		List<Menu> menus = menuMapper.selectAll();
//		for (Menu menu : menus) {
//			System.out.println(menu);
//		}
		
		List<Menu> menus = menuMapper.selectByRoleName("commonsAdmin");
		for (Menu menu : menus) {
			System.out.println(menu);
		}
	}
	
	@Test
	public void ensum(){
		String error = ResponseStatus.ERROR.toString();
		System.out.println(error);
	}
	
	@Test
	public void permissionTest(){
//		List<ActionPermission> permission = permissionMapper.selectByRole("systemAdmin");
//		for (ActionPermission actionPermission : permission) {
//			System.out.println(actionPermission);
//		}
		
		ActionPermission permission = permissionMapper.selectById(1);
		System.out.println(permission);
	}
	
	@Test
	public void roleMapperTest(){
		// test select operation
//		Role role = roleMapper.selectById(1);
//		System.out.println(role);
		List<Role> roles = roleMapper.selectAll();
		for (Role role : roles) {
			System.out.println(role);
		}
		
//		Role role = roleMapper.selectByUserId(1001);
//		System.out.println(role);
		
		// test insert operation
//		Role role = new Role();
//		role.setRoleName("public");
//		role.setRolePrefix("public");
//		roleMapper.insert(role);
		
		// test delete operation
//		roleMapper.delete(3);
	}
	
	@Test
	public void userMapperTest(){
		// test select operation
		User user = userMapper.selectById(1);
		System.out.println("selectById() Id=1 : " + user);
//		
//		user = userMapper.selectByName("ken");
//		System.out.println("selectByName() name=ken : " + user);
//		
//		List<User> users = userMapper.selectAll();
//		System.out.println("selectAll():");
//		System.out.println("count:" + users.size());
//		for (User user2 : users) {
//			System.out.println(user2);
//		}
		
		// test insert operation
//		User user = new User();
//		user.setUserName("AA");
//		user.setPassword("123");
//		user.setUserType("commonsAdmin");
//		userMapper.insert(user);
		
		//test update operation
//		User user = userMapper.selectById(2);
//		user.setUserName("BB");
//		userMapper.updateById(user);
//		user = userMapper.selectById(2);
//		System.out.println("updateById(): " + user);
		
		//test delete operation
//		userMapper.deleteById(3);
	}

}
