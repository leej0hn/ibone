package com.redscarf.ibone.sys.admin.controller;

import com.redscarf.ibone.configuration.IboneConfiguration;
import com.redscarf.ibone.shiro.utils.UserUtils;
import com.redscarf.ibone.sys.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    IboneConfiguration iboneConfiguration;
    @Autowired
    private UserService userService;
    /**
     * 管理登录
     */
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        PrincipalCollection principals = UserUtils.getPrincipals();

        // 如果已经登录，则跳转到管理首页
        if (principals != null) {
            return "redirect:" + iboneConfiguration.getCas().getSuccessUrl();
        }
        return "/login";
    }

    @PostMapping("/login")
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model){
        PrincipalCollection principals = UserUtils.getPrincipals();

        // 如果已经登录，则跳转到管理首页
        if (principals != null) {
            return "redirect:" + iboneConfiguration.getCas().getSuccessUrl();
        }

        return "/login";
    }

}
