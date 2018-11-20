package com.redscarf.ibone.sys.admin.controller;

import com.redscarf.ibone.configuration.IboneConfiguration;
import com.redscarf.ibone.sys.api.dto.response.UserInfoResponseDTO;
import com.redscarf.ibone.sys.core.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IboneConfiguration iboneConfiguration;

    @Autowired
    private UserService userService;

    @RequestMapping("/welcome")
    public String welcome(ModelMap modelMap){
        setCurrentUser(modelMap,iboneConfiguration);
        return "index";
    }

    @RequestMapping("/dashboard")
    public String dashboard(ModelMap modelMap){
        return "pages/dashboard";
    }

    private void setCurrentUser(ModelMap modelMap, IboneConfiguration iboneConfiguration){
        UserInfoResponseDTO currentUser = (UserInfoResponseDTO) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        String username = currentUser.getUsername();
        String serverName = iboneConfiguration.getSys().getServerName();
        UserInfoResponseDTO userModel = userService.getUserDetailByNameAndServerName(username, serverName);
        modelMap.put("user", userModel);
    }
}
