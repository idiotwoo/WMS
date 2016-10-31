package com.ken.wms.domain;

public class Repository {

	private Integer id;
	private String address;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Repository(Integer id, String address, String status) {
		super();
		this.id = id;
		this.address = address;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Repository [id=" + id + ", address=" + address + ", status=" + status + "]";
	}

}
