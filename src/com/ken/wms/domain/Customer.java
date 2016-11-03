package com.ken.wms.domain;

/**
 * 客户信息
 * @author Ken
 *
 */
public class Customer {

	private Integer id;// 客户ID
	private String name;// 客户名
	private String personInCharge;// 负责人
	private String tel;// 联系电话
	private String email;// 电子邮件
	private String address;// 地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Customer(Integer id, String name, String personInCharge, String tel, String email, String address) {
		super();
		this.id = id;
		this.name = name;
		this.personInCharge = personInCharge;
		this.tel = tel;
		this.email = email;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", personInCharge=" + personInCharge + ", tel=" + tel
				+ ", email=" + email + ", address=" + address + "]";
	}

}
