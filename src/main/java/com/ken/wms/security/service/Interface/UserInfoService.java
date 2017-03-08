package com.ken.wms.security.service.Interface;

import com.ken.wms.domain.UserInfoDTO;
import com.ken.wms.exception.UserInfoServiceException;

import java.util.List;
import java.util.Set;

/**
 * 用户账户信息 service
 * @author ken
 * @since 2017/2/26.
 */
public interface UserInfoService {

    /**
     * 获取指定用户ID对应的用户账户信息
     * @param userID 用户ID
     * @return 返回用户账户信息
     */
    UserInfoDTO getUserInfo(Integer userID) throws UserInfoServiceException;

    /**
     * 获取指定 userName 对应的用户账户信息
     * @param userName 用户名
     * @return 返回用户账户信息
     */
    UserInfoDTO getUserInfo(String userName) throws UserInfoServiceException;

    /**
     * 获取所有用户账户信息
     * @return 返回所有的用户账户信息
     */
    List<UserInfoDTO> getAllUserInfo() throws UserInfoServiceException;

    /**
     * 更新用户的账户信息
     * @param userInfoDTO 用户账户信息
     */
    void updateUserInfo(UserInfoDTO userInfoDTO) throws UserInfoServiceException;

    /**
     * 删除指定 userID 的用户账户信息
     * @param userID 指定的用户ID
     */
    void deleteUserInfo(Integer userID) throws UserInfoServiceException;

    /**
     * 添加一条用户账户信息
     * @param userInfoDTO 需要添加的用户账户信息
     */
    void insertUserInfo(UserInfoDTO userInfoDTO) throws UserInfoServiceException;

    /**
     * 获取用户的权限角色
     * @param userID 用户 ID
     * @return 返回一个保存有用户角色的 Set，若该用户没有任何角色，则返回一个不包含任何元素的 Set
     */
    Set<String> getUserRoles(Integer userID) throws UserInfoServiceException;
}
