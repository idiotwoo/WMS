package com.ken.wms.security.Service.Impl;

import com.ken.wms.dao.UserInfoMapper;
import com.ken.wms.dao.UserPermissionMapper;
import com.ken.wms.domain.RoleDO;
import com.ken.wms.domain.UserInfoDO;
import com.ken.wms.domain.UserInfoDTO;
import com.ken.wms.security.Service.Interface.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户账户信息 Service 实现类
 *
 * @author ken
 * @since 2017/2/26.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    /**
     * 获取指定用户ID对应的用户账户信息
     *
     * @param userID 用户ID
     * @return 返回用户账户信息
     */
    @Override
    public UserInfoDTO getUserInfo(Integer userID) {
        if (userID == null)
            return null;

        // 获取用户信息
        UserInfoDO userInfoDO = userInfoMapper.selectByUserID(userID);
        // 获取用户角色信息
        List<RoleDO> roles = userPermissionMapper.selectUserRole(userID);

        return assembleUserInfo(userInfoDO, roles);
    }

    /**
     * 获取指定 userName 对应的用户账户信息
     *
     * @param userName 用户名
     * @return 返回用户账户信息
     */
    @Override
    public UserInfoDTO getUserInfo(String userName) {
        if (userName == null)
            return null;

        // 获取用户信息
        UserInfoDO userInfoDO = userInfoMapper.selectByName(userName);
        // 获取用户角色信息
        if (userInfoDO != null) {
            List<RoleDO> roles = userPermissionMapper.selectUserRole(userInfoDO.getUserID());
            return assembleUserInfo(userInfoDO, roles);
        }else
            return null;
    }

    /**
     * 获取所有用户账户信息
     *
     * @return 返回所有的用户账户信息
     */
    @Override
    public List<UserInfoDTO> getAllUserInfo() {
        // 保存所有用户的 UserInfoDTO 对象
        List<UserInfoDTO> userInfoDTOS = null;

        // 获取所有用户的账户信息（不包含角色信息）
        List<UserInfoDO> userInfoDOS = userInfoMapper.selectAll();
        if (userInfoDOS != null){
            List<RoleDO> roles;
            UserInfoDTO userInfoDTO;
            userInfoDTOS = new ArrayList<>(userInfoDOS.size());
            for (UserInfoDO userInfoDO : userInfoDOS){
                // 获取用户对应的角色信息
                roles = userPermissionMapper.selectUserRole(userInfoDO.getUserID());
                userInfoDTO = assembleUserInfo(userInfoDO, roles);
                userInfoDTOS.add(userInfoDTO);
            }
        }

        return userInfoDTOS;
    }

    /**
     * 更新用户的账户信息
     *
     * @param userInfoDTO 用户账户信息
     */
    @Override
    public void updateUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoDTO != null){
            // 更新 UserDo 对象信息
            Integer userID = userInfoDTO.getUserID();
            String userName = userInfoDTO.getUserName();
            String password = userInfoDTO.getPassword();
            if (userID != null && userName != null && password != null){
                UserInfoDO userInfoDO = new UserInfoDO();
                userInfoDO.setUserID(userID);
                userInfoDO.setUserName(userName);
                userInfoDO.setPassword(password);
                userInfoMapper.update(userInfoDO);
            }

            // 更新角色信息
        }

    }

    /**
     * 删除指定 userID 的用户账户信息
     *
     * @param userID 指定的用户ID
     */
    @Override
    public void deleteUserInfo(Integer userID) {

    }

    /**
     * 添加一条用户账户信息
     *
     * @param userInfoDTO 需要添加的用户账户信息
     */
    @Override
    public void insertUserInfo(UserInfoDTO userInfoDTO) {

    }

    /**
     * 组装 UserInfoDTO 对象，包括用户账户信息和角色信息
     * @param userInfoDO 用户账户信息
     * @param roles 用户角色信息
     * @return 返回组装后的UserInfoDTO
     */
    private UserInfoDTO assembleUserInfo(UserInfoDO userInfoDO, List<RoleDO> roles){
        UserInfoDTO userInfoDTO = null;
        if (userInfoDO != null && roles != null){
            userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserID(userInfoDO.getUserID());
            userInfoDTO.setUserName(userInfoDO.getUserName());
            userInfoDTO.setPassword(userInfoDO.getPassword());

            for (RoleDO role : roles){
                userInfoDTO.getRole().add(role.getRoleName());
            }
        }
        return userInfoDTO;
    }

    /**
     * 获取用户的权限角色
     *
     * @param userID 用户 ID
     * @return 返回一个保存有用户角色的 Set，若该用户没有任何角色，则返回一个不包含任何元素的 Set
     */
    @Override
    public Set<String> getUserRoles(Integer userID) {
        // 获取用户信息
        UserInfoDTO userInfo = getUserInfo(userID);

        // 返回用户的角色
        if (userInfo != null){
            return new HashSet<>(userInfo.getRole());
        }else{
            return new HashSet<>();
        }
    }


}
