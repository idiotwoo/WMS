package com.ken.wms.junitTest;

import com.ken.wms.dao.RolesMapper;
import com.ken.wms.dao.UserPermissionMapper;
import com.ken.wms.domain.RoleDO;
import com.ken.wms.domain.RolePermissionDO;
import com.ken.wms.security.Service.Interface.UserInfoService;
import com.ken.wms.dao.RolePermissionMapper;
import com.ken.wms.dao.UserInfoMapper;
import com.ken.wms.domain.UserInfoDO;
import com.ken.wms.domain.UserInfoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = "classpath:config/SpringApplicationConfiguration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {


    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Test
    public void testRolesMapper(){
        List<RoleDO> roleDOS = userPermissionMapper.selectUserRole(1001);
        for (RoleDO roleDO : roleDOS){
            System.out.println(roleDO);
        }
    }

//    @Test
//    public void userMapperTest(){
//        List<UserInfoDO> userInfoDOS = userInfoMapper.selectByUserID(1001);
//        for (UserInfoDO userInfoDO : userInfoDOS){
//            System.out.println(userInfoDO);
//        }
//    }

    @Test
    public void rolePermissionMapperTest(){
        List<RolePermissionDO> rolePermissionDOS = rolePermissionMapper.selectAll();
        System.out.println("size of the list:" + rolePermissionDOS.size());
        for (RolePermissionDO rolePermissionDO : rolePermissionDOS){
            System.out.println(rolePermissionDO);
        }
    }

    @Test
    public void getUserInfo1(){
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(1001);
        System.out.println(userInfoDTO);
    }
}
