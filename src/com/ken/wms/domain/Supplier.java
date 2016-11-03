package com.ken.wms.domain;

/**
 * 供应商信息
 * @author Ken
 *
 */
public class Supplier {

	private Integer id;// 供应商ID
	private String name;// 供应商名
	private String personInCharge;// 负责人
	private String tel;// 联系电话
	private String email;// 电子邮件
	private String address;// 供应商地址

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

	public Supplier(Integer id, String name, String personInCharge, String tel, String email, String address) {
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
		return "Supplier [id=" + id + ", name=" + name + ", personInCharge=" + personInCharge + ", tel=" + tel
				+ ", email=" + email + ", address=" + address + "]";
	}

}
