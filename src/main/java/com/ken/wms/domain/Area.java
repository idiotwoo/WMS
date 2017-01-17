package com.ken.wms.domain;

/**
 * 仓库内区域信息
 * @author Ken
 *
 */
public class Area {

	private Integer id;// 区域ID
	private String repositoryBelong;// 所属仓库

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRepositoryBelong() {
		return repositoryBelong;
	}

	public void setRepositoryBelong(String repositoryBelong) {
		this.repositoryBelong = repositoryBelong;
	}

	public Area(Integer id, String repositoryBelong) {
		super();
		this.id = id;
		this.repositoryBelong = repositoryBelong;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", repositoryBelong=" + repositoryBelong + "]";
	}

}
