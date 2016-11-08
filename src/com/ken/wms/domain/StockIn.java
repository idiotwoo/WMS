package com.ken.wms.domain;

import java.sql.Date;

/**
 * 入库记录
 * 
 * @author Ken
 *
 */
public class StockIn {

	private Integer id; // 入库记录ID
	private Integer supplierID;// 供应商ID
	private String supplierName; // 供应商名称
	private Integer goodID;// 商品ID
	private String goodName; // 商品名称
	private int number; // 数量
	private Date time; // 日期
	private String personInCharge; // 经手人

	public Integer getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Integer supplierID) {
		this.supplierID = supplierID;
	}

	public Integer getGoodID() {
		return goodID;
	}

	public void setGoodID(Integer goodID) {
		this.goodID = goodID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	@Override
	public String toString() {
		return "StockIn [id=" + id + ", supplierID=" + supplierID + ", supplierName=" + supplierName + ", goodID="
				+ goodID + ", goodName=" + goodName + ", number=" + number + ", time=" + time + ", personInCharge="
				+ personInCharge + "]";
	}

}
