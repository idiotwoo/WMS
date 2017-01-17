package com.ken.wms.controller.commons;

import com.ken.wms.service.Impl.MenuComponentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 负责页面加载
 * @author Ken
 *
 */
@RequestMapping("/")
@Controller
public class PageLoadHandler {
	
	// 日志
	private static Logger log = Logger.getLogger("application");
	
	@Autowired
	private MenuComponentService menuComponentService;
	
	/**
	 * 跳转到主页面
	 * @return
	 */
	@RequestMapping("")
	public String showLoginView(){
		return "mainPage";
	}
	
	/**
	 * 获取侧边栏菜单项
	 * @param request
	 * @return
	 */
	@RequestMapping(value="commons/pageLoad/loadMenus",method=RequestMethod.GET)
	public @ResponseBody Map<String, List<String[]>> loadMenu(HttpServletRequest request){
		// 获得 Session
		HttpSession session = request.getSession();
		Map<String, List<String[]>> menus = null;
		
		String roleName = (String) session.getAttribute("role");
		if(roleName != null)
			menus = menuComponentService.getMenuByRole(roleName);
		
		log.debug("get  menus by role:" + roleName);
		if(menus != null){
			log.debug("get menus successfully");
		}
		
		return menus;
	}
	
}
