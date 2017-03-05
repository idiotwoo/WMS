package com.ken.wms.dao;

import com.ken.wms.domain.AccessRecordDO;

/**
 * 用户登入登出记录因映射器
 *
 * @author Ken
 * @since 2017/3/5.
 */
public interface AccessRecordMapper {

    /**
     * 插入一条用户用户登入登出记录
     * @param accessRecord 用户登入登出记录
     */
    void insertAccessRecord(AccessRecordDO accessRecord);

}
