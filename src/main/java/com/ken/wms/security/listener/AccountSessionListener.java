package com.ken.wms.security.listener;

import com.ken.wms.dao.AccessRecordMapper;
import com.ken.wms.domain.AccessRecordDO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * 用户Session监听器
 * 当用户session注销时，记录用户账户登出的时间
 *
 * @author Ken
 * @since 2017/3/4.
 */
@Component
public class AccountSessionListener implements HttpSessionListener, ApplicationContextAware{

    @Autowired
    private AccessRecordMapper accessRecordMapper;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // 当session被创建时
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 获取用户的session
        HttpSession session = se.getSession();

        // 判断是否为已经登陆的用户
        // 判断依据是isAuthentication的值
        if (accessRecordMapper == null){
            System.out.println("session is null");
        }
        String isAuthenticate = (String) session.getAttribute("isAuthenticate");
        if (isAuthenticate != null && isAuthenticate.equals("true")){
            AccessRecordDO accessRecord = new AccessRecordDO();
            accessRecord.setUserID((Integer) session.getAttribute("userID"));
            accessRecord.setUserName((String) session.getAttribute("userName"));
            accessRecord.setAccessType("登出");
            accessRecord.setAccessTime(new Date());
            accessRecord.setAccessIP("-");

            accessRecordMapper.insertAccessRecord(accessRecord);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof WebApplicationContext){
            ((WebApplicationContext)applicationContext).getServletContext().addListener(this);
        }else{
            throw new RuntimeException();
        }
    }
}
