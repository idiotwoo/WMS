package com.ken.wms.security.service.Impl;


import com.ken.wms.domain.UserInfoDTO;
import com.ken.wms.exception.UserAccountServiceException;
import com.ken.wms.exception.UserInfoServiceException;
import com.ken.wms.security.service.Interface.AccountService;
import com.ken.wms.security.service.Interface.UserInfoService;
import com.ken.wms.security.util.EncryptingModel;
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
    public void passwordModify(Integer userID, Map<String, Object> passwordInfo) throws UserAccountServiceException {

        if (passwordInfo == null)
            throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);

        // 获取更改密码信息
        String rePassword = (String) passwordInfo.get(REPEAT_PASSWORD);
        String newPassword = (String) passwordInfo.get(NEW_PASSWORD);
        String oldPassword = (String) passwordInfo.get(OLD_PASSWORD);
        if (rePassword == null || newPassword == null || oldPassword == null)
            throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);

        try {
            // 获取用户的账户信息
            UserInfoDTO user = userInfoService.getUserInfo(userID);
            if (user == null) {
                throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);
            }

            // 新密码一致性验证
            if (!newPassword.equals(rePassword)) {
                throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_UNMATCH);
            }

            // 原密码正确性验证
            String password;
            password = encryptingModel.MD5(oldPassword + userID);
            if (!password.equals(user.getPassword()))
                throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);

            // 获得新的密码并加密
            password = encryptingModel.MD5(newPassword + userID);

            // 验证成功后更新数据库
            user.setPassword(password);
            userInfoService.updateUserInfo(user);
        } catch (NoSuchAlgorithmException | NullPointerException | UserInfoServiceException e) {
            throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);
        }

    }

}
