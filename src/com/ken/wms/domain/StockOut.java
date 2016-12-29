package com.ken.wms.domain;

import java.sql.Date;

/**
 * 出库记录
 * 
 * @author Ken
 *
 */
public class StockOut {

	private Integer id;// 出库记录ID
	private Integer customerID;// 客户ID
	private String customerName;// 客户名称
	private Integer goodID;// 商品ID
	private String goodName;// 商品名称
	private Integer repositoryID;// 出库仓库ID
	private long number;// 数量
	private Date time;// 日期
	private String personInCharge;// 经手人

	public Integer getRepositoryID() {
		return repositoryID;
	}

	public void setRepositoryID(Integer repositoryID) {
		this.repositoryID = repositoryID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getGoodID() {
		return goodID;
	}

	public void setGoodID(Integer goodID) {
		this.goodID = goodID;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
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
		return "StockOut [id=" + id + ", customerID=" + customerID + ", customerName=" + customerName + ", goodID="
				+ goodID + ", goodName=" + goodName + ", repositoryID=" + repositoryID + ", number=" + number
				+ ", time=" + time + ", personInCharge=" + personInCharge + "]";
	}

}
