package com.ken.wms.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面重定向
 *
 * @author ken
 * @since 2017/2/27.
 */
@RequestMapping("/")
@Controller
public class PageForwardHandler {

    /**
     * 内部重定向到登陆页面
     * @return 跳转的 jsp
     */
    @RequestMapping("login")
    public String loginPageForward(){
        return "login";
    }

    /**
     * 内部重定向到主页面
     * @return 跳转的 jsp
     */
    @RequestMapping("")
    public String showLoginView() {
        return "mainPage";
    }
}
