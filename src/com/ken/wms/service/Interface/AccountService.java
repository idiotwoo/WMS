package com.ken.wms.service.Interface;

import com.ken.wms.controller.entity.PasswordModification;
import com.ken.wms.domain.User;

/**
 * 账号相关服务
 * @author Ken
 *
 */
public interface AccountService {

	/**
	 * 登陆
	 * @param user 用户信息，包括：用户ID，密码
	 * @param checkCode 验证码
	 */
	public void signIn(User user, String checkCode);
	
	/**
	 * 注销
	 * @param userID 用户ID
	 */
	public void signOut(Integer userID);
	
	/**
	 * 密码更改
	 * @param userID 用户ID
	 * @param passwordInfo 更改的密码信息
	 */
	public void passwordModify(Integer userID,PasswordModification passwordInfo);
}
