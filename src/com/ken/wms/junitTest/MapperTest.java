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
import com.ken.wms.dao.CustomerMapper;
import com.ken.wms.dao.GoodsMapper;
import com.ken.wms.dao.MenuMapper;
import com.ken.wms.dao.RoleMapper;
import com.ken.wms.dao.StockInMapper;
import com.ken.wms.dao.StockOutMapper;
import com.ken.wms.dao.SupplierMapper;
import com.ken.wms.dao.UserMapper;
import com.ken.wms.domain.ActionPermission;
import com.ken.wms.domain.Customer;
import com.ken.wms.domain.Goods;
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
	@Autowired
	private StockOutMapper stockOutMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	
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
	
	@Test
	public void stockOutTest(){
		// insert operation test
//		StockOut stockOut = new StockOut();
//		stockOut.setCustomerID(4);
//		stockOut.setGoodID(1);
//		stockOut.setNumber(100);
//		stockOut.setPersonInCharge("person1");
//		stockOut.setTime(new Date(new java.util.Date().getTime()));
//		stockOutMapper.insert(stockOut);
		
		// select operation test
//		List<StockOut> stockOuts = stockOutMapper.selectAll();
//		for (StockOut stockOut : stockOuts) {
//			System.out.println(stockOut);
//		}
//		List<StockOut> stockOuts = stockOutMapper.selectByCustomerId(4);
//		for (StockOut stockOut : stockOuts) {
//			System.out.println(stockOut);
//		}
//		List<StockOut> stockOuts = stockOutMapper.selectByGoodId(1);
//		for (StockOut stockOut : stockOuts) {
//			System.out.println(stockOut);
//		}
		
//		// update operation test
//		StockOut stockOut = stockOutMapper.selectById(2);
//		stockOut.setPersonInCharge("person3");
//		stockOutMapper.update(stockOut);
		
		// delete operation test
		stockOutMapper.deleteById(3);
	}
	
	@Test
	public void customerTest(){
		// insert operation test
//		Customer customer = new Customer();
//		customer.setName("AA");
//		customer.setPersonInCharge("person1");
//		customer.setTel("123456");
//		customer.setEmail("123@outlook.com");
//		customer.setAddress("GuanZhou");
//		customerMapper.insert(customer);
		Customer customer1 = new Customer();
		Customer customer2 = new Customer();
		customer1.setName("BB");
		customer1.setPersonInCharge("person2");
		customer1.setTel("789123");
		customer1.setEmail("6987@outlook.com");
		customer1.setAddress("ShenZhen");
		customer2.setName("CC");
		customer2.setPersonInCharge("person3");
		customer2.setTel("987654");
		customer2.setEmail("1456@outlook.com");
		customer2.setAddress("FoShan");
		List<Customer> customers = new ArrayList<>();
		customers.add(customer2);
		customers.add(customer1);
		customerMapper.insertBatch(customers);
		
		// select operation test
//		List<Customer> customers = customerMapper.selectAll();
//		for (Customer customer : customers) {
//			System.out.println(customer);
//		}
//		Customer customer = customerMapper.selectById(2);
//		System.out.println(customer);
//		Customer customer = customerMapper.selectByName("BB");
//		System.out.println(customer);
//		List<Customer> customers = customerMapper.selectApproximateByName("A");
//		for (Customer customer : customers) {
//			System.out.println(customer);
//		}
		
		// update operation test
//		Customer customer = customerMapper.selectById(2);
//		customer.setName("CC");
//		customerMapper.update(customer);
		
		// delete operation test
//		customerMapper.deleteById(2);
//		customerMapper.deleteByName("BB");
	}
	
	@Test
	public void goodsMapper(){
		// insert operation test
//		Goods goods = new Goods();
//		goods.setName("Banana");
//		goods.setType("fruit");
//		goods.setValue(1);
//		goodsMapper.insert(goods);
//		Goods goods1 = new Goods();
//		goods1.setName("Cup");
//		goods1.setType("pottery");
//		goods1.setValue(10);
//		Goods goods2 = new Goods();
//		goods2.setName("Phone");
//		goods2.setType("Electricity Product");
//		goods2.setValue(1000);
//		List<Goods> goods = new ArrayList<>();
//		goods.add(goods2);
//		goods.add(goods1);
//		goodsMapper.insertBatch(goods);
		
		// select operation test
//		List<Goods> result = goodsMapper.selectAll();
//		for (Goods goods : result) {
//			System.out.println(goods);
//		}
//		Goods goods = goodsMapper.selectById(1);
//		System.out.println(goods);
//		Goods goods = goodsMapper.selectByName("phone");
//		System.out.println(goods);
//		List<Goods> goods = goodsMapper.selectApproximateByName("a");
//		for (Goods good1 : goods) {
//			System.out.println(good1);
//		}
//		goodsMapper.deleteById(4);
		goodsMapper.deleteByName("phone");
	}
}
