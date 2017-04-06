package com.ken.wms.security.realms;

import com.ken.wms.common.service.Interface.RepositoryAdminManageService;
import com.ken.wms.dao.AccessRecordMapper;
import com.ken.wms.domain.AccessRecordDO;
import com.ken.wms.domain.RepositoryAdmin;
import com.ken.wms.domain.UserInfoDTO;
import com.ken.wms.exception.RepositoryAdminManageServiceException;
import com.ken.wms.exception.UserInfoServiceException;
import com.ken.wms.security.service.Interface.UserInfoService;
import com.ken.wms.security.util.EncryptingModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户的认证与授权
 *
 * @author ken
 * @since 2017/2/26.
 */
public class UserAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EncryptingModel encryptingModel;
    @Autowired
    private RepositoryAdminManageService repositoryAdminManageService;
    @Autowired
    private AccessRecordMapper accessRecordMapper;

    /**
     * 对用户进行角色授权
     *
     * @param principalCollection 用户信息
     * @return 返回用户授权信息
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建存放用户角色的 Set
        Set<String> roles = new HashSet<>();

        //获取用户角色
        Object principal = principalCollection.getPrimaryPrincipal();
        if (principal instanceof String) {
            String userID = (String) principal;
            if (StringUtils.isNumeric(userID)) {
                try {
                    UserInfoDTO userInfo = userInfoService.getUserInfo(Integer.valueOf(userID));
                    if (userInfo != null) {
                        // 设置用户角色
                        roles.addAll(userInfo.getRole());

                        // 设置用户信息到 Session 中
                        Subject currentUser = SecurityUtils.getSubject();
                        Session session = currentUser.getSession();
                        List<RepositoryAdmin> repositoryAdmin = (List<RepositoryAdmin>) repositoryAdminManageService.selectByID(userInfo.getUserID()).get("data");
                        session.setAttribute("repositoryBelong", (repositoryAdmin.isEmpty()) ? "none" : repositoryAdmin.get(0).getRepositoryBelongID());

                        // 记录用户登陆状态，用于作登出日志
                        session.setAttribute("isAuthenticate", "true");
                        AccessRecordDO accessRecord = new AccessRecordDO();
                        accessRecord.setUserID((Integer) session.getAttribute("userID"));
                        accessRecord.setUserName((String) session.getAttribute("userName"));
                        accessRecord.setAccessType("登入");
                        accessRecord.setAccessTime(new Date());
                        accessRecord.setAccessIP(session.getHost());

                        accessRecordMapper.insertAccessRecord(accessRecord);
                    }
                } catch (UserInfoServiceException | RepositoryAdminManageServiceException | PersistenceException e) {
                    // do logger
                }
            }
        }

        return new SimpleAuthorizationInfo(roles);
    }

    /**
     * 对用户进行认证
     *
     * @param authenticationToken 用户凭证
     * @return 返回用户的认证信息
     * @throws AuthenticationException 用户认证异常信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String realmName = getName();
        String credentials = "";

        // 获取用户名对应的用户账户信息
        try {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            String principal = usernamePasswordToken.getUsername();

            if (!StringUtils.isNumeric(principal))
                throw new AuthenticationException();
            Integer userID = Integer.valueOf(principal);

            UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userID);

            if (userInfoDTO != null) {
                Subject currentSubject = SecurityUtils.getSubject();
                Session session = currentSubject.getSession();

                // 设置部分用户信息到 Session
                session.setAttribute("userID", userID);
                session.setAttribute("userName", userInfoDTO.getUserName());

                // 结合验证码对密码进行处理
                String checkCode = (String) session.getAttribute("checkCode");
                String password = userInfoDTO.getPassword();
                if (checkCode != null && password != null) {
                    checkCode = checkCode.toUpperCase();
                    credentials = encryptingModel.MD5(password + checkCode);
                }
            }
            return new SimpleAuthenticationInfo(principal, credentials, realmName);

        } catch (UserInfoServiceException | NoSuchAlgorithmException e) {
            throw new AuthenticationException();
        }
    }
}
