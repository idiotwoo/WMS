package com.ken.wms.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ken.wms.dao.ActionPermissionMapper;
import com.ken.wms.dao.RoleMapper;
import com.ken.wms.domain.ActionPermission;
import com.ken.wms.domain.Role;

/**
 * 
 * @author Ken
 *
 */
@Component
public class SecurityService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ActionPermissionMapper actionPermissionMapper;
	
	// role 权限的映射信息
	private Map<String, List<ActionPermission>> permissionMap;
	// role url 前缀的映射信息
	private Map<String, String> urlPrefixMap;
	
	/**
	 * Security Service 初始化
	 */
	@PostConstruct
	public void init(){
		// 初始化 permissionMapper & urlPrefixMap
		permissionMap = new HashMap<>();
		urlPrefixMap = new HashMap<>();
		
		// 查询已经注册的 Role
		List<Role> roles = roleMapper.selectAll();
		if(roles == null)
			return;
		
		// 扫描各个 Role 的 permission 并设置到 permissionMapper 在中
		List<ActionPermission> permissions;
		for (Role role : roles) {
			permissions = actionPermissionMapper.selectByRole(role.getRoleName());
			permissionMap.put(role.getRoleName(), permissions);
			urlPrefixMap.put(role.getRoleName(), role.getRolePrefix());
		}
	}
	
	public SecurityService() {
		// TODO Auto-generated constructor stub
	}
	
//	public void testPermission(){
//		Set<String> keys = permissionMap.keySet();
//		for (String key : keys) {
//			List<ActionPermission> permissions = permissionMap.get(key);
//			System.out.println(key);
//			for (ActionPermission actionPermission : permissions) {
//				System.out.println(actionPermission);
//			}
//		}
//	}
//	public void testUrlPrefix(){
//		Set<String> keys = urlPrefixMap.keySet();
//		for (String key : keys) {
//			System.out.println(urlPrefixMap.get(key));
//		}
//	}
	
	/**
	 * 判断 roleName 对应的用户角色是否拥有 urlRequest 所代表的操作权限
	 * @param roleName 角色名
	 * @return
	 */
	public boolean isAuthorise(String roleName, String urlRequest){
		
		List<ActionPermission> permissions = permissionMap.get(roleName);
		if(permissions != null){
			for (ActionPermission actionPermission : permissions) {
				if(urlRequest.equals(actionPermission.getActionParam()))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断该 roleName 对应的用户是否与 urlPrefix 匹配
	 * @param roleName 角色名
	 * @param urlPrefix 请求的前缀
	 * @return
	 */
	public boolean isRole(String roleName, String urlPrefix){
		
		String param = urlPrefixMap.get(roleName);
		if(param != null && param.equals(urlPrefix))
			return true;
		
		return false;
	}
	
	/**
	 * 获得用户的角色，通过指定用户的 userId
	 * @param userID 用户ID
	 * @return
	 */
	public Role getRole(Integer userId){
		return roleMapper.selectByUserId(userId);
	}
}
