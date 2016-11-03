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
	private double value;// 货物价值

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Goods(Integer id, String name, String type, String size, double value) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", type=" + type + ", size=" + size + ", value=" + value + "]";
	}

}
