package com.ken.wms.junitTest;

import java.util.ArrayList;
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
import com.ken.wms.dao.StockInMapper;
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
	@Autowired
	private StockInMapper stockInMapper;
	
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
	
	@Test
	public void supplierMapperTest(){
		// test select operation
//		Supplier supplier = supplierMapper.selectById(2);
//		System.out.println(supplier);
//		Supplier supplier = supplierMapper.selectBuName("AA");
//		System.out.println(supplier);
//		List<Supplier> suppliers = supplierMapper.selectAll();
//		for (Supplier supplier : suppliers) {
//			System.out.println(supplier);
//		}
		
		// test insert operation
//		Supplier supplier = new Supplier();
//		supplier.setName("cc");
//		supplier.setPersonInCharge("person");
//		supplier.setTel("789");
//		supplier.setEmail("789@outlook.com");
//		supplier.setAddress("FoShan");
//		supplierMapper.insert(supplier);
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
		List<Supplier> suppliers = new ArrayList<>();
		suppliers.add(supplier1);
		suppliers.add(supplier2);
		supplierMapper.insertBatch(suppliers);
		
		// test update operation
//		Supplier supplier = supplierMapper.selectById(3);
//		supplier.setPersonInCharge("person0");
//		supplierMapper.update(supplier);
//		Supplier supplier = supplierMapper.selectById(3);
//		supplier.setId(null);
//		supplierMapper.update(supplier);
		
		// test delete operation
//		supplierMapper.deleteById(4);
//		supplierMapper.deleteByName("EE");
		
//		List<Supplier> suppliers = supplierMapper.selectApproximateByName("A");
//		if(suppliers != null){
//			System.out.println(suppliers.size());
//			for (Supplier supplier : suppliers) {
//				System.out.println(supplier);
//			}
//		}
		// return id test
//		Supplier supplier = new Supplier();
//		supplier.setName("EE");
//		supplier.setPersonInCharge("person2");
//		supplier.setTel("7123d89");
//		supplier.setEmail("712d389@outlook.com");
//		supplier.setAddress("HongKong");
//		supplierMapper.insert(supplier);
//		if(supplier.getId() == null){
//			System.out.println("fail");
//		}else{
//			System.out.println("success");
//			System.out.println(supplier.getId());
//		}
	}
		
	@Test
	public void stockInTest(){
		// select operation test
//		List<StockIn> stockIns = stockInMapper.selectAll();
//		for (StockIn stockIn : stockIns) {
//			System.out.println(stockIn);
//		}
//		List<StockIn> stockIns = stockInMapper.selectBySupplierID(2);
//		for (StockIn stockIn : stockIns) {
//			System.out.println(stockIn);
//		}
//		List<StockIn> stockIns = stockInMapper.selectByGoodID(1);
//		for (StockIn stockIn : stockIns) {
//			System.out.println(stockIn);
//		}
//		StockIn stockIns = stockInMapper.selectByID(11);
//		System.out.println(stockIns);
		
//		// insert operation test
//		StockIn stockIn = new StockIn();
//		stockIn.setSupplierID(2);
//		stockIn.setGoodID(1);
//		stockIn.setNumber(1000);
//		stockIn.setTime(new Date(new java.util.Date().getTime()));
//		stockIn.setPersonInCharge("AA");
//		stockInMapper.insert(stockIn);
		
		// update operation test
//		StockIn stockIn = stockInMapper.selectByID(12);
//		stockIn.setNumber(1500);
//		stockInMapper.update(stockIn);
		
		// delete operation test
//		stockInMapper.deleteByID(12);
	}
}
