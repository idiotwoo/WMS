package com.ken.wms.domain;

/**
 * 货物信息
 * @author Ken
 *
 */
public class Goods {

	private Integer id;// 货物ID
	private String name;// 货物名
	private String type;// 货物类型
	private String size;// 货物规格
	private Double value;// 货物价值

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", type=" + type + ", size=" + size + ", value=" + value + "]";
	}

}
