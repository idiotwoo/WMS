package com.ken.wms.service.Impl;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		if(user == null)
			throw new NullException();
		
		// 获取数据库中正确的用户信息
		User validUser = userMapper.selectById(user.getId());
		
		// 验证输入的用户名、密码、身份类型是否一致
		if(validUser == null)
			throw new AccountServiceException(false, AccountServiceException.USERID_ERROR);
//		if(!validUser.getUserType().equals(user.getUserType()))
//			throw new AccountServiceException(false, AccountServiceException.AUTHORIATION_ERROR);
		
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
		
		log.debug("account validate end successfully");
	}

}
