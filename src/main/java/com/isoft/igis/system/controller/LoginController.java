package com.isoft.igis.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoft.igis.common.annotation.Log;
import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.controller.BaseController;
import com.isoft.igis.common.domain.FileDO;
import com.isoft.igis.common.domain.Tree;
import com.isoft.igis.common.service.FileService;
import com.isoft.igis.common.utils.MD5Utils;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.common.utils.ShiroUtils;
import com.isoft.igis.system.domain.MenuDO;
import com.isoft.igis.system.service.MenuService;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    IGISParkConfig igisConfig;

    @GetMapping({"/", ""})
    String welcome(Model model) {
        return "redirect:/index.html";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", igisConfig.getUsername());
        model.addAttribute("password", igisConfig.getPassword());
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "forward:/login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

}
