package com.ken.wms.controller.commons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理基本的页面跳转
 * @author Ken
 *
 */
@RequestMapping("/")
@Controller
public class PageDispatchHandler {
	
	/**
	 * 跳转到默认的 login 页面
	 * @return
	 */
	@RequestMapping("")
	public String showLoginView(){
		return "mainPage";
	}
	
	/**
	 * 跳转到 commonsAdmin 页面
	 * @return
	 */
	@RequestMapping("commonsAdmin")
	public String showCommonsAdminView(){
		return "commonsAdmin";
	}
	
	/**
	 * 跳转到 systemAdmin 页面
	 * @return
	 */
	@RequestMapping("systemAdmin")
	public String showSystemAdminView(){
		return "systemAdmin";
	}
}
