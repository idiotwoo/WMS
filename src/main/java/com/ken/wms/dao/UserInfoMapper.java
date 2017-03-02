package com.ken.wms.dao;

import com.ken.wms.domain.UserInfoDO;

import java.util.List;

/**
 * 用户账户信息 Mapper
 *
 * @author ken
 * @since 2017/2/26.
 */
public interface UserInfoMapper {

    /**
     * 选择指定 id 的 user 信息
     *
     * @param userID 用户ID
     * @return 返回指定 userID 对应的 user 信息
     */
    UserInfoDO selectByUserID(Integer userID);

    /**
     * 选择指定 userName 的 user 信息
     *
     * @param userName 用户名
     * @return 返回指定 userName 对应的 user 信息
     */
    UserInfoDO selectByName(String userName);

    /**
     * 选择全部的 user 信息
     *
     * @return 返回所有的 user 信息
     */
    List<UserInfoDO> selectAll();


    /**
     * 更新 user 对象信息
     *
     * @param user 更新 user 对象信息
     */
    void update(UserInfoDO user);

    /**
     * 删除指定 id 的user 信息
     *
     * @param id 用户ID
     */
    void deleteById(Integer id);

    /**
     * 插入一个 user 对象信息
     * 不需指定对象的主键id，数据库自动生成
     *
     * @param user 需要插入的用户信息
     */
    void insert(UserInfoDO user);

}
