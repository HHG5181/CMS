package com.roots.cms.controller;

import com.roots.cms.service.IConfigService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author admin
 * @ClassName ErrorController.java
 * @Description TODO
 * @createTime 2020年08月06日 16:49:00
 */
@Controller
@RequestMapping("/error")
@AllArgsConstructor
public class ErrorController {

    private final IConfigService configService;

    @RequestMapping("/403")
    public String noPerms(Model model) {
        getConfig(model);
        return "/error/403.html";
    }

    @GetMapping("/404")
    public String noFind(HttpServletResponse response, Model model) {
        getConfig(model);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return "/error/404.html";
    }

    @RequestMapping("/500")
    public String sysError(Model model) {
        getConfig(model);
        return "/error/500.html";
    }

    private void getConfig(Model model) {
        Map<String, String> map = configService.selectAll();
        model.addAttribute("config", map);
    }
}
