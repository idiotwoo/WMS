package com.ken.wms.security.Service.Interface;

import java.util.Map;

/**
 * 账号相关服务
 * @author Ken
 *
 */
public interface AccountService {

	/**
	 * 密码更改
	 * @param userID 用户ID
	 * @param passwordInfo 更改的密码信息
	 */
	public void passwordModify(Integer userID, Map<String, Object> passwordInfo);
}
