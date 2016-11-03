package com.ken.wms.domain;

/**
 * 侧边栏菜单信息
 * @author Ken
 *
 */
public class Menu {

	private Integer id;// 菜单项ID
	private String menuTitle;// 标题
	private String menuDesc;// 描述
	private String menuURL;// 标题指向页面的 URL
	private String parentMenuTitle;// 所属父标题

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public String getParentMenuTitle() {
		return parentMenuTitle;
	}

	public void setParentMenuTitle(String parentMenuTitle) {
		this.parentMenuTitle = parentMenuTitle;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuTitle=" + menuTitle + ", menuDesc=" + menuDesc + ", menuURL=" + menuURL
				+ ", parentMenuTitle=" + parentMenuTitle + "]";
	}

}
