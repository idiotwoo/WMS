package com.ken.wms.security.Service.Impl;


import com.ken.wms.security.Service.Interface.AccountService;
import com.ken.wms.security.Service.Interface.UserInfoService;
import com.ken.wms.security.Service.execption.AccountServiceException;
import com.ken.wms.domain.UserInfoDTO;
import com.ken.wms.security.util.EncryptingModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 账户Service
 *
 * @author Ken
 * @since 2017-3-1
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static Logger log = Logger.getLogger("application");

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private EncryptingModel encryptingModel;


    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String REPEAT_PASSWORD = "rePassword";

	/**
	 * 密码更改
	 */
	@Override
	public void passwordModify(Integer userID, Map<String, Object> passwordInfo) {

	    if (passwordInfo == null)
            throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);

		// 获取更改密码信息
        String rePassword = (String) passwordInfo.get(REPEAT_PASSWORD);
        String newPassword = (String) passwordInfo.get(NEW_PASSWORD);
        String oldPassword = (String) passwordInfo.get(OLD_PASSWORD);
        if (rePassword == null || newPassword == null || oldPassword == null)
            throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);

		// 获取用户的账户信息
		UserInfoDTO user = userInfoService.getUserInfo(userID);
        if (user == null) {
            throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);
        }

        // 新密码一致性验证
        if (!newPassword.equals(rePassword)) {
            throw new AccountServiceException(false, AccountServiceException.PASSWORD_UNMATCH);
        }

        // 原密码正确性验证
        String password = user.getPassword();
        try {
			password = encryptingModel.MD5(oldPassword + userID);
			if (!password.equals(user.getPassword()))
				throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);
			
			// 获得新的密码并加密
			password = encryptingModel.MD5(newPassword + userID);
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			log.debug("password encryptionModel fail");
			throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);
		}

		// 验证成功后
		// 更新数据库
		user.setPassword(password);
		userInfoService.updateUserInfo(user);
		log.debug("password modify successfully");
	}

}
