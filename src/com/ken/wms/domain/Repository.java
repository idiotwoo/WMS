package com.ken.wms.domain;

/**
 * 仓库信息
 * @author Ken
 *
 */
public class Repository {

	private Integer id;// 仓库ID
	private String address;// 仓库地址
	private String status;// 仓库状态
	private String area;// 仓库面积
	private String desc;// 仓库描述
	private Integer adminID;//仓库管理员ID
	private String adminName; //仓库管理员名字

	public Integer getAdminID() {
		return adminID;
	}

	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

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

	@Override
	public String toString() {
		return "Repository [id=" + id + ", address=" + address + ", status=" + status + ", area=" + area + ", desc="
				+ desc + ", adminID=" + adminID + ", adminName=" + adminName + "]";
	}

}
