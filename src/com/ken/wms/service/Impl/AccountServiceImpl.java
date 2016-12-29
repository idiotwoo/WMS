package com.ken.wms.service.Impl;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ken.wms.controller.entity.PasswordModification;
import com.ken.wms.dao.UserMapper;
import com.ken.wms.domain.User;
import com.ken.wms.service.Interface.AccountService;
import com.ken.wms.service.execption.AccountServiceException;
import com.ken.wms.service.execption.NullException;
import com.ken.wms.service.util.EncryptingModel;

/**
 * 
 * @author Ken
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static Logger log = Logger.getLogger("application");

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private EncryptingModel encryptingModel;

	/**
	 * 
	 * @param user
	 * @param checkCode
	 */
	@Override
	public void signIn(User user, String checkCode) {
		log.debug("account validate start");

		if (user == null)
			throw new NullException();

		// 获取数据库中正确的用户信息
		User validUser = userMapper.selectById(user.getId());

		// 验证密码是否一致
		if (validUser == null)
			throw new AccountServiceException(false, AccountServiceException.USERID_ERROR);
		try {
			String passwordRaw = validUser.getPassword();
			checkCode = checkCode.toUpperCase();

			// 用数据库中获取的密码与验证码进行 MD5
			String passwordCheck = encryptingModel.MD5(passwordRaw + checkCode);

			if (!passwordCheck.equals(user.getPassword()))// 验证一致性
				throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);

		} catch (NoSuchAlgorithmException | NullPointerException e) {
			log.error("encrying fail");
			throw new AccountServiceException(false, AccountServiceException.ENCRYING_ERROR);
		}
		
		// 填充其他字段到user供以后使用
		user.setUserName(validUser.getUserName());

		log.debug("account validate end successfully");
	}

	@Override
	public void signOut(Integer userID) {
		// TODO Auto-generated method stub

	}

	/**
	 * 密码更改
	 */
	@Override
	public void passwordModify(Integer userID, PasswordModification passwordInfo) {

		// 获取用户的账户信息
		User user = userMapper.selectById(userID);
		if (passwordInfo == null || user == null)
			throw new NullException();

		// 新密码一致性验证
		if (!passwordInfo.getNewPassword().equals(passwordInfo.getRePassword()))
			throw new AccountServiceException(false, AccountServiceException.PASSWORD_UNMATCH);

		// 原密码正确性验证
		String password = user.getPassword();
		try {
			password = encryptingModel.MD5(passwordInfo.getOldPassword() + userID);
			if (!password.equals(user.getPassword()))
				throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);
			
			// 获得新的密码并加密
			password = encryptingModel.MD5(passwordInfo.getNewPassword() + userID);
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			log.debug("password encryptionModel fail");
			throw new AccountServiceException(false, AccountServiceException.PASSWORD_ERROR);
		}

		// 验证成功后
		// 更新数据库
		user.setPassword(password);
		userMapper.update(user);
		log.debug("password modify successfully");
	}

}
