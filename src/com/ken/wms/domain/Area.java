package com.ken.wms.domain;

public class Area {

	private Integer id;
	private String repositoryBelong;

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
