package com.ken.wms.service.Impl;


import com.ken.wms.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ken.wms.dao.MenuMapper;
import com.ken.wms.domain.Role;
import com.ken.wms.security.SecurityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 侧边栏菜单组件加载
 * 保存有每一个 role 所授权访问的菜单项，菜单项包括子菜单以及对应的父菜单项
 * 调用 getMenuByRole（） 返回role 可访问的菜单项
 * @author Ken
 *
 */
@Service
public class MenuComponentService {

	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private SecurityService securityService;
	
	// role - menu map
	private Map<String, Map<String, List<String[]>>> roleMenusMap;

	@Autowired
	public void init(){
		// 初始化
		roleMenusMap = new HashMap<>();
		
		// 读取数据
		List<Role> roles = securityService.getAllRole();
		if(roles == null)
			return;
		
		for (Role role : roles) {
			String roleName = role.getRoleName();
			Map<String, List<String[]>> roleMenus = new HashMap<>();
			roleMenusMap.put(roleName, roleMenus);
			
			// 查询 role 授权的菜单
			List<Menu> menus = menuMapper.selectByRoleName(roleName);
			
			// 构造父菜单 map
			for (Menu menu : menus) {
				String parentMenuTiitle = menu.getParentMenuTitle();
				if(!roleMenus.containsKey(parentMenuTiitle)){
					roleMenus.put(parentMenuTiitle, new ArrayList<>());
				}
			}
			// 读取子菜单信息
			for (Menu menu : menus) {
				String parentMenuTitle = menu.getParentMenuTitle();
				String[] info = new String[2];
				info[0] = menu.getMenuTitle();
				info[1] = menu.getMenuURL();
				List<String[]> subMenu = roleMenus.get(parentMenuTitle);
				if (subMenu != null)
					subMenu.add(info);
			}
		}
	}
	
	/**
	 * 返回角色 role 可以访问的菜单
	 * @param roleName
	 * @return
	 */
	public Map<String, List<String[]>> getMenuByRole(String roleName) {
		return roleMenusMap.get(roleName);
	}

}
