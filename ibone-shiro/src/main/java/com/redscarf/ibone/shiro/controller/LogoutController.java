package com.redscarf.ibone.shiro.controller;

import com.redscarf.ibone.configuration.IboneConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LogoutController {
    @Autowired
    IboneConfiguration iboneConfiguration;

    @GetMapping("/logout")
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model){
        SecurityUtils.getSubject().logout();
        return "redirect:" + iboneConfiguration.getCas().getLoginUrl();
    }

}
