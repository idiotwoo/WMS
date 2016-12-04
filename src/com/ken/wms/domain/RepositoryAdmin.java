package com.ken.wms.domain;

import java.sql.Date;

/**
 * 仓库管理员信息
 * @author Ken
 *
 */
public class RepositoryAdmin {

	private Integer id;// 仓库管理员ID
	private String name;// 姓名
	private String sex;// 性别
	private String tel;// 联系电话
	private String address;// 地址
	private Date birth;// 出生日期
	private Integer repositoryBelongID;// 所属仓库ID

	
	public Integer getRepositoryBelongID() {
		return repositoryBelongID;
	}

	public void setRepositoryBelongID(Integer repositoryBelongID) {
		this.repositoryBelongID = repositoryBelongID;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "RepositoryAdmin [id=" + id + ", name=" + name + ", sex=" + sex + ", tel=" + tel + ", address=" + address
				+ ", birth=" + birth + ", repositoryBelongID=" + repositoryBelongID + "]";
	}

}
